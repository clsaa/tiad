package com.clsaa.tiad.common.data;

/**
 * @author clsaa
 */
@FunctionalInterface
public interface DataProvider {
    Object getData(String dataId);
}