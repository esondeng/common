package com.eson.common.core.domain;

import lombok.Getter;
import lombok.Setter;

/**
 * 用于访问HTTP接口
 *
 * @author dengxiaolin
 * @since 2021/03/03
 */
@Getter
@Setter
public class JsonResponse<T> {
    private Integer code;
    private String msg;
    private T data;
}
