/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.es.common.service;

import com.sishuok.es.common.entity.User;
import com.sishuok.es.common.entity.search.Searchable;
import com.sishuok.es.common.test.BaseUserIT;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 13-1-17 下午7:55
 * <p>Version: 1.0
 */
public class UserServiceTest extends BaseUserIT {

    @Autowired
    protected UserService userService;

    @Test
    public void testSave() {
        User dbUser = userService.save(createUser());
        assertNotNull(dbUser.getId());
    }

    @Test
    public void testUpdate() {
        User dbUser = userService.save(createUser());
        clear();

        String newUsername = "zhang$$$$" + System.currentTimeMillis();
        dbUser.setUsername(newUsername);
        userService.update(dbUser);

        clear();

        assertEquals(newUsername, userService.findOne(dbUser.getId()).getUsername());
    }

    @Test
    public void testDeleteById() {
        User dbUser = userService.save(createUser());
        clear();
        userService.delete(dbUser.getId());
        clear();

        assertNull(userService.findOne(dbUser.getId()));
    }

    @Test
    public void testDeleteByEntity() {
        User dbUser = userService.save(createUser());
        clear();
        userService.delete(dbUser);
        clear();

        assertNull(userService.findOne(dbUser.getId()));
    }

    @Test
    public void testFindOne() {
        User dbUser = userService.save(createUser());
        clear();

        assertNotNull(userService.findOne(dbUser.getId()));
    }


    @Test
    public void testExists() {
        User dbUser = userService.save(createUser());
        clear();
        assertTrue(userService.exists(dbUser.getId()));
    }

    @Test
    public void testCount() {
        int count = 15;
        for (int i = 0; i < count; i++) {
            userService.save(createUser());
        }
        assertEquals(count, userService.count());
    }

    @Test
    public void testFindAll() {
        int count = 15;
        User user = null;
        for (int i = 0; i < count; i++) {
            user = userService.save(createUser());
        }
        List<User> userList = userService.findAll();
        assertEquals(count, userList.size());
        assertTrue(userList.contains(user));
    }


    @Test
    public void testFindAllBySort() {
        int count = 15;
        User user = null;
        for (int i = 0; i < count; i++) {
            user = userService.save(createUser());
        }

        Sort sortDesc = new Sort(Sort.Direction.DESC, "id");
        Sort sortAsc = new Sort(Sort.Direction.ASC, "id");
        List<User> userDescList = userService.findAll(sortDesc);
        List<User> userAscList = userService.findAll(sortAsc);

        assertEquals(count, userDescList.size());
        assertEquals(count, userAscList.size());
        assertTrue(userDescList.contains(user));
        assertTrue(userAscList.contains(user));

        assertTrue(userAscList.get(0).getId() < userAscList.get(1).getId());
        assertTrue(userDescList.get(0).getId() > userDescList.get(1).getId());
    }


    @Test
    public void testFindAllByPageableAndSortDesc() {
        int count = 15;
        User lastUser = null;
        for (int i = 0; i < count; i++) {
            lastUser = userService.save(createUser());
        }

        Sort sortDesc = new Sort(Sort.Direction.DESC, "id");
        Pageable pageable = new PageRequest(0, 5, sortDesc);
        Page<User> userPage = userService.findAll(pageable);

        assertEquals(5, userPage.getNumberOfElements());
        assertTrue(userPage.getContent().contains(lastUser));
        assertTrue(userPage.getContent().get(0).getId() > userPage.getContent().get(1).getId());
    }

    @Test
    public void testFindAllByPageableAndSortAsc() {
        int count = 15;
        User lastUser = null;
        for (int i = 0; i < count; i++) {
            lastUser = userService.save(createUser());
        }

        Sort sortAsc = new Sort(Sort.Direction.ASC, "id");
        Pageable pageable = new PageRequest(0, 5, sortAsc);
        Page<User> userPage = userService.findAll(pageable);

        assertEquals(5, userPage.getNumberOfElements());
        assertFalse(userPage.getContent().contains(lastUser));
        assertTrue(userPage.getContent().get(0).getId() < userPage.getContent().get(1).getId());
    }


    @Test
    public void testFindAllBySearchAndNoPage() {
        int count = 15;
        User lastUser = null;
        for (int i = 0; i < count; i++) {
            lastUser = createUser();
            lastUser.setUsername("zhang" + i);
            userService.save(lastUser);
        }

        Map<String, Object> searchParams = new HashMap<String, Object>();
        searchParams.put("username_like", "zhang");
        Searchable search = Searchable.newSearchable(searchParams);

        List<User> userList = userService.findAllWithNoPageNoSort(search);
        assertEquals(count, userList.size());
        assertTrue(userList.contains(lastUser));
    }


    @Test
    public void testFindAllBySearchAndSort() {
        int count = 15;
        User lastUser = null;
        for (int i = 0; i < count; i++) {
            lastUser = createUser();
            lastUser.setUsername("zhang" + i);
            userService.save(lastUser);
        }

        Map<String, Object> searchParams = new HashMap<String, Object>();
        searchParams.put("username_like", "zhang");
        Sort sortDesc = new Sort(Sort.Direction.DESC, "id");
        Searchable search = Searchable.newSearchable(searchParams).addSort(sortDesc);

        List<User> userList = userService.findAllWithSort(search);
        assertEquals(count, userList.size());
        assertTrue(userList.contains(lastUser));

        assertTrue(userList.get(0).getId() > userList.get(1).getId());
    }

    @Test
    public void testFindAllBySearchAndPageableAndSortAsc() {
        int count = 15;
        User lastUser = null;
        for (int i = 0; i < count; i++) {
            lastUser = userService.save(createUser());
        }

        Sort sortAsc = new Sort(Sort.Direction.ASC, "id");
        Pageable pageable = new PageRequest(0, 5, sortAsc);
        Map<String, Object> searchParams = new HashMap<String, Object>();
        searchParams.put("username_like", "zhang");
        Searchable search = Searchable.newSearchable(searchParams).setPage(pageable);

        Page<User> userPage = userService.findAll(search);
        assertEquals(5, userPage.getNumberOfElements());
        assertFalse(userPage.getContent().contains(lastUser));
        assertTrue(userPage.getContent().get(0).getId() < userPage.getContent().get(1).getId());
    }


}
