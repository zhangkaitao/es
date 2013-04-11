/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.es.common.entity.search;

import com.google.common.collect.Lists;
import com.sishuok.es.common.entity.search.exception.InvlidSpecificationSearchOperatorException;
import org.apache.shiro.util.CollectionUtils;

import java.util.List;

/**
 * <p>查询过滤条件</p>
 * <p>User: Zhang Kaitao
 * <p>Date: 13-1-15 上午7:12
 * <p>Version: 1.0
 */
public final class SearchFilter {

    private String searchProperty;
    private SearchOperator operator;
    private Object value;

    private List<SearchFilter> orFilters;

    /**
     * @param searchProperty 属性名
     * @param operator 操作
     * @param value    值
     */
    public SearchFilter(final String searchProperty, final SearchOperator operator, final Object value) {

        this.searchProperty = searchProperty;
        this.operator = operator;
        this.value = value;
    }

    /**
     * 目前仅支持一级或操作
     * 或 条件运算
     * @param searchProperty
     * @param operator
     * @param value
     * @return
     */
    public SearchFilter or(final String searchProperty, final SearchOperator operator, final Object value) {
       return or(new SearchFilter(searchProperty, operator, value));
    }
    public SearchFilter or(SearchFilter orSearchFilter) {
        if(orFilters == null) {
            orFilters = Lists.newArrayList();
        }
        orFilters.add(orSearchFilter);
        return this;
    }

    public List<SearchFilter> getOrFilters() {
        return orFilters;
    }
    public boolean  hasOrSearchFilters() {
        return !CollectionUtils.isEmpty(getOrFilters());
    }

    public String getSearchProperty() {
        return searchProperty;
    }


    /**
     * 获取 操作符
     * @return
     */
    public SearchOperator getOperator() throws InvlidSpecificationSearchOperatorException {
        if(operator != null && operator != SearchOperator.custom) {
            return operator;
        }
        throw new InvlidSpecificationSearchOperatorException(getSearchProperty(), getOperatorStr());
    }

    /**
     * 获取自定义查询使用的操作符
     * 1、首先获取前台传的
     * 2、获取SearchPropertyMappingInfo中定义的默认的
     * 3、返回空
     * @return
     */
    public String getOperatorStr() {
        if(operator != null && operator != SearchOperator.custom) {
            return operator.getSymbol();
        }
        return "";
    }

    public Object getValue() {
        return value;
    }


    public void setValue(final Object value) {
        this.value = value;
    }

    public void setOperator(final SearchOperator operator) {
        this.operator = operator;
    }

    public void setSearchProperty(final String searchProperty) {
        this.searchProperty = searchProperty;
    }


    /**
     * 得到实体属性名
     * @return
     */
    public String getEntityProperty() {
        return searchProperty;
    }

    /**
     * 是否是一元过滤 如is null is not null
     * @return
     */
    public boolean isUnaryFilter() {
        String operatorStr = getOperator().getSymbol();
        return operatorStr.startsWith("is");
    }

    @Override
    public int hashCode() {
        int result = searchProperty != null ? searchProperty.hashCode() : 0;
        result = 31 * result + (operator != null ? operator.hashCode() : 0);
        result = 31 * result + (value != null ? value.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "SearchFilter{" +
                "searchProperty='" + searchProperty + '\'' +
                ", operator=" + operator +
                ", value=" + value +
                '}';
    }
}
