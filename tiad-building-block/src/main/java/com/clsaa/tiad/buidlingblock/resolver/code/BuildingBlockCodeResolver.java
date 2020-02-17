package com.clsaa.tiad.buidlingblock.resolver.code;

import com.clsaa.tiad.buidlingblock.entity.buildingblock.BuildingBlock;

public interface BuildingBlockCodeResolver {

    /**
     * interface of return code for different building block
     *
     * @param buildingBlock {@link BuildingBlock}
     * @return code of building block
     */
    String resolver(BuildingBlock buildingBlock);
}
