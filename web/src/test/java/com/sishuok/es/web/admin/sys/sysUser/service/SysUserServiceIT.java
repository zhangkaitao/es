/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.es.web.admin.sys.sysUser.service;

import com.sishuok.es.web.admin.sys.user.entity.SysUser;
import com.sishuok.es.web.admin.sys.user.entity.SysUserStatus;
import com.sishuok.es.web.admin.sys.user.exception.SysUserBlockedException;
import com.sishuok.es.web.admin.sys.user.exception.SysUserNotExistsException;
import com.sishuok.es.web.admin.sys.user.exception.SysUserPasswordNotMatchException;
import com.sishuok.es.web.admin.sys.user.exception.SysUserPasswordRetryLimitExceedException;
import com.sishuok.es.web.admin.sys.user.service.PasswordService;
import com.sishuok.es.web.admin.sys.user.service.SysUserService;
import com.sishuok.es.web.test.BaseIT;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 13-3-11 下午8:55
 * <p>Version: 1.0
 */
public class SysUserServiceIT extends BaseIT {

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private PasswordService passwordService;

    int maxtRetryCount = 10;

    @Before
    public void setUp() {
        sysUserService.setPasswordService(passwordService);
        passwordService.setMaxRetryCount(maxtRetryCount);
    }

    private String username = "__z__hang123";
    String password = "12345";
    @Test
    public void testLoginSuccess() {
        SysUser sysUser = createSysUser(username, password);

        Assert.assertNotNull(sysUserService.login(sysUser.getUsername(), password));
    }

    @Test(expected = SysUserNotExistsException.class)
    public void testLoginFailWithSysUserNotExists() {
        SysUser sysUser = createSysUser(username, password);
        sysUserService.login(username + "1", password);
    }

    @Test(expected = SysUserPasswordNotMatchException.class)
    public void testLoginFailWithSysUserPasswordNotMatch() {
        SysUser sysUser = createSysUser(username, password);
        sysUserService.login(username, password + "1");
    }


    @Test(expected = SysUserBlockedException.class)
    public void testLoginFailWithSysBlocked() {
        SysUser sysUser = createSysUser(username, password);
        sysUserService.changeStatus(sysUser, SysUserStatus.blocked, "test");
        sysUserService.login(username, password);
    }

    @Test(expected = SysUserPasswordRetryLimitExceedException.class)
    public void testLoginFailWithRetryLimitExceed() {
        SysUser sysUser = createSysUser(username, password);
        for(int i = 0; i < maxtRetryCount; i++) {
            try {
                sysUserService.login(username, password + "1");
            } catch (SysUserPasswordNotMatchException e) {
            }
        }
        sysUserService.login(username, password + "1");
    }



    private SysUser createSysUser(String username, String password) {
        SysUser sysUser = new SysUser();
        sysUser.setUsername(username);
        sysUser.setPassword(password);
        sysUserService.saveAndFlush(sysUser);
        return sysUser;
    }


}
