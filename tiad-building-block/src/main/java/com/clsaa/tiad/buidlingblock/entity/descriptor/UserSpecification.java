package com.clsaa.tiad.buidlingblock.entity.descriptor;

import com.clsaa.tiad.common.data.DataHolder;
import com.clsaa.tiad.common.data.MapDataContext;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author clsaa
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserSpecification implements Serializable {
    protected String code;
    protected String name;
    protected String description;
    protected DataHolder attributes;

    public UserSpecification(String code, String name, String description) {
        this.code = code;
        this.name = name;
        this.description = description;
        this.attributes = new MapDataContext();
    }
}
