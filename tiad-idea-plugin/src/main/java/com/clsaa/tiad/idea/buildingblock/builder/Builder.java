package com.clsaa.tiad.idea.buildingblock.builder;

import com.clsaa.tiad.buidlingblock.entity.buildingblock.BuildingBlock;
import com.clsaa.tiad.idea.buildingblock.context.BuilderChain;
import com.clsaa.tiad.idea.buildingblock.context.BuilderContext;

import java.util.List;

/**
 * @author clsaa
 */
public abstract class Builder {

    abstract List<BuildingBlock> doBuild(BuilderContext context, BuilderChain chain);


    public void build(BuilderContext context, BuilderChain chain) {
        List<BuildingBlock> buildingBlocks = this.doBuild(context, chain);
        if (buildingBlocks != null) {
            context.addResult(buildingBlocks);
        }
    }


    public Integer getOrder() {
        return Integer.MAX_VALUE;
    }
}
