/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.es.common.entity.search.utils;

import com.google.common.collect.Lists;
import com.sishuok.es.common.entity.search.SearchOperator;
import com.sishuok.es.common.entity.search.Searchable;
import com.sishuok.es.common.entity.search.exception.InvalidSearchPropertyException;
import com.sishuok.es.common.entity.search.exception.InvalidSearchValueException;
import com.sishuok.es.common.entity.search.exception.SearchException;
import com.sishuok.es.common.entity.search.filter.AndCondition;
import com.sishuok.es.common.entity.search.filter.Condition;
import com.sishuok.es.common.entity.search.filter.OrCondition;
import com.sishuok.es.common.entity.search.filter.SearchFilter;
import com.sishuok.es.common.utils.SpringUtils;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.InvalidPropertyException;
import org.springframework.core.convert.ConversionService;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.List;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 13-1-15 上午11:46
 * <p>Version: 1.0
 */
public final class SearchableConvertUtils {

    private static volatile ConversionService conversionService;

    /**
     * 设置用于类型转换的conversionService
     * 把如下代码放入spring配置文件即可
     * <bean class="org.springframework.beans.factory.config.MethodInvokingFactoryBean">
     * <property name="staticMethod"
     * value="com.sishuok.es.common.entity.search.utils.SearchableConvertUtils.setConversionService"/>
     * <property name="arguments" ref="conversionService"/>
     * </bean>
     *
     * @param conversionService
     */
    public static void setConversionService(ConversionService conversionService) {
        SearchableConvertUtils.conversionService = conversionService;
    }

    public static ConversionService getConversionService() {
        if (conversionService == null) {
            synchronized (SearchableConvertUtils.class) {
                if (conversionService == null) {
                    try {
                        conversionService = SpringUtils.getBean(ConversionService.class);
                    } catch (Exception e) {
                        throw new SearchException("conversionService is null, " +
                                "search param convert must use conversionService. " +
                                "please see [com.sishuok.es.common.entity.search.utils." +
                                "SearchableConvertUtils#setConversionService]");
                    }
                }
            }
        }
        return conversionService;
    }

    /**
     * @param search      查询条件
     * @param entityClass 实体类型
     * @param <T>
     */
    public static <T> void convertSearchValueToEntityValue(final Searchable search, final Class<T> entityClass) {

        if (search.isConverted()) {
            return;
        }

        Collection<SearchFilter> searchFilters = search.getSearchFilters();
        BeanWrapperImpl beanWrapper = new BeanWrapperImpl(entityClass);
        beanWrapper.setAutoGrowNestedPaths(true);
        beanWrapper.setConversionService(getConversionService());

        for (SearchFilter searchFilter : searchFilters) {
            convertSearchValueToEntityValue(beanWrapper, searchFilter);


        }
    }

    private static void convertSearchValueToEntityValue(BeanWrapperImpl beanWrapper, SearchFilter searchFilter) {
        if (searchFilter instanceof Condition) {
            Condition condition = (Condition) searchFilter;
            convert(beanWrapper, condition);
            return;
        }

        if (searchFilter instanceof OrCondition) {
            for (SearchFilter orFilter : ((OrCondition) searchFilter).getOrFilters()) {
                convertSearchValueToEntityValue(beanWrapper, orFilter);
            }
            return;
        }

        if (searchFilter instanceof AndCondition) {
            for (SearchFilter andFilter : ((AndCondition) searchFilter).getAndFilters()) {
                convertSearchValueToEntityValue(beanWrapper, andFilter);
            }
            return;
        }


    }

    private static void convert(BeanWrapperImpl beanWrapper, Condition condition) {
        String searchProperty = condition.getSearchProperty();

        //自定义的也不转换
        if (condition.getOperator() == SearchOperator.custom) {
            return;
        }

        //一元运算符不需要计算
        if (condition.isUnaryFilter()) {
            return;
        }


        String entityProperty = condition.getEntityProperty();

        Object value = condition.getValue();

        Object newValue = null;
        boolean isCollection = value instanceof Collection;
        boolean isArray = value != null && value.getClass().isArray();
        if (isCollection || isArray) {
            List<Object> list = Lists.newArrayList();
            if (isCollection) {
                list.addAll((Collection) value);
            } else {
                list = Lists.newArrayList(CollectionUtils.arrayToList(value));
            }
            int length = list.size();
            for (int i = 0; i < length; i++) {
                list.set(i, getConvertedValue(beanWrapper, searchProperty, entityProperty, list.get(i)));
            }
            newValue = list;
        } else {
            newValue = getConvertedValue(beanWrapper, searchProperty, entityProperty, value);
        }
        condition.setValue(newValue);
    }

    private static Object getConvertedValue(
            final BeanWrapperImpl beanWrapper,
            final String searchProperty,
            final String entityProperty,
            final Object value) {

        Object newValue;
        try {

            beanWrapper.setPropertyValue(entityProperty, value);
            newValue = beanWrapper.getPropertyValue(entityProperty);
        } catch (InvalidPropertyException e) {
            throw new InvalidSearchPropertyException(searchProperty, entityProperty, e);
        } catch (Exception e) {
            throw new InvalidSearchValueException(searchProperty, entityProperty, value, e);
        }

        return newValue;
    }

/*    public static <T> void convertSearchValueToEntityValue(SearchRequest search, Class<T> domainClass) {
        List<Condition> searchFilters = search.getSearchFilters();
        for (Condition searchFilter : searchFilters) {
            String property = searchFilter.getSearchProperty();
            Class<? extends Comparable> targetPropertyType = getPropertyType(domainClass, property);
            Object value = searchFilter.getValue();
            Comparable newValue = convert(value, targetPropertyType);
            searchFilter.setValue(newValue);
        }
    }*/
/*
    private static <T> Class getPropertyType(Class<T> domainClass, String property) {
        String[] names = StringUtils.split(property, ".");
        Class<?> clazz = null;

        for (String name : names) {
            if (clazz == null) {
                clazz = BeanUtils.findPropertyType(name, ArrayUtils.toArray(domainClass));
            } else {
                clazz = BeanUtils.findPropertyType(name, ArrayUtils.toArray(clazz));
            }
        }

        return clazz;
    }*/

/*

    public static <S, T> T convert(S sourceValue, Class<T> targetClass) {
        ConversionService conversionService = getConversionService();
        if (!conversionService.canConvert(sourceValue.getClass(), targetClass)) {
            throw new IllegalArgumentException(
                    "search param can not convert value:[" + sourceValue + "] to target type:[" + targetClass + "]");
        }

        return conversionService.convert(sourceValue, targetClass);
    }
*/


}
