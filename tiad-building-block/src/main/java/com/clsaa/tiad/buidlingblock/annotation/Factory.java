package com.clsaa.tiad.buidlingblock.annotation;

import java.lang.annotation.*;

/**
 * REPOSITORY of DDD
 *
 * @author clsaa
 */
@Documented
@Target(value = ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface Factory {

    /**
     * if use default code, it will be replaced by name of be annotated class
     *
     * @return unique code of domain factory
     */
    String code() default "";

    /**
     * @return name of domain factory
     */
    String name() default "";

    /**
     * @return description of domain factory
     */
    String description() default "";
}
