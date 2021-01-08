package com.eson.common.core.enums;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import com.eson.common.core.utils.Assert;

/**
 * @author dengxiaolin
 * @since 2021/01/07
 */
public interface EnumBase {
    int id();

    String message();

    public static <E extends Enum<E> & EnumBase> E ofId(Class<E> type, int id) {
        E t = ofIdNullable(type, id);
        Assert.throwIfNull(t, "No enum const {} for id {}", type.getName(), id);
        return t;
    }

    public static <E extends Enum<E> & EnumBase> E ofIdNullable(Class<E> type, int id) {
        final EnumSet<E> values = EnumSet.allOf(type);
        for (E t : values) {
            if (t.id() == id) {
                return t;
            }
        }
        return null;
    }

    public static <E extends Enum<E> & EnumBase> E ofMessage(Class<E> type, String message) {
        E t = ofMessageNullable(type, message);
        Assert.throwIfNull(t, "No enum const {} for message {}", type.getName(), message);
        return t;
    }

    public static <E extends Enum<E> & EnumBase> E ofMessageNullable(Class<E> type, String message) {
        Assert.throwIfBlank(message, "message must not be blank");

        final EnumSet<E> values = EnumSet.allOf(type);
        for (E t : values) {
            if (t.message().equals(message)) {
                return t;
            }
        }
        return null;
    }

    public static <E extends Enum<E> & EnumBase> Map<Integer, String> enumToMap(Class<E> type) {
        EnumSet<E> values = EnumSet.allOf(type);
        Map<Integer, String> map = new HashMap<>(8);
        for (E t : values) {
            map.put(t.id(), t.message());
        }
        return map;
    }

    public static <E extends Enum<E> & EnumBase> boolean hasId(Class<E> type, int id) {
        return ofIdNullable(type, id) != null;
    }

    public static <E extends Enum<E> & EnumBase> boolean notHasId(Class<E> type, int id) {
        return !hasId(type, id);
    }

    public static <E extends Enum<E> & EnumBase> boolean hasMessage(Class<E> type, String message) {
        return ofMessageNullable(type, message) != null;
    }

    public static <E extends Enum<E> & EnumBase> boolean notHasMessage(Class<E> type, String message) {
        return !hasMessage(type, message);
    }
}
