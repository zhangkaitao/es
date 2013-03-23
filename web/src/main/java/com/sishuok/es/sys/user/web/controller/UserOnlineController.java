/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.es.sys.user.web.controller;

import com.sishuok.es.common.web.controller.BaseCRUDController;
import com.sishuok.es.sys.user.entity.UserOnline;
import com.sishuok.es.sys.user.service.UserOnlineService;
import org.apache.shiro.session.mgt.OnlineSession;
import org.apache.shiro.session.mgt.eis.OnlineSessionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 13-1-28 下午4:29
 * <p>Version: 1.0
 */
@Controller
@RequestMapping(value = "/admin/sys/user/online")
public class UserOnlineController extends BaseCRUDController<UserOnline, String> {

    private UserOnlineService userOnlineService;

    @Autowired
    private OnlineSessionDAO onlineSessionDAO;

    @Autowired
    public UserOnlineController(UserOnlineService userOnlineService) {
        super(userOnlineService);
        this.userOnlineService = userOnlineService;
    }


    @RequestMapping("/{id}/forceLogout")
    public String forceLogout(@PathVariable(value = "id") UserOnline online) {
        if(online == null) {
            return redirectToUrl(null);
        }

        OnlineSession onlineSession = (OnlineSession) onlineSessionDAO.readSession(online.getId());
        if(onlineSession != null) {
            onlineSession.setStatus(OnlineSession.OnlineStatus.force_logout);
        }
        online.setStatus(OnlineSession.OnlineStatus.force_logout);
        userOnlineService.update(online);

        return redirectToUrl(null);
    }

}
