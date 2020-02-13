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
}
