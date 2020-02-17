package com.clsaa.tiad.idea.buildingblock.builder;

import com.clsaa.tiad.buidlingblock.entity.buildingblock.BuildingBlock;
import com.intellij.psi.PsiFile;

/**
 * @author clsaa
 */
public class EntityBuilder implements Builder {

    @Override
    public BuildingBlock doBuild(BuilderContext context, BuilderChain chain) {
        PsiFile psiFile = context.getPsiFile();

        return null;
    }
}
