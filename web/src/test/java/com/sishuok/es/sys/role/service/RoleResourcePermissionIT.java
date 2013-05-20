/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.es.sys.role.service;

import com.google.common.collect.Sets;
import com.sishuok.es.sys.permission.entity.Permission;
import com.sishuok.es.sys.permission.entity.Role;
import com.sishuok.es.sys.permission.entity.RoleResourcePermission;
import com.sishuok.es.sys.permission.service.PermissionService;
import com.sishuok.es.sys.permission.service.RoleService;
import com.sishuok.es.sys.resource.entity.Resource;
import com.sishuok.es.sys.resource.service.ResourceService;
import com.sishuok.es.test.BaseIT;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 13-4-16 下午3:50
 * <p>Version: 1.0
 */
public class RoleResourcePermissionIT extends BaseIT {

    @Autowired
    private RoleService roleService;

    @Autowired
    private ResourceService resourceService;
    @Autowired
    private PermissionService permissionService;


    @Test
    public void testCascadeSave() {

        Resource resource1 = new Resource();
        resource1.setName("123");
        resource1.setIdentity("123");
        resourceService.save(resource1);

        Permission permission1 = new Permission();
        permission1.setName("123");
        permission1.setPermission("abc");
        permissionService.save(permission1);

        Permission permission2 = new Permission();
        permission2.setName("123");
        permission2.setPermission("abc");
        permissionService.save(permission2);

        Role role = new Role();
        role.setName("abc");
        role.setRole("abc");
        role.addResourcePermission(
                new RoleResourcePermission(
                        resource1.getId(),
                        Sets.newHashSet(permission1.getId(), permission2.getId())));


        roleService.save(role);
        clear();
        Role dbRole = roleService.findOne(role.getId());

        Assert.assertEquals(1, dbRole.getResourcePermissions().size());
        Assert.assertEquals(2, dbRole.getResourcePermissions().get(0).getPermissionIds().size());

    }


    @Test
    public void testCascadeDelete() {

        Resource resource1 = new Resource();
        resource1.setName("123");
        resource1.setIdentity("123");
        resourceService.save(resource1);

        Permission permission1 = new Permission();
        permission1.setName("123");
        permission1.setPermission("abc");
        permissionService.save(permission1);

        Permission permission2 = new Permission();
        permission2.setName("123");
        permission2.setPermission("abc");
        permissionService.save(permission2);

        Role role = new Role();
        role.setName("abc");
        role.setRole("abc");

        roleService.save(role);

        role.addResourcePermission(
                new RoleResourcePermission(
                        resource1.getId(),
                        Sets.newHashSet(permission1.getId(), permission2.getId())));

        clear();

        Role dbRole = roleService.findOne(role.getId());

        Assert.assertEquals(1, dbRole.getResourcePermissions().size());

        RoleResourcePermission roleResourcePermission = dbRole.getResourcePermissions().get(0);
        roleResourcePermission.getPermissionIds().remove(roleResourcePermission.getPermissionIds().iterator().next());
        //此处必须复制一份
        clear();

        dbRole = roleService.findOne(role.getId());

        Assert.assertEquals(1, dbRole.getResourcePermissions().get(0).getPermissionIds().size());

    }

}
