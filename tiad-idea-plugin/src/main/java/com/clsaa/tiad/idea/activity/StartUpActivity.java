package com.clsaa.tiad.idea.activity;

import com.clsaa.tiad.idea.executors.TiadReadExecutor;
import com.clsaa.tiad.idea.service.BuildingBlockStructureService;
import com.clsaa.tiad.idea.service.ProjectDescriptorService;
import com.intellij.openapi.application.ReadAction;
import com.intellij.openapi.fileTypes.StdFileTypes;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.startup.StartupActivity;
import com.intellij.openapi.vcs.ProjectLevelVcsManager;
import com.intellij.openapi.vcs.impl.ProjectLevelVcsManagerImpl;
import com.intellij.openapi.vfs.*;
import com.intellij.openapi.vfs.newvfs.impl.VirtualDirectoryImpl;
import com.intellij.openapi.vfs.newvfs.impl.VirtualFileImpl;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;

@Slf4j
public class StartUpActivity implements StartupActivity {

    private BuildingBlockStructureService blockStructureService;

    @Override
    public void runActivity(@NotNull Project project) {
        log.info("taid start running...");
        this.blockStructureService = BuildingBlockStructureService.getInstance(project);
        //init BuildingBlockStructure
        ReadAction.nonBlocking(blockStructureService::refresh).inSmartMode(project).submit(TiadReadExecutor.getInstance());

        final VirtualFileSystem virtualFileSystem = VirtualFileManager.getInstance().getFileSystem("file");
        virtualFileSystem.addVirtualFileListener(new VirtualFileListener() {
            @Override
            public void beforeFileDeletion(@NotNull VirtualFileEvent event) {
                updateWhenMoveAndDelete(event.getFile());
            }

            @Override
            public void beforeFileMovement(@NotNull VirtualFileMoveEvent event) {
                updateWhenMoveAndDelete(event.getFile());
            }
        });

        final ProjectDescriptorService projectDescriptorService = ProjectDescriptorService.getInstance(project);
        projectDescriptorService.refresh();
        ProjectLevelVcsManagerImpl vcsManager = (ProjectLevelVcsManagerImpl) ProjectLevelVcsManager.getInstance(project);
        vcsManager.addVcsListener(projectDescriptorService::refresh);
    }

    public void updateWhenMoveAndDelete(VirtualFile file) {
        if (file instanceof VirtualFileImpl && file.getFileType().equals(StdFileTypes.JAVA)) {
            final VirtualFileImpl virtualFile = (VirtualFileImpl) file;
            blockStructureService.remove(String.valueOf(virtualFile.getId()));
        }
        if (file instanceof VirtualDirectoryImpl) {
            final VirtualDirectoryImpl virtualFile = (VirtualDirectoryImpl) file;
            VfsUtilCore.visitChildrenRecursively(virtualFile, new VirtualFileVisitor<Object>() {
                @Override
                public boolean visitFile(@NotNull VirtualFile file) {
                    if (file instanceof VirtualFileImpl && file.getFileType().equals(StdFileTypes.JAVA)) {
                        final VirtualFileImpl virtualFile1 = (VirtualFileImpl) file;
                        blockStructureService.remove(String.valueOf(virtualFile1.getId()));
                    }
                    return true;
                }
            });
        }
    }
}
