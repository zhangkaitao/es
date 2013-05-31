/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.es.common.web.validate;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 13-2-8 上午11:10
 * <p>Version: 1.0
 */
public class AjaxResponse {
    private Boolean success;
    private String message;

    public AjaxResponse() {
        this(Boolean.TRUE, "操作成功");
    }

    public AjaxResponse(Boolean success) {
        this(success, null);
    }

    public AjaxResponse(String message) {
        this(Boolean.TRUE, "操作成功");
    }

    public AjaxResponse(Boolean success, String message) {
        this.success = success;
        this.message = message;
        if (this.message == null) {
            if (Boolean.FALSE.equals(success)) {
                this.message = "操作失败";
            }
            if (Boolean.TRUE.equals(success)) {
                this.message = "操作成功";
            }

        }
    }


    public static AjaxResponse fail() {
        return fail(null);
    }

    public static AjaxResponse fail(String message) {
        return new AjaxResponse(Boolean.FALSE, message);
    }

    public static AjaxResponse success() {
        return success(null);
    }

    public static AjaxResponse success(String message) {
        return new AjaxResponse(Boolean.TRUE, message);
    }


    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
