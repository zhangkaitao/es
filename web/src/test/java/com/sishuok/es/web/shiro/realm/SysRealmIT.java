/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.es.web.shiro.realm;

import com.sishuok.es.web.admin.sys.user.entity.SysUser;
import com.sishuok.es.web.admin.sys.user.entity.SysUserStatus;
import com.sishuok.es.web.admin.sys.user.exception.SysUserBlockedException;
import com.sishuok.es.web.admin.sys.user.exception.SysUserNotExistsException;
import com.sishuok.es.web.admin.sys.user.exception.SysUserPasswordNotMatchException;
import com.sishuok.es.web.admin.sys.user.exception.SysUserPasswordRetryLimitExceedException;
import com.sishuok.es.web.admin.sys.user.service.PasswordService;
import com.sishuok.es.web.admin.sys.user.service.SysUserService;
import com.sishuok.es.web.test.BaseIT;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 13-3-12 下午9:36
 * <p>Version: 1.0
 */
public class SysRealmIT extends BaseIT {


    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private PasswordService passwordService;
    @Autowired
    private SysUserRealm sysUserRealm;

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
        createSysUser(username, password);

        UsernamePasswordToken upToken = new UsernamePasswordToken(username, password);
        Subject subject = SecurityUtils.getSubject();
        subject.login(upToken);
        Assert.assertEquals(username, subject.getPrincipal());

    }

    @Test(expected = UnknownAccountException.class)
    public void testLoginFailWithSysUserNotExists() {
        createSysUser(username, password);

        UsernamePasswordToken upToken = new UsernamePasswordToken(username + "1", password);
        Subject subject = SecurityUtils.getSubject();
        subject.login(upToken);
    }

    @Test(expected = AuthenticationException.class)
    public void testLoginFailWithSysUserPasswordNotMatch() {
        createSysUser(username, password);

        UsernamePasswordToken upToken = new UsernamePasswordToken(username, password + "1");
        Subject subject = SecurityUtils.getSubject();
        subject.login(upToken);
    }


    @Test(expected = LockedAccountException.class)
    public void testLoginFailWithSysBlocked() {
        SysUser sysUser = createSysUser(username, password);
        sysUserService.changeStatus(sysUser, SysUserStatus.blocked, "test");

        UsernamePasswordToken upToken = new UsernamePasswordToken(username, password);
        Subject subject = SecurityUtils.getSubject();
        subject.login(upToken);

    }

    @Test(expected = ExcessiveAttemptsException.class)
    public void testLoginFailWithRetryLimitExceed() {
        createSysUser(username, password);
        for(int i = 0; i < maxtRetryCount; i++) {
            try {
                UsernamePasswordToken upToken = new UsernamePasswordToken(username, password + "1");
                Subject subject = SecurityUtils.getSubject();
                subject.login(upToken);
            } catch (AuthenticationException e) {
            }
        }

        UsernamePasswordToken upToken = new UsernamePasswordToken(username, password);
        Subject subject = SecurityUtils.getSubject();
        subject.login(upToken);
    }


    private SysUser createSysUser(String username, String password) {
        SysUser sysUser = new SysUser();
        sysUser.setUsername(username);
        sysUser.setPassword(password);
        sysUserService.saveAndFlush(sysUser);
        return sysUser;
    }

}
