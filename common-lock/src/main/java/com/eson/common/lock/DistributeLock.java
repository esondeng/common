package com.eson.common.lock;

import java.util.concurrent.TimeUnit;

/**
 * @author dengxiaolin
 * @since 2021/03/01
 */
public interface DistributeLock {

    void lock();

    boolean tryLock(long time, TimeUnit unit);

    void unlock();
}
