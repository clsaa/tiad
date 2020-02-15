package com.clsaa.tiad.buidlingblock.entity.descriptor;

import lombok.Data;

import java.io.Serializable;

/**
 * @author clsaa
 */
@Data
public class UserSpecification implements Serializable {
    protected String code;
    protected String name;
    protected String description;
}
