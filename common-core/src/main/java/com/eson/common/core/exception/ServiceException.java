package com.eson.common.core.exception;

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
     * first place in error stack
     */
    private String errorPlace;

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

    public String getErrorPlace() {
        return errorPlace;
    }

    public ServiceException setErrorPlace(String errorPlace) {
        this.errorPlace = errorPlace;
        return this;
    }
}
