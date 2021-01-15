package com.eson.common.core.utils;

import java.lang.reflect.Field;

/**
 * @author dengxiaolin
 * @since 2021/01/14
 */
public class ReflectUtils {

    public static Field getFieldByName(Object obj, String fieldName) {
        for (Class<?> superClass = obj.getClass(); superClass != Object.class; superClass = superClass.getSuperclass()) {
            try {
                return superClass.getDeclaredField(fieldName);
            }
            catch (NoSuchFieldException e) {
            }
        }
        return null;
    }

    public static Object getFieldValueByName(Object obj, String fieldName) {
        Field field = getFieldByName(obj, fieldName);
        if (field != null) {
            if (field.isAccessible()) {
                return getValue(obj, field);
            }
            else {
                field.setAccessible(true);
                try {
                    return getValue(obj, field);
                }
                finally {
                    field.setAccessible(false);
                }
            }
        }
        return null;
    }

    private static Object getValue(Object obj, Field field) {
        try {
            return field.get(obj);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
