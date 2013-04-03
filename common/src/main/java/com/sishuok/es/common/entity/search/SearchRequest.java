/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.es.common.entity.search;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.sishuok.es.common.entity.search.exception.SearchException;
import com.sishuok.es.common.entity.search.exception.InvlidSpecificationSearchOperatorException;
import com.sishuok.es.common.entity.specification.SearchSpecifications;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.Assert;

import java.util.*;

/**
 * <p>查询条件（包括分页和排序）</p>
 * <p>User: Zhang Kaitao
 * <p>Date: 13-1-15 上午7:29
 * <p>Version: 1.0
 */

public final class SearchRequest implements Searchable {

    private final Map<String, SearchFilter> searchFilterMap = Maps.newHashMap();

    private final Pageable page;

    private final Sort sort;

    private boolean converted;

    /**
     * @param searchParams
     * @see SearchRequest#SearchRequest(java.util.Map<java.lang.String,java.lang.Object>)
     */
    public SearchRequest(final Map<String, Object> searchParams) {
        this(searchParams, null, null);
    }

    /**
     * @param searchParams
     * @see SearchRequest#SearchRequest(java.util.Map<java.lang.String,java.lang.Object>)
     */
    public SearchRequest(Map<String, Object> searchParams, final Pageable page) {
        this(searchParams, page, null);
    }

    /**
     * @param searchParams
     * @see SearchRequest#SearchRequest(java.util.Map<java.lang.String,java.lang.Object>)
     */
    public SearchRequest(Map<String, Object> searchParams, final Sort sort) {
        this(searchParams, null, sort);
    }


    /**
     * <p>根据查询参数拼Search<br/>
     * 查询参数格式：property_op=value 或 customerProperty=value<br/>
     * customerProperty查找规则是：1、先查找domain的属性，2、如果找不到查找domain上的SearchPropertyMappings映射规则
     * 属性、操作符之间用_分割，op可省略/或custom，省略后值默认为custom，即程序中自定义<br/>
     * 如果op=custom，property也可以自定义（即可以与domain的不一样）,
     * </p>
     *
     * @param searchParams 查询参数组
     * @param page         分页
     * @param sort         排序
     */
    public SearchRequest(final Map<String, Object> searchParams, final Pageable page, final Sort sort) {
        toSearchFilters(searchParams);

        //合并排序
        if(sort == null) {
            this.sort = page != null ? page.getSort() : null;
        } else {
            this.sort = (page != null ? sort.and(page.getSort()) : sort);
        }
        //把排序合并到page中
        if(page != null) {
            this.page = new PageRequest(page.getPageNumber(), page.getPageSize(), this.sort);
        } else {
            this.page = null;
        }
    }

    private void toSearchFilters(final Map<String, Object> searchParams) throws SearchException {
        if(searchParams == null || searchParams.size() == 0) {
            return;
        }
        for (Map.Entry<String, Object> entry : searchParams.entrySet()) {
            String key = entry.getKey();
            Assert.notNull(key, "SearchRequest params key must not null");
            Object value = entry.getValue();

            String[] searchs = StringUtils.split(key, separator);

            if (searchs.length == 0) {
                throw new SearchException("SearchRequest params key format must be : property or property_op");
            }

            String searchProperty = searchs[0];

            SearchOperator operator = null;
            if (searchs.length == 1) {
                operator = SearchOperator.custom;
            } else {
                try {
                    operator = SearchOperator.valueOf(searchs[1]);
                } catch (IllegalArgumentException e) {
                    throw new InvlidSpecificationSearchOperatorException(searchProperty, searchs[1]);
                }
            }

            boolean allowBlankValue = isAllowBlankValue(operator);
            boolean isValueBlank = value == null;
            isValueBlank = isValueBlank || (value instanceof String && StringUtils.isBlank((String) value));
            isValueBlank = isValueBlank || (value instanceof List && ((List)value).size() == 0);
            //过滤掉空值，即不参与查询
            if (!allowBlankValue && isValueBlank) {
                continue;
            }
            addSearchFilter(key, searchProperty, operator, value);
        }
    }

    /**
     * 操作符是否允许为空
     *
     * @param operator
     * @return
     */
    private boolean isAllowBlankValue(final SearchOperator operator) {
        return operator == SearchOperator.isNotNull || operator == SearchOperator.isNull;
    }


    /**
     * @param searchProperty
     * @param operator
     * @param value
     * @see SearchFilter#SearchFilter(java.lang.String, SearchOperator, java.lang.Object)
     */
    public void addSearchFilter(final String key, final String searchProperty, final SearchOperator operator, final Object value) {
        searchFilterMap.put(key, new SearchFilter(searchProperty, operator, value));
    }

    @Override
    public void addSearchFilter(String key, Object value) {
        Map<String, Object> map = Maps.newHashMap();
        map.put(key, value);
        toSearchFilters(map);
    }

    @Override
    public void removeSearchFilter(String key) {
        getSearchFilterMap().remove(key);
    }

    @Override
    public void addSearchFilter(String searchProperty, SearchOperator operator, Object value) {
        addSearchFilter(searchProperty + separator + operator.toString(), value);
    }

    public Collection<SearchFilter> getSearchFilters() {
        return Collections.unmodifiableCollection(searchFilterMap.values());
    }

    public Map<String, SearchFilter> getSearchFilterMap() {
        return searchFilterMap;
    }

    public Pageable getPage() {
        return page;
    }

    public Sort getSort() {
        return sort;
    }

    /**
     * 按条件拼的Specification
     * @param entityClass
     * @param <T>
     * @return
     */
    public <T> Specification<T> getSpecifications(final Class<T> entityClass) {
        return SearchSpecifications.<T>bySearch(this, entityClass);
    }

    public boolean isConverted() {
        return converted;
    }

    @Override
    public void markConverted() {
        this.converted = true;
    }

    @Override
    public boolean hasSearchFilter() {
        return searchFilterMap.size() > 0;
    }

    @Override
    public boolean hashSort() {
        return this.sort != null && this.sort.iterator().hasNext();
    }

    @Override
    public boolean hasPageable() {
        return this.page != null && this.page.getPageSize() > 0;
    }

    @Override
    public boolean containsSearchProperty(String searchProperty) {
        return searchFilterMap.containsKey(searchProperty);
    }

    @Override
    public Object getValue(String searchProperty) {
        return searchFilterMap.get(searchProperty).getValue();
    }


    @Override
    public String toString() {
        return "SearchRequest{" +
                "searchFilterMap=" + searchFilterMap +
                ", page=" + page +
                ", sort=" + sort +
                '}';
    }
}
