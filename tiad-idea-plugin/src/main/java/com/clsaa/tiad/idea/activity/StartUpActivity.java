package com.clsaa.tiad.idea.activity;

import com.clsaa.tiad.idea.executors.TiadReadExecutor;
import com.clsaa.tiad.idea.service.BuildingBlockStructureService;
import com.intellij.openapi.application.ReadAction;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.startup.StartupActivity;
import com.intellij.openapi.vfs.*;
import com.intellij.openapi.vfs.newvfs.impl.VirtualFileImpl;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;

@Slf4j
public class StartUpActivity implements StartupActivity {


    @Override
    public void runActivity(@NotNull Project project) {
        log.info("taid start running...");
        final BuildingBlockStructureService blockStructureService = BuildingBlockStructureService.getInstance(project);
        //init BuildingBlockStructure
        ReadAction.nonBlocking(blockStructureService::refresh).inSmartMode(project).submit(TiadReadExecutor.getInstance());

        final VirtualFileSystem virtualFileSystem = VirtualFileManager.getInstance().getFileSystem("file");
        virtualFileSystem.addVirtualFileListener(new VirtualFileListener() {
            @Override
            public void beforeFileDeletion(@NotNull VirtualFileEvent event) {
                ReadAction.nonBlocking(() -> {
                    final VirtualFileImpl virtualFile = (VirtualFileImpl) event.getFile();
                    blockStructureService.remove(String.valueOf(virtualFile.getId()));
                }).inSmartMode(project).submit(TiadReadExecutor.getInstance());
            }

            @Override
            public void beforeFileMovement(@NotNull VirtualFileMoveEvent event) {
                ReadAction.nonBlocking(() -> {
                    final VirtualFileImpl virtualFile = (VirtualFileImpl) event.getFile();
                    blockStructureService.remove(String.valueOf(virtualFile.getId()));
                }).inSmartMode(project).submit(TiadReadExecutor.getInstance());
            }
        });
    }

}
