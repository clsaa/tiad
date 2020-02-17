package com.clsaa.tiad.buidlingblock.resolver.code;

import com.clsaa.tiad.buidlingblock.entity.buildingblock.BuildingBlock;

/**
 * method as code
 *
 * @author clsaa
 */
public class MethodCodeResolver implements BuildingBlockCodeResolver {
    @Override
    public String resolver(BuildingBlock buildingBlock) {
        return buildingBlock.getLocation().getMethodName();
    }
}
