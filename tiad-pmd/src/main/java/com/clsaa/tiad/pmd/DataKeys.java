package com.clsaa.tiad.pmd;

import com.clsaa.tiad.buidlingblock.entity.structure.BuildingBlockStructure;
import com.clsaa.tiad.buidlingblock.entity.structure.ProjectDescriptor;
import com.clsaa.tiad.common.data.DataKey;

/**
 * @author clsaa
 */
public interface DataKeys {
    DataKey<BuildingBlockStructure> BUILDING_BLOCK_STRUCTURE_DATA_KEY = DataKey.create("building_block_structure");
    DataKey<ProjectDescriptor> PROJECT_DESCRIPTOR_DATA_KEY = DataKey.create("project_descriptor");
}
