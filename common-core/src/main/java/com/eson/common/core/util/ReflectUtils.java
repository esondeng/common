package com.eson.common.core.util;

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
        throw new RuntimeException(Strings.of("there is not field named {}", fieldName));
    }

    public static Object getFieldValueByName(Object obj, String fieldName) {
        Field field = getFieldByName(obj, fieldName);
        return getValue(obj, field);
    }

    public static Object getValue(Object obj, Field field) {
        if (field.isAccessible()) {
            return getValue(obj, field);
        }
        else {
            field.setAccessible(true);
            try {
                return field.get(obj);
            }
            catch (Exception e) {
                throw new RuntimeException(e);
            }
            finally {
                field.setAccessible(false);
            }
        }
    }

    public static void setValue(Field field, Object targetObj, Object value) {
        try {
            if (field.isAccessible()) {
                field.set(targetObj, value);
            }
            else {
                field.setAccessible(true);
                try {
                    field.set(targetObj, value);
                }
                finally {
                    field.setAccessible(false);
                }
            }
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
