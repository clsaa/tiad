package com.clsaa.tiad.pmd.lang.java.rule.repository;

import com.clsaa.tiad.buidlingblock.annotation.Repository;
import com.clsaa.tiad.buidlingblock.entity.buildingblock.Aggregate;
import com.clsaa.tiad.buidlingblock.entity.buildingblock.BuildingBlock;
import com.clsaa.tiad.pmd.lang.java.rule.AbstractOneToOneRule;
import lombok.extern.slf4j.Slf4j;

import java.lang.annotation.Annotation;


/**
 * @author clsaa
 */
@Slf4j
public class RepositoryOneAggregateRule extends AbstractOneToOneRule {

    @Override
    public Class<? extends BuildingBlock> getSubBuildingBlockClass() {
        return com.clsaa.tiad.buidlingblock.entity.buildingblock.Repository.class;
    }

    @Override
    public Class<? extends BuildingBlock> getParentBuildingBlockClass() {
        return Aggregate.class;
    }

    @Override
    public Class<? extends Annotation> getTargetAnnotation() {
        return Repository.class;
    }
}