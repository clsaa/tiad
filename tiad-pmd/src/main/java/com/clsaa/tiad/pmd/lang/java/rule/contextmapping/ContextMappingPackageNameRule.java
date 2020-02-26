package com.clsaa.tiad.pmd.lang.java.rule.contextmapping;

import com.clsaa.tiad.buidlingblock.annotation.ContextMapping;
import com.clsaa.tiad.pmd.lang.java.constances.PackageNames;
import com.clsaa.tiad.pmd.lang.java.rule.abstractrule.name.AbstractPackageNameRule;
import lombok.extern.slf4j.Slf4j;

import java.lang.annotation.Annotation;

/**
 * @author clsaa
 */
@Slf4j
public class ContextMappingPackageNameRule extends AbstractPackageNameRule {

    @Override
    public String getPackageSuffix() {
        return PackageNames.CONTEXT_MAPPING;
    }

    @Override
    public Class<? extends Annotation> getTargetAnnotation() {
        return ContextMapping.class;
    }
}