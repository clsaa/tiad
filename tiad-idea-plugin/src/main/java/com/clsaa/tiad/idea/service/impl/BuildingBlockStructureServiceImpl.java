package com.clsaa.tiad.idea.service.impl;

import com.clsaa.tiad.buidlingblock.entity.structure.BuildingBlockStructure;
import com.clsaa.tiad.idea.service.BuildingBlockStructureService;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.openapi.fileTypes.StdFileTypes;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ProjectFileIndex;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;
import com.intellij.psi.util.PsiTreeUtil;

/**
 * @author clsaa
 */
public class BuildingBlockStructureServiceImpl implements BuildingBlockStructureService {
    private Project project;
    private BuildingBlockStructure buildingBlockStructure;

    public BuildingBlockStructureServiceImpl(Project project) {
        this.project = project;
    }

    @Override
    public void refresh() {
        BuildingBlockStructure buildingBlockStructure = new BuildingBlockStructure();
        ProjectFileIndex.SERVICE.getInstance(project).iterateContent(fileOrDir -> {
            System.out.println(fileOrDir.getName());
            PsiFile file = PsiManager.getInstance(project).findFile(fileOrDir);
            PsiClass childOfAnyType = PsiTreeUtil.findChildOfAnyType(file, PsiClass.class);
            return true;
        }, file -> {
            FileType fileType = file.getFileType();
            return fileType == StdFileTypes.JAVA;
        });
    }

    @Override
    public BuildingBlockStructure get() {
        return null;
    }
}
