/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package org.apache.shiro.realm;

import com.sishuok.es.sys.user.entity.User;
import com.sishuok.es.sys.user.entity.UserStatus;
import com.sishuok.es.sys.user.service.PasswordService;
import com.sishuok.es.sys.user.service.UserService;
import com.sishuok.es.test.BaseIT;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 13-3-12 下午9:36
 * <p>Version: 1.0
 */
public class UserRealmIT extends BaseIT {


    @Autowired
    private UserService userService;
    @Autowired
    private PasswordService passwordService;
    @Autowired
    private UserRealm userRealm;

    int maxtRetryCount = 10;


    private String username = "__z__hang123";
    private String email = "zhang@163.com";
    private String mobilePhoneNumber = "13112345678";
    String password = "12345";

    @Before
    public void setUp() {
        userService.setPasswordService(passwordService);
        passwordService.setMaxRetryCount(maxtRetryCount);

        User user = userService.findByUsername(username);
        if(user != null) {
            userService.delete(user);
        }
        user = userService.findByEmail(email);
        if(user != null) {
            userService.delete(user);
        }
        user = userService.findByMobilePhoneNumber(mobilePhoneNumber);
        if(user != null) {
            userService.delete(user);
        }
    }


    @After
    public void tearDown() {
        passwordService.clearLoginRecordCache(username);
    }


    @Test
    public void testLoginSuccess() {
        createUser(username, password);

        UsernamePasswordToken upToken = new UsernamePasswordToken(username, password);
        Subject subject = SecurityUtils.getSubject();
        subject.login(upToken);
        Assert.assertEquals(username, subject.getPrincipal());
    }

    @Test(expected = UnknownAccountException.class)
    public void testLoginFailWithUserNotExists() {
        createUser(username, password);

        UsernamePasswordToken upToken = new UsernamePasswordToken(username + "1", password);
        Subject subject = SecurityUtils.getSubject();
        subject.login(upToken);
    }

    @Test(expected = AuthenticationException.class)
    public void testLoginFailWithUserPasswordNotMatch() {
        createUser(username, password);

        UsernamePasswordToken upToken = new UsernamePasswordToken(username, password + "1");
        Subject subject = SecurityUtils.getSubject();
        subject.login(upToken);
    }


    @Test(expected = LockedAccountException.class)
    public void testLoginFailWithSysBlocked() {
        User user = createUser(username, password);
        userService.changeStatus(user, user, UserStatus.blocked, "sql");

        UsernamePasswordToken upToken = new UsernamePasswordToken(username, password);
        Subject subject = SecurityUtils.getSubject();
        subject.login(upToken);

    }

    @Test(expected = ExcessiveAttemptsException.class)
    public void testLoginFailWithRetryLimitExceed() {
        createUser(username, password);
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


    private User createUser(String username, String password) {
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setMobilePhoneNumber(mobilePhoneNumber);
        user.setPassword(password);
        userService.saveAndFlush(user);
        return user;
    }

}
