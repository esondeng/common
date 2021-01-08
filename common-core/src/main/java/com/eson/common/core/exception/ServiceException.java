package com.eson.common.core.exception;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;

/**
 * 微服务异常
 *
 * @author dengxiaolin
 * @since 2021/01/05
 */
public class ServiceException extends BusinessException {
    /**
     * rpc interface name
     */
    private String interfacePath;

    /**
     * 为了减少数据量，这里只记录错误栈前三条
     */
    private List<String> errorStack;

    public ServiceException() {

    }

    public ServiceException(String message) {
        super(message);
    }


    public String getInterfacePath() {
        return interfacePath;
    }

    public ServiceException setInterfacePath(String interfacePath) {
        this.interfacePath = interfacePath;
        return this;
    }

    public List<String> getErrorStack() {
        return errorStack;
    }

    public ServiceException setErrorStack(List<String> errorStack) {
        this.errorStack = errorStack;
        return this;
    }

    public String getErrorPlace() {
        if (CollectionUtils.isEmpty(errorStack)) {
            return null;
        }
        else {
            return errorStack.get(0);
        }
    }
}
