/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.es.sys.user.service;

import com.sishuok.es.sys.user.entity.User;
import com.sishuok.es.sys.user.entity.UserStatus;
import com.sishuok.es.sys.user.exception.UserBlockedException;
import com.sishuok.es.sys.user.exception.UserNotExistsException;
import com.sishuok.es.sys.user.exception.UserPasswordNotMatchException;
import com.sishuok.es.sys.user.exception.UserPasswordRetryLimitExceedException;
import org.junit.Assert;
import org.junit.Test;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 13-3-11 下午8:55
 * <p>Version: 1.0
 */
public class UserServiceIT extends BaseUserIT {


    @Test
    public void testLoginSuccessWithUsername() {
        User user = createUser(username, email, mobilePhoneNumber, password);
        Assert.assertNotNull(userService.login(user.getUsername(), password));
    }

    @Test
    public void testLoginSuccessWithEmail() {
        User user = createUser(username, email, mobilePhoneNumber, password);
        Assert.assertNotNull(userService.login(user.getEmail(), password));
    }


    @Test
    public void testLoginSuccessWithMobilePhoneNumber() {
        User user = createUser(username, email, mobilePhoneNumber, password);
        Assert.assertNotNull(userService.login(user.getMobilePhoneNumber(), password));
    }


    @Test(expected = UserNotExistsException.class)
    public void testLoginFailWithUserNotExists() {
        User user = createUser(username, email, mobilePhoneNumber, password);
        userService.login(username + "1", password);
    }


    @Test(expected = UserNotExistsException.class)
    public void testLoginFailWithUserDeleted() {
        User user = createUser(username, email, mobilePhoneNumber, password);
        userService.delete(user);
        clear();
        userService.login(username, password);
    }

    @Test(expected = UserPasswordNotMatchException.class)
    public void testLoginFailWithUserPasswordNotMatch() {
        User user = createUser(username, email, mobilePhoneNumber, password);
        userService.login(username, password + "1");
    }


    @Test(expected = UserBlockedException.class)
    public void testLoginFailWithStatusBlocked() {
        User user = createUser(username, email, mobilePhoneNumber, password);
        userService.changeStatus(user, user, UserStatus.blocked, "sql");
        userService.login(username, password);
    }

    @Test(expected = UserPasswordRetryLimitExceedException.class)
    public void testLoginFailWithRetryLimitExceed() {
        User user = createUser(username, email, mobilePhoneNumber, password);
        for (int i = 0; i < maxtRetryCount; i++) {
            try {
                userService.login(username, password + "1");
            } catch (UserPasswordNotMatchException e) {
            }
        }
        userService.login(username, password + "1");
        passwordService.clearLoginRecordCache(username);
    }


}
