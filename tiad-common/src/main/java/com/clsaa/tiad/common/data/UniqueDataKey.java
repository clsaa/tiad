package com.clsaa.tiad.common.data;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author clsaa
 */
public class UniqueDataKey<T> extends DataKey<T> {
    private static final ConcurrentMap<String, DataKey> dataKeyIndex = new ConcurrentHashMap<>(64);

    private UniqueDataKey(String name) {
        super(name);
    }

    public static <T> DataKey<T> create(String name) {
        return dataKeyIndex.computeIfAbsent(name, DataKey::new);
    }

}
