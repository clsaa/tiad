package com.clsaa.tiad.buidlingblock.entity.descriptor;

import com.clsaa.tiad.buidlingblock.constance.DefaultStrings;
import lombok.Data;

import java.io.Serializable;

/**
 * @author clsaa
 */
@Data
public class Location implements Serializable {
    protected String packageName;
    protected String className;
    protected String fieldName;
    protected String methodName;

    public String generateIdentifier() {
        StringBuilder builder = new StringBuilder(64);
        if (this.getPackageName() != null) {
            builder.append(this.getPackageName());
        }
        if (this.getClassName() != null) {
            builder.append(DefaultStrings.DOT);
            builder.append(this.getClassName());
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
