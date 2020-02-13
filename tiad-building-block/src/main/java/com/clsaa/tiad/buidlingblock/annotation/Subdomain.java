package com.clsaa.tiad.buidlingblock.annotation;

import com.clsaa.tiad.buidlingblock.constance.BuildingBlockDefaultCodes;
import com.clsaa.tiad.buidlingblock.constance.BuildingBlockDefaultNames;
import com.clsaa.tiad.buidlingblock.enums.SubDomainType;

import java.lang.annotation.*;

/**
 * subdomain of DDD
 * <p>
 * Ideally, SubDomain and BoundedContext have a one-to-one relationship
 *
 * @author clsaa
 */
@Documented
@Target(value = {ElementType.TYPE, ElementType.PACKAGE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Subdomain {

    /**
     * @return type of subdomain
     */
    SubDomainType type();

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
