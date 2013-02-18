/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.es.common.entity.search;

import com.sishuok.es.common.entity.search.exception.InvlidSpecificationSearchOperatorException;
import org.apache.commons.lang3.StringUtils;

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

    /**
     * 自定义查询映射信息
     */
    private SearchPropertyMappingDefinition.SearchPropertyMappingInfo searchPropertyMappingInfo;

    /**
     * @param searchProperty 属性名
     * @param operator 操作
     * @param value    值
     */
    public SearchFilter(final String searchProperty, final SearchOperator operator, final Object value) {
        this(searchProperty, operator, value, null);
    }

    /**
     * @param searchProperty 属性名
     * @param operator 操作
     * @param value    值
     * @param searchPropertyMappingInfo  自定义查询映射信息
     */
    public SearchFilter(
            final String searchProperty, final SearchOperator operator, final Object value,
            final SearchPropertyMappingDefinition.SearchPropertyMappingInfo searchPropertyMappingInfo) {

        this.searchProperty = searchProperty;
        this.operator = operator;
        this.value = value;
        this.searchPropertyMappingInfo = searchPropertyMappingInfo;
    }

    public String getSearchProperty() {
        return searchProperty;
    }


    /**
     * 获取 Specification 使用的操作符
     * @return
     */
    public SearchOperator getSpecificationOperator() throws InvlidSpecificationSearchOperatorException {
        if(operator != null && operator != SearchOperator.custom) {
            return operator;
        }
        if (searchPropertyMappingInfo != null && StringUtils.isNotBlank(searchPropertyMappingInfo.getDefaultOperator())) {
            return SearchOperator.valueBySymbol(searchPropertyMappingInfo.getDefaultOperator());
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
        if(searchPropertyMappingInfo != null && StringUtils.isNotBlank(searchPropertyMappingInfo.getDefaultOperator())) {
            return searchPropertyMappingInfo.getDefaultOperator();
        }
        return "";
    }

    public Object getValue() {
        return value;
    }

    public SearchPropertyMappingDefinition.SearchPropertyMappingInfo getSearchPropertyMappingInfo() {
        return searchPropertyMappingInfo;
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

    public void setSearchPropertyMappingInfo(SearchPropertyMappingDefinition.SearchPropertyMappingInfo searchPropertyMappingInfo) {
        this.searchPropertyMappingInfo = searchPropertyMappingInfo;
    }

    /**
     * 得到实体属性名
     * @return
     */
    public String getEntityProperty() {
        if(searchPropertyMappingInfo != null) {
            return searchPropertyMappingInfo.getEntityProperty();
        }
        return searchProperty;
    }

    /**
     * 是否是一元过滤 如is null is not null
     * @return
     */
    public boolean isUnaryFilter() {
        String operatorStr = getSpecificationOperator().getSymbol();
        if (operator == SearchOperator.custom) {
            operatorStr = searchPropertyMappingInfo.getDefaultOperator();
        }
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
