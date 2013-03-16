/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.es.web.admin.sys.user.web.controller;

import com.sishuok.es.common.Constants;
import com.sishuok.es.common.entity.search.Searchable;
import com.sishuok.es.common.web.bind.annotation.PageableDefaults;
import com.sishuok.es.common.web.bind.annotation.SearchableDefaults;
import com.sishuok.es.common.web.controller.BaseCRUDController;
import com.sishuok.es.common.web.validate.ValidateResponse;
import com.sishuok.es.web.admin.sys.user.entity.SysUser;
import com.sishuok.es.web.admin.sys.user.entity.SysUserStatus;
import com.sishuok.es.web.admin.sys.user.entity.SysUserStatusHistory;
import com.sishuok.es.web.admin.sys.user.service.SysUserService;
import com.sishuok.es.web.admin.sys.user.service.SysUserStatusHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 13-1-28 下午4:29
 * <p>Version: 1.0
 */
@Controller
@RequestMapping(value = "/admin/sys/user/statusHistory")
public class SysUserStatusHistoryController extends BaseCRUDController<SysUserStatusHistory, Long> {

    private SysUserStatusHistoryService sysUserStatusHistoryService;


    @Autowired
    public SysUserStatusHistoryController(SysUserStatusHistoryService sysUserStatusHistoryService) {
        super(sysUserStatusHistoryService);
        this.sysUserStatusHistoryService = sysUserStatusHistoryService;
    }

    @Override
    protected void setCommonData(Model model) {
        model.addAttribute("statusList", SysUserStatus.values());
    }

    @RequestMapping(method = RequestMethod.GET)
    @PageableDefaults(sort = "id=desc")
    @Override
    public String list(Searchable searchable, Model model) {
        setCommonData(model);
        return super.list(searchable, model);
    }

}
