package com.eson.common.web;

/**
 * @author dengxiaolin
 * @since 2021/01/07
 */
public class WebResponse<T> {
    public static final int SUCCESS = 0;
    public static final int FAILED = 1;

    private static final String SUCCESS_MSG = "成功";

    private Integer code;
    private String msg;
    private T data;

    private WebResponse(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static <T> WebResponse<T> success() {
        return new WebResponse<>(SUCCESS, SUCCESS_MSG, null);
    }

    public static <T> WebResponse<T> success(T data) {
        return new WebResponse<>(SUCCESS, SUCCESS_MSG, data);
    }

    public static <T> WebResponse<T> fail(String msg) {
        return new WebResponse<>(FAILED, msg, null);
    }

    public static <T> WebResponse<T> of(Integer code, String msg) {
        return new WebResponse<>(code, msg, null);
    }

    public static <T> WebResponse<T> of(Integer code, String msg, T data) {
        return new WebResponse<>(code, msg, data);
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public T getData() {
        return data;
    }
}
