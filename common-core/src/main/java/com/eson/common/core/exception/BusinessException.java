package com.eson.common.core.exception;

import com.eson.common.core.constants.Constants;
import com.eson.common.core.util.Strings;

import lombok.Getter;

/**
 * @author dengxiaolin
 * @since 2021/01/05
 */
@Getter
public class BusinessException extends RuntimeException {

    private int code = Constants.FAILED;
    private String detailMsg;
    private boolean trivial;

    public BusinessException() {

    }

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }


    public BusinessException setCode(int code) {
        this.code = code;
        return this;
    }

    public BusinessException setDetailMsg(String detailMsg) {
        this.detailMsg = detailMsg;
        return this;
    }

    public BusinessException setDetailMsg(String format, Object... args) {
        this.detailMsg = Strings.of(format, args);
        return this;
    }

    public BusinessException setTrivial(boolean trivial) {
        this.trivial = trivial;
        return this;
    }
}
