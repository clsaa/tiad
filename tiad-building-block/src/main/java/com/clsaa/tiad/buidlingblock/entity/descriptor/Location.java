package com.clsaa.tiad.buidlingblock.entity.descriptor;

import lombok.Data;

import java.io.Serializable;

/**
 * @author clsaa
 */
@Data
public class Location implements Serializable {
    protected String packageName;
    protected String className;
    protected String methodName;
}
