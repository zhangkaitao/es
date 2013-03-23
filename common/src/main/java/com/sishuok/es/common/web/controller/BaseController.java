/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.es.common.web.controller;

import com.sishuok.es.common.entity.AbstractEntity;
import com.sishuok.es.common.service.BaseService;
import com.sishuok.es.common.utils.ReflectUtils;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.Serializable;

/**
 * 基础控制器
 * <p>User: Zhang Kaitao
 * <p>Date: 13-2-23 下午3:56
 * <p>Version: 1.0
 */
public class BaseController<M extends AbstractEntity, ID extends Serializable> {

    protected BaseService<M, ID> baseService;

    /**
     * 实体类型
     */
    protected final Class<M> entityClass;

    /**
     * 当前模块 视图的前缀
     * 默认
     * 1、获取当前类头上的@RequestMapping中的value作为前缀
     * 2、如果没有就使用当前模型小写的简单类名
     */
    private String viewPrefix;

    protected  <S extends BaseService<M, ID>> BaseController(S baseService) {
        this.baseService = baseService;
        this.entityClass = ReflectUtils.findParameterizedType(getClass(), 0);
        setViewPrefix(defaultViewPrefix());
    }

    public void setViewPrefix(String viewPrefix) {
        if(viewPrefix.startsWith("/")) {
            viewPrefix = viewPrefix.substring(1);
        }
        this.viewPrefix = viewPrefix;
    }

    public String getViewPrefix() {
        return viewPrefix;
    }

    protected M newModel() {
        try {
            return entityClass.newInstance();
        } catch (Exception e) {
            throw new IllegalStateException("can not instantiated model : " + this.entityClass, e);
        }
    }

    /**
     * 共享的验证规则
     * 验证失败返回true
     * @param m
     * @param result
     * @return
     */
    protected boolean hasError(M m, BindingResult result) {
        Assert.notNull(m);
        return result.hasErrors();
    }


    /**
     * 获取返回时（比如CRUD）的URL
     * @param backURL
     * @return
     */
    protected String redirectUrl(String backURL) {
        if(StringUtils.hasLength(backURL)) {
            return backURL;
        }
        return "/" + getViewPrefix();
    }

    /**
     * @param backURL null 将重定向到默认getViewPrefix()
     * @return
     */
    protected String redirectToUrl(String backURL) {
        return "redirect:" + redirectUrl(backURL);
    }

    protected String defaultViewPrefix() {
        String currentViewPrefix = "";
        RequestMapping requestMapping = AnnotationUtils.findAnnotation(getClass(), RequestMapping.class);
        if(requestMapping != null && requestMapping.value().length > 0) {
            currentViewPrefix = requestMapping.value()[0];
        }

        if(StringUtils.isEmpty(currentViewPrefix)) {
            currentViewPrefix = this.entityClass.getSimpleName();
        }

        return currentViewPrefix;
    }
}
