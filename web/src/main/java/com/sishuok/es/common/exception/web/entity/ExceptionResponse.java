/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.es.common.exception.web.entity;

import org.apache.shiro.authz.UnauthenticatedException;

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

        exceptionResponse.setMessage(e.getMessage());
        exceptionResponse.setException(e.getClass().getName());

        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        e.printStackTrace(printWriter);
        exceptionResponse.setException(stringWriter.toString());

        return exceptionResponse;
    }
}
