/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.es.common.service;

import com.sishuok.es.common.entity.User;
import com.sishuok.es.common.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 13-1-17 下午7:52
 * <p>Version: 1.0
 */
@Service()
public class UserService extends BaseService<User, Long> {


    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        setBaseRepository(userRepository);
    }
}
