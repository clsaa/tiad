package com.clsaa.tiad.buidlingblock.entity.buildingblock;

import com.clsaa.tiad.buidlingblock.entity.param.BuildingBlockDataKeys;
import com.clsaa.tiad.buidlingblock.enums.ContextMappingRole;
import com.clsaa.tiad.buidlingblock.enums.ContextMappingType;
import com.clsaa.tiad.common.data.DataContext;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

/**
 * @author clsaa
 */
@Getter
@Setter
public class ContextMapping extends BuildingBlock {
    private ContextMappingType contextMappingType;
    private ContextMappingRole contextMappingRole;

    /**
     * @param dataContext params to build building block
     * @see BuildingBlockDataKeys.RequiredToBuild
     */
    public ContextMapping(@NonNull DataContext dataContext) {
        super(dataContext);
        this.contextMappingRole = dataContext.getData(BuildingBlockDataKeys.CONTEXT_MAPPING_ROLE_DATA_KEY);
        this.contextMappingType = dataContext.getData(BuildingBlockDataKeys.CONTEXT_MAPPING_TYPE_DATA_KEY);
    }
}
