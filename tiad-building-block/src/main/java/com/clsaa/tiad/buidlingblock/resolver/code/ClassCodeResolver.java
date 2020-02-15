package com.clsaa.tiad.buidlingblock.resolver.code;

import com.clsaa.tiad.buidlingblock.entity.buildingblock.AbstractBuildingBlock;

/**
 * class as code
 *
 * @author clsaa
 */
public class ClassCodeResolver implements BuildingBlockCodeResolver {
    @Override
    public String resolver(AbstractBuildingBlock buildingBlock) {
        return buildingBlock.getLocation().getClassName();
    }
}
