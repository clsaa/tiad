package com.clsaa.tiad.common.data;

import java.util.HashMap;
import java.util.Map;

/**
 * @author clsaa
 */
public class MapDataContext implements DataContext, DataHolder {
    private final Map<String, Object> dataIndex = new HashMap<>();

    public MapDataContext() {
    }

    public MapDataContext(Map<DataKey<?>, Object> context) {
        context.forEach((k, v) -> dataIndex.put(k.getName(), v));
    }

    @Override
    public void putData(String dataId, Object data) {
        dataIndex.put(dataId, data);
    }

    @Override
    public Object getData(String dataId) {
        return dataIndex.get(dataId);
    }

    @Override
    public <T> T getData(DataKey<T> key) {
        return (T) getData(key.getName());
    }

}