/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package org.apache.shiro.realm;

import com.sishuok.es.sys.user.entity.User;
import com.sishuok.es.sys.user.entity.UserStatus;
import com.sishuok.es.sys.user.service.BaseUserIT;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.junit.Assert;
import org.junit.Test;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 13-3-12 下午9:36
 * <p>Version: 1.0
 */
public class UserRealmIT extends BaseUserIT {

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
        for (int i = 0; i < maxtRetryCount; i++) {
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
