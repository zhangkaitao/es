/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.es.common.repository;

import com.sishuok.es.common.entity.Sex;
import com.sishuok.es.common.entity.User;
import com.sishuok.es.common.entity.search.builder.SearchableBuilder;
import com.sishuok.es.common.entity.search.SearchRequest;
import com.sishuok.es.common.entity.search.Searchable;
import com.sishuok.es.common.entity.search.exception.InvalidSearchPropertyException;
import com.sishuok.es.common.entity.search.exception.InvalidSearchValueException;
import com.sishuok.es.common.entity.search.exception.InvlidSpecificationSearchOperatorException;
import com.sishuok.es.common.test.BaseUserIT;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

public class UserRepositoryImplForDefaultSearchIT extends BaseUserIT {

    @Autowired
    private UserRepositoryImpl userRepositoryImpl;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testEqForEnum() {
        int count = 15;
        for (int i = 0; i < count; i++) {
            User user = createUser();
            user.getBaseInfo().setSex(Sex.male);
            userRepository.save(user);
        }
        Map<String, Object> searchParams = new HashMap<String, Object>();
        searchParams.put("baseInfo.sex_eq", "male");
        Searchable search = SearchableBuilder.newInstance(searchParams).buildSearchable();
        assertEquals(count, userRepositoryImpl.countAllByDefault(search));
    }

    @Test
    public void testNotEqForEnum() {
        int count = 15;
        for (int i = 0; i < count; i++) {
            User user = createUser();
            user.getBaseInfo().setSex(Sex.male);
            userRepository.save(user);
        }
        Map<String, Object> searchParams = new HashMap<String, Object>();
        searchParams.put("baseInfo.sex_notEq", "male");
        Searchable search = SearchableBuilder.newInstance(searchParams).buildSearchable();
        assertEquals(0, userRepositoryImpl.countAllByDefault(search));
    }

    @Test
    public void testEqForInteger() {
        int count = 15;
        for (int i = 0; i < count; i++) {
            User user = createUser();
            user.getBaseInfo().setAge(15);
            userRepository.save(user);
        }
        Map<String, Object> searchParams = new HashMap<String, Object>();
        searchParams.put("baseInfo.age_eq", "15");
        SearchRequest search = new SearchRequest(searchParams);
        assertEquals(count, userRepositoryImpl.countAllByDefault(search));
    }


    @Test
    public void testEqForDate() throws ParseException {
        int count = 15;
        String dateStr = "2012-01-15 16:59:00";
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (int i = 0; i < count; i++) {
            User user = createUser();
            user.setRegisterDate(df.parse(dateStr));
            userRepository.save(user);
        }
        Map<String, Object> searchParams = new HashMap<String, Object>();
        searchParams.put("registerDate_eq", dateStr);
        SearchRequest search = new SearchRequest(searchParams);
        assertEquals(count, userRepositoryImpl.countAllByDefault(search));
    }


    @Test
    public void testLtAndGtForDate() throws ParseException {
        int count = 15;
        String dateStr = "2012-01-15 16:59:00";
        String dateStrFrom = "2012-01-15 16:58:00";
        String dateStrEnd = "2012-01-15 16:59:01";
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (int i = 0; i < count; i++) {
            User user = createUser();
            user.getBaseInfo().setBirthday(new Timestamp(df.parse(dateStr).getTime()));
            userRepository.save(user);
        }
        Map<String, Object> searchParams = new HashMap<String, Object>();
        searchParams.put("baseInfo.birthday_gt", dateStrFrom);
        searchParams.put("baseInfo.birthday_lt", dateStrEnd);
        Searchable search = SearchableBuilder.newInstance(searchParams).buildSearchable();
        assertEquals(count, userRepositoryImpl.countAllByDefault(search));
    }


    @Test
    public void testLteAndGteForDate() throws ParseException {
        int count = 15;
        String dateStr = "2012-01-15 16:59:00";
        String dateStrFrom = "2012-01-15 16:59:00";
        String dateStrEnd = "2012-01-15 16:59:00";
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (int i = 0; i < count; i++) {
            User user = createUser();
            user.getBaseInfo().setBirthday(new Timestamp(df.parse(dateStr).getTime()));
            userRepository.save(user);
        }
        Map<String, Object> searchParams = new HashMap<String, Object>();
        searchParams.put("baseInfo.birthday_gte", dateStrFrom);
        searchParams.put("baseInfo.birthday_lte", dateStrEnd);
        Searchable search = SearchableBuilder.newInstance(searchParams).buildSearchable();
        assertEquals(count, userRepositoryImpl.countAllByDefault(search));
    }


    @Test
    public void testIsNotNull() throws ParseException {
        int count = 15;
        for (int i = 0; i < count; i++) {
            User user = createUser();
            userRepository.save(user);
        }
        Map<String, Object> searchParams = new HashMap<String, Object>();
        searchParams.put("username_isNotNull", null);
        Searchable search = SearchableBuilder.newInstance(searchParams).buildSearchable();
        assertEquals(count, userRepositoryImpl.countAllByDefault(search));
    }

    @Test
    public void testInWithList() throws ParseException {
        List<Long> uuids = new ArrayList<Long>();
        int count = 15;
        for (int i = 0; i < count; i++) {
            User user = createUser();
            userRepository.save(user);
            uuids.add(user.getId());
        }
        Map<String, Object> searchParams = new HashMap<String, Object>();
        searchParams.put("id_in", uuids);
        Searchable search = SearchableBuilder.newInstance(searchParams).buildSearchable();
        assertEquals(count, userRepositoryImpl.countAllByDefault(search));
    }


    @Test
    public void testInWithArray() throws ParseException {
        List<Long> uuids = new ArrayList<Long>();
        int count = 15;
        for (int i = 0; i < count; i++) {
            User user = createUser();
            userRepository.save(user);
            uuids.add(user.getId());
        }
        Map<String, Object> searchParams = new HashMap<String, Object>();
        searchParams.put("id_in", uuids.toArray(new Long[count]));
        Searchable search = SearchableBuilder.newInstance(searchParams).buildSearchable();
        assertEquals(count, userRepositoryImpl.countAllByDefault(search));
    }

    @Test
    public void testInWithSingleValue() throws ParseException {
        List<Long> uuids = new ArrayList<Long>();
        int count = 15;
        for (int i = 0; i < count; i++) {
            User user = createUser();
            userRepository.save(user);
            uuids.add(user.getId());
        }
        Map<String, Object> searchParams = new HashMap<String, Object>();
        searchParams.put("id_in", uuids.get(0));
        Searchable search = SearchableBuilder.newInstance(searchParams).buildSearchable();
        assertEquals(1, userRepositoryImpl.countAllByDefault(search));
    }


    /**
     * @throws ParseException
     * @see com.sishuok.es.common.entity.User @SearchPropertyMappings注解定义的自定义查询条件
     */
    @Test
    public void testCustomSearchWithDefaultOperator() throws ParseException {
        int count = 15;
        String dateStr = "2012-01-15 16:59:00";
        String dateStrFrom = "2012-01-15 16:59:00";
        String dateStrEnd = "2012-01-15 16:59:00";
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (int i = 0; i < count; i++) {
            User user = createUser();
            user.setRegisterDate(df.parse(dateStr));
            userRepository.save(user);
        }
        Map<String, Object> searchParams = new HashMap<String, Object>();
        searchParams.put("beginRegisterDate", dateStrFrom);
        searchParams.put("endRegisterDate", dateStrEnd);
        Searchable search = SearchableBuilder.newInstance(searchParams).buildSearchable();
        assertEquals(count, userRepositoryImpl.countAllByDefault(search));
    }

    /**
     * @throws ParseException
     * @see com.sishuok.es.common.entity.User @SearchPropertyMappings注解定义的自定义查询条件
     */
    @Test
    public void testCustomSearchWithCustomOperator() throws ParseException {
        int count = 15;
        String dateStr = "2012-01-15 16:59:00";
        String dateStrFrom = "2012-01-15 16:59:00";
        String dateStrEnd = "2012-01-15 16:59:00";
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (int i = 0; i < count; i++) {
            User user = createUser();
            user.setRegisterDate(df.parse(dateStr));
            userRepository.save(user);
        }
        Map<String, Object> searchParams = new HashMap<String, Object>();
        searchParams.put("beginRegisterDate_lte", dateStrFrom);
        searchParams.put("endRegisterDate_gte", dateStrEnd);
        Searchable search = SearchableBuilder.newInstance(searchParams).buildSearchable();
        assertEquals(count, userRepositoryImpl.countAllByDefault(search));
    }


    /**
     * @throws ParseException
     * @see com.sishuok.es.common.entity.User @SearchPropertyMappings注解定义的自定义查询条件
     */
    @Test(expected = InvlidSpecificationSearchOperatorException.class)
    public void testCustomSearchWithInvlidSpecificationSearchOperator() throws ParseException {
        int count = 15;
        String dateStr = "2012-01-15 16:59:00";
        String dateStrFrom = "2012-01-15 16:59:00";
        String dateStrEnd = "2012-01-15 16:59:00";
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (int i = 0; i < count; i++) {
            User user = createUser();
            user.setRegisterDate(df.parse(dateStr));
            userRepository.save(user);
        }
        Map<String, Object> searchParams = new HashMap<String, Object>();
        searchParams.put("beginRegisterDate_lTe", dateStrFrom);
        searchParams.put("endRegisterDate_gTe", dateStrEnd);
        Searchable search = SearchableBuilder.newInstance(searchParams).buildSearchable();
        assertEquals(count, userRepositoryImpl.countAllByDefault(search));
    }


    /**
     * @throws ParseException
     * @see com.sishuok.es.common.entity.User @SearchPropertyMappings注解定义的自定义查询条件
     */
    @Test(expected = InvalidSearchPropertyException.class)
    public void testCustomSearchWithInvalidSearchProperty() throws ParseException {
        int count = 15;
        String dateStr = "2012-01-15 16:59:00";
        String dateStrFrom = "2012-01-15 16:59:00";
        String dateStrEnd = "2012-01-15 16:59:00";
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (int i = 0; i < count; i++) {
            User user = createUser();
            user.setRegisterDate(df.parse(dateStr));
            userRepository.save(user);
        }
        Map<String, Object> searchParams = new HashMap<String, Object>();
        searchParams.put("beginRegisterDate1_lte", dateStrFrom);
        searchParams.put("endRegisterDate1_gte", dateStrEnd);
        Searchable search = SearchableBuilder.newInstance(searchParams).buildSearchable();
        assertEquals(count, userRepositoryImpl.countAllByDefault(search));
    }


    /**
     * @throws ParseException
     * @see com.sishuok.es.common.entity.User @SearchPropertyMappings注解定义的自定义查询条件
     */
    @Test(expected = InvalidSearchValueException.class)
    public void testCustomSearchWithInvalidSearchValue() throws ParseException {
        int count = 15;
        String dateStr = "2012-01-15 16:59:00";
        String dateStrFrom = "2012-01-15";
        String dateStrEnd = "2012-01-15 19:00";
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (int i = 0; i < count; i++) {
            User user = createUser();
            user.setRegisterDate(df.parse(dateStr));
            userRepository.save(user);
        }
        Map<String, Object> searchParams = new HashMap<String, Object>();
        searchParams.put("beginRegisterDate_lte", dateStrFrom);
        searchParams.put("endRegisterDate_gte", dateStrEnd);
        Searchable search = SearchableBuilder.newInstance(searchParams).buildSearchable();
        assertEquals(count, userRepositoryImpl.countAllByDefault(search));
    }


    /**
     * @throws ParseException
     * @see com.sishuok.es.common.entity.User @SearchPropertyMappings注解定义的自定义查询条件
     */
    @Test
    public void testPageAndCustomSearch1() throws ParseException {
        int count = 15;
        String dateStr = "2012-01-15 16:59:00";
        String dateStrFrom = "2012-01-15 16:59:00";
        String dateStrEnd = "2012-01-15 16:59:00";
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (int i = 0; i < count; i++) {
            User user = createUser();
            user.setRegisterDate(df.parse(dateStr));
            userRepository.save(user);
        }
        Map<String, Object> searchParams = new HashMap<String, Object>();
        searchParams.put("beginRegisterDate_lte", dateStrFrom);
        searchParams.put("endRegisterDate_gte", dateStrEnd);
        Searchable search =
                SearchableBuilder
                        .newInstance(searchParams)
                        .setPage(new PageRequest(0, 10))   //分页页码从0开始
                        .buildSearchable();
        assertEquals(10, userRepositoryImpl.findAllByDefault(search).getNumberOfElements());
    }

    /**
     * @throws ParseException
     * @see com.sishuok.es.common.entity.User @SearchPropertyMappings注解定义的自定义查询条件
     */
    @Test
    public void testPageAndCustomSearch2() throws ParseException {
        int count = 15;
        String dateStr = "2012-01-15 16:59:00";
        String dateStrFrom = "2012-01-15 16:59:00";
        String dateStrEnd = "2012-01-15 16:59:00";
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (int i = 0; i < count; i++) {
            User user = createUser();
            user.setRegisterDate(df.parse(dateStr));
            userRepository.save(user);
        }
        Map<String, Object> searchParams = new HashMap<String, Object>();
        searchParams.put("beginRegisterDate_lte", dateStrFrom);
        searchParams.put("endRegisterDate_gte", dateStrEnd);
        Searchable search =
                SearchableBuilder
                        .newInstance(searchParams)
                        .setPage(new PageRequest(1, 5))
                        .buildSearchable();
        assertEquals(5, userRepositoryImpl.findAllByDefault(search).getNumberOfElements());
    }


    /**
     * @throws ParseException
     * @see com.sishuok.es.common.entity.User @SearchPropertyMappings注解定义的自定义查询条件
     */
    @Test
    public void testPageAndCustomSearch3() throws ParseException {
        int count = 15;
        String dateStr = "2012-01-15 16:59:00";
        String dateStrFrom = "2012-01-15 16:59:00";
        String dateStrEnd = "2012-01-15 16:59:00";
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (int i = 0; i < count; i++) {
            User user = createUser();
            user.setRegisterDate(df.parse(dateStr));
            userRepository.save(user);
        }
        Map<String, Object> searchParams = new HashMap<String, Object>();
        searchParams.put("beginRegisterDate_lte", dateStrFrom);
        searchParams.put("endRegisterDate_gte", dateStrEnd);
        Searchable search =
                SearchableBuilder
                        .newInstance(searchParams)
                        .setPage(new PageRequest(2, 10))
                        .buildSearchable();
        assertEquals(0, userRepositoryImpl.findAllByDefault(search).getNumberOfElements());
    }


    /**
     * @throws ParseException
     * @see com.sishuok.es.common.entity.User @SearchPropertyMappings注解定义的自定义查询条件
     */
    @Test
    public void testOrderAndCustomSearch4() throws ParseException {
        int count = 15;
        String dateStr = "2012-01-15 16:59:00";
        String dateStrFrom = "2012-01-15 16:59:00";
        String dateStrEnd = "2012-01-15 16:59:00";
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (int i = 0; i < count; i++) {
            User user = createUser();
            user.setRegisterDate(df.parse(dateStr));
            userRepository.save(user);
        }
        Map<String, Object> searchParams = new HashMap<String, Object>();
        searchParams.put("beginRegisterDate_lte", dateStrFrom);
        searchParams.put("endRegisterDate_gte", dateStrEnd);


        Sort.Order idAsc = new Sort.Order(Sort.Direction.DESC, "id");
        Sort.Order usernameDesc = new Sort.Order(Sort.Direction.DESC, "username");
        Sort sort = new Sort(idAsc, usernameDesc);
        Searchable search =
                SearchableBuilder
                        .newInstance(searchParams)
                        .setSort(sort)
                        .buildSearchable();

        List<User> list = userRepositoryImpl.findAllByDefault(search).getContent();
        assertTrue(list.get(0).getId() > list.get(1).getId());

    }


    /**
     * @throws ParseException
     * @see com.sishuok.es.common.entity.User @SearchPropertyMappings注解定义的自定义查询条件
     */
    @Test
    public void testPageAndOrderAndCustomSearch5() throws ParseException {
        int count = 15;
        String dateStr = "2012-01-15 16:59:00";
        String dateStrFrom = "2012-01-15 16:59:00";
        String dateStrEnd = "2012-01-15 16:59:00";
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (int i = 0; i < count; i++) {
            User user = createUser();
            user.setRegisterDate(df.parse(dateStr));
            userRepository.save(user);
        }
        Map<String, Object> searchParams = new HashMap<String, Object>();
        searchParams.put("beginRegisterDate_lte", dateStrFrom);
        searchParams.put("endRegisterDate_gte", dateStrEnd);


        Sort.Order idAsc = new Sort.Order(Sort.Direction.DESC, "id");
        Sort.Order usernameDesc = new Sort.Order(Sort.Direction.DESC, "username");
        Sort sort = new Sort(idAsc, usernameDesc);
        Searchable search =
                SearchableBuilder
                        .newInstance(searchParams)
                        .setPage(new PageRequest(0, 10, sort))
                        .buildSearchable();

        Page<User> page = userRepositoryImpl.findAllByDefault(search);
        assertTrue(page.getContent().get(0).getId() > page.getContent().get(1).getId());
    }


}
