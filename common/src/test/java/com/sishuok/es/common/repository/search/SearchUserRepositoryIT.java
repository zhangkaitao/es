/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.es.common.repository.search;

import com.google.common.collect.Lists;
import com.sishuok.es.common.entity.Sex;
import com.sishuok.es.common.entity.User;
import com.sishuok.es.common.entity.search.SearchFilter;
import com.sishuok.es.common.entity.search.SearchOperator;
import com.sishuok.es.common.entity.search.SearchRequest;
import com.sishuok.es.common.entity.search.Searchable;
import com.sishuok.es.common.entity.search.exception.InvalidSearchPropertyException;
import com.sishuok.es.common.entity.search.exception.InvalidSearchValueException;
import com.sishuok.es.common.entity.search.exception.InvlidSpecificationSearchOperatorException;
import com.sishuok.es.common.repository.UserRepository;
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
 * <p>测试查询条件</p>
 * <p>User: Zhang Kaitao
 * <p>Date: 13-1-15 下午12:21
 * <p>Version: 1.0
 */
public class SearchUserRepositoryIT extends BaseUserIT {

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
        Searchable search = Searchable.newSearchable(searchParams);
        assertEquals(count, userRepository.count(search.getSpecifications(User.class)));
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
        Searchable search = Searchable.newSearchable(searchParams);
        assertEquals(0, userRepository.count(search.getSpecifications(User.class)));
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
        assertEquals(count, userRepository.count(search.getSpecifications(User.class)));
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
        assertEquals(count, userRepository.count(search.getSpecifications(User.class)));
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
        Searchable search = Searchable.newSearchable(searchParams);
        assertEquals(count, userRepository.count(search.getSpecifications(User.class)));
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
        Searchable search = Searchable.newSearchable(searchParams);
        assertEquals(count, userRepository.count(search.getSpecifications(User.class)));
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
        Searchable search = Searchable.newSearchable(searchParams);
        assertEquals(count, userRepository.count(search.getSpecifications(User.class)));
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
        Searchable search = Searchable.newSearchable(searchParams);
        assertEquals(count, userRepository.count(search.getSpecifications(User.class)));
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
        Searchable search = Searchable.newSearchable(searchParams);
        assertEquals(count, userRepository.count(search.getSpecifications(User.class)));
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
        Searchable search = Searchable.newSearchable(searchParams);
        assertEquals(1, userRepository.count(search.getSpecifications(User.class)));
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
        Searchable search = Searchable.newSearchable(searchParams);
        assertEquals(count, userRepository.count(search.getSpecifications(User.class)));
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
        Searchable search = Searchable.newSearchable(searchParams);
        assertEquals(count, userRepository.count(search.getSpecifications(User.class)));
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
        Searchable search = Searchable.newSearchable(searchParams);
        assertEquals(count, userRepository.count(search.getSpecifications(User.class)));
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
        Searchable search = Searchable.newSearchable(searchParams);
        assertEquals(count, userRepository.count(search.getSpecifications(User.class)));
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
        Searchable search = Searchable.newSearchable(searchParams);
        assertEquals(count, userRepository.count(search.getSpecifications(User.class)));
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
        Searchable search = Searchable.newSearchable(searchParams).setPage(0, 10);   //分页页码从0开始
        assertEquals(10, userRepository.findAll(search.getSpecifications(User.class), search.getPage()).getNumberOfElements());
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
        Searchable search = Searchable.newSearchable(searchParams).setPage(new PageRequest(1, 5));
        assertEquals(5, userRepository.findAll(search.getSpecifications(User.class), search.getPage()).getNumberOfElements());
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
        Searchable search = Searchable.newSearchable(searchParams).setPage(2, 10); //没有那么多数据
        assertEquals(0, userRepository.findAll(search.getSpecifications(User.class), search.getPage()).getNumberOfElements());
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


        Searchable search =
                Searchable.newSearchable(searchParams)
                .addSort(Sort.Direction.DESC, "id").addSort(Sort.Direction.DESC, "username");

        List<User> list = userRepository.findAll(search.getSpecifications(User.class), search.getSort());
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
                Searchable.newSearchable(searchParams).setPage(new PageRequest(0, 10, sort)); //分页出错时 取最后10个

        Page<User> page = userRepository.findAll(search.getSpecifications(User.class), search.getPage());
        assertTrue(page.getContent().get(0).getId() > page.getContent().get(1).getId());
    }


    @Test
    public void testOr() {
        int count = 15;
        for (int i = 0; i < count; i++) {
            User user = createUser();
            user.getBaseInfo().setAge(i);
            userRepository.save(user);
        }
        Searchable search = Searchable.newSearchable();
        search.addOrSearchFilters(
                Lists.newArrayList(
                        SearchFilter.newSearchFilter("baseInfo.age", SearchOperator.gt, 10),
                        SearchFilter.newSearchFilter("baseInfo.age", SearchOperator.eq, 12)
                )
        );

        assertEquals(2, userRepository.count(search.getSpecifications(User.class)));
    }



}
