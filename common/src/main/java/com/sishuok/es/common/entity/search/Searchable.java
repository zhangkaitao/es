/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.es.common.entity.search;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * <p>查询条件接口</p>
 * <p>User: Zhang Kaitao
 * <p>Date: 13-1-16 上午8:47
 * <p>Version: 1.0
 */
public abstract class Searchable {


    /**
     * 创建一个新的查询
     * @return
     */
    public static Searchable newSearchable() {
        return new SearchRequest();
    }

    /**
     * 创建一个新的查询
     * @return
     */
    public static Searchable newSearchable(final Map<String, Object> searchParams) {
        return new SearchRequest(searchParams);
    }

    /**
     * 创建一个新的查询
     * @return
     */
    public static Searchable newSearchable(final Map<String, Object> searchParams, final Pageable page) {
        return new SearchRequest(searchParams, page);
    }

    /**
     * 创建一个新的查询
     * @return
     */
    public static Searchable newSearchable(final Map<String, Object> searchParams, final Sort sort) {
        return new SearchRequest(searchParams, sort);
    }

    /**
     * 创建一个新的查询
     * @return
     */
    public static Searchable newSearchable(final Map<String, Object> searchParams, final Pageable page, final Sort sort) {
        return new SearchRequest(searchParams, page, sort);
    }


    /**
     * 添加查询参数
     * @param key  如 name_like
     * @param value  如果是in查询 多个值之间","分隔
     * @return
     */
    public abstract Searchable addSearchParam(final String key, final Object value);

    /**
     * 添加一组查询参数
     * @param searchParams
     * @return
     */
    public abstract Searchable addAllSearchParams(final Map<String, Object> searchParams);

    /**
     * 添加过滤条件
     * @param searchProperty 查询的属性名
     * @param operator 操作运算符
     * @param value 值
     */
    public abstract Searchable addSearchFilter(final String searchProperty, final SearchOperator operator, final Object value);

    /**
     * 添加过滤条件 如key="parent.id_eq" value = 1
     * @param key
     */
    public abstract Searchable addSearchFilter(final String key, final Object value);

    public abstract Searchable addSearchFilter(final SearchFilter searchFilter);

    /**
     * 添加多个and连接的过滤条件
     * @param searchFilters
     * @return
     */
    public abstract Searchable addSearchFilters(Collection<SearchFilter> searchFilters);

    /**
     * 添加多个or连接的过滤条件
     * @param searchFilters
     * @return
     */
    public abstract Searchable addOrSearchFilters(Collection<SearchFilter> searchFilters);

    /**
     * 移除指定key的过滤条件
     * @param key
     */
    public abstract Searchable removeSearchFilter(final String key);


    /**
     * 把字符串类型的值转化为entity属性值
     * @param entityClass
     * @param <T>
     */
    public abstract <T> Searchable convert(final Class<T> entityClass);

    /**
     * 标识为已经转换过了 避免多次转换
     */
    public abstract Searchable markConverted();

    public abstract Searchable setPage(final Pageable page);

    /**
     * @param pageNumber 分页页码 索引从 0 开始
     * @param pageSize  每页大小
     * @return
     */
    public abstract Searchable setPage(final int pageNumber, final int pageSize);

    public abstract Searchable addSort(final Sort sort);

    public abstract Searchable addSort(final Sort.Direction direction, String property);




    /**
     * 获取查询过滤条件
     * @return
     */
    public abstract Collection<SearchFilter> getSearchFilters();

    /**
     * 返回一个Entity的Specification
     * @param entityClass
     * @param <T>
     * @return
     */
    public abstract <T> Specification<T> getSpecifications(final Class<T> entityClass);



    /**
     * 是否已经转换过了 避免多次转换
     * @return
     */
    public abstract boolean isConverted();


    /**
     * 是否有查询参数
     * @return
     */
    public abstract boolean hasSearchFilter();

    /**
     * 是否有排序
     * @return
     */
    public abstract boolean hashSort();

    public abstract void removeSort();

    /**
     * 是否有分页
     * @return
     */
    public abstract boolean hasPageable();

    public abstract void removePageable();

    /**
     * 获取分页和排序信息
     * @return
     */
    public abstract Pageable getPage();

    /**
     * 获取排序信息
     * @return
     */
    public abstract Sort getSort();


    /**
     * 是否包含查询属性
      * @param searchProperty
     * @return
     */
    public abstract boolean containsSearchProperty(final String searchProperty);

    /**
     * 获取查询属性对应的值
     * @param searchProperty
     * @return
     */
    public abstract <T> T getValue(final String searchProperty);



}
