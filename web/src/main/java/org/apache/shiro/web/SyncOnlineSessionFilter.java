/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package org.apache.shiro.web;

import com.sishuok.es.common.Constants;
import org.apache.shiro.ShiroConstants;
import org.apache.shiro.session.mgt.OnlineSession;
import org.apache.shiro.session.mgt.eis.OnlineSessionDAO;
import org.apache.shiro.web.servlet.OncePerRequestFilter;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

/**
 * 同步当前会话数据到数据库
 * <p>User: Zhang Kaitao
 * <p>Date: 13-3-21 下午5:53
 * <p>Version: 1.0
 */
public class SyncOnlineSessionFilter extends OncePerRequestFilter {

    private OnlineSessionDAO onlineSessionDAO;

    public void setOnlineSessionDAO(OnlineSessionDAO onlineSessionDAO) {
        this.onlineSessionDAO = onlineSessionDAO;
    }

    @Override
    protected void doFilterInternal(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        chain.doFilter(request, response);

        OnlineSession session = (OnlineSession) request.getAttribute(ShiroConstants.ONLINE_SESSION);
        //如果session stop了 也不同步
        if (session != null && session.getStopTimestamp() == null) {
            onlineSessionDAO.syncToDb(session);
        }
    }

}
