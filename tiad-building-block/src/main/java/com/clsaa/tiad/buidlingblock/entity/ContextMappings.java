package com.clsaa.tiad.buidlingblock.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author clsaa
 */
@Getter
@Setter
public class ContextMappings extends AbstractBuildingBlock {
    List<ContextMapping> contextMappings;
}
