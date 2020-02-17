package com.clsaa.tiad.buidlingblock.entity.buildingblock;

import com.clsaa.tiad.buidlingblock.entity.param.BuildingBlockDataKeys;
import com.clsaa.tiad.buidlingblock.enums.ContextMappingRole;
import com.clsaa.tiad.buidlingblock.enums.ContextMappingType;
import com.clsaa.tiad.common.data.DataContext;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.NonFinal;

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
    public ContextMapping(@NonNull DataContext dataContext,
                          @NonFinal ContextMappingType contextMappingType,
                          @NonNull ContextMappingRole contextMappingRole) {
        super(dataContext);
        this.contextMappingType = contextMappingType;
        this.contextMappingRole = contextMappingRole;
    }
}
