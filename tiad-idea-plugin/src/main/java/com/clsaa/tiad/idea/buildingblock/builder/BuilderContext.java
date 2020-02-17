package com.clsaa.tiad.idea.buildingblock.builder;

import com.clsaa.tiad.buidlingblock.entity.buildingblock.BuildingBlock;
import com.clsaa.tiad.common.data.DataHolder;
import com.clsaa.tiad.common.data.MapDataContext;
import com.intellij.psi.PsiFile;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author clsaa
 */
@Getter
public class BuilderContext {
    private PsiFile psiFile;
    private DataHolder dataHolder = new MapDataContext();
    private int index = 0;
    private BuilderChain builderChain = BuilderChain.getInstance();
    List<BuildingBlock> result = new ArrayList<>(32);

    public BuilderContext(PsiFile psiFile) {
        this.psiFile = psiFile;
    }

    public Builder nextBuilder() {
        Builder builder = builderChain.getBuilder(index);
        if (builder != null) {
            index++;
        }
        return builder;
    }

    public void addResult(BuildingBlock buildingBlock) {
        result.add(buildingBlock);
    }

    public List<BuildingBlock> getResult() {
        return result;
    }
}
