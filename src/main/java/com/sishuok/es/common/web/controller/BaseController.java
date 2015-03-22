/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.es.common.web.controller;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sishuok.es.common.entity.AbstractEntity;
import com.sishuok.es.common.utils.ReflectUtils;

/**
 * 基础控制器
 * <p>User: Zhang Kaitao
 * <p>Date: 13-2-23 下午3:56
 * <p>Version: 1.0
 */
@SuppressWarnings("rawtypes")
public abstract class BaseController<M extends AbstractEntity, ID extends Serializable> {

    /**
     * 实体类型
     */
    protected final Class<M> entityClass;

    private String viewPrefix;


    protected BaseController() {
        this.entityClass = ReflectUtils.findParameterizedType(getClass(), 0);
        setViewPrefix(defaultViewPrefix());
    }


    /**
     * 设置通用数据
     *
     * @param model
     */
    protected void setCommonData(Model model) {
    	//将菜单数据放入通用资源中
        Field [] fields = entityClass.getDeclaredFields();
        for(int i=0; i< fields.length; i++)
        {
            Field f = fields[i];
            String value = getFieldValue(this ,f.getName()).toString();
            System.out.println(f.getName()+"---------------------"+value);
        } 
        
        
    }
    
    private  String getFieldValue(Object owner, String fieldName)
    {
        return invokeMethod(owner, fieldName,null).toString();
    }
    /**
     * 
     * 执行某个Field的getField方法
     * 
     * @param owner 类
     * @param fieldName 类的属性名称
     * @param args 参数，默认为null
     * @return 
     */
    private   Object invokeMethod(Object owner, String fieldName, Object[] args)
    {
        Class<? extends Object> ownerClass = owner.getClass();
        
        //fieldName -> FieldName  
        String methodName = fieldName.substring(0, 1).toUpperCase()+ fieldName.substring(1);
        
        Method method = null;
        try 
        {
            method = ownerClass.getMethod("get" + methodName);
        } 
        catch (SecurityException e) 
        {

            //e.printStackTrace();
        } 
        catch (NoSuchMethodException e) 
        {
            // e.printStackTrace();
            return "";
        }
        
        //invoke getMethod
        try
        {
            return method.invoke(owner);
        } 
        catch (Exception e)
        {
            return "";
        }
    }


    /**
     * 当前模块 视图的前缀
     * 默认
     * 1、获取当前类头上的@RequestMapping中的value作为前缀
     * 2、如果没有就使用当前模型小写的简单类名
     */
    public void setViewPrefix(String viewPrefix) {
        if (viewPrefix.startsWith("/")) {
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
     * 获取视图名称：即prefixViewName + "/" + suffixName
     *
     * @return
     */
    public String viewName(String suffixName) {
        if (!suffixName.startsWith("/")) {
            suffixName = "/" + suffixName;
        }
        return getViewPrefix() + suffixName;
    }

    /**
     * 共享的验证规则
     * 验证失败返回true
     *
     * @param m
     * @param result
     * @return
     */
    protected boolean hasError(M m, BindingResult result) {
        Assert.notNull(m);
        return result.hasErrors();
    }

    /**
     * @param backURL null 将重定向到默认getViewPrefix()
     * @return
     */
    protected String redirectToUrl(String backURL) {
        if (StringUtils.isEmpty(backURL)) {
            backURL = getViewPrefix();
        }
        if (!backURL.startsWith("/") && !backURL.startsWith("http")) {
            backURL = "/" + backURL;
        }
        return "redirect:" + backURL;
    }

    protected String defaultViewPrefix() {
        String currentViewPrefix = "";
        RequestMapping requestMapping = AnnotationUtils.findAnnotation(getClass(), RequestMapping.class);
        if (requestMapping != null && requestMapping.value().length > 0) {
            currentViewPrefix = requestMapping.value()[0];
        }

        if (StringUtils.isEmpty(currentViewPrefix)) {
            currentViewPrefix = this.entityClass.getSimpleName();
        }

        return currentViewPrefix;
    }
}
