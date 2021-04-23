package com.eson.common.web.handler;

import javax.servlet.http.HttpServletRequest;

/**
 * @author dengxiaolin
 * @since 2021/04/23
 */
public interface PreHandler {
    /**
     * 统一异常处理前置处理器
     */
    void handle(Exception e, HttpServletRequest request);
}
