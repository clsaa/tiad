package com.clsaa.tiad.idea.buildingblock.processor;

import com.clsaa.tiad.buidlingblock.entity.buildingblock.BuildingBlock;
import com.clsaa.tiad.idea.buildingblock.context.BuilderChain;
import com.clsaa.tiad.idea.buildingblock.context.BuilderContext;
import com.intellij.psi.PsiFile;

import java.util.List;

/**
 * @author clsaa
 */
public class PsiFileProcessor {

    public List<BuildingBlock> process(PsiFile psiFile) {
        BuilderContext context = new BuilderContext(psiFile);
        BuilderChain chain = BuilderChain.getInstance();
        chain.startBuild(context, chain);
        List<BuildingBlock> result = context.getResult();
        return result;
    }
}
