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

/**
 * <p>查询条件接口</p>
 * <p>User: Zhang Kaitao
 * <p>Date: 13-1-16 上午8:47
 * <p>Version: 1.0
 */
public interface Searchable {

    //查询参数分隔符
    public static final String separator = "_";

    /**
     * 获取查询过滤条件
     * @return
     */
    public Collection<SearchFilter> getSearchFilters();

    /**
     * 添加查询过滤条件
     * @param key 前台传入的查询条件名字 如age_lt
     * @param searchProperty 根据key提取的属性名 如age
     * @param operator 根据key提取的操作符 如lt
     * @param value
     */
    public void addSearchFilter(final String key, final String searchProperty, final SearchOperator operator, final Object value);

    /**
     * 添加过滤条件 如key="parent.id_eq" value = 1
     * @param key
     */
    public void addSearchFilter(final String key, Object value);

    /**
     * 添加过滤条件
     * @param searchProperty 查询的属性名
     * @param operator 操作运算符
     * @param value 值
     */
    public void addSearchFilter(final String searchProperty, SearchOperator operator, Object value);


    /**
     * 获取分页和排序信息
     * @return
     */
    public Pageable getPage();

    /**
     * 获取排序信息
     * @return
     */
    public Sort getSort();

    /**
     * 返回一个Domain的Specification
     * @param domainClass
     * @param <T>
     * @return
     */
    public <T> Specification<T> getSpecifications(final Class<T> domainClass);


    /**
     * 是否已经转换过了 避免多次转换
     * @return
     */
    public boolean isConverted();

    /**
     * 标识为已经转换过了 避免多次转换
     */
    public void markConverted();

    /**
     * 是否有查询参数
     * @return
     */
    public boolean hasSearchFilter();

    /**
     * 是否有排序
     * @return
     */
    public boolean hashSort();

    /**
     * 是否有分页
     * @return
     */
    public boolean hasPageable();

    /**
     * 是否包含查询属性
      * @param searchProperty
     * @return
     */
    public boolean containsSearchProperty(String searchProperty);

    /**
     * 获取查询属性对应的值
     * @param searchProperty
     * @return
     */
    public Object getValue(String searchProperty);


}
