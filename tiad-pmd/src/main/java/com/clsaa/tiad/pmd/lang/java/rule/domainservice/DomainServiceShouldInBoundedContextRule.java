package com.clsaa.tiad.pmd.lang.java.rule.domainservice;

import com.clsaa.tiad.buidlingblock.annotation.DomainService;
import com.clsaa.tiad.pmd.lang.java.rule.abstractrule.scope.AbstractInBoundedContextRule;
import lombok.extern.slf4j.Slf4j;

import java.lang.annotation.Annotation;

/**
 * @author clsaa
 */
@Slf4j
public class DomainServiceShouldInBoundedContextRule extends AbstractInBoundedContextRule {

    @Override
    public Class<? extends Annotation> getTargetAnnotation() {
        return DomainService.class;
    }
}