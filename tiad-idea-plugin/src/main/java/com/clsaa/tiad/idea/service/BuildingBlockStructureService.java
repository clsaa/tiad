package com.clsaa.tiad.idea.service;

import com.clsaa.tiad.buidlingblock.entity.structure.BuildingBlockStructure;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiFile;
import org.jetbrains.annotations.NotNull;

/**
 * @author clsaa
 */
public interface BuildingBlockStructureService {

    static BuildingBlockStructureService getInstance(@NotNull Project project) {
        return ServiceManager.getService(project, BuildingBlockStructureService.class);
    }

    void refresh();

    void update(PsiFile psiFile);

    void remove(String fileId);

    BuildingBlockStructure get();
}
