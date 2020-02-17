package com.clsaa.tiad.common.data;

import org.junit.Test;

import static org.junit.Assert.*;

public class UniqueDataKeyTest {
    private static final DataKey<Person> PERSON_DATA_KEY = DataKey.create("person");

    @Test
    public void testUniqueDataKey() {
        DataHolder dataHolder = new MapDataContext();
        Person data1 = new Person("1");
        dataHolder.putData(PERSON_DATA_KEY, data1);
        Person data = dataHolder.getData(PERSON_DATA_KEY);
        assert data1 == data;

    }
}