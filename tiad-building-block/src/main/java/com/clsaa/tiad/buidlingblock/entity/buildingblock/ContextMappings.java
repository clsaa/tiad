package com.clsaa.tiad.buidlingblock.entity.buildingblock;

import com.clsaa.tiad.buidlingblock.entity.param.BuildingBlockDataKeys;
import com.clsaa.tiad.common.data.DataContext;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.util.List;

/**
 * @author clsaa
 */
@Getter
@Setter
public class ContextMappings extends BuildingBlock {
    private List<ContextMapping> contextMappings;

    /**
     * @param dataContext params to build building block
     * @see BuildingBlockDataKeys.RequiredToBuild
     */
    public ContextMappings(@NonNull DataContext dataContext, List<ContextMapping> contextMappings) {
        super(dataContext);
        this.contextMappings = contextMappings;
    }
}
