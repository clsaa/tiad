package com.clsaa.tiad.common.data;

/**
 * @author clsaa
 */
public class DataKey<T> {

    protected final String name;

    protected DataKey(String name) {
        this.name = name;
    }


    public static <T> DataKey<T> create(String key) {
        //noinspection unchecked
        return new DataKey<>(key);
    }

    public static <T> T cast(Object o) {
        return (T) o;
    }

    public String getName() {
        return name;
    }

    public final boolean is(String dataId) {
        return name.equals(dataId);
    }

    public T getData(DataContext dataContext) {
        //noinspection unchecked
        return (T) dataContext.getData(name);
    }

    public T getData(DataProvider dataProvider) {
        //noinspection unchecked
        return (T) dataProvider.getData(name);
    }
}
