package com.clsaa.tiad.buidlingblock.annotation;

import java.lang.annotation.*;

/**
 * Identifier for entity of DDD
 *
 * @author clsaa
 */
@Documented
@Target(value = ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface Identifier {
    /**
     * if use default code, it will be replaced by name of be annotated class
     *
     * @return unique code of identifier
     */
    String code() default "";

    /**
     * @return name of identifier
     */
    String name() default "";

    /**
     * @return description of identifier
     */
    String description() default "";
}
