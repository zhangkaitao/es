/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.es.common.repository.callback;

import com.sishuok.es.common.entity.search.SearchFilter;
import com.sishuok.es.common.entity.search.Searchable;
import org.springframework.data.domain.Pageable;

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
            ql.append(" and ");
            //自定义条件
            String entityProperty = searchFilter.getEntityProperty();
            String operatorStr = searchFilter.getOperatorStr();
            //实体名称
            ql.append(entityProperty);
            //操作符
            //1、如果是自定义查询符号，则使用SearchPropertyMappings中定义的默认的操作符
            ql.append(" ");
            ql.append(operatorStr);

            if (!searchFilter.isUnaryFilter()) {
                ql.append(" :");
                ql.append(paramPrefix);
                ql.append(paramIndex++);
            }
        }

    }

    public void prepareOrder(StringBuilder ql, Searchable search) {
        if(search.hashSort()) {
            ql.append(" order by ");
            ql.append(search.getSort().toString().replace(":", " "));
        }
    }


    @Override
    public void setValues(Query query, Searchable search) {

        int paramIndex = 1;

        for(SearchFilter searchFilter : search.getSearchFilters()) {
            if(searchFilter.isUnaryFilter()) {
                continue;
            }
            query.setParameter(paramPrefix + paramIndex++, searchFilter.getValue());
        }

    }

    public void setPageable(Query query, Searchable search) {
        if (search.hasPageable()) {
            Pageable pageable = search.getPage();
            query.setFirstResult(pageable.getOffset());
            query.setMaxResults(pageable.getPageSize());
        }
    }

}
