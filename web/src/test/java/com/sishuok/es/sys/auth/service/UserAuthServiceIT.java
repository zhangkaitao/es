/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.es.sys.auth.service;

import com.sishuok.es.common.Constants;
import com.sishuok.es.common.repository.hibernate.HibernateUtils;
import com.sishuok.es.sys.user.entity.User;
import com.sishuok.es.sys.user.service.UserService;
import com.sishuok.es.test.BaseIT;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 13-5-2 下午3:54
 * <p>Version: 1.0
 */
public class UserAuthServiceIT extends BaseIT {
    @Autowired
    private UserAuthService userAuthService;
    @Autowired
    private UserService userService;

    @Before
    public void setUp() {
        setSqlScriptEncoding(Constants.ENCODING);
        executeSqlScript("sql/intergration-test-clear-all-data.sql", false);
        executeSqlScript("sql/intergration-test-resource-permission-role-data.sql", false);
        //clear cache
        userService.delete(1L);
        HibernateUtils.evictLevel1Cache(entityManager);
        HibernateUtils.evictLevel2Cache(entityManager);

    }

    ///////////////////////////用户相关
    @Test
    public void testUserAuth() {
        executeSqlScript("sql/intergration-test-user-data.sql", false);

        User user = userService.findOne(1L);
        Set<String> roles = userAuthService.findStringRoles(user);

        Assert.assertTrue(roles.contains("admin"));
        Assert.assertTrue(roles.contains("test"));

        Assert.assertFalse(roles.contains("none"));

        Set<String> permissions = userAuthService.findStringPermissions(user);

        Assert.assertTrue(permissions.contains("example:example:all"));
        Assert.assertTrue(permissions.contains("example:example:save"));

        Assert.assertTrue(permissions.contains("example:upload:all"));
        Assert.assertTrue(permissions.contains("example:upload:update"));

        Assert.assertFalse(permissions.contains("example:deleted:all"));
        Assert.assertFalse(permissions.contains("example:example:none"));
    }


    @Test
    public void testDefaultUserGroupAuth() {
        executeSqlScript("sql/intergration-test-defualt-user-group-data.sql", false);

        User user = userService.findOne(1L);
        Set<String> roles = userAuthService.findStringRoles(user);

        Assert.assertTrue(roles.contains("admin"));

        Assert.assertFalse(roles.contains("test"));
        Assert.assertFalse(roles.contains("none"));

        Set<String> permissions = userAuthService.findStringPermissions(user);

        Assert.assertTrue(permissions.contains("example:example:all"));
        Assert.assertTrue(permissions.contains("example:example:save"));

        Assert.assertFalse(permissions.contains("example:upload:all"));
        Assert.assertFalse(permissions.contains("example:upload:update"));

        Assert.assertFalse(permissions.contains("example:deleted:all"));
        Assert.assertFalse(permissions.contains("example:example:none"));
    }


    @Test
    public void testUserGroupWithUniqueUserAuth() {
        executeSqlScript("sql/intergration-test-user-group-unique-data.sql", false);

        User user = userService.findOne(1L);
        Set<String> roles = userAuthService.findStringRoles(user);

        Assert.assertTrue(roles.contains("admin"));
        Assert.assertTrue(roles.contains("test"));

        Assert.assertFalse(roles.contains("none"));

        Set<String> permissions = userAuthService.findStringPermissions(user);

        Assert.assertTrue(permissions.contains("example:example:all"));
        Assert.assertTrue(permissions.contains("example:example:save"));

        Assert.assertTrue(permissions.contains("example:upload:all"));
        Assert.assertTrue(permissions.contains("example:upload:update"));

        Assert.assertFalse(permissions.contains("example:deleted:all"));
        Assert.assertFalse(permissions.contains("example:example:none"));
    }

    @Test
    public void testUserGroupWithRangeUserAuth() {
        executeSqlScript("sql/intergration-test-user-group-range-data.sql", false);
        User user = userService.findOne(1L);

        Set<String> roles = userAuthService.findStringRoles(user);

        Assert.assertTrue(roles.contains("admin"));
        Assert.assertTrue(roles.contains("test"));

        Assert.assertFalse(roles.contains("none"));

        Set<String> permissions = userAuthService.findStringPermissions(user);

        Assert.assertTrue(permissions.contains("example:example:all"));
        Assert.assertTrue(permissions.contains("example:example:save"));

        Assert.assertTrue(permissions.contains("example:upload:all"));
        Assert.assertTrue(permissions.contains("example:upload:update"));

        Assert.assertFalse(permissions.contains("example:deleted:all"));
        Assert.assertFalse(permissions.contains("example:example:none"));
    }


    /////////////////////////////组织机构相关
    @Test
    public void testOrganizationAuth() {

        executeSqlScript("sql/intergration-test-organization-data.sql", false);
        User user = userService.findOne(1L);

        Set<String> roles = userAuthService.findStringRoles(user);

        Assert.assertTrue(roles.contains("admin"));

        Assert.assertFalse(roles.contains("test"));
        Assert.assertFalse(roles.contains("none"));

        Set<String> permissions = userAuthService.findStringPermissions(user);

        Assert.assertTrue(permissions.contains("example:example:all"));
        Assert.assertTrue(permissions.contains("example:example:save"));

        Assert.assertFalse(permissions.contains("example:upload:all"));
        Assert.assertFalse(permissions.contains("example:upload:update"));

        Assert.assertFalse(permissions.contains("example:deleted:all"));
        Assert.assertFalse(permissions.contains("example:example:none"));
    }

    @Test
    public void testOrganizationWithInheritAuth() {

        executeSqlScript("sql/intergration-test-organization-with-inherit-data.sql", false);
        User user = userService.findOne(1L);

        Set<String> roles = userAuthService.findStringRoles(user);

        Assert.assertTrue(roles.contains("admin"));

        Assert.assertTrue(roles.contains("test"));
        Assert.assertFalse(roles.contains("none"));

        Set<String> permissions = userAuthService.findStringPermissions(user);

        Assert.assertTrue(permissions.contains("example:example:all"));
        Assert.assertTrue(permissions.contains("example:example:save"));

        Assert.assertTrue(permissions.contains("example:upload:all"));
        Assert.assertTrue(permissions.contains("example:upload:update"));

        Assert.assertFalse(permissions.contains("example:deleted:all"));
        Assert.assertFalse(permissions.contains("example:example:none"));
    }


    @Test
    public void testOrganizationGroupAuth() {

        executeSqlScript("sql/intergration-test-organization-group-data.sql", false);
        User user = userService.findOne(1L);

        Set<String> roles = userAuthService.findStringRoles(user);

        Assert.assertTrue(roles.contains("admin"));

        Assert.assertTrue(roles.contains("test"));
        Assert.assertFalse(roles.contains("none"));

        Set<String> permissions = userAuthService.findStringPermissions(user);

        Assert.assertTrue(permissions.contains("example:example:all"));
        Assert.assertTrue(permissions.contains("example:example:save"));

        Assert.assertTrue(permissions.contains("example:upload:all"));
        Assert.assertTrue(permissions.contains("example:upload:update"));

        Assert.assertFalse(permissions.contains("example:deleted:all"));
        Assert.assertFalse(permissions.contains("example:example:none"));
    }


    /////////////////////////////工作职务相关
    @Test
    public void testJobAuth() {

        executeSqlScript("sql/intergration-test-job-data.sql", false);
        User user = userService.findOne(1L);

        Set<String> roles = userAuthService.findStringRoles(user);

        Assert.assertTrue(roles.contains("admin"));

        Assert.assertFalse(roles.contains("test"));
        Assert.assertFalse(roles.contains("none"));

        Set<String> permissions = userAuthService.findStringPermissions(user);

        Assert.assertTrue(permissions.contains("example:example:all"));
        Assert.assertTrue(permissions.contains("example:example:save"));

        Assert.assertFalse(permissions.contains("example:upload:all"));
        Assert.assertFalse(permissions.contains("example:upload:update"));

        Assert.assertFalse(permissions.contains("example:deleted:all"));
        Assert.assertFalse(permissions.contains("example:example:none"));
    }

    @Test
    public void testJobWithInheritAuth() {
        executeSqlScript("sql/intergration-test-job-with-inherit-data.sql", false);
        User user = userService.findOne(1L);

        Set<String> roles = userAuthService.findStringRoles(user);

        Assert.assertTrue(roles.contains("admin"));

        Assert.assertTrue(roles.contains("test"));
        Assert.assertFalse(roles.contains("none"));

        Set<String> permissions = userAuthService.findStringPermissions(user);

        Assert.assertTrue(permissions.contains("example:example:all"));
        Assert.assertTrue(permissions.contains("example:example:save"));

        Assert.assertTrue(permissions.contains("example:upload:all"));
        Assert.assertTrue(permissions.contains("example:upload:update"));

        Assert.assertFalse(permissions.contains("example:deleted:all"));
        Assert.assertFalse(permissions.contains("example:example:none"));
    }


    /////////////////////////////组织机构和工作职务混合
    @Test
    public void testOrganizationAndJobAuth() {
        executeSqlScript("sql/intergration-test-organization-and-job-data.sql", false);

        User user = userService.findOne(1L);

        Set<String> roles = userAuthService.findStringRoles(user);
        Assert.assertTrue(roles.contains("admin"));

        Assert.assertFalse(roles.contains("test"));
        Assert.assertFalse(roles.contains("none"));

        Set<String> permissions = userAuthService.findStringPermissions(user);

        Assert.assertTrue(permissions.contains("example:example:all"));
        Assert.assertTrue(permissions.contains("example:example:save"));

        Assert.assertFalse(permissions.contains("example:upload:all"));
        Assert.assertFalse(permissions.contains("example:upload:update"));

        Assert.assertFalse(permissions.contains("example:deleted:all"));
        Assert.assertFalse(permissions.contains("example:example:none"));
    }


    ///////////////////////////all

    @Test
    public void testAllAuth() {
        executeSqlScript("sql/intergration-test-all-data.sql", false);
        User user = userService.findOne(1L);

        Set<String> roles = userAuthService.findStringRoles(user);

        Assert.assertTrue(roles.contains("admin"));

        Assert.assertTrue(roles.contains("test"));
        Assert.assertFalse(roles.contains("none"));

        Set<String> permissions = userAuthService.findStringPermissions(user);

        Assert.assertTrue(permissions.contains("example:example:all"));
        Assert.assertTrue(permissions.contains("example:example:save"));

        Assert.assertTrue(permissions.contains("example:upload:all"));
        Assert.assertTrue(permissions.contains("example:upload:update"));

        Assert.assertFalse(permissions.contains("example:deleted:all"));
        Assert.assertFalse(permissions.contains("example:example:none"));
    }

}
