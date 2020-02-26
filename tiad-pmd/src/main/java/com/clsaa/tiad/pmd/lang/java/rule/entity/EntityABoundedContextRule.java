package com.clsaa.tiad.pmd.lang.java.rule.entity;

import com.clsaa.tiad.buidlingblock.annotation.Entity;
import com.clsaa.tiad.pmd.lang.java.rule.abstractrule.scope.AbstractBoundedContextRule;
import lombok.extern.slf4j.Slf4j;

import java.lang.annotation.Annotation;

/**
 * @author clsaa
 */
@Slf4j
public class EntityABoundedContextRule extends AbstractBoundedContextRule {

    @Override
    public Class<? extends Annotation> getTargetAnnotation() {
        return Entity.class;
    }
}