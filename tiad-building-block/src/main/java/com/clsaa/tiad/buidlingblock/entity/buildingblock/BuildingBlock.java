package com.clsaa.tiad.buidlingblock.entity.buildingblock;

import com.clsaa.tiad.buidlingblock.constance.DefaultStrings;
import com.clsaa.tiad.buidlingblock.entity.descriptor.Location;
import com.clsaa.tiad.buidlingblock.entity.descriptor.TiadSpecification;
import com.clsaa.tiad.buidlingblock.entity.descriptor.UserSpecification;
import com.clsaa.tiad.buidlingblock.entity.param.BuildingBlockDataKeys;
import com.clsaa.tiad.common.data.DataContext;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.util.Collections;
import java.util.List;

/**
 * @author clsaa
 */
@Getter
@Setter
public abstract class BuildingBlock implements Serializable {
    protected String id;
    protected Integer index;
    protected TiadSpecification tiadSpecification;
    protected UserSpecification userSpecification;
    protected Location location;


    /**
     * @param dataContext params to build building block
     * @see BuildingBlockDataKeys.RequiredToBuild
     */
    public BuildingBlock(@NonNull DataContext dataContext) {
        String fileId = dataContext.getData(BuildingBlockDataKeys.RequiredToBuild.FILE_ID_DATA_KEY);
        this.location = dataContext.getData(BuildingBlockDataKeys.RequiredToBuild.LOCATION_DATA_KEY);
        this.userSpecification = dataContext.getData(BuildingBlockDataKeys.RequiredToBuild.USER_SPECIFICATION_DATA_KEY);
        String identifier = this.location.generateIdentifier();

        String code = this.userSpecification.getCode();
        if (code == null || code.trim().length() == 0) {
            this.userSpecification.setCode(identifier);
        }
        String name = this.userSpecification.getName();
        if (name == null || name.trim().length() == 0) {
            this.userSpecification.setName(identifier);
        }

        this.tiadSpecification = new TiadSpecification();
        tiadSpecification.setFileId(fileId);
        tiadSpecification.setInstanceId(identifier);
        tiadSpecification.setAnnotations(this.getMappedAnnotations());

        this.id = this.getClass().getName() + DefaultStrings.SEPARATOR + fileId + DefaultStrings.SEPARATOR + identifier;
    }

    public List<Annotation> getMappedAnnotations() {
        return Collections.emptyList();
    }
}
