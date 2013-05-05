/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.es.common.repository;

import com.sishuok.es.common.entity.User;
import com.sishuok.es.common.test.BaseUserIT;
import org.junit.Assert;
import org.junit.Before;

import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 13-5-5 上午9:10
 * <p>Version: 1.0
 */
public class BaseRepositoryImplHelperIT extends BaseUserIT {

    @PersistenceUnit
    private EntityManagerFactory entityManagerFactory;


    @Before
    public void setUp() {
        BaseRepositoryImplHelper.setEntityManagerFactory(entityManagerFactory);
    }


    public void testGetEntityManager() {
        Assert.assertNotNull(BaseRepositoryImplHelper.getEntityManager());
    }

    public void testFind() {

        User user = createUser();

//        BaseRepositoryImplHelper.find();

    }

}
