/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.es.common.entity.search;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.sishuok.es.common.entity.Sex;
import com.sishuok.es.common.entity.User;
import com.sishuok.es.common.entity.search.exception.InvlidSearchOperatorException;
import com.sishuok.es.common.entity.search.filter.SearchFilterHelper;
import com.sishuok.es.common.entity.search.utils.SearchableConvertUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.format.support.DefaultFormattingConversionService;

import java.util.ArrayList;
import java.util.Map;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 13-5-6 下午12:05
 * <p>Version: 1.0
 */
public class SearchableTest {


    private ConversionService oldConversionService;
    @Before
    public void setUp() {
        try {
            oldConversionService = SearchableConvertUtils.getConversionService();
        } catch (Exception e) {
            //ignore null case
        }
        SearchableConvertUtils.setConversionService(new DefaultFormattingConversionService());
    }

    @After
    public void tearDown() {
        SearchableConvertUtils.setConversionService(oldConversionService);
    }


    @Test
    public void testNewSearchable() {

        Map<String, Object> searchParams1 = Maps.newHashMap();
        searchParams1.put("name_like", "123");
        searchParams1.put("name_like", "234");
        searchParams1.put("age_eq", 1);
        Searchable searchable1 = Searchable.newSearchable(searchParams1);

        Assert.assertTrue(searchable1.containsSearchKey("name_like"));
        Assert.assertTrue(searchable1.containsSearchKey("age_eq"));
        Assert.assertEquals("234", searchable1.getValue("name_like"));
        Assert.assertEquals(1, searchable1.getValue("age_eq"));
        Assert.assertEquals(2, searchable1.getSearchFilters().size());


        Searchable searchable2 = Searchable.newSearchable(
                null,
                new PageRequest(0, 1),
                new Sort(Sort.Direction.DESC, "uuid"));

        Assert.assertTrue(searchable2.hasPageable());
        Assert.assertTrue(searchable2.hashSort());

        Searchable searchable3 = Searchable.newSearchable(
                null,
                new PageRequest(0, 1, new Sort(Sort.Direction.DESC, "uuid")));

        Assert.assertTrue(searchable3.hasPageable());
        Assert.assertTrue(searchable3.hashSort());

    }

    @Test(expected = InvlidSearchOperatorException.class)
    public void testNewSearchableWithErrorOperator() {

        Map<String, Object> searchParams1 = Maps.newHashMap();
        searchParams1.put("name_lik", "123");

        Searchable.newSearchable(searchParams1);

    }


    @Test
    public void testAddParam() {
        Searchable searchable = Searchable.newSearchable();
        searchable.addSearchParam("abc", "123");
        searchable.addSearchParam("bc_like", "23");

        Assert.assertEquals(2, searchable.getSearchFilters().size());

        Assert.assertTrue(searchable.containsSearchKey("abc"));
        //如果添加时不加操作符 默认是custom
        Assert.assertTrue(searchable.containsSearchKey("abc_custom"));
        Assert.assertTrue(searchable.containsSearchKey("bc_like"));
    }


    @Test
    public void testAddAllParams() {


        Map<String, Object> searchParams1 = Maps.newHashMap();
        searchParams1.put("name_like", "123");
        searchParams1.put("name_like", "234");
        searchParams1.put("age_eq", 1);

        Searchable searchable = Searchable.newSearchable();
        searchable.addSearchParams(searchParams1);
        searchable.addSearchParam("bc_like", "23");

        Assert.assertEquals(3, searchable.getSearchFilters().size());

        Assert.assertTrue(searchable.containsSearchKey("name_like"));
        Assert.assertTrue(searchable.containsSearchKey("age_eq"));
        Assert.assertTrue(searchable.containsSearchKey("bc_like"));
    }


    @Test
    public void testAddSearchFilter() {

        Searchable searchable = Searchable.newSearchable();

        searchable.addSearchFilter("age", SearchOperator.like, "123");
        searchable.addSearchFilter("name", SearchOperator.custom, "234");

        searchable.addSearchFilter(SearchFilterHelper.newCondition("sex_like", "male"));
        searchable.addSearchFilter(SearchFilterHelper.newCondition("birthday_custom", "2012"));
        searchable.addSearchFilter(SearchFilterHelper.newCondition("realname", "123"));

        searchable.addSearchFilter(SearchFilterHelper.newCondition("a", SearchOperator.eq, "234"));
        searchable.addSearchFilter(SearchFilterHelper.newCondition("b", SearchOperator.custom, "234"));


        Assert.assertTrue(searchable.containsSearchKey("age_like"));
        Assert.assertTrue(searchable.containsSearchKey("name"));
        Assert.assertTrue(searchable.containsSearchKey("name_custom"));

        Assert.assertTrue(searchable.containsSearchKey("sex_like"));
        Assert.assertTrue(searchable.containsSearchKey("birthday"));
        Assert.assertTrue(searchable.containsSearchKey("birthday_custom"));
        Assert.assertTrue(searchable.containsSearchKey("realname"));
        Assert.assertTrue(searchable.containsSearchKey("realname_custom"));

        Assert.assertTrue(searchable.containsSearchKey("a_eq"));
        Assert.assertTrue(searchable.containsSearchKey("b_custom"));
        Assert.assertTrue(searchable.containsSearchKey("b"));

    }

    @Test
    public void testAddSearchFilters() {

        Searchable searchable = Searchable.newSearchable();

        searchable.addSearchFilters(Lists.newArrayList(
                SearchFilterHelper.newCondition("sex_like", "male"),
                SearchFilterHelper.newCondition("birthday_custom", "2012"),
                SearchFilterHelper.newCondition("realname", "123")
        ));


        Assert.assertTrue(searchable.containsSearchKey("sex_like"));
        Assert.assertTrue(searchable.containsSearchKey("birthday"));
        Assert.assertTrue(searchable.containsSearchKey("birthday_custom"));
        Assert.assertTrue(searchable.containsSearchKey("realname"));
        Assert.assertTrue(searchable.containsSearchKey("realname_custom"));


    }

    @Test
    public void testOrSearchFilters() {

        Searchable searchable = Searchable.newSearchable();

        searchable.or(
                SearchFilterHelper.newCondition("sex_like", "male"),
                SearchFilterHelper.newCondition("birthday_custom", "2012"),
                SearchFilterHelper.newCondition("realname", "123")
        );


        Assert.assertEquals(1, searchable.getSearchFilters().size());

        Assert.assertTrue(searchable.containsSearchKey("sex_like"));

        Assert.assertTrue(searchable.containsSearchKey("realname"));


    }

    @Test
    public void testAndSearchFilters() {

        Searchable searchable = Searchable.newSearchable();

        searchable.and(
                SearchFilterHelper.newCondition("sex_like", "male"),
                SearchFilterHelper.newCondition("birthday_custom", "2012"),
                SearchFilterHelper.newCondition("realname", "123")
        );


        Assert.assertEquals(1, searchable.getSearchFilters().size());

        Assert.assertTrue(searchable.containsSearchKey("sex_like"));

        Assert.assertTrue(searchable.containsSearchKey("realname"));


    }


    @Test
    public void testRemoveSearchFilter() {

        Searchable searchable = Searchable.newSearchable();

        searchable.addSearchParam("a", "123");
        searchable.addSearchParam("b", "123");
        searchable.addSearchParam("c", "123");

        searchable.or(
                SearchFilterHelper.newCondition("sex_like", "male"),
                SearchFilterHelper.newCondition("birthday_custom", "2012"),
                SearchFilterHelper.newCondition("realname", "123"),
                SearchFilterHelper.newCondition("name_custom", "123"),
                SearchFilterHelper.newCondition("age_eq", 1)
        );


        searchable.removeSearchFilter("a");
        searchable.removeSearchFilter("b_custom");
        searchable.removeSearchFilter("sex_like");
        searchable.removeSearchFilter("realname_custom");
        searchable.removeSearchFilter("name");

        Assert.assertEquals(2, searchable.getSearchFilters().size());
    }


    @Test
    public void testPageAndSort() {
        Searchable searchable = Searchable.newSearchable();
        searchable.setPage(0, 10);

        searchable.addSort(Sort.Direction.DESC, "uuid");
        searchable.addSort(Sort.Direction.ASC, "name");


        Assert.assertEquals(0, searchable.getPage().getPageNumber());
        Assert.assertEquals(10, searchable.getPage().getPageSize());

        Assert.assertEquals("name", searchable.getPage().getSort().iterator().next().getProperty());

        Assert.assertEquals("name", searchable.getSort().iterator().next().getProperty());

    }


    @Test
    public void testConvert() {

        Searchable searchable = Searchable.newSearchable();
        searchable.addSearchParam("username_like", "zhang");
        searchable.addSearchParam("baseInfo.sex_eq", "male");
        searchable.addSearchParam("id_in", new String[]{"1", "2", "3"});

        searchable.convert(User.class);

        Assert.assertTrue(searchable.isConverted());

        Assert.assertEquals(Sex.male, searchable.getValue("baseInfo.sex_eq"));

        Assert.assertEquals(3, ((ArrayList) searchable.getValue("id_in")).size());
        Assert.assertTrue(((ArrayList) searchable.getValue("id_in")).contains(1L));

    }


}
