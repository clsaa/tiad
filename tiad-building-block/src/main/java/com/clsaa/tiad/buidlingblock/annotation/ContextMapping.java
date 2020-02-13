package com.clsaa.tiad.buidlingblock.annotation;

import com.clsaa.tiad.buidlingblock.constance.BuildingBlockDefaultCodes;
import com.clsaa.tiad.buidlingblock.constance.BuildingBlockDefaultNames;
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
     * @return type of subdomain
     */
    ContextMappingType[] type();

    /**
     * @return role of current
     */
    ContextMappingRole role();

    /**
     * if use default code, it will be replaced by class/package name of be annotated class/package
     *
     * @return unique code of subdomain
     */
    String code() default BuildingBlockDefaultCodes.SUBDOMAIN_CONTEXT_DEFAULT_CODE;

    /**
     * @return name of subdomain
     */
    String name() default BuildingBlockDefaultNames.SUBDOMAIN_CONTEXT_DEFAULT_NAME;

    /**
     * @return description of subdomain
     */
    String description() default "";
}
