/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.es.common.web.validate;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * //验证结果
 * <p>User: Zhang Kaitao
 * <p>Date: 13-2-7 下午7:11
 * <p>Version: 1.0
 */
public class ValidateResponse {
    /**
     * 验证成功
     */
    private static final Integer OK = 1;
    /**
     * 验证失败
     */
    private static final Integer FAIL = 0;

    private List<Object> results = Lists.newArrayList();

    private ValidateResponse() {
    }

    public static ValidateResponse newInstance() {
        return new ValidateResponse();
    }

    /**
     * 验证成功（使用前台alertTextOk定义的消息）
     *
     * @param fieldId 验证成功的字段名
     */
    public void validateFail(String fieldId) {
        validateFail(fieldId, "");
    }

    /**
     * 验证成功
     *
     * @param fieldId 验证成功的字段名
     * @param message 验证成功时显示的消息
     */
    public void validateFail(String fieldId, String message) {
        results.add(new Object[]{fieldId, FAIL, message});
    }

    /**
     * 验证成功（使用前台alertTextOk定义的消息）
     *
     * @param fieldId 验证成功的字段名
     */
    public void validateSuccess(String fieldId) {
        validateSuccess(fieldId, "");
    }

    /**
     * 验证成功
     *
     * @param fieldId 验证成功的字段名
     * @param message 验证成功时显示的消息
     */
    public void validateSuccess(String fieldId, String message) {
        results.add(new Object[]{fieldId, OK, message});
    }

    /**
     * 返回验证结果
     *
     * @return
     */
    public Object result() {
        if (results.size() == 1) {
            return results.get(0);
        }
        return results;
    }

}
