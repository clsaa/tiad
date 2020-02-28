/*
 *    Copyright 2019 Clsaa Group
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.clsaa.tiad.common.utils;

import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * @author clsaa
 */
public class UnsafeUtil {
    public static Unsafe getUnsafe() {
        try {
            Field field = Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            return (Unsafe) field.get(null);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public static void putStaticObject(Field field, Object value) {
        final Unsafe unsafe = getUnsafe();
        final Object fieldBase = unsafe.staticFieldBase(field);
        final long fieldOffset = unsafe.staticFieldOffset(field);
        unsafe.putObject(fieldBase, fieldOffset, value);
    }

    private static final String[] arr = {"a", "b"};

    public static void main(String[] args) throws Exception {
        final Field field = UnsafeUtil.class.getDeclaredField("arr");
        //unsafe
        System.out.println(arr.length);
        String[] newArr = {"a", "b", "c"};
        putStaticObject(field, newArr);
        System.out.println(arr.length);

        //ref
        String[] newArr2 = {"a", "b", "c", "d"};
        Field modifiersField = Field.class.getDeclaredField("modifiers");
        modifiersField.setAccessible(true);
        modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);
        field.set(null, newArr2);
        System.out.println(arr.length);
    }
}
