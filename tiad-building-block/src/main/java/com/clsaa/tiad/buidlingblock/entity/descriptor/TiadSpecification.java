package com.clsaa.tiad.buidlingblock.entity.descriptor;

import lombok.Data;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.util.List;

/**
 * @author clsaa
 */
@Data
public class TiadSpecification implements Serializable {
    /**
     * unique in files
     */
    private String fileId;
    /**
     * unique in language
     */
    private String instanceId;

    private List<Annotation> annotations;

}
