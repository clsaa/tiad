package com.clsaa.tiad.buidlingblock.annotation;

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
@Target(value = ElementType.PACKAGE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Subdomain {

    /**
     * @return type of subdomain
     */
    SubDomainType type();

    /**
     * if use default code, it will be replaced by name of be annotated class
     *
     * @return unique code of subdomain
     */
    String code() default "";

    /**
     * @return name of subdomain
     */
    String name() default "";

    /**
     * @return description of subdomain
     */
    String description() default "";
}
