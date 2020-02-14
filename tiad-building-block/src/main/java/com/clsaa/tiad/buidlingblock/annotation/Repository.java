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
public @interface Repository {
    /**
     * if use default code, it will be replaced by name of be annotated class
     *
     * @return unique code of repository
     */
    String code() default "";

    /**
     * @return name of repository
     */
    String name() default "";

    /**
     * @return description of repository
     */
    String description() default "";
}
