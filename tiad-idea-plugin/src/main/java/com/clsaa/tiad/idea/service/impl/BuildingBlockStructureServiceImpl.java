package com.clsaa.tiad.idea.service.impl;

import com.clsaa.tiad.buidlingblock.entity.buildingblock.BuildingBlock;
import com.clsaa.tiad.buidlingblock.entity.structure.BuildingBlockStructure;
import com.clsaa.tiad.idea.buildingblock.processor.PsiFileProcessor;
import com.clsaa.tiad.idea.service.BuildingBlockStructureService;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.openapi.fileTypes.StdFileTypes;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ProjectFileIndex;
import com.intellij.openapi.vfs.newvfs.impl.VirtualFileImpl;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @author clsaa
 */
@Slf4j
public class BuildingBlockStructureServiceImpl implements BuildingBlockStructureService {
    private Project project;
    private BuildingBlockStructure buildingBlockStructure;
    private final Object refreshLock = new Object();

    public BuildingBlockStructureServiceImpl(Project project) {
        this.project = project;
    }

    @Override
    public void refresh() {
        log.info("building block structure refresh start");
        synchronized (this.refreshLock) {
            final BuildingBlockStructure buildingBlockStructure = new BuildingBlockStructure();
            ProjectFileIndex.SERVICE.getInstance(project).iterateContent(fileOrDir -> {
                PsiFile file = PsiManager.getInstance(project).findFile(fileOrDir);
                if (file != null) {
                    PsiFileProcessor psiFileProcessor = new PsiFileProcessor();
                    List<BuildingBlock> buildingBlocks = psiFileProcessor.process(file);
                    buildingBlockStructure.put(buildingBlocks);
                }
                return true;
            }, file -> {
                FileType fileType = file.getFileType();
                return fileType == StdFileTypes.JAVA;
            });
            this.buildingBlockStructure = buildingBlockStructure;
        }
        log.info("building block structure refresh finished");
    }


    @Override
    public void update(PsiFile psiFile) {
        this.tryRefresh();
        PsiFileProcessor psiFileProcessor = new PsiFileProcessor();
        List<BuildingBlock> buildingBlocks = psiFileProcessor.process(psiFile);
        final VirtualFileImpl virtualFile = (VirtualFileImpl) psiFile.getVirtualFile();
        if (buildingBlocks.isEmpty()) {
            this.buildingBlockStructure.removeByFileId(String.valueOf(virtualFile.getId()));
        } else {
            synchronized (psiFile) {
                this.buildingBlockStructure.removeByFileId(String.valueOf(virtualFile.getId()));
                this.buildingBlockStructure.put(buildingBlocks);
            }
        }
    }

    @Override
    public BuildingBlockStructure get() {
        this.tryRefresh();
        return this.buildingBlockStructure;
    }

    public void tryRefresh() {
        if (this.buildingBlockStructure == null) {
            synchronized (this.refreshLock) {
                if (this.buildingBlockStructure == null) {
                    this.refresh();
                }
            }
        }
    }
}
