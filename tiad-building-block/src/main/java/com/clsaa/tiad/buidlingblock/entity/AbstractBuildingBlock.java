package com.clsaa.tiad.buidlingblock.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * @author clsaa
 */
@Getter
@Setter
public abstract class AbstractBuildingBlock {
    protected String code;
    protected String name;
    protected String description;
}
