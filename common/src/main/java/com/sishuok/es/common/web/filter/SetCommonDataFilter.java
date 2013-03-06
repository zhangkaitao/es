/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.es.common.web.filter;

import org.springframework.util.StringUtils;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.util.UriTemplate;

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
        request.setAttribute("currentURL", extractCurrentURL(request, true));
        request.setAttribute("noQueryStringCurrentURL", extractCurrentURL(request, false));
        request.setAttribute("BackURL", extractBackURL(request));

        chain.doFilter(request, response);
    }

    private String extractCurrentURL(HttpServletRequest request, boolean needQueryString) {
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
        if(StringUtils.hasLength(queryString) && needQueryString) {
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
        String url = request.getParameter("BackURL");
        if(url == null) {
            url = request.getHeader("Referer");
        }
        if(url != null && url.startsWith(request.getContextPath())) {
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
