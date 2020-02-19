package com.clsaa.tiad.idea.buildingblock.builder;

import com.clsaa.tiad.buidlingblock.entity.buildingblock.BuildingBlock;
import com.clsaa.tiad.buidlingblock.entity.buildingblock.ValueObject;
import com.clsaa.tiad.common.data.DataContext;

import java.lang.annotation.Annotation;

public class ValueObjectBuilder extends AbstractAnnotationBuilder {
    @Override
    Class<? extends Annotation> getAnnotationClass() {
        return com.clsaa.tiad.buidlingblock.annotation.ValueObject.class;
    }

    @Override
    BuildingBlock doBuild(DataContext dataContext) {
        return new ValueObject(dataContext);
    }
}
