package com.clsaa.tiad.buidlingblock.annotation;

import com.clsaa.tiad.buidlingblock.enums.ContextMappingRole;
import com.clsaa.tiad.buidlingblock.enums.ContextMappingType;

import java.lang.annotation.*;

/**
 * context mapping of DDD
 *
 * @author clsaa
 */
@Documented
@Target(value = {ElementType.TYPE, ElementType.PACKAGE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(ContextMappings.class)
public @interface ContextMapping {

    /**
     * @return type of context mapping
     */
    ContextMappingType type();

    /**
     * @return role of current
     */
    ContextMappingRole role();

    /**
     * if use default code, it will be replaced by name of be annotated class/package
     *
     * @return unique code of context mapping
     */
    String code() default "";

    /**
     * @return name of context mapping
     */
    String name() default "";

    /**
     * @return description of context mapping
     */
    String description() default "";
}
