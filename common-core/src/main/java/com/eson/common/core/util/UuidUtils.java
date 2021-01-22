package com.eson.common.core.util;

import java.util.UUID;

/**
 * @author dengxiaolin
 * @since 2021/01/22
 */
public class UuidUtils {
    public static String genUuid() {
        return UUID.randomUUID()
                .toString()
                .replaceAll("-", "")
                .toLowerCase();
    }
}
