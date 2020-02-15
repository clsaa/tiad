package com.clsaa.tiad.buidlingblock.entity.buildingblock;

import com.clsaa.tiad.buidlingblock.entity.descriptor.Location;
import com.clsaa.tiad.buidlingblock.entity.descriptor.UserSpecification;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author clsaa
 */
@Getter
@Setter
public abstract class AbstractBuildingBlock implements Serializable {
    protected String id;
    protected UserSpecification userSpecification;
    protected Location location;
}
