package com.clsaa.tiad.buidlingblock.entity.descriptor;

import com.clsaa.tiad.buidlingblock.constance.DefaultStrings;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * @author clsaa
 */
@Data
@Builder
public class Location implements Serializable {
    protected String packageName;
    protected String simpleClassName;
    protected String fullClassName;
    protected String fieldName;
    protected String methodName;

    public String generateIdentifier() {
        StringBuilder builder = new StringBuilder(64);
        if (this.getPackageName() != null) {
            builder.append(this.getPackageName());
        }
        if (this.getSimpleClassName() != null) {
            builder.append(DefaultStrings.DOT);
            builder.append(this.getSimpleClassName());
        }
        if (this.getFieldName() != null) {
            builder.append(DefaultStrings.SEPARATOR);
            builder.append(this.getFieldName());
        }
        if (this.getMethodName() != null) {
            builder.append(DefaultStrings.SEPARATOR);
            builder.append(this.getMethodName());
        }
        return builder.toString();
    }
}
