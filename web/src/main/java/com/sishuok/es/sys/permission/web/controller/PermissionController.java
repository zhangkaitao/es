/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.es.sys.permission.web.controller;

import com.sishuok.es.common.web.controller.BaseCRUDController;
import com.sishuok.es.sys.permission.entity.Permission;
import com.sishuok.es.sys.permission.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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



}
