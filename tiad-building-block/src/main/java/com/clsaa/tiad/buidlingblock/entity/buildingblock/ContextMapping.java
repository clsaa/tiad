package com.clsaa.tiad.buidlingblock.entity.buildingblock;

import com.clsaa.tiad.buidlingblock.enums.ContextMappingRole;
import com.clsaa.tiad.buidlingblock.enums.ContextMappingType;
import lombok.Getter;
import lombok.Setter;

/**
 * @author clsaa
 */
@Getter
@Setter
public class ContextMapping extends AbstractBuildingBlock {
    private ContextMappingType contextMappingType;
    private ContextMappingRole contextMappingRole;
}
