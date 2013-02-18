/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.es.common.web.filter;

import org.springframework.util.StringUtils;
import org.springframework.web.bind.ServletRequestUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 设置通用数据的filter
 * 1、ctx---->request.contextPath
 * 2、currentURL---->当前地址
 * <p>User: Zhang Kaitao
 * <p>Date: 13-1-22 下午4:35
 * <p>Version: 1.0
 */
public class SetCommonDataFilter extends BaseFilter {

    private String[] excludeParameterPattern = new String[] {
            "\\&\\w*page.pn=\\d+",
            "\\?\\w*page.pn=\\d+",
            "\\&\\w*page.size=\\d+"
    };

    @Override
    public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        request.setAttribute("ctx", request.getContextPath());
        request.setAttribute("currentURL", extractCurrentURL(request));

        chain.doFilter(request, response);
    }

    private String extractCurrentURL(HttpServletRequest request) {
        String url = request.getRequestURI();
        String queryString = request.getQueryString();
        if (StringUtils.hasLength(queryString)) {
            queryString = "?" + queryString;
            for(String pattern : excludeParameterPattern) {
                queryString = queryString.replaceAll(pattern, "");
            }
            if(queryString.startsWith("&")) {
                queryString = "?" + queryString.substring(1);
            }
        }
        if(StringUtils.hasLength(queryString)) {
            url = url + queryString;
        }
        return url;
    }

}
