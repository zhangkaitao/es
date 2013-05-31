/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.es.common.repository.callback;

import com.sishuok.es.common.entity.search.SearchOperator;
import com.sishuok.es.common.entity.search.Searchable;
import com.sishuok.es.common.entity.search.filter.AndCondition;
import com.sishuok.es.common.entity.search.filter.Condition;
import com.sishuok.es.common.entity.search.filter.OrCondition;
import com.sishuok.es.common.entity.search.filter.SearchFilter;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.util.StringUtils;

import javax.persistence.Query;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 13-1-16 下午4:20
 * <p>Version: 1.0
 */
public class DefaultSearchCallback implements SearchCallback {

    private static final String paramPrefix = "param_";

    private String alias;
    private String aliasWithDot;

    public DefaultSearchCallback() {
        this("");
    }

    public DefaultSearchCallback(String alias) {
        this.alias = alias;
        if (!StringUtils.isEmpty(alias)) {
            this.aliasWithDot = alias + ".";
        } else {
            this.aliasWithDot = "";
        }
    }

    public String getAlias() {
        return alias;
    }

    public String getAliasWithDot() {
        return aliasWithDot;
    }

    @Override
    public void prepareQL(StringBuilder ql, Searchable search) {
        if (!search.hasSearchFilter()) {
            return;
        }

        int paramIndex = 1;
        for (SearchFilter searchFilter : search.getSearchFilters()) {

            if (searchFilter instanceof Condition) {
                Condition condition = (Condition) searchFilter;
                if (condition.getOperator() == SearchOperator.custom) {
                    continue;
                }
            }

            ql.append(" and ");

            paramIndex = genCondition(ql, paramIndex, searchFilter);

        }
    }

    private int genCondition(StringBuilder ql, int paramIndex, SearchFilter searchFilter) {
        boolean needAppendBracket = searchFilter instanceof OrCondition || searchFilter instanceof AndCondition;

        if (needAppendBracket) {
            ql.append("(");
        }

        if (searchFilter instanceof Condition) {
            Condition condition = (Condition) searchFilter;
            //自定义条件
            String entityProperty = condition.getEntityProperty();
            String operatorStr = condition.getOperatorStr();
            //实体名称
            ql.append(getAliasWithDot());
            ql.append(entityProperty);
            //操作符
            //1、如果是自定义查询符号，则使用SearchPropertyMappings中定义的默认的操作符
            ql.append(" ");
            ql.append(operatorStr);

            if (!condition.isUnaryFilter()) {
                ql.append(" :");
                ql.append(paramPrefix);
                ql.append(paramIndex++);
                return paramIndex;
            }
        } else if (searchFilter instanceof OrCondition) {
            boolean isFirst = true;
            for (SearchFilter orSearchFilter : ((OrCondition) searchFilter).getOrFilters()) {
                if (!isFirst) {
                    ql.append(" or ");
                }
                paramIndex = genCondition(ql, paramIndex, orSearchFilter);
                isFirst = false;
            }
        } else if (searchFilter instanceof AndCondition) {
            boolean isFirst = true;
            for (SearchFilter andSearchFilter : ((AndCondition) searchFilter).getAndFilters()) {
                if (!isFirst) {
                    ql.append(" and ");
                }
                paramIndex = genCondition(ql, paramIndex, andSearchFilter);
                isFirst = false;
            }
        }

        if (needAppendBracket) {
            ql.append(")");
        }
        return paramIndex;
    }


    @Override
    public void setValues(Query query, Searchable search) {

        int paramIndex = 1;

        for (SearchFilter searchFilter : search.getSearchFilters()) {
            paramIndex = setValues(query, searchFilter, paramIndex);
        }

    }

    private int setValues(Query query, SearchFilter searchFilter, int paramIndex) {
        if (searchFilter instanceof Condition) {

            Condition condition = (Condition) searchFilter;
            if (condition.getOperator() == SearchOperator.custom) {
                return paramIndex;
            }
            if (condition.isUnaryFilter()) {
                return paramIndex;
            }
            query.setParameter(paramPrefix + paramIndex++, formtValue(condition, condition.getValue()));

        } else if (searchFilter instanceof OrCondition) {

            for (SearchFilter orSearchFilter : ((OrCondition) searchFilter).getOrFilters()) {
                paramIndex = setValues(query, orSearchFilter, paramIndex);
            }

        } else if (searchFilter instanceof AndCondition) {
            for (SearchFilter andSearchFilter : ((AndCondition) searchFilter).getAndFilters()) {
                paramIndex = setValues(query, andSearchFilter, paramIndex);
            }
        }
        return paramIndex;
    }

    private Object formtValue(Condition condition, Object value) {
        SearchOperator operator = condition.getOperator();
        if (operator == SearchOperator.like || operator == SearchOperator.notLike) {
            return "%" + value + "%";
        }
        if (operator == SearchOperator.prefixLike || operator == SearchOperator.prefixNotLike) {
            return value + "%";
        }

        if (operator == SearchOperator.suffixLike || operator == SearchOperator.suffixNotLike) {
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
        if (search.hashSort()) {
            ql.append(" order by ");
            for (Sort.Order order : search.getSort()) {
                ql.append(String.format("%s%s %s, ", getAliasWithDot(), order.getProperty(), order.getDirection().name().toLowerCase()));
            }

            ql.delete(ql.length() - 2, ql.length());
        }
    }


}
