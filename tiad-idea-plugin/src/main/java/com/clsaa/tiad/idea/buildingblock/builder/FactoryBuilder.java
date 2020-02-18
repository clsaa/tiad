package com.clsaa.tiad.idea.buildingblock.builder;

import com.clsaa.tiad.buidlingblock.entity.buildingblock.BuildingBlock;
import com.clsaa.tiad.buidlingblock.entity.buildingblock.Factory;
import com.clsaa.tiad.common.data.DataContext;

import java.lang.annotation.Annotation;

public class FactoryBuilder extends AbstractAnnotationBuilder {
    @Override
    Class<? extends Annotation> getAnnotationClass() {
        return com.clsaa.tiad.buidlingblock.annotation.Factory.class;
    }

    @Override
    BuildingBlock doBuild(DataContext dataContext) {
        return new Factory(dataContext);
    }
}
