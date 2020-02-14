package com.clsaa.tiad.buidlingblock.annotation;

import java.lang.annotation.*;

/**
 * bounded context of DDD
 * <p>
 * Ideally, SubDomain and BoundedContext have a one-to-one relationship
 *
 * @author clsaa
 */
@Documented
@Target(value = {ElementType.TYPE, ElementType.PACKAGE})
@Retention(RetentionPolicy.RUNTIME)
public @interface BoundedContext {
    /**
     * if use default code, it will be replaced by name of be annotated class/package
     *
     * @return unique code of bounded context
     */
    String code() default "";

    /**
     * @return name of bounded context
     */
    String name() default "";

    /**
     * @return description of bounded context
     */
    String description() default "";
}
