/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.es.common.repository;

import com.google.common.collect.Lists;
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
import javax.persistence.criteria.*;
import java.util.Arrays;
import java.util.Date;
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


    @Test
    public void testFindAll() {
        int count = 15;
        List<Long> ids = Lists.newArrayList();
        List<Date> birthdayList = Lists.newArrayList();
        String realnamePrefix = "zhang";
        for (int i = 0; i < count; i++) {
            User user = createUser();
            user.getBaseInfo().setRealname(realnamePrefix + i);
            userRepository.save(user);
            ids.add(user.getId());
            birthdayList.add(user.getBaseInfo().getBirthday());
        }

        System.out.println(birthdayList);
        String ql = "from User u where u.id in(?1) and u.baseInfo.realname like ?2 and u.baseInfo.birthday in (?3)";
        assertEquals(count, userRepositoryImpl.findAll(ql, null, ids, realnamePrefix + "%", birthdayList).size());
    }

    @Test
    public void testCountAll() {
        int count = 15;
        List<Long> ids = Lists.newArrayList();
        List<Date> birthdayList = Lists.newArrayList();
        String realnamePrefix = "zhang";
        for (int i = 0; i < count; i++) {
            User user = createUser();
            user.getBaseInfo().setRealname(realnamePrefix + i);
            userRepository.save(user);
            ids.add(user.getId());
            birthdayList.add(user.getBaseInfo().getBirthday());
        }

        String ql = "select count(o) from User u where u.id in(?1) and u.baseInfo.realname like ?2 and u.baseInfo.birthday in (?3)";
        assertEquals(Long.valueOf(count), userRepositoryImpl.countAll(ql, ids, realnamePrefix + "%", birthdayList));
    }

    @Test
    public void testFindOne() {
        int count = 15;
        User lastUser = null;
        String realnamePrefix = "zhang";
        for (int i = 0; i < count; i++) {
            User user = createUser();
            user.getBaseInfo().setRealname(realnamePrefix + i);
            lastUser = userRepository.save(user);
        }
        String ql = "select u from User u where u=?1 and u.baseInfo.realname like ?2";
        assertEquals(lastUser, userRepositoryImpl.findOne(ql, lastUser, realnamePrefix + "%"));
    }

    @Test
    public void testBatchUpdate() {
        int count = 15;
        String realname = "123321";
        User lastUser = null;
        for (int i = 0; i < count; i++) {
            User user = createUser();
            user.getBaseInfo().setRealname(realname);
            lastUser = userRepository.save(user);
        }

        String ql = "update BaseInfo set realname=?1";
        assertEquals(count, userRepositoryImpl.batchUpdate(ql, realname));

        String findOneQL = "select u from User u where u=?1";
        User user = userRepositoryImpl.findOne(findOneQL, lastUser);
        assertEquals(realname, user.getBaseInfo().getRealname());

    }

}
