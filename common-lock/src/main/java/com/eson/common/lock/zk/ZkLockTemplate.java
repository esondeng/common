package com.eson.common.lock.zk;

import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

import org.apache.curator.framework.CuratorFramework;
import org.springframework.beans.factory.annotation.Autowired;

import com.eson.common.core.exception.BusinessException;

/**
 * @author dengxiaolin
 * @since 2021/03/01
 */
public class ZkLockTemplate {
    @Autowired
    private CuratorFramework curatorClient;

    /**
     * 带返回值执行
     *
     * @param basePath 推荐使用 "/" + appKey + "/dist_lock/"
     * @param lockKey  锁定关键字
     * @param supplier 返回值函数
     * @param <T>      泛型参数
     */
    public <T> T tryLockWithReturn(String basePath, String lockKey, Supplier<T> supplier) {
        ZkDistributeLock lock = new ZkDistributeLock(curatorClient, basePath, lockKey);

        if (lock.tryLock(1, TimeUnit.MILLISECONDS)) {
            try {
                return supplier.get();
            }
            finally {
                lock.unlock();
            }
        }
        else {
            throw new BusinessException("系统正在处理，请稍后再重试");
        }
    }

    /**
     * 不带返回值执行
     *
     * @param basePath 推荐使用 "/" + appKey + "/dist_lock/"
     * @param lockKey  锁定关键字
     * @param runnable 运行函数
     */
    public void tryLock(String basePath, String lockKey, Runnable runnable) {
        ZkDistributeLock lock = new ZkDistributeLock(curatorClient, basePath, lockKey);

        if (lock.tryLock(1, TimeUnit.MILLISECONDS)) {
            try {
                runnable.run();
            }
            finally {
                lock.unlock();
            }
        }
        else {
            throw new BusinessException("系统正在处理，请稍后再重试:" + lockKey);
        }
    }

    public <T> T lockWithReturn(String basePath, String lockKey, Supplier<T> supplier) {
        ZkDistributeLock lock = new ZkDistributeLock(curatorClient, basePath, lockKey);

        lock.lock();
        try {
            return supplier.get();
        }
        finally {
            lock.unlock();
        }
    }

    public void lock(String basePath, String lockKey, Runnable runnable) {
        ZkDistributeLock lock = new ZkDistributeLock(curatorClient, basePath, lockKey);

        lock.lock();
        try {
            runnable.run();
        }
        finally {
            lock.unlock();
        }
    }
}
