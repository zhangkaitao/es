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
        if(success == Boolean.FALSE && this.message == null) {
            this.message = "操作失败";
        }
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
