/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.es.common.entity.search;

import com.sishuok.es.common.entity.search.exception.InvalidSearchPropertyException;
import com.sishuok.es.common.entity.search.exception.InvalidSearchValueException;
import com.sishuok.es.common.entity.search.exception.SearchException;
import com.sishuok.es.common.entity.search.filter.SearchFilter;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Collection;
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
     *
     * @return
     */
    public static Searchable newSearchable() {
        return new SearchRequest();
    }

    /**
     * 创建一个新的查询
     *
     * @return
     */
    public static Searchable newSearchable(final Map<String, Object> searchParams) throws SearchException {
        return new SearchRequest(searchParams);
    }

    /**
     * 创建一个新的查询
     *
     * @return
     */
    public static Searchable newSearchable(final Map<String, Object> searchParams, final Pageable page)
            throws SearchException {
        return new SearchRequest(searchParams, page);
    }

    /**
     * 创建一个新的查询
     *
     * @return
     */
    public static Searchable newSearchable(final Map<String, Object> searchParams, final Sort sort)
            throws SearchException {
        return new SearchRequest(searchParams, sort);
    }

    /**
     * 创建一个新的查询
     *
     * @return
     */
    public static Searchable newSearchable(final Map<String, Object> searchParams, final Pageable page, final Sort sort) {
        return new SearchRequest(searchParams, page, sort);
    }


    /**
     * 添加过滤条件 如key="parent.id_eq" value = 1
     * 如果添加时不加操作符 默认是custom 即如key=parent 实际key是parent_custom
     *
     * @param key   如 name_like
     * @param value 如果是in查询 多个值之间","分隔
     * @return
     */
    public abstract Searchable addSearchParam(final String key, final Object value) throws SearchException;

    /**
     * 添加一组查询参数
     *
     * @param searchParams
     * @return
     */
    public abstract Searchable addSearchParams(final Map<String, Object> searchParams) throws SearchException;

    /**
     * 添加过滤条件
     *
     * @param searchProperty 查询的属性名
     * @param operator       操作运算符
     * @param value          值
     */
    public abstract Searchable addSearchFilter(
            final String searchProperty, final SearchOperator operator, final Object value) throws SearchException;

    public abstract Searchable addSearchFilter(final SearchFilter searchFilter);

    /**
     * 添加多个and连接的过滤条件
     *
     * @param searchFilters
     * @return
     */
    public abstract Searchable addSearchFilters(final Collection<? extends SearchFilter> searchFilters);

    /**
     * 添加多个or连接的过滤条件
     *
     * @param first  第一个
     * @param others 其他
     * @return
     */
    public abstract Searchable or(final SearchFilter first, final SearchFilter... others);

    /**
     * 添加多个and连接的过滤条件
     *
     * @param first
     * @param others
     * @return
     */
    public abstract Searchable and(final SearchFilter first, final SearchFilter... others);

    /**
     * 移除指定key的过滤条件
     *
     * @param key
     */
    public abstract Searchable removeSearchFilter(final String key);

    /**
     * 移除指定属性 和 操作符的过滤条件
     * @param searchProperty
     * @param operator
     * @return
     */
    public abstract Searchable removeSearchFilter(String searchProperty, SearchOperator operator);

    /**
     * 把字符串类型的值转化为entity属性值
     *
     * @param entityClass
     * @param <T>
     */
    public abstract <T> Searchable convert(final Class<T> entityClass)
            throws InvalidSearchValueException, InvalidSearchPropertyException;

    /**
     * 标识为已经转换过了 避免多次转换
     */
    public abstract Searchable markConverted();

    public abstract Searchable setPage(final Pageable page);

    /**
     * @param pageNumber 分页页码 索引从 0 开始
     * @param pageSize   每页大小
     * @return
     */
    public abstract Searchable setPage(final int pageNumber, final int pageSize);

    public abstract Searchable addSort(final Sort sort);

    public abstract Searchable addSort(final Sort.Direction direction, String property);


    /**
     * 获取查询过滤条件
     *
     * @return
     */
    public abstract Collection<SearchFilter> getSearchFilters();


    /**
     * 是否已经转换过了 避免多次转换
     *
     * @return
     */
    public abstract boolean isConverted();


    /**
     * 是否有查询参数
     *
     * @return
     */
    public abstract boolean hasSearchFilter();

    /**
     * 是否有排序
     *
     * @return
     */
    public abstract boolean hashSort();

    public abstract void removeSort();

    /**
     * 是否有分页
     *
     * @return
     */
    public abstract boolean hasPageable();

    public abstract void removePageable();

    /**
     * 获取分页和排序信息
     *
     * @return
     */
    public abstract Pageable getPage();

    /**
     * 获取排序信息
     *
     * @return
     */
    public abstract Sort getSort();


    /**
     * 是否包含查询键  如 name_like
     * 包括 or 和 and的
     *
     * @param key
     * @return
     */
    public abstract boolean containsSearchKey(final String key);

    /**
     * 获取查询属性对应的值
     * 不能获取or 或 and 的
     *
     * @param key
     * @return
     */
    public abstract <T> T getValue(final String key);


}
