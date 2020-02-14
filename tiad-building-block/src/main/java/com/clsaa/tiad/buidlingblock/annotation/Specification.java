package com.clsaa.tiad.buidlingblock.annotation;

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
public @interface Specification {
    /**
     * if use default code, it will be replaced by name of be annotated class
     *
     * @return unique code of specification
     */
    String code() default "";

    /**
     * @return name of specification
     */
    String name() default "";

    /**
     * @return description of specification
     */
    String description() default "";
}
