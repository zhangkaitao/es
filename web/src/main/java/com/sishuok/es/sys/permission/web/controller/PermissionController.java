/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.es.sys.permission.web.controller;

import com.sishuok.es.common.Constants;
import com.sishuok.es.common.entity.enums.AvailableEnum;
import com.sishuok.es.common.web.controller.BaseCRUDController;
import com.sishuok.es.sys.permission.entity.Permission;
import com.sishuok.es.sys.permission.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.annotation.DefaultAnnotationHandlerMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 13-1-28 下午4:29
 * <p>Version: 1.0
 */
@Controller
@RequestMapping(value = "/admin/sys/permission/permission")
public class PermissionController extends BaseCRUDController<Permission, Long> {

    private PermissionService permissionService;

    @Autowired
    public PermissionController(PermissionService permissionService) {
        super(permissionService);
        this.permissionService = permissionService;
    }


    @Override
    protected void setCommonData(Model model) {
        super.setCommonData(model);
        model.addAttribute("availableList", AvailableEnum.values());
    }

    @RequestMapping(value = "/changeStatus/{newStatus}")
    public String changeStatus(
            HttpServletRequest request,
            @PathVariable("newStatus") Boolean newStatus,
            @RequestParam("ids") Long[] ids
    ) {


        for(Long id : ids) {
            Permission permission = permissionService.findOne(id);
            permission.setShow(newStatus);
            permissionService.update(permission);
        }


        return "redirect:" + request.getAttribute(Constants.BACK_URL);
    }




}
