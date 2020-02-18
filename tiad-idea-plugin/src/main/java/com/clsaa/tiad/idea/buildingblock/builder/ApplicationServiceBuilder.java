package com.clsaa.tiad.idea.buildingblock.builder;

import com.clsaa.tiad.buidlingblock.entity.buildingblock.ApplicationService;
import com.clsaa.tiad.buidlingblock.entity.buildingblock.BuildingBlock;
import com.clsaa.tiad.common.data.DataContext;

import java.lang.annotation.Annotation;

public class ApplicationServiceBuilder extends AbstractAnnotationBuilder {
    @Override
    Class<? extends Annotation> getAnnotationClass() {
        return com.clsaa.tiad.buidlingblock.annotation.ApplicationService.class;
    }

    @Override
    BuildingBlock doBuild(DataContext dataContext) {
        return new ApplicationService(dataContext);
    }
}
