package com.clsaa.tiad.common.data;

/**
 * @author clsaa
 */
@FunctionalInterface
public interface DataContext {
    Object getData(String dataId);

    DataContext EMPTY_CONTEXT = dataId -> null;

    default <T> T getData(DataKey<T> key) {
        return (T) getData(key.getName());
    }
}
