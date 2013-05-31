/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.es.common.repository;

import com.sishuok.es.common.entity.User;
import com.sishuok.es.common.entity.search.Searchable;
import com.sishuok.es.common.test.BaseUserIT;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * <p>User Repository集成测试</p>
 * <p>测试时使用内嵌的HSQL内存数据库完成</p>
 * <p>User: Zhang Kaitao
 * <p>Date: 13-1-14 下午2:36
 * <p>Version: 1.0
 */

public class UserRepository2ImplForCustomSearchIT extends BaseUserIT {

    @Autowired
    private UserRepository2 userRepository2;


    @Test
    public void testFindAllByCustomSearch1() {
        int count = 15;
        for (int i = 0; i < count; i++) {
            User user = createUser();
            user.getBaseInfo().setRealname("zhang" + i);
            userRepository2.save(user);
        }
        Searchable search = Searchable.newSearchable().addSearchParam("realname_custom", "zhang");
        assertEquals(count, userRepository2.findAllByCustom(search).getNumberOfElements());
    }


    @Test
    public void testFindAllByPageAndCustomSearch2() {
        int count = 15;
        for (int i = 0; i < count; i++) {
            User user = createUser();
            user.getBaseInfo().setRealname("zhang" + i);
            userRepository2.save(user);
        }
        Map<String, Object> searchParams = new HashMap<String, Object>();
        searchParams.put("realname_custom", "zhang");
        Searchable search = Searchable.newSearchable(searchParams).setPage(0, 5);
        assertEquals(5, userRepository2.findAllByCustom(search).getSize());
    }

    @Test
    public void testCountAllByCustomSearch1() {
        int count = 15;
        for (int i = 0; i < count; i++) {
            User user = createUser();
            user.getBaseInfo().setRealname("zhang" + i);
            userRepository2.save(user);
        }
        Searchable search = Searchable.newSearchable().addSearchParam("realname", "zhang1");
        assertEquals(6, userRepository2.countAllByCustom(search));
    }

    @Test
    public void testCountAllByCustomSearch2() {
        int count = 15;
        for (int i = 0; i < count; i++) {
            User user = createUser();
            user.getBaseInfo().setRealname("zhang" + i);
            userRepository2.save(user);
        }
        Searchable search = Searchable.newSearchable().addSearchParam("realname", "zhanga");
        assertEquals(0, userRepository2.countAllByCustom(search));
    }


}
