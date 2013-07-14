/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.es.index.web.controller;

import com.google.common.collect.Maps;
import com.sishuok.es.maintain.notification.service.NotificationApi;
import com.sishuok.es.personal.message.service.MessageApi;
import com.sishuok.es.sys.resource.entity.tmp.Menu;
import com.sishuok.es.sys.resource.service.ResourceService;
import com.sishuok.es.sys.user.entity.User;
import com.sishuok.es.sys.user.web.bind.annotation.CurrentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 13-1-18 下午10:15
 * <p>Version: 1.0
 */
@Controller("adminIndexController")
@RequestMapping("/admin")
public class IndexController {

    @Autowired
    private ResourceService resourceService;
    @Autowired
    private MessageApi messageApi;

    @Autowired
    private NotificationApi notificationApi;

    @RequestMapping(value = {"/{index:index;?.*}"}) //spring3.2.2 bug see  http://jinnianshilongnian.iteye.com/blog/1831408
    public String index(@CurrentUser User user, Model model) {

        List<Menu> menus = resourceService.findMenus(user);

        model.addAttribute("menus", menus);

        return "admin/index/index";
    }


    @RequestMapping(value = "/welcome")
    public String welcome() {
        return "admin/index/welcome";
    }


    /**
     * 获取页面的提示信息
     * @return
     */
    @RequestMapping(value = "/fetchTips")
    @ResponseBody
    public Object fetchTip(@CurrentUser User user) {
        Long unreadMessageCount = messageApi.countUnread(user.getId());
        List<Map<String, Object>> notifications = notificationApi.topFiveNotification(user.getId());

        Map<String, Object> result = Maps.newHashMap();

        result.put("unreadMessageCount", unreadMessageCount);
        result.put("notifications", notifications);

        return result;
    }


}
