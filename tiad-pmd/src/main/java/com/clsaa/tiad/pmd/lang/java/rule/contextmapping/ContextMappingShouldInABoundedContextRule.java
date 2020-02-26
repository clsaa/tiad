package com.clsaa.tiad.pmd.lang.java.rule.contextmapping;

import com.clsaa.tiad.buidlingblock.annotation.ContextMapping;
import com.clsaa.tiad.pmd.lang.java.rule.AbstractShouldInBoundedContextRule;
import lombok.extern.slf4j.Slf4j;

import java.lang.annotation.Annotation;

/**
 * @author clsaa
 */
@Slf4j
public class ContextMappingShouldInABoundedContextRule extends AbstractShouldInBoundedContextRule {

    @Override
    public Class<? extends Annotation> getTargetAnnotation() {
        return ContextMapping.class;
    }
}