package com.clsaa.tiad.buidlingblock.resolver.code;

import com.clsaa.tiad.buidlingblock.entity.buildingblock.AbstractBuildingBlock;

public interface BuildingBlockCodeResolver {

    /**
     * interface of return code for different building block
     *
     * @param buildingBlock {@link AbstractBuildingBlock}
     * @return code of building block
     */
    String resolver(AbstractBuildingBlock buildingBlock);
}
