package com.eson.common.core.utils;

import org.apache.logging.log4j.message.ParameterizedMessageFactory;

/**
 * @author dengxiaolin
 * @since 2021/01/05
 */
public class Strings {
    private static final ParameterizedMessageFactory messageFactory = ParameterizedMessageFactory.INSTANCE;

    public static String of(String format, Object... args) {
        if (args == null || args.length == 0) {
            return format;
        }
        return messageFactory.newMessage(format, args).getFormattedMessage();
    }
}
