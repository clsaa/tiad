package com.clsaa.tiad.idea.buildingblock.builder;

import com.clsaa.tiad.buidlingblock.entity.buildingblock.BoundedContext;
import com.clsaa.tiad.buidlingblock.entity.buildingblock.BuildingBlock;
import com.clsaa.tiad.common.data.DataContext;

import java.lang.annotation.Annotation;

public class BoundedContextBuilder extends AbstractAnnotationBuilder {
    @Override
    Class<? extends Annotation> getAnnotationClass() {
        return com.clsaa.tiad.buidlingblock.annotation.BoundedContext.class;
    }

    @Override
    BuildingBlock doBuild(DataContext dataContext) {
        return new BoundedContext(dataContext);
    }
}
