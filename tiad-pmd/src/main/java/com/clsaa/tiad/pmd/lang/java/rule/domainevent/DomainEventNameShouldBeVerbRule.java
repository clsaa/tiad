package com.clsaa.tiad.pmd.lang.java.rule.domainevent;

import com.clsaa.tiad.buidlingblock.annotation.DomainEvent;
import com.clsaa.tiad.pmd.lang.java.rule.abstractrule.name.AbstractClassNameRule;
import lombok.extern.slf4j.Slf4j;

import java.lang.annotation.Annotation;

/**
 * @author clsaa
 */
@Slf4j
public class DomainEventNameShouldBeVerbRule extends AbstractClassNameRule {

    @Override
    public Class<? extends Annotation> getTargetAnnotation() {
        return DomainEvent.class;
    }

    @Override
    public boolean validClassName(String simpleClassName) {
        return simpleClassName.endsWith("ed");
    }
}