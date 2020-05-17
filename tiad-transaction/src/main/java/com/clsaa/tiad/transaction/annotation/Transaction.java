package com.clsaa.tiad.transaction.annotation;

import com.clsaa.tiad.transaction.listener.DefaultTiadTransactionListener;
import com.clsaa.tiad.transaction.listener.TiadTransactionListener;

import java.lang.annotation.*;

/**
 * transaction declare
 *
 * @author clsaa
 */
@Documented
@Target(value = ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Transaction {
    Class<? extends TiadTransactionListener> listener() default DefaultTiadTransactionListener.class;
}
