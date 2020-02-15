package com.clsaa.tiad.buidlingblock.entity.buildingblock;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author clsaa
 */
@Getter
@Setter
public class ContextMappings extends AbstractBuildingBlock {
    private List<ContextMapping> contextMappings;
}
