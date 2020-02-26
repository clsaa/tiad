package com.clsaa.tiad.pmd.lang.java.rule.entity;

import com.clsaa.tiad.buidlingblock.annotation.Entity;
import com.clsaa.tiad.pmd.lang.java.constances.PackageNames;
import com.clsaa.tiad.pmd.lang.java.rule.abstractrule.name.AbstractPackageNameRule;
import lombok.extern.slf4j.Slf4j;

import java.lang.annotation.Annotation;

/**
 * @author clsaa
 */
@Slf4j
public class EntityPackageNameRule extends AbstractPackageNameRule {

    @Override
    public String getPackageSuffix() {
        return PackageNames.ENTITY;
    }

    @Override
    public Class<? extends Annotation> getTargetAnnotation() {
        return Entity.class;
    }
}