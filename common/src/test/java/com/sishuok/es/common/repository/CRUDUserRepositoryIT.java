/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.es.common.repository;

import com.sishuok.es.common.entity.SchoolType;
import com.sishuok.es.common.entity.User;
import com.sishuok.es.common.test.BaseUserIT;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.junit.Assert.*;

/**
 * <p>User Repository集成测试</p>
 * <p>测试时使用内嵌的HSQL内存数据库完成</p>
 * <p>User: Zhang Kaitao
 * <p>Date: 13-1-14 下午2:36
 * <p>Version: 1.0
 */

public class CRUDUserRepositoryIT extends BaseUserIT {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    private User user;

    @Before
    public void setUp() {
        user = createUser();
    }

    @After
    public void tearDown() {
        user = null;
    }

    @Test
    public void testSave() {
        User dbUser = userRepository.save(user);
        assertNotNull(dbUser.getId());
    }

    @Test
    public void testUpdate() {
        User dbUser = userRepository.save(user);
        clear();

        String newUsername = "zhang$$$$" + System.currentTimeMillis();
        dbUser.setUsername(newUsername);
        userRepository.save(dbUser);

        clear();

        assertEquals(newUsername, userRepository.findOne(dbUser.getId()).getUsername());
    }


    @Test
    public void testUpdateRealname() {
        String realname = "lisi";
        User dbUser = userRepository.save(user);
        userRepository.updateRealname(realname, dbUser.getId());

        userRepository.flush();
        entityManager.clear();

        dbUser = userRepository.findOne(dbUser.getId());
        assertEquals(realname, dbUser.getBaseInfo().getRealname());
    }

    @Test
    public void testUpdateRealnameWithNamedParam() {
        String realname = "lisi";
        User dbUser = userRepository.save(user);
        userRepository.updateRealnameWithNamedParam(realname, dbUser.getId());

        userRepository.flush();
        entityManager.clear();

        dbUser = userRepository.findOne(dbUser.getId());
        assertEquals(realname, dbUser.getBaseInfo().getRealname());
    }

    @Test
    public void testDeleteByUsername() {
        User dbUser = userRepository.save(user);
        userRepository.deleteBaseInfoByUser(dbUser.getId());

        userRepository.flush();
        entityManager.clear();

        dbUser = userRepository.findOne(dbUser.getId());
        assertNull(dbUser.getBaseInfo());
    }

    @Test
    public void testFindByUsername() {
        userRepository.save(user);
        User dbUser = userRepository.findByUsername(user.getUsername());

        assertNotNull(dbUser);
    }


    @Test
    public void testFindByBaseInfoSex() {
        userRepository.save(user);
        User dbUser = userRepository.findByBaseInfoSex(user.getBaseInfo().getSex());
        assertNotNull(dbUser);
    }

    @Test
    public void testFindByBaseInfoSexAndShcoolInfoType() {
        userRepository.save(user);
        User dbUser = userRepository.findByBaseInfoSexAndShcoolInfoSetType(
                user.getBaseInfo().getSex(),
                SchoolType.secondary_school);

        assertNotNull(dbUser);
    }
}
