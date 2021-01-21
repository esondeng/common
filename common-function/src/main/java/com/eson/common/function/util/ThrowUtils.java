package com.eson.common.function.util;

import com.eson.common.function.ThrowCallable;
import com.eson.common.function.ThrowRunnable;

/**
 * @author dengxiaolin
 * @since 2021/01/21
 */
public class ThrowUtils {

    public static <T> T execute(ThrowCallable<T> callable) {
        try {
            return callable.call();
        }
        catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }


    public static void execute(ThrowRunnable runnable) {
        try {
            runnable.run();
        }
        catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }
}
