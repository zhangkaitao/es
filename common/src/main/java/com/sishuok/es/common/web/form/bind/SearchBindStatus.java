/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.es.common.web.form.bind;

import org.springframework.util.StringUtils;
import org.springframework.web.servlet.support.BindStatus;
import org.springframework.web.servlet.support.RequestContext;
import org.springframework.web.util.HtmlUtils;

import javax.servlet.jsp.PageContext;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 13-3-28 下午4:46
 * <p>Version: 1.0
 */
public class SearchBindStatus extends BindStatus {

    private static final String pathToUse = "___search";

    private Object value;
    private boolean htmlEscape;

    private SearchBindStatus(RequestContext requestContext, String path, boolean htmlEscape) throws IllegalStateException {
        super(requestContext, path, htmlEscape);
    }

    public static BindStatus create(PageContext pageContext, String name, RequestContext requestContext, boolean htmlEscape) {
        pageContext.getRequest().setAttribute(pathToUse, SearchModel.EMPTY);
        SearchBindStatus bindStatus = new SearchBindStatus(requestContext, pathToUse, htmlEscape);
        bindStatus.value = getValue(pageContext, name);
        bindStatus.htmlEscape = htmlEscape;
        return bindStatus;
    }

    public static Object getValue(PageContext pageContext, String name) {
        if (StringUtils.isEmpty(name)) {
            return null;
        }
        Object value = null;
        String[] parameters = pageContext.getRequest().getParameterValues(name);
        if (parameters != null && parameters.length == 1) {
            value = parameters[0];
        } else {
            value = parameters;
        }
        //万一同名就选中了
//        if(value == null || (value instanceof String && StringUtils.isEmpty((String) value))) {
//            Object attributeValue = pageContext.findAttribute(name);
//            if(attributeValue != null) {
//                value = attributeValue;
//            }
//        }
        return value;
    }

    @Override
    public Object getValue() {
        return value;
    }

    @Override
    public Object getActualValue() {
        return value;
    }

    @Override
    public String getDisplayValue() {
        if (this.value instanceof String) {
            return (String) this.value;
        }
        if (this.value != null) {
            return (this.htmlEscape ? HtmlUtils.htmlEscape(this.value.toString()) : this.value.toString());
        }
        return "";
    }

    private static class SearchModel {
        public static final SearchModel EMPTY = new SearchModel();
    }


}
