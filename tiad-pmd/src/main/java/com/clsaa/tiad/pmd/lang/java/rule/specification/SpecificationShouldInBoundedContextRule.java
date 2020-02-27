package com.clsaa.tiad.pmd.lang.java.rule.specification;

import com.clsaa.tiad.buidlingblock.annotation.Specification;
import com.clsaa.tiad.pmd.lang.java.rule.abstractrule.scope.AbstractInBoundedContextRule;
import lombok.extern.slf4j.Slf4j;

import java.lang.annotation.Annotation;

/**
 * @author clsaa
 */
@Slf4j
public class SpecificationShouldInBoundedContextRule extends AbstractInBoundedContextRule {

    @Override
    public Class<? extends Annotation> getTargetAnnotation() {
        return Specification.class;
    }
}