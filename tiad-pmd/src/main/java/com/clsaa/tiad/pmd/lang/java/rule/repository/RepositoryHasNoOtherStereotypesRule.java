package com.clsaa.tiad.pmd.lang.java.rule.repository;

import com.clsaa.tiad.buidlingblock.annotation.Repository;
import com.clsaa.tiad.pmd.lang.java.rule.abstractrule.include.AbstractIncludeUniqueStereotypesRule;
import lombok.extern.slf4j.Slf4j;

import java.lang.annotation.Annotation;

/**
 * @author clsaa
 */
@Slf4j
public class RepositoryHasNoOtherStereotypesRule extends AbstractIncludeUniqueStereotypesRule {

    @Override
    public Class<? extends Annotation> getTargetAnnotation() {
        return Repository.class;
    }
}