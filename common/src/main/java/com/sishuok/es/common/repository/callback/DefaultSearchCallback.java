/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.es.common.repository.callback;

import com.sishuok.es.common.entity.search.SearchFilter;
import com.sishuok.es.common.entity.search.SearchOperator;
import com.sishuok.es.common.entity.search.Searchable;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.query.QueryUtils;

import javax.persistence.Query;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 13-1-16 下午4:20
 * <p>Version: 1.0
 */
public class DefaultSearchCallback implements SearchCallback {

    private static final String paramPrefix = "param_";

    @Override
    public void prepareQL(StringBuilder ql, Searchable search) {
        if(!search.hasSearchFilter()) {
            return;
        }

        int paramIndex = 1;
        for(SearchFilter searchFilter : search.getSearchFilters()) {

            if(searchFilter.getOperator() == SearchOperator.custom) {
                continue;
            }

            ql.append(" and ");

            if(searchFilter.hasOrSearchFilters()) {
                StringBuilder orCondition = new StringBuilder();
                genCondition(orCondition, paramIndex++, searchFilter);
                for(SearchFilter orSearchFilter : searchFilter.getOrFilters()) {
                    orCondition.append(" or ");
                    genCondition(orCondition, paramIndex, orSearchFilter);
                    paramIndex++;
                }
                ql.append("(");
                ql.append(orCondition);
                ql.append(")");
            } else {
                genCondition(ql, paramIndex, searchFilter);
                paramIndex++;
            }

        }
    }

    private void genCondition(StringBuilder condition, int paramIndex, SearchFilter searchFilter) {

        //自定义条件
        String entityProperty = searchFilter.getEntityProperty();
        String operatorStr = searchFilter.getOperatorStr();
        //实体名称
        condition.append(entityProperty);
        //操作符
        //1、如果是自定义查询符号，则使用SearchPropertyMappings中定义的默认的操作符
        condition.append(" ");
        condition.append(operatorStr);

        if (!searchFilter.isUnaryFilter()) {
            condition.append(" :");
            condition.append(paramPrefix);
            condition.append(paramIndex);
        }

    }


    @Override
    public void setValues(Query query, Searchable search) {

        int paramIndex = 1;

        for(SearchFilter searchFilter : search.getSearchFilters()) {

            if(searchFilter.getOperator() == SearchOperator.custom) {
                continue;
            }

            if(searchFilter.isUnaryFilter()) {
                continue;
            }

            query.setParameter(paramPrefix + paramIndex++, formtValue(searchFilter, searchFilter.getValue()));

            if(searchFilter.hasOrSearchFilters()) {
                for(SearchFilter orSearchFilter : searchFilter.getOrFilters()) {
                    query.setParameter(paramPrefix + paramIndex++, formtValue(orSearchFilter, orSearchFilter.getValue()));
                }
            }
        }

    }

    private Object formtValue(SearchFilter searchFilter, Object value) {
        SearchOperator operator = searchFilter.getOperator();
        if(operator == SearchOperator.like || operator == SearchOperator.notLike) {
            return "%" + value + "%";
        }
        if(operator == SearchOperator.prefixLike || operator == SearchOperator.prefixNotLike) {
            return value + "%";
        }

        if(operator == SearchOperator.suffixLike || operator == SearchOperator.suffixNotLike) {
            return "%" + value;
        }
        return value;
    }

    public void setPageable(Query query, Searchable search) {
        if (search.hasPageable()) {
            Pageable pageable = search.getPage();
            query.setFirstResult(pageable.getOffset());
            query.setMaxResults(pageable.getPageSize());
        }
    }

    public void prepareOrder(StringBuilder ql, Searchable search) {
        String alias = "o";
        if(search.hashSort()) {
            ql.append(" order by ");
            for (Sort.Order order : search.getSort()) {
                ql.append(String.format("%s%s %s, ", alias + ".", order.getProperty(), order.getDirection().name().toLowerCase()));
            }

            ql.delete(ql.length() - 2, ql.length());
        }
    }


}
