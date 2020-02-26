/*
 *    Copyright 2019 Clsaa Group
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.clsaa.tiad.pmd.lang.java.util;

import com.clsaa.tiad.buidlingblock.entity.buildingblock.BuildingBlock;
import com.clsaa.tiad.buidlingblock.entity.descriptor.Location;
import com.clsaa.tiad.buidlingblock.entity.structure.BuildingBlockStructure;
import com.clsaa.tiad.common.data.DataKey;
import com.clsaa.tiad.pmd.DataKeys;
import net.sourceforge.pmd.RuleContext;

import java.util.List;

public interface BuildBlockUtils {
    static BuildingBlockStructure getBuildBlockStructure(Object data) {
        final RuleContext ruleContext = (RuleContext) data;
        final DataKey<BuildingBlockStructure> dataKey = DataKeys.BUILDING_BLOCK_STRUCTURE_DATA_KEY;
        final Object attribute = ruleContext.getAttribute(dataKey.getName());
        if (attribute == null) {
            return null;
        }
        final BuildingBlockStructure buildingBlockStructure = dataKey.cast(attribute);
        return buildingBlockStructure;
    }

    static <T extends BuildingBlock> T findParent(BuildingBlockStructure structure, Class<T> parentClass, BuildingBlock subBuildingBlock) {
        final Location targetLocation = subBuildingBlock.getLocation();
        final String targetPackageName = targetLocation.getPackageName();
        return findParent(structure, parentClass, targetPackageName);
    }

    static <T extends BuildingBlock> T findParent(BuildingBlockStructure structure, Class<T> parentClass, String subPackage) {
        final List<T> buildingBlocks = structure.getByClass(parentClass);
        for (BuildingBlock block : buildingBlocks) {
            final Location location = block.getLocation();
            final String packageName = location.getPackageName();
            if (subPackage.startsWith(packageName)) {
                return parentClass.cast(block);
            }
        }
        return null;
    }
}
