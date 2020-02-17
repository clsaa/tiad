package com.clsaa.tiad.idea.buildingblock.builder;

import com.clsaa.tiad.buidlingblock.entity.buildingblock.BuildingBlock;

/**
 * @author clsaa
 */
public interface Builder {
    BuildingBlock doBuild(BuilderContext context, BuilderChain chain);

    default void build(BuilderContext context, BuilderChain chain) {
        BuildingBlock buildingBlock = this.doBuild(context, chain);
        if (buildingBlock != null) {
            context.addResult(buildingBlock);
        }
    }

    default Integer getOrder() {
        return Integer.MAX_VALUE;
    }
}
