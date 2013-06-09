/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.es.index.web.controller;

import com.sishuok.es.personal.service.MessageApi;
import com.sishuok.es.sys.resource.entity.tmp.Menu;
import com.sishuok.es.sys.resource.service.ResourceService;
import com.sishuok.es.sys.user.entity.User;
import com.sishuok.es.sys.user.web.bind.annotation.CurrentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

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


    @RequestMapping(value = {"/{index:index;?.*}"}) //spring3.2.2 bug see  http://jinnianshilongnian.iteye.com/blog/1831408
    public String index(@CurrentUser User user, Model model) {

        List<Menu> menus = resourceService.findMenus(user);

        model.addAttribute("menus", menus);
        model.addAttribute("unreadMessageCount", messageApi.countUnread(user.getId()));

        return "admin/index/index";
    }


    @RequestMapping(value = "/welcome")
    public String welcome() {
        return "admin/index/welcome";
    }


}
