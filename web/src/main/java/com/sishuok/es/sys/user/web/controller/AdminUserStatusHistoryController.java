/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.es.sys.user.web.controller;

import com.sishuok.es.common.entity.search.Searchable;
import com.sishuok.es.common.web.bind.annotation.PageableDefaults;
import com.sishuok.es.common.web.controller.BaseCRUDController;
import com.sishuok.es.sys.user.entity.UserStatus;
import com.sishuok.es.sys.user.entity.UserStatusHistory;
import com.sishuok.es.sys.user.service.UserStatusHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 13-1-28 下午4:29
 * <p>Version: 1.0
 */
@Controller
@RequestMapping(value = "/admin/sys/user/statusHistory")
public class AdminUserStatusHistoryController extends BaseCRUDController<UserStatusHistory, Long> {

    private UserStatusHistoryService userStatusHistoryService;


    @Autowired
    public AdminUserStatusHistoryController(UserStatusHistoryService userStatusHistoryService) {
        super(userStatusHistoryService);
        this.userStatusHistoryService = userStatusHistoryService;
    }

    @Override
    protected void setCommonData(Model model) {
        model.addAttribute("statusList", UserStatus.values());
    }

    @RequestMapping(method = RequestMethod.GET)
    @PageableDefaults(sort = "id=desc")
    @Override
    public String list(Searchable searchable, Model model) {
        setCommonData(model);
        return super.list(searchable, model);
    }

}
