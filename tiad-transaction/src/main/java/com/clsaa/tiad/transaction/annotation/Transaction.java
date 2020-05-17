package com.clsaa.tiad.transaction.annotation;

import java.lang.annotation.*;

/**
 * transaction declare
 *
 * @author clsaa
 */
@Documented
@Target(value = ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Transaction {
  
}
