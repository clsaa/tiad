package com.clsaa.tiad.pmd.lang.java.rule.domainevent;

import com.clsaa.tiad.buidlingblock.annotation.DomainEvent;
import com.clsaa.tiad.pmd.lang.java.rule.abstractrule.include.AbstractIncludeIdentifierRule;
import lombok.extern.slf4j.Slf4j;

import java.lang.annotation.Annotation;

/**
 * @author clsaa
 */
@Slf4j
public class DomainEventMustHaveOneIdentifierRule extends AbstractIncludeIdentifierRule {

    @Override
    public Class<? extends Annotation> getTargetAnnotation() {
        return DomainEvent.class;
    }
}