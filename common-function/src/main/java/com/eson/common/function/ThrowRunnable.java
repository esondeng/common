package com.eson.common.function;

/**
 * @author dengxiaolin
 * @since 2021/01/08
 */
@FunctionalInterface
public interface ThrowRunnable {
    void run() throws Throwable;
}
