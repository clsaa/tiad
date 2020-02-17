package com.clsaa.tiad.buidlingblock.entity.buildingblock;

import com.clsaa.tiad.buidlingblock.entity.param.BuildingBlockDataKeys;
import com.clsaa.tiad.common.data.DataContext;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

/**
 * @author clsaa
 */
@Getter
@Setter
public class Repository extends BuildingBlock {
    /**
     * @param dataContext params to build building block
     * @see BuildingBlockDataKeys.RequiredToBuild
     */
    public Repository(@NonNull DataContext dataContext) {
        super(dataContext);
    }
}
