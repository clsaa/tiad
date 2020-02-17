package com.clsaa.tiad.common.data;

import java.io.Serializable;

/**
 * @author clsaa
 */
public interface DataHolder extends Serializable {
    void putData(String dataId, Object data);

    Object getData(String dataId);

    default <T> void putData(DataKey<T> key, T data) {
        putData(key.getName(), data);
    }

    default <T> T getData(DataKey<T> key) {
        return (T) getData(key.getName());
    }
}