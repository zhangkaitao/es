/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.es.common.service;

import com.sishuok.es.common.repository.UserRepository2;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 13-1-17 下午7:55
 * <p>Version: 1.0
 */
public class UserServiceWithRespository2ImplTest extends UserServiceTest {


    @Autowired
    private UserService2 userService2;

    @Before
    public void setUp() {
        userService = userService2;
    }


}
