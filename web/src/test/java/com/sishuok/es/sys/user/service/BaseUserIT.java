/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.es.sys.user.service;

import com.sishuok.es.sys.user.entity.User;
import com.sishuok.es.test.BaseIT;
import org.junit.After;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 13-4-5 下午3:24
 * <p>Version: 1.0
 */
public abstract class BaseUserIT extends BaseIT {


    @Autowired
    protected UserService userService;

    @Autowired
    protected PasswordService passwordService;

    protected int maxtRetryCount = 10;

    protected String username = "__z__hang123";
    protected String email = "zhang@163.com";
    protected String mobilePhoneNumber = "15612345678";
    protected String password = "12345";

    @Before
    public void setUp() {
        userService.setPasswordService(passwordService);
        passwordService.setMaxRetryCount(maxtRetryCount);

        userService.setPasswordService(passwordService);
        passwordService.setMaxRetryCount(maxtRetryCount);

        User user = userService.findByUsername(username);
        if (user != null) {
            userService.delete(user);//因为用户是逻辑删除 此处的目的主要是清 缓存
            delete(user);              //所以还需要物理删除
        }
        user = userService.findByEmail(email);
        if (user != null) {
            userService.delete(user);
            delete(user);
        }
        user = userService.findByMobilePhoneNumber(mobilePhoneNumber);
        if (user != null) {
            userService.delete(user);
            delete(user);
        }
        clear();
    }

    @After
    public void tearDown() {
        passwordService.clearLoginRecordCache(username);
        passwordService.clearLoginRecordCache(email);
        passwordService.clearLoginRecordCache(mobilePhoneNumber);
    }


    protected User createUser(String username, String email, String mobilePhoneNumber, String password) {
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setMobilePhoneNumber(mobilePhoneNumber);
        user.setPassword(password);
        userService.saveAndFlush(user);
        return user;
    }

    protected User createDefaultUser() {
        return createUser(username, email, mobilePhoneNumber, password);
    }

}
