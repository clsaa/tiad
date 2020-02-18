package com.clsaa.tiad.idea.buildingblock.builder;

import com.clsaa.tiad.buidlingblock.entity.buildingblock.BuildingBlock;
import com.clsaa.tiad.buidlingblock.entity.buildingblock.Repository;
import com.clsaa.tiad.common.data.DataContext;

import java.lang.annotation.Annotation;

public class RepositoryBuilder extends AbstractAnnotationBuilder {
    @Override
    Class<? extends Annotation> getAnnotationClass() {
        return com.clsaa.tiad.buidlingblock.annotation.Repository.class;
    }

    @Override
    BuildingBlock doBuild(DataContext dataContext) {
        return new Repository(dataContext);
    }
}
