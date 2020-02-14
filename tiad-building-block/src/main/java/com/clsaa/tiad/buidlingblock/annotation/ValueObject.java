package com.clsaa.tiad.buidlingblock.annotation;


import java.lang.annotation.*;

/**
 * value object of DDD
 *
 * @author clsaa
 */
@Documented
@Target(value = ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValueObject {
    /**
     * if use default code, it will be replaced by name of be annotated class
     *
     * @return unique code of value object
     */
    String code() default "";

    /**
     * @return name of value object
     */
    String name() default "";

    /**
     * @return description of value object
     */
    String description() default "";
}
