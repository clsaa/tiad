package com.clsaa.tiad.buidlingblock.entity.descriptor;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.util.List;

/**
 * @author clsaa
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
