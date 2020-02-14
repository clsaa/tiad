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
public @interface ApplicationService {
    /**
     * if use default code, it will be replaced by class name of be annotated class
     *
     * @return unique code of application service
     */
    String code() default "";

    /**
     * @return name of application service
     */
    String name() default "";

    /**
     * @return description of application service
     */
    String description() default "";
}
