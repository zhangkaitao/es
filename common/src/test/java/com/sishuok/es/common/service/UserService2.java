/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.es.common.service;

import com.sishuok.es.common.entity.User;
import com.sishuok.es.common.repository.BaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 13-1-17 下午7:52
 * <p>Version: 1.0
 */
@DependsOn("userRepository2")
@Service()
public class UserService2 extends UserService {

    @Autowired
    @Qualifier("userRepository2")
    @Override
    public void setBaseRepository(BaseRepository<User, Long> baseRepository) {
        super.setBaseRepository(baseRepository);
    }
}
