package com.clsaa.tiad.pmd.lang.java.rule.factory;

import com.clsaa.tiad.buidlingblock.annotation.Factory;
import com.clsaa.tiad.pmd.lang.java.rule.abstractrule.scope.AbstractInBoundedContextRule;
import lombok.extern.slf4j.Slf4j;

import java.lang.annotation.Annotation;

/**
 * @author clsaa
 */
@Slf4j
public class FactoryShouldInBoundedContextRule extends AbstractInBoundedContextRule {

    @Override
    public Class<? extends Annotation> getTargetAnnotation() {
        return Factory.class;
    }
}