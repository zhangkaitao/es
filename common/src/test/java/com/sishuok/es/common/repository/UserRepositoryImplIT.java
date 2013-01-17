/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.es.common.repository;

import com.sishuok.es.common.entity.BaseInfo;
import com.sishuok.es.common.entity.SchoolInfo;
import com.sishuok.es.common.entity.User;
import com.sishuok.es.common.test.BaseUserIT;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * <p>User Repository集成测试</p>
 * <p>测试时使用内嵌的HSQL内存数据库完成</p>
 * <p>User: Zhang Kaitao
 * <p>Date: 13-1-14 下午2:36
 * <p>Version: 1.0
 */

public class UserRepositoryImplIT extends BaseUserIT {

    @Autowired
    private UserRepositoryImpl userRepositoryImpl;
    @Autowired
    private UserRepository userRepository;

    private User user;

    @PersistenceContext
    private EntityManager entityManager;

    @Before
    public void setUp() {
        user = createUser();
    }

    @After
    public void tearDown() {
        user = null;
    }

    @Test
    public void testFindBaseInfoByUserId() {
        userRepository.save(user);

        userRepository.flush();
        entityManager.clear();

        BaseInfo baseInfo = userRepositoryImpl.findBaseInfoByUserId(user.getId());
        assertNotNull(baseInfo);
    }


    @Test
    public void findAllSchoolTypeByUserId() {
        userRepository.save(user);

        userRepository.flush();
        entityManager.clear();

        List<SchoolInfo> schoolInfoList = userRepositoryImpl.findAllSchoolTypeByUserId(user.getId());
        assertEquals(user.getSchoolInfoSet().size(), schoolInfoList.size());
    }

}
