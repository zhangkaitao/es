/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.es.sys.user.service;

import com.sishuok.es.sys.user.entity.User;
import com.sishuok.es.sys.user.repository.UserRepository;
import com.sishuok.es.test.BaseIT;
import org.junit.After;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 13-4-5 下午3:24
 * <p>Version: 1.0
 */
public class BaseUserIT extends BaseIT {


    @Autowired
    UserService userService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordService passwordService;

    int maxtRetryCount = 10;

    String username = "__z__hang123";
    String email = "zhang@163.com";
    String mobilePhoneNumber = "15612345678";
    String password = "12345";

    @Before
    public void setUp() {
        userService.setPasswordService(passwordService);
        passwordService.setMaxRetryCount(maxtRetryCount);

        userRepository.deleteAll();
        userService.findAll();
    }

    @After
    public void tearDown() {
        passwordService.clearLoginRecordCache(username);
        passwordService.clearLoginRecordCache(email);
        passwordService.clearLoginRecordCache(mobilePhoneNumber);
    }



    User createUser(String username, String email, String mobilePhoneNumber, String password) {
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setMobilePhoneNumber(mobilePhoneNumber);
        user.setPassword(password);
        userService.saveAndFlush(user);
        return user;
    }

    User createDefaultUser() {
        return createUser(username, email, mobilePhoneNumber, password);
    }

}
