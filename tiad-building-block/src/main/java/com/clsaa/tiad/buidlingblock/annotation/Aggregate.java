package com.clsaa.tiad.buidlingblock.annotation;


import java.lang.annotation.*;

/**
 * Aggregate of DDD
 *
 * @author clsaa
 */
@Documented
@Target(value = ElementType.PACKAGE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Aggregate {
    /**
     * if use default code, it will be replaced by class name of be annotated class
     *
     * @return unique code of aggregate
     */
    String code() default "";

    /**
     * @return name of aggregate
     */
    String name() default "";

    /**
     * @return description of aggregate
     */
    String description() default "";
}
