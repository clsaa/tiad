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
public @interface DomainService {
    /**
     * if use default code, it will be replaced by name of be annotated class
     *
     * @return unique code of domain service
     */
    String code() default "";

    /**
     * @return name of domain service
     */
    String name() default "";

    /**
     * @return description of domain service
     */
    String description() default "";
}
