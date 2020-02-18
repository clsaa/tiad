package com.clsaa.tiad.buidlingblock.entity.param;

import com.clsaa.tiad.buidlingblock.entity.buildingblock.ContextMapping;
import com.clsaa.tiad.buidlingblock.entity.descriptor.Location;
import com.clsaa.tiad.buidlingblock.entity.descriptor.TiadSpecification;
import com.clsaa.tiad.buidlingblock.entity.descriptor.UserSpecification;
import com.clsaa.tiad.buidlingblock.enums.ContextMappingRole;
import com.clsaa.tiad.buidlingblock.enums.ContextMappingType;
import com.clsaa.tiad.common.data.DataKey;

import java.util.List;

/**
 * @author clsaa
 */
public interface BuildingBlockDataKeys {
    DataKey<String> FILE_ID_DATA_KEY = DataKey.create("file_id");
    DataKey<Location> LOCATION_DATA_KEY = DataKey.create("location");
    DataKey<UserSpecification> USER_SPECIFICATION_DATA_KEY = DataKey.create("user_specification");
    DataKey<TiadSpecification> TIAD_SPECIFICATION_DATA_KEY = DataKey.create("tiad_specification");
    DataKey<ContextMappingRole> CONTEXT_MAPPING_ROLE_DATA_KEY = DataKey.create("context_mapping_role");
    DataKey<ContextMappingType> CONTEXT_MAPPING_TYPE_DATA_KEY = DataKey.create("context_mapping_type");
    DataKey<List<ContextMapping>> CONTEXT_MAPPINGS_DATA_KEY = DataKey.create("context_mappings");

    interface RequiredToBuild {
        DataKey<String> FILE_ID_DATA_KEY = BuildingBlockDataKeys.FILE_ID_DATA_KEY;
        DataKey<Location> LOCATION_DATA_KEY = BuildingBlockDataKeys.LOCATION_DATA_KEY;
        DataKey<UserSpecification> USER_SPECIFICATION_DATA_KEY = BuildingBlockDataKeys.USER_SPECIFICATION_DATA_KEY;
    }
}
