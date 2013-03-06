/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.es.common.repository;

import com.sishuok.es.common.entity.User;
import com.sishuok.es.common.entity.search.builder.SearchableBuilder;
import com.sishuok.es.common.entity.search.Searchable;
import com.sishuok.es.common.test.BaseUserIT;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * <p>User Repository集成测试</p>
 * <p>测试时使用内嵌的HSQL内存数据库完成</p>
 * <p>User: Zhang Kaitao
 * <p>Date: 13-1-14 下午2:36
 * <p>Version: 1.0
 */

public class UserRepositoryImplForCustomSearchIT extends BaseUserIT {

    @Autowired
    private UserRepositoryImpl userRepositoryImpl;

    @Autowired
    private UserRepository userRepository;


    @Test
    public void testFindAllByCustomSearch1() {
        int count = 15;
        for (int i = 0; i < count; i++) {
            User user = createUser();
            user.getBaseInfo().setRealname("zhang" + i);
            userRepository.save(user);
        }
        Map<String, Object> searchParams = new HashMap<String, Object>();
        searchParams.put("realname_like", "zhang");
        Searchable search = SearchableBuilder.newInstance(searchParams).buildSearchable();
        assertEquals(count, userRepositoryImpl.findAllByCustom(search).getNumberOfElements());
    }


    @Test
    public void testFindAllByPageAndCustomSearch2() {
        int count = 15;
        for (int i = 0; i < count; i++) {
            User user = createUser();
            user.getBaseInfo().setRealname("zhang" + i);
            userRepository.save(user);
        }
        Map<String, Object> searchParams = new HashMap<String, Object>();
        searchParams.put("realname_like", "zhang");
        Searchable search =
                SearchableBuilder
                        .newInstance(searchParams)
                        .setPage(new PageRequest(0, 5))
                        .buildSearchable();
        assertEquals(5, userRepositoryImpl.findAllByCustom(search).getSize());
    }

    @Test
    public void testCountAllByCustomSearch1() {
        int count = 15;
        for (int i = 0; i < count; i++) {
            User user = createUser();
            user.getBaseInfo().setRealname("zhang" + i);
            userRepository.save(user);
        }
        Map<String, Object> searchParams = new HashMap<String, Object>();
        searchParams.put("realname", "zhang1");
        Searchable search = SearchableBuilder.newInstance(searchParams).buildSearchable();
        assertEquals(6, userRepositoryImpl.countAllByCustom(search));
    }

    @Test
     public void testCountAllByCustomSearch2() {
         int count = 15;
         for (int i = 0; i < count; i++) {
             User user = createUser();
             user.getBaseInfo().setRealname("zhang" + i);
             userRepository.save(user);
         }
         Map<String, Object> searchParams = new HashMap<String, Object>();
         searchParams.put("realname", "zhanga");
         Searchable search = SearchableBuilder.newInstance(searchParams).buildSearchable();
         assertEquals(0, userRepositoryImpl.countAllByCustom(search));
     }



}
