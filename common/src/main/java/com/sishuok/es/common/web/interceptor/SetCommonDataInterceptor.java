/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.es.common.web.interceptor;

import com.sishuok.es.common.Constants;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 设置通用数据的Interceptor
 *
 * 使用Filter时 文件上传时 getParameter时为null 所以改成Interceptor
 *
 * 1、ctx---->request.contextPath
 * 2、currentURL---->当前地址
 * <p>User: Zhang Kaitao
 * <p>Date: 13-1-22 下午4:35
 * <p>Version: 1.0
 */
public class SetCommonDataInterceptor extends HandlerInterceptorAdapter {

    private static final String[] DEFAULT_EXCLUDE_PARAMETER_PATTERN = new String[] {
            "\\&\\w*page.pn=\\d+",
            "\\?\\w*page.pn=\\d+",
            "\\&\\w*page.size=\\d+"
    };

    private String[] excludeParameterPattern = DEFAULT_EXCLUDE_PARAMETER_PATTERN;

    public void setExcludeParameterPattern(String[] excludeParameterPattern) {
        this.excludeParameterPattern = excludeParameterPattern;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if(request.getAttribute(Constants.CONTEXT_PATH) == null) {
            request.setAttribute(Constants.CONTEXT_PATH, request.getContextPath());
        }
        if(request.getAttribute(Constants.CURRENT_URL) == null) {
            request.setAttribute(Constants.CURRENT_URL, extractCurrentURL(request, true));
        }
        if(request.getAttribute(Constants.NO_QUERYSTRING_CURRENT_URL) == null) {
            request.setAttribute(Constants.NO_QUERYSTRING_CURRENT_URL, extractCurrentURL(request, false));
        }
        if(request.getAttribute(Constants.BACK_URL) == null) {
            request.setAttribute(Constants.BACK_URL, extractBackURL(request));
        }

        return super.preHandle(request, response, handler);
    }


    private String extractCurrentURL(HttpServletRequest request, boolean needQueryString) {
        String url = request.getRequestURI();
        String queryString = request.getQueryString();
        if (!StringUtils.isEmpty(queryString)) {
            queryString = "?" + queryString;
            for(String pattern : excludeParameterPattern) {
                queryString = queryString.replaceAll(pattern, "");
            }
            if(queryString.startsWith("&")) {
                queryString = "?" + queryString.substring(1);
            }
        }
        if(!StringUtils.isEmpty(queryString) && needQueryString) {
            url = url + queryString;
        }
        return getBasePath(request) + url;
    }

    /**
     * 上一次请求的地址
     * 1、先从request.parameter中查找BackURL
     * 2、获取header中的 referer
     * @param request
     * @return
     */
    private String extractBackURL(HttpServletRequest request) {
        String url = request.getParameter(Constants.BACK_URL);

        //使用Filter时 文件上传时 getParameter时为null 所以改成Interceptor

        if(StringUtils.isEmpty(url)) {
            url = request.getHeader("Referer");
        }
        if(!StringUtils.isEmpty(url) && url.startsWith(request.getContextPath())) {
            url = getBasePath(request) + url;
        }
        return url;
    }

    private String getBasePath(HttpServletRequest req) {
        StringBuffer baseUrl = new StringBuffer();
        String scheme = req.getScheme();
        int port = req.getServerPort();

        //String		servletPath = req.getServletPath ();
        //String		pathInfo = req.getPathInfo ();

        baseUrl.append(scheme);        // http, https
        baseUrl.append("://");
        baseUrl.append(req.getServerName());
        if ((scheme.equals("http") && port != 80) || (scheme.equals("https") && port != 443)) {
            baseUrl.append(':');
            baseUrl.append(req.getServerPort());
        }
        return baseUrl.toString();
    }


}
