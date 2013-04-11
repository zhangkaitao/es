/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.es.common.entity.specification;

import com.google.common.collect.Lists;
import com.sishuok.es.common.entity.search.SearchFilter;
import com.sishuok.es.common.entity.search.SearchOperator;
import com.sishuok.es.common.entity.search.Searchable;
import com.sishuok.es.common.entity.search.exception.InvlidSpecificationSearchOperatorException;
import com.sishuok.es.common.entity.search.exception.SearchException;
import com.sishuok.es.common.entity.search.utils.SearchableConvertUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.CollectionUtils;

import javax.persistence.criteria.*;
import java.util.Collection;
import java.util.List;

/**
 * 动态查询Specification
 * <p>User: Zhang Kaitao
 * <p>Date: 13-1-15 上午10:51
 * <p>Version: 1.0
 */
public final class SearchSpecifications {

    /**
     * 根据sql拼
     * @param search
     * @throws SearchException 如果有不支持的操作符等抛出该异常
     * @return
     */
    public static <T> Specification<T> bySearch(final Searchable search, Class<T> domainClass) throws SearchException {

        //将查询的数据转换为domain对应属性的类型
        SearchableConvertUtils.convertSearchValueToEntityValue(search, domainClass);

        return new Specification<T>() {
            @Override
            public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Collection<SearchFilter> searchFilters = search.getSearchFilters();
                if(CollectionUtils.isEmpty(searchFilters)) {
                    return cb.conjunction();
                }

                List<Predicate> predicateList = Lists.newArrayList();
                for(SearchFilter searchFilter : searchFilters) {
                    try {
                        Path path = toPath(root, searchFilter);
                        Predicate predicate = transformOperatorToPredicate(searchFilter, cb, path);
                        //拼or
                        if(searchFilter.hasOrSearchFilters()) {
                            List<Predicate> orPredicates = Lists.newArrayList(predicate);
                            for(SearchFilter orSearchFilter : searchFilter.getOrFilters()) {
                                Path orPath = toPath(root, orSearchFilter);
                                orPredicates.add(transformOperatorToPredicate(orSearchFilter, cb, orPath));
                            }

                            predicate = cb.or(orPredicates.toArray(new Predicate[0]));
                        }

                        predicateList.add(predicate);
                    } catch (ClassCastException e) {
                        throw new SearchException(
                                "search class cast error. search property:" + searchFilter.getSearchProperty() +
                                ",operator:" + searchFilter.getOperatorStr() + ",value:" + searchFilter.getValue(), e);
                    }
                }

                return cb.and(predicateList.toArray(new Predicate[predicateList.size()]));
            }
        };

    }

    private static <T> Path toPath(Root<T> root, SearchFilter searchFilter) {
        String entityProperty = searchFilter.getEntityProperty();

        String[] names = StringUtils.split(entityProperty, ".");
        Path expression = null;
        for (String name : names) {
            boolean isCollection = name.contains("[") && name.contains("]");
            if(isCollection) {
                name = name.substring(0, name.indexOf('['));
            }
            if(expression == null) {
                if(isCollection) {
                    expression = root.join(name);
                } else {
                    expression = root.get(name);
                }
            } else {
                if(isCollection && expression instanceof Join) {
                    expression = ((Join) expression).join(name);
                } else {
                    expression = expression.get(name);
                }
            }
        }
        return expression;
    }


    /**
     * 根据操作符转换expression和value为Predicate
     * @param searchFilter
     * @param cb
     * @param expression
     * @return
     */
    private static Predicate transformOperatorToPredicate(
            SearchFilter searchFilter, CriteriaBuilder cb, Path expression)  throws SearchException {

        Object value = searchFilter.getValue();

        SearchOperator operator = searchFilter.getOperator();

        switch (operator) {
            case eq :
                return cb.equal(expression, value);
            case notEq://不走索引 慎用
                return cb.notEqual(expression, value);
            case gt :
                return cb.greaterThan(expression, (Comparable)value);
            case gte :
                return cb.greaterThanOrEqualTo(expression, (Comparable)value);
            case lt :
                return cb.lessThan(expression, (Comparable)value);
            case lte:
                return cb.lessThanOrEqualTo(expression, (Comparable)value);
            case prefixLike: //不走索引 慎用
                return cb.like(expression, "%" + String.valueOf(value));
            case prefixNotLike://不走索引 慎用
                return cb.notLike(expression, "%" + String.valueOf(value));
            case suffixLike:
                return cb.like(expression, String.valueOf(value) + "%");
            case suffixNotLike://不走索引 慎用
                return cb.notLike(expression, String.valueOf(value) + "%");
            case like://不走索引 慎用
                return cb.like(expression, "%" + String.valueOf(value) + "%");
            case notLike://不走索引 慎用
                return cb.notLike(expression, "%" + String.valueOf(value) + "%");
            case isNull://可能不走索引 慎用
                return cb.isNull(expression);
            case isNotNull://可能不走索引 慎用
                return cb.isNotNull(expression);
            case in:
                return expression.in((Collection) value);
            case notIn://不走索引 慎用
                 return cb.not(expression.in((Collection) value));
            case custom:
                throw new InvlidSpecificationSearchOperatorException(searchFilter.getSearchProperty(), operator.toString());
        }

        throw new InvlidSpecificationSearchOperatorException(searchFilter.getSearchProperty(), operator.toString());
    }

}
