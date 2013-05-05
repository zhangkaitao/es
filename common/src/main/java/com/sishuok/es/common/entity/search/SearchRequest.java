/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.es.common.entity.search;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.sishuok.es.common.entity.search.exception.SearchException;
import com.sishuok.es.common.entity.search.exception.InvlidSpecificationSearchOperatorException;
import com.sishuok.es.common.entity.search.utils.SearchableConvertUtils;
import com.sishuok.es.common.entity.specification.SearchSpecifications;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.util.CollectionUtils;
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

public final class SearchRequest extends Searchable {

    private final Map<String, SearchFilter> searchFilterMap = Maps.newHashMap();

    private Pageable page;

    private Sort sort;

    private boolean converted;

    /**
     * @param searchParams
     * @see SearchRequest#SearchRequest(java.util.Map<java.lang.String,java.lang.Object>)
     */
    public SearchRequest(final Map<String, Object> searchParams) {
        this(searchParams, null, null);
    }

    public SearchRequest() {
        this(null, null, null);
    }

    /**
     * @param searchParams
     * @see SearchRequest#SearchRequest(java.util.Map<java.lang.String,java.lang.Object>)
     */
    public SearchRequest(final Map<String, Object> searchParams, final Pageable page) {
        this(searchParams, page, null);
    }

    /**
     * @param searchParams
     * @see SearchRequest#SearchRequest(java.util.Map<java.lang.String,java.lang.Object>)
     */
    public SearchRequest(final Map<String, Object> searchParams, final Sort sort) {
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

        merge(sort, page);
    }


    private void toSearchFilters(final Map<String, Object> searchParams) throws SearchException {
        if(searchParams == null || searchParams.size() == 0) {
            return;
        }
        for (Map.Entry<String, Object> entry : searchParams.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();

            addSearchFilter(SearchFilter.newSearchFilter(key, value));
        }
    }


    @Override
    public Searchable addSearchParam(final String key, final Object value) {
        addSearchFilter(SearchFilter.newSearchFilter(key, value));
        return this;
    }

    @Override
    public Searchable addAllSearchParams(Map<String, Object> searchParams) {
        toSearchFilters(searchParams);
        return this;
    }

    @Override
    public Searchable addSearchFilters(Collection<SearchFilter> searchFilters) {
        if(CollectionUtils.isEmpty(searchFilters)) {
            return this;
        }
        for(SearchFilter searchFilter : searchFilters) {
            addSearchFilter(searchFilter);
        }
        return this;
    }

    @Override
    public Searchable addOrSearchFilters(Collection<SearchFilter> searchFilters) {
        if(CollectionUtils.isEmpty(searchFilters)) {
            return this;
        }
        Iterator<SearchFilter> iter = searchFilters.iterator();
        SearchFilter first = iter.next();
        while(iter.hasNext()) {
            first.or(iter.next());
        }
        addSearchFilter(first);

        return this;
    }

    @Override
    public Searchable addSearchFilter(final String key, final Object value) {
        Map<String, Object> map = Maps.newHashMap();
        map.put(key, value);
        toSearchFilters(map);
        return this;
    }


    @Override
    public Searchable addSearchFilter(final String searchProperty, final SearchOperator operator, final Object value) {
        SearchFilter searchFilter = SearchFilter.newSearchFilter(searchProperty, operator, value);
        return addSearchFilter(searchFilter);
    }

    @Override
    public Searchable addSearchFilter(SearchFilter searchFilter) {
        if(searchFilter == null) {
            return this;
        }
        String key = searchFilter.getKey();
        searchFilterMap.put(key, searchFilter);
        return this;
    }

    /**
     * @param key
     * @return
     */
    @Override
    public Searchable removeSearchFilter(final String key) {
        if(key == null) {
            return this;
        }
        Iterator<Map.Entry<String, SearchFilter>> entries = searchFilterMap.entrySet().iterator();

        while(entries.hasNext()) {
            Map.Entry<String, SearchFilter> entry = entries.next();
            SearchFilter searchFilter = entry.getValue();
            boolean hasFirstRemoved = false;
            if(key.equals(entry.getKey())) {
                entries.remove();
                hasFirstRemoved = true;
            }

            //如果移除的有or查询 则删除第一个 后续的跟上
            if(hasFirstRemoved && searchFilter.hasOrSearchFilters()) {
                List<SearchFilter> orSearchFilters = searchFilter.getOrFilters();
                orSearchFilters.remove(0);//第一个移除即可
                SearchFilter first = orSearchFilters.remove(1);//变成新的根
                for(SearchFilter orSearchFilter : orSearchFilters) {
                    first.or(orSearchFilter);
                }
                addSearchFilter(first);
            }

            //考虑or的情况
            if(!hasFirstRemoved && searchFilter.hasOrSearchFilters()) {
                Iterator<SearchFilter> orIter = searchFilter.getOrFilters().iterator();

                if(orIter.hasNext()) {
                    SearchFilter orSearchFilter = orIter.next();
                    if(key.equals(orSearchFilter.getKey())) {
                        orIter.remove();
                    }
                }
            }
        }

        return this;
    }

    @Override
    public Searchable setPage(final Pageable page) {
        merge(sort, page);
        return this;
    }

    @Override
    public Searchable setPage(int pageNumber, int pageSize) {
        merge(sort, new PageRequest(pageNumber, pageSize));
        return this;
    }

    @Override
    public Searchable addSort(final Sort sort) {
        merge(sort, page);
        return this;
    }

    @Override
    public Searchable addSort(final Sort.Direction direction, final String property) {
        merge(new Sort(direction, property), page);
        return this;
    }




    @Override
    public <T> Searchable convert(final Class<T> entityClass) {
        SearchableConvertUtils.convertSearchValueToEntityValue(this, entityClass);
        markConverted();
        return this;
    }


    @Override
    public Searchable markConverted() {
        this.converted = true;
        return this;
    }





    public Collection<SearchFilter> getSearchFilters() {
        return Collections.unmodifiableCollection(searchFilterMap.values());
    }

    public Map<String, SearchFilter> getSearchFilterMap() {
        return searchFilterMap;
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
    public void removeSort() {
        this.sort = null;
        if(this.page != null) {
            this.page = new PageRequest(page.getPageNumber(), page.getPageSize(), null);
        }
    }

    @Override
    public void removePageable() {
        this.page = null;
    }

    public Pageable getPage() {
        return page;
    }

    public Sort getSort() {
        return sort;
    }

    @Override
    public boolean containsSearchProperty(String searchProperty) {
        return
                searchFilterMap.containsKey(searchProperty) ||
                searchFilterMap.containsKey(searchProperty + SearchFilter.separator + SearchOperator.custom);
    }

    @Override
    public Object getValue(String searchProperty) {
        SearchFilter searchFilter = searchFilterMap.get(searchProperty);
        if(searchFilter == null) {
            searchFilter = searchFilterMap.get(searchProperty + SearchFilter.separator + SearchOperator.custom);
        }
        if(searchFilter == null) {
            return null;
        }
        return searchFilter.getValue();
    }




    private void merge(Sort sort, Pageable page) {
        if(sort == null) {
            sort = this.sort;
        }
        if(page == null) {
            page = this.page;
        }

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



    @Override
    public String toString() {
        return "SearchRequest{" +
                "searchFilterMap=" + searchFilterMap +
                ", page=" + page +
                ", sort=" + sort +
                '}';
    }
}
