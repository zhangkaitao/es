/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.es.sys.role.task;

import com.google.common.collect.Sets;
import com.sishuok.es.sys.permission.entity.Permission;
import com.sishuok.es.sys.permission.entity.Role;
import com.sishuok.es.sys.permission.entity.RoleResourcePermission;
import com.sishuok.es.sys.permission.service.PermissionService;
import com.sishuok.es.sys.permission.service.RoleService;
import com.sishuok.es.sys.permission.task.RoleClearRelationTask;
import com.sishuok.es.sys.resource.entity.Resource;
import com.sishuok.es.sys.resource.service.ResourceService;
import com.sishuok.es.test.BaseIT;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 13-5-18 下午4:42
 * <p>Version: 1.0
 */
public class RoleClearRelationTaskIT extends BaseIT {

    @Autowired
    private RoleClearRelationTask roleClearRelationTask;

    @Autowired
    private RoleService roleService;

    @Autowired
    private ResourceService resourceService;
    @Autowired
    private PermissionService permissionService;

    @Test
    public void testClearDeletedRoleRelation() {

        Resource resource1 = new Resource();
        resource1.setName("123");
        resource1.setIdentity("123");
        resourceService.save(resource1);

        Resource resource2 = new Resource();
        resource2.setName("234");
        resource2.setIdentity("234");
        resourceService.save(resource2);

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

        role.addResourcePermission(
                new RoleResourcePermission(
                        resource2.getId(),
                        Sets.newHashSet(permission1.getId(), permission2.getId())));


        roleService.save(role);
        clear();
        Role dbRole = roleService.findOne(role.getId());

        Assert.assertEquals(2, dbRole.getResourcePermissions().size());
        Assert.assertEquals(2, dbRole.getResourcePermissions().get(0).getPermissionIds().size());


        resourceService.delete(resource1);
        permissionService.delete(permission1);
        clear();

        roleClearRelationTask.clearDeletedRoleRelation();


        role = roleService.findOne(role.getId());

        Assert.assertEquals(1, role.getResourcePermissions().size());
        Assert.assertEquals(1, role.getResourcePermissions().get(0).getPermissionIds().size());


    }


}
