package com.clsaa.tiad.buidlingblock.resolver.code;

import com.clsaa.tiad.buidlingblock.entity.buildingblock.BuildingBlock;

/**
 * package as code
 *
 * @author clsaa
 */
public class PackageCodeResolver implements BuildingBlockCodeResolver {
    @Override
    public String resolver(BuildingBlock buildingBlock) {
        return buildingBlock.getLocation().getPackageName();
    }
}
