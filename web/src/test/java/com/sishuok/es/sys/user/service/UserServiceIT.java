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
import com.sishuok.es.test.BaseIT;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 13-3-11 下午8:55
 * <p>Version: 1.0
 */
public class UserServiceIT extends BaseIT {

    @Autowired
    private UserService userService;
    @Autowired
    private PasswordService passwordService;

    int maxtRetryCount = 10;

    private String username = "__z__hang123";
    private String email = "zhang@163.com";
    private String mobilePhoneNumber = "15612345678";
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
        passwordService.clearLoginRecordCache(email);
        passwordService.clearLoginRecordCache(mobilePhoneNumber);
    }

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
        userService.changeStatus(user, user, UserStatus.blocked, "test");
        userService.login(username, password);
    }

    @Test(expected = UserPasswordRetryLimitExceedException.class)
    public void testLoginFailWithRetryLimitExceed() {
        User user = createUser(username, email, mobilePhoneNumber, password);
        for(int i = 0; i < maxtRetryCount; i++) {
            try {
                userService.login(username, password + "1");
            } catch (UserPasswordNotMatchException e) {
            }
        }
        userService.login(username, password + "1");
        passwordService.clearLoginRecordCache(username);;
    }



    private User createUser(String username, String email, String mobilePhoneNumber, String password) {
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setMobilePhoneNumber(mobilePhoneNumber);
        user.setPassword(password);
        userService.saveAndFlush(user);
        return user;
    }


}
