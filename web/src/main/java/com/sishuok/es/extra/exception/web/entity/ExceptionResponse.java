/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.es.extra.exception.web.entity;

import org.apache.shiro.authz.UnauthorizedException;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 13-5-7 下午4:05
 * <p>Version: 1.0
 */
public class ExceptionResponse {

    private String exception;

    private String message;

    private String stackTrace;

    private ExceptionResponse() {

    }

    public String getException() {
        return exception;
    }

    public void setException(String exception) {
        this.exception = exception;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStackTrace() {
        return stackTrace;
    }

    public void setStackTrace(String stackTrace) {
        this.stackTrace = stackTrace;
    }

    @Override
    public String toString() {
        return "ExceptionResponse{" +
                "exception='" + exception + '\'' +
                ", message='" + message + '\'' +
                ", stackTrace='" + stackTrace + '\'' +
                '}';
    }

    public static ExceptionResponse from(Throwable e) {
        ExceptionResponse exceptionResponse = new ExceptionResponse();

        String errorMessage = "<h3 style='display: inline;'>出错了！</h3><br/>错误信息：" + convertMessage(e);

        exceptionResponse.setMessage(errorMessage);

        exceptionResponse.setException(e.getClass().getName());

        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        e.printStackTrace(printWriter);
        exceptionResponse.setStackTrace(stringWriter.toString());

        return exceptionResponse;
    }

    private static String convertMessage(Throwable e) {

        String errorMessage = e.getMessage();
        //验证失败
        if (e instanceof UnauthorizedException) {
            if (errorMessage.startsWith("Subject does not have permission")) {
                errorMessage = errorMessage.replaceAll("Subject does not have permission", "您没有操作权限，请联系管理员添加权限");
            }
            if (errorMessage.startsWith("User is not permitted")) {
                errorMessage = errorMessage.replaceAll("User is not permitted", "您没有操作权限，请联系管理员添加权限");
            }
            if (errorMessage.startsWith("Subject does not have role")) {
                errorMessage = errorMessage.replaceAll("Subject does not have role", "您没有操作权限，请联系管理员添加角色");
            }
        }

        return errorMessage;
    }
}
