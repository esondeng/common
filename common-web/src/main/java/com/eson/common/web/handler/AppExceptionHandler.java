package com.eson.common.web.handler;

import java.util.ServiceLoader;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.eson.common.core.constants.Constants;
import com.eson.common.core.exception.BusinessException;
import com.eson.common.core.exception.ServiceException;
import com.eson.common.web.WebResponse;

import lombok.extern.slf4j.Slf4j;

/**
 * @author dengxiaolin
 * @since 2021/01/05
 */
@ControllerAdvice
@Slf4j
public class AppExceptionHandler {

    private static final ServiceLoader<PreHandler> preHandlers = ServiceLoader.load(PreHandler.class);

    @ExceptionHandler
    @ResponseBody
    public WebResponse<Void> handleException(Exception e, HttpServletRequest request) {
        preHandleException(e, request);
        return buildWebResponse(e);
    }

    private void preHandleException(Exception e, HttpServletRequest request) {
        for (PreHandler preHandler : preHandlers) {
            preHandler.handle(e, request);
        }
    }

    private WebResponse<Void> buildWebResponse(Exception e) {
        String msg;
        if (e instanceof MethodArgumentNotValidException) {
            MethodArgumentNotValidException mex = (MethodArgumentNotValidException) e;
            FieldError error = mex.getBindingResult().getFieldErrors().get(0);
            msg = error == null ? "输入参数错误" : error.getDefaultMessage();
            log.error(msg);
            return WebResponse.fail(msg);
        }

        if (e instanceof ConstraintViolationException) {
            ConstraintViolationException cve = (ConstraintViolationException) e;
            Set<ConstraintViolation<?>> violations = cve.getConstraintViolations();
            msg = CollectionUtils.isEmpty(violations) ? cve.getMessage() : (violations.iterator().next()).getMessage();
            log.error(msg);
            return WebResponse.fail(msg);
        }

        if (e instanceof MissingServletRequestParameterException) {
            msg = e.getMessage();
            log.error(msg);
            return WebResponse.fail(msg);
        }

        if (e instanceof MethodArgumentTypeMismatchException) {
            MethodArgumentTypeMismatchException mex = (MethodArgumentTypeMismatchException) e;
            msg = mex.getParameter() + "参数类型错误";
            return WebResponse.fail(msg);
        }

        if (e instanceof IllegalArgumentException) {
            msg = e.getMessage();
            log.error(msg);
            return WebResponse.fail(msg);
        }

        if (e instanceof ServiceException) {
            ServiceException sex = (ServiceException) e;
            return logAndBuildResponse(sex);
        }

        if (e instanceof BusinessException) {
            BusinessException bex = (BusinessException) e;
            return logAndBuildResponse(bex);
        }

        log.error(e.getMessage());
        return WebResponse.fail("系统服务异常，请稍后重试");

    }

    private WebResponse<Void> logAndBuildResponse(ServiceException sex) {
        String message = sex.getMessage()
                + " detailMsg: "
                + sex.getDetailMsg()
                + Constants.NEW_LINE
                + Constants.TAB
                + sex.getInterfacePath()
                + " : "
                + sex.getExceptionClass();

        if (sex.isTrivial()) {
            log.warn(message);
        }
        else {
            log.error(message);
        }

        String msg = sex.getInterfacePath() + " : " + sex.getExceptionClass() + " " + sex.getMessage();
        return WebResponse.of(sex.getCode(), msg);
    }

    private WebResponse<Void> logAndBuildResponse(BusinessException bex) {
        String message = bex.getMessage() + " detailMsg: " + bex.getDetailMsg();

        if (bex.isTrivial()) {
            log.warn(message);
        }
        else {
            log.error(message);
        }

        return WebResponse.of(bex.getCode(), bex.getMessage());
    }
}



