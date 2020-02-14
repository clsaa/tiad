package com.clsaa.tiad.buidlingblock.annotation;

import java.lang.annotation.*;

/**
 * aggregate of DDD
 *
 * @author clsaa
 */
@Documented
@Target(value = ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface AggregateRoot {
    /**
     * if use default code, it will be replaced by class name of be annotated class
     *
     * @return unique code of aggregate root
     */
    String code() default "";

    /**
     * @return name of aggregate root
     */
    String name() default "";

    /**
     * @return description of aggregate root
     */
    String description() default "";
}
