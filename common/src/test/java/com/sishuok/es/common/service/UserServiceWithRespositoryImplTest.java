/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.es.common.service;

import com.sishuok.es.common.entity.User;
import com.sishuok.es.common.entity.search.Searchable;
import com.sishuok.es.common.entity.search.builder.SearchableBuilder;
import com.sishuok.es.common.repository.UserRepositoryImpl;
import com.sishuok.es.common.test.BaseUserIT;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 13-1-17 下午7:55
 * <p>Version: 1.0
 */
public class UserServiceWithRespositoryImplTest extends UserServiceTest {


    @Autowired
    private UserRepositoryImpl userRepositoryImpl;

    @Before
    public void setUp() {
        userService.setBaseRepositoryImpl(userRepositoryImpl);
    }


}
