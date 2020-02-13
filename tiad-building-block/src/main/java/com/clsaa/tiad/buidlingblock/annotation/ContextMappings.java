package com.clsaa.tiad.buidlingblock.annotation;

import java.lang.annotation.*;

/**
 * context mapping of DDD
 *
 * @author clsaa
 */
@Documented
@Target(value = {ElementType.TYPE, ElementType.PACKAGE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ContextMappings {
    ContextMapping[] value();
}
