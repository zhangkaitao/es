/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.es.common.web.bind.method.annotation;

import com.sishuok.es.common.entity.search.SearchOperator;
import com.sishuok.es.common.entity.search.Searchable;
import com.sishuok.es.common.entity.search.filter.SearchFilter;
import com.sishuok.es.common.entity.search.filter.SearchFilterHelper;
import com.sishuok.es.common.web.bind.annotation.PageableDefaults;
import com.sishuok.es.common.web.bind.annotation.SearchableDefaults;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.MethodParameter;
import org.springframework.data.domain.Sort;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.ServletWebRequest;

import java.lang.reflect.Method;
import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 13-1-23 下午9:50
 * <p>Version: 1.0
 */
public class SearchableMethodArgumentResolverTest {
    Method searchable, searchableAndNoPageAndSort, searchableAndNoPageAndNoSort, methodDefaultSearchable,
            parameterDefaultSearchable, customNamePrefixSearchableAndPageableAndSort, methodMergeDefaultSearchable;

    MockHttpServletRequest request;

    @Before
    public void setUp() throws NoSuchMethodException {
        searchable = Controller.class.getDeclaredMethod("searchable", new Class[]{Searchable.class});
        searchableAndNoPageAndSort = Controller.class.getDeclaredMethod("searchableAndNoPageAndSort", new Class[]{Searchable.class});
        searchableAndNoPageAndNoSort = Controller.class.getDeclaredMethod("searchableAndNoPageAndNoSort", new Class[]{Searchable.class});
        methodDefaultSearchable = Controller.class.getDeclaredMethod("methodDefaultSearchable", new Class[]{Searchable.class});
        parameterDefaultSearchable = Controller.class.getDeclaredMethod("parameterDefaultSearchable", new Class[]{Searchable.class});
        customNamePrefixSearchableAndPageableAndSort = Controller.class.getDeclaredMethod("customNamePrefixSearchableAndPageableAndSort",
                new Class[]{Searchable.class, Searchable.class});
        methodMergeDefaultSearchable = Controller.class.getDeclaredMethod("methodMergeDefaultSearchable",
                new Class[]{Searchable.class});

        request = new MockHttpServletRequest();
    }


    @Test
    public void testSearchable() throws Exception {
        int pn = 1;
        int pageSize = 10;
        request.setParameter("page.pn", String.valueOf(pn));
        request.setParameter("page.size", String.valueOf(pageSize));

        request.setParameter("sort1.baseInfo.realname", "asc");
        request.setParameter("sort2.id", "desc");

        request.setParameter("search.baseInfo.realname_like", "zhang");
        request.setParameter("search.username_eq", "zhang");

        MethodParameter parameter = new MethodParameter(searchable, 0);
        NativeWebRequest webRequest = new ServletWebRequest(request);
        Searchable searchable = (Searchable) new SearchableMethodArgumentResolver().resolveArgument(parameter, null, webRequest, null);

        //内部会自动-1，从0开始
        assertEquals(pn - 1, searchable.getPage().getPageNumber());
        assertEquals(pageSize, searchable.getPage().getPageSize());

        Sort expectedSort = new Sort(Sort.Direction.ASC, "baseInfo.realname").and(new Sort(Sort.Direction.DESC, "id"));
        assertEquals(expectedSort, searchable.getSort());


        assertContainsSearchFilter(SearchFilterHelper.newCondition("baseInfo.realname", SearchOperator.like, "zhang"), searchable);
        assertContainsSearchFilter(SearchFilterHelper.newCondition("username", SearchOperator.eq, "zhang"), searchable);
    }


    @Test
    public void testSearchableWithDefaultPage() throws Exception {

        request.setParameter("search.baseInfo.realname_like", "zhang");
        request.setParameter("search.username_eq", "zhang");

        MethodParameter parameter = new MethodParameter(searchable, 0);
        NativeWebRequest webRequest = new ServletWebRequest(request);
        Searchable searchable = (Searchable) new SearchableMethodArgumentResolver().resolveArgument(parameter, null, webRequest, null);

        //内部会自动-1，从0开始
        assertEquals(0, searchable.getPage().getPageNumber());
        assertEquals(10, searchable.getPage().getPageSize());
        assertEquals(null, searchable.getPage().getSort());


        assertContainsSearchFilter(SearchFilterHelper.newCondition("baseInfo.realname", SearchOperator.like, "zhang"), searchable);
        assertContainsSearchFilter(SearchFilterHelper.newCondition("username", SearchOperator.eq, "zhang"), searchable);

    }


    @Test
    public void testSearchableAndNoPageAndSort() throws Exception {

        int pn = 1;
        int pageSize = 10;
        request.setParameter("page.pn", String.valueOf(pn));
        request.setParameter("page.size", String.valueOf(pageSize));

        request.setParameter("sort1.baseInfo.realname", "asc");
        request.setParameter("sort2.id", "desc");

        request.setParameter("search.baseInfo.realname_like", "zhang");
        request.setParameter("search.username_eq", "zhang");

        MethodParameter parameter = new MethodParameter(searchableAndNoPageAndSort, 0);
        NativeWebRequest webRequest = new ServletWebRequest(request);
        Searchable searchable = (Searchable) new SearchableMethodArgumentResolver().resolveArgument(parameter, null, webRequest, null);

        assertEquals(null, searchable.getPage());

        Sort expectedSort = new Sort(Sort.Direction.ASC, "baseInfo.realname").and(new Sort(Sort.Direction.DESC, "id"));
        assertEquals(expectedSort, searchable.getSort());


        assertContainsSearchFilter(SearchFilterHelper.newCondition("baseInfo.realname", SearchOperator.like, "zhang"), searchable);
        assertContainsSearchFilter(SearchFilterHelper.newCondition("username", SearchOperator.eq, "zhang"), searchable);
    }


    @Test
    public void testSearchableAndNoPageAndNoSort() throws Exception {

        int pn = 1;
        int pageSize = 10;
        request.setParameter("page.pn", String.valueOf(pn));
        request.setParameter("page.size", String.valueOf(pageSize));

        request.setParameter("sort1.baseInfo.realname", "asc");
        request.setParameter("sort2.id", "desc");

        request.setParameter("search.baseInfo.realname_like", "zhang");
        request.setParameter("search.username_eq", "zhang");

        MethodParameter parameter = new MethodParameter(searchableAndNoPageAndNoSort, 0);
        NativeWebRequest webRequest = new ServletWebRequest(request);
        Searchable searchable = (Searchable) new SearchableMethodArgumentResolver().resolveArgument(parameter, null, webRequest, null);

        assertEquals(null, searchable.getPage());

        assertEquals(null, searchable.getSort());


        assertContainsSearchFilter(SearchFilterHelper.newCondition("baseInfo.realname", SearchOperator.like, "zhang"), searchable);
        assertContainsSearchFilter(SearchFilterHelper.newCondition("username", SearchOperator.eq, "zhang"), searchable);
    }


    @Test
    public void testMethodDefaultSearchable() throws Exception {
        int pn = 1;
        int pageSize = 10;
        request.setParameter("page.pn", String.valueOf(pn));
        request.setParameter("page.size", String.valueOf(pageSize));

        request.setParameter("sort1.baseInfo.realname", "asc");
        request.setParameter("sort2.id", "desc");

        MethodParameter parameter = new MethodParameter(methodDefaultSearchable, 0);
        NativeWebRequest webRequest = new ServletWebRequest(request);
        Searchable searchable = (Searchable) new SearchableMethodArgumentResolver().resolveArgument(parameter, null, webRequest, null);

        //内部会自动-1，从0开始
        assertEquals(pn - 1, searchable.getPage().getPageNumber());
        assertEquals(pageSize, searchable.getPage().getPageSize());

        Sort expectedSort = new Sort(Sort.Direction.ASC, "baseInfo.realname").and(new Sort(Sort.Direction.DESC, "id"));
        assertEquals(expectedSort, searchable.getSort());


        assertContainsSearchFilter(SearchFilterHelper.newCondition("baseInfo.realname", SearchOperator.like, "zhang"), searchable);
        assertContainsSearchFilter(SearchFilterHelper.newCondition("id", SearchOperator.eq, "1"), searchable);
    }


    @Test
    public void testParameterDefaultSearchable() throws Exception {
        int pn = 1;
        int pageSize = 10;
        request.setParameter("page.pn", String.valueOf(pn));
        request.setParameter("page.size", String.valueOf(pageSize));

        request.setParameter("sort1.baseInfo.realname", "asc");
        request.setParameter("sort2.id", "desc");

        MethodParameter parameter = new MethodParameter(parameterDefaultSearchable, 0);
        NativeWebRequest webRequest = new ServletWebRequest(request);
        Searchable searchable = (Searchable) new SearchableMethodArgumentResolver().resolveArgument(parameter, null, webRequest, null);

        //内部会自动-1，从0开始
        assertEquals(pn - 1, searchable.getPage().getPageNumber());
        assertEquals(pageSize, searchable.getPage().getPageSize());

        Sort expectedSort = new Sort(Sort.Direction.ASC, "baseInfo.realname").and(new Sort(Sort.Direction.DESC, "id"));
        assertEquals(expectedSort, searchable.getSort());


        assertContainsSearchFilter(SearchFilterHelper.newCondition("baseInfo.realname", SearchOperator.like, "zhang"), searchable);
        assertContainsSearchFilter(SearchFilterHelper.newCondition("id", SearchOperator.eq, "1"), searchable);
    }


    @Test
    public void testParameterDefaultSearchableWithOverrideSearchParams() throws Exception {
        int pn = 1;
        int pageSize = 10;
        request.setParameter("page.pn", String.valueOf(pn));
        request.setParameter("page.size", String.valueOf(pageSize));

        request.setParameter("sort1.baseInfo.realname", "asc");
        request.setParameter("sort2.id", "desc");


        request.setParameter("search.baseInfo.realname_like", "zhang");
        request.setParameter("search.username_eq", "zhang");


        MethodParameter parameter = new MethodParameter(parameterDefaultSearchable, 0);
        NativeWebRequest webRequest = new ServletWebRequest(request);
        Searchable searchable = (Searchable) new SearchableMethodArgumentResolver().resolveArgument(parameter, null, webRequest, null);

        //内部会自动-1，从0开始
        assertEquals(pn - 1, searchable.getPage().getPageNumber());
        assertEquals(pageSize, searchable.getPage().getPageSize());

        Sort expectedSort = new Sort(Sort.Direction.ASC, "baseInfo.realname").and(new Sort(Sort.Direction.DESC, "id"));
        assertEquals(expectedSort, searchable.getSort());

        assertContainsSearchFilter(SearchFilterHelper.newCondition("baseInfo.realname", SearchOperator.like, "zhang"), searchable);
        assertContainsSearchFilter(SearchFilterHelper.newCondition("username", SearchOperator.eq, "zhang"), searchable);
    }


    @Test
    public void testCustomNamePrefixSearchableAndPageableAndSort() throws Exception {
        int pn = 1;
        int pageSize = 10;
        request.setParameter("foo_page.pn", String.valueOf(pn));
        request.setParameter("foo_page.size", String.valueOf(pageSize));

        request.setParameter("foo_sort1.baseInfo.realname", "asc");
        request.setParameter("foo_sort2.id", "desc");


        request.setParameter("foo_search.baseInfo.realname_like", "zhang");
        request.setParameter("foo_search.username_eq", "zhang");


        MethodParameter parameter = new MethodParameter(customNamePrefixSearchableAndPageableAndSort, 0);
        NativeWebRequest webRequest = new ServletWebRequest(request);
        Searchable searchable = (Searchable) new SearchableMethodArgumentResolver().resolveArgument(parameter, null, webRequest, null);

        //内部会自动-1，从0开始
        assertEquals(pn - 1, searchable.getPage().getPageNumber());
        assertEquals(pageSize, searchable.getPage().getPageSize());

        Sort expectedSort = new Sort(Sort.Direction.ASC, "baseInfo.realname").and(new Sort(Sort.Direction.DESC, "id"));
        assertEquals(expectedSort, searchable.getSort());

        assertContainsSearchFilter(SearchFilterHelper.newCondition("baseInfo.realname", SearchOperator.like, "zhang"), searchable);
        assertContainsSearchFilter(SearchFilterHelper.newCondition("username", SearchOperator.eq, "zhang"), searchable);
    }


    @Test
    public void testMergeDefaultSearchableWithSearchParams() throws Exception {

        request.setParameter("search.username_eq", "zhang");

        MethodParameter parameter = new MethodParameter(methodMergeDefaultSearchable, 0);
        NativeWebRequest webRequest = new ServletWebRequest(request);
        Searchable searchable = (Searchable) new SearchableMethodArgumentResolver().resolveArgument(parameter, null, webRequest, null);

        assertContainsSearchFilter(SearchFilterHelper.newCondition("baseInfo.realname", SearchOperator.like, "zhang"), searchable);
        assertContainsSearchFilter(SearchFilterHelper.newCondition("username", SearchOperator.eq, "zhang"), searchable);
    }


    private void assertContainsSearchFilter(SearchFilter expected, Searchable searchable) {
        for (SearchFilter acutal : searchable.getSearchFilters()) {
            if (acutal.equals(expected)) {
                return;
            }
        }

        failSearchable(expected, searchable.getSearchFilters());
    }

    private void failSearchable(SearchFilter expected, Collection<SearchFilter> acutal) {
        String message = "search filter not contains " + "\r\n" + "Expected :" + expected + "\r\n" + "Actual :" + acutal;
        fail(message);
    }

    static class Controller {
        static final int DEFAULT_PAGESIZE = 198;
        static final int DEFAULT_PAGENUMBER = 42;

        public void searchable(Searchable searchable) {
        }

        @SearchableDefaults(needPage = false)
        public void searchableAndNoPageAndSort(Searchable searchable) {
        }

        @SearchableDefaults(needPage = false, needSort = false)
        public void searchableAndNoPageAndNoSort(Searchable searchable) {
        }


        @SearchableDefaults(value = {"baseInfo.realname_like=zhang", "id_eq=1"}, needPage = true)
        @PageableDefaults(value = DEFAULT_PAGESIZE, pageNumber = DEFAULT_PAGENUMBER, sort = {"id=desc", "name=asc"})
        public void methodDefaultSearchable(Searchable searchable) {
        }

        public void parameterDefaultSearchable(
                @SearchableDefaults(value = {"baseInfo.realname_like=zhang", "id_eq=1"}, needPage = true)
                @PageableDefaults(value = DEFAULT_PAGESIZE, pageNumber = DEFAULT_PAGENUMBER, sort = {"id=desc", "name=asc"})
                Searchable searchable) {
        }


        public void customNamePrefixSearchableAndPageableAndSort(
                @Qualifier("foo") Searchable foo, @Qualifier("test") Searchable test) {
        }

        @SearchableDefaults(value = {"baseInfo.realname_like=zhang"}, merge = true, needPage = true)
        @PageableDefaults(value = DEFAULT_PAGESIZE, pageNumber = DEFAULT_PAGENUMBER, sort = {"id=desc", "name=asc"})
        public void methodMergeDefaultSearchable(Searchable searchable) {
        }

    }
}
