package com.clsaa.tiad.pmd.lang.java.rule.aggregate;

import com.clsaa.tiad.buidlingblock.annotation.Aggregate;
import com.clsaa.tiad.pmd.lang.java.constances.PackageNames;
import com.clsaa.tiad.pmd.lang.java.rule.AbstractPackageNameRule;
import lombok.extern.slf4j.Slf4j;

import java.lang.annotation.Annotation;

/**
 * @author clsaa
 */
@Slf4j
public class AggregatePackageNameRule extends AbstractPackageNameRule {

    @Override
    public String getPackageSuffix() {
        return PackageNames.AGGREGATE_SUFFIX;
    }

    @Override
    public Class<? extends Annotation> getTargetAnnotation() {
        return Aggregate.class;
    }
}