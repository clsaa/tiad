package com.clsaa.tiad.buidlingblock.annotation;

import com.clsaa.tiad.buidlingblock.constance.BuildingBlockDefaultCodes;
import com.clsaa.tiad.buidlingblock.constance.BuildingBlockDefaultNames;

import java.lang.annotation.*;

/**
 * entity of DDD
 *
 * @author clsaa
 */
@Documented
@Target(value = ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface Entity {
    /**
     * if use default code, it will be replaced by class name of be annotated class
     *
     * @return unique code of subdomain
     */
    String code() default BuildingBlockDefaultCodes.ENTITY_DEFAULT_CODE;

    /**
     * @return name of subdomain
     */
    String name() default BuildingBlockDefaultNames.ENTITY_DEFAULT_NAME;

    /**
     * @return description of subdomain
     */
    String description() default "";
}
