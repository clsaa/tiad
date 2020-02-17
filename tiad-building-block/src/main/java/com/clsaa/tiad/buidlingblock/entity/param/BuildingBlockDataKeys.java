package com.clsaa.tiad.buidlingblock.entity.param;

import com.clsaa.tiad.buidlingblock.entity.descriptor.Location;
import com.clsaa.tiad.buidlingblock.entity.descriptor.TiadSpecification;
import com.clsaa.tiad.buidlingblock.entity.descriptor.UserSpecification;
import com.clsaa.tiad.common.data.DataKey;

/**
 * @author clsaa
 */
public interface BuildingBlockDataKeys {
    DataKey<String> FILE_ID_DATA_KEY = DataKey.create("file_id");
    DataKey<Location> LOCATION_DATA_KEY = DataKey.create("location");
    DataKey<UserSpecification> USER_SPECIFICATION_DATA_KEY = DataKey.create("user_specification");
    DataKey<TiadSpecification> TIAD_SPECIFICATION_DATA_KEY = DataKey.create("tiad_specification");

    interface RequiredToBuild {
        DataKey<String> FILE_ID_DATA_KEY = BuildingBlockDataKeys.FILE_ID_DATA_KEY;
        DataKey<Location> LOCATION_DATA_KEY = BuildingBlockDataKeys.LOCATION_DATA_KEY;
        DataKey<UserSpecification> USER_SPECIFICATION_DATA_KEY = BuildingBlockDataKeys.USER_SPECIFICATION_DATA_KEY;
    }
}
