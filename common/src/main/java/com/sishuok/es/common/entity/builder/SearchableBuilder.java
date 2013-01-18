/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.es.common.entity.builder;

import com.google.common.collect.Maps;
import com.sishuok.es.common.entity.search.SearchFilter;
import com.sishuok.es.common.entity.search.SearchOperator;
import com.sishuok.es.common.entity.search.SearchRequest;
import com.sishuok.es.common.entity.search.Searchable;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Map;

/**
 * <p>构建Searchable  </p>
 * <p>User: Zhang Kaitao
 * <p>Date: 13-1-16 上午8:47
 * <p>Version: 1.0
 */
public final class SearchableBuilder {

    private Map<String, Object> searchParams;
    private Map<String, SearchFilter> searchFilterMap = Maps.newHashMap();
    private Pageable page;
    private Sort sort;


    private SearchableBuilder() {
    }

    public static SearchableBuilder newInstance() {
        return newInstance(null);
    }

    public static SearchableBuilder newInstance(Map<String, Object> searchParams) {
        SearchableBuilder searchBuilder = new SearchableBuilder();

        searchBuilder.searchParams = searchParams;

        return searchBuilder;
    }

    public SearchableBuilder addSearchParam(String key, String value) {
        searchParams.put(key, value);
        return this;
    }

    public SearchableBuilder addSearchFilter(String searchProperty, SearchOperator operator, Object value) {
        this.searchFilterMap.put(searchProperty, new SearchFilter(searchProperty, operator, value));
        return this;
    }

    public SearchableBuilder addSearchFilter(SearchFilter searchFilter) {
        this.searchFilterMap.put(searchFilter.getSearchProperty(), searchFilter);
        return this;
    }

    public SearchableBuilder setPage(Pageable page) {
        this.page = page;
        return this;
    }

    public SearchableBuilder setSort(Sort sort) {
        this.sort = sort;
        return this;
    }


    public Searchable buildSearchable() {
        SearchRequest search = new SearchRequest(searchParams, page, sort);
        search.getSearchFilterMap().putAll(searchFilterMap);
        return search;
    }


}
