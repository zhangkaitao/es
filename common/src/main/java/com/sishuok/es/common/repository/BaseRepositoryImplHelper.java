/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.es.common.repository;

import com.sishuok.es.common.entity.search.Searchable;
import com.sishuok.es.common.entity.search.utils.SearchableConvertUtils;
import org.springframework.data.domain.Sort;

import javax.persistence.Query;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 13-4-14 下午5:28
 * <p>Version: 1.0
 */
public class BaseRepositoryImplHelper {

    /**
     * 按顺序设置Query参数
     * @param query
     * @param params
     */
    public static void setParameters(Query query, Object[] params) {
        if (params != null) {
            for (int i = 0; i < params.length; i++) {
                query.setParameter(i + 1, params[i]);
            }
        }
    }

    /**
     * 将Searchable中的字符串数据转换为Entity的实际值
     * @param search
     */
    public static void convertSearchable(Searchable search, Class<?> entityClass) {
        SearchableConvertUtils.convertSearchValueToEntityValue(search, entityClass);
    }


    public static String prepareOrder(Sort sort) {
        if(sort == null || !sort.iterator().hasNext()) {
            return "";
        }
        StringBuilder orderBy = new StringBuilder("");
        orderBy.append(" order by ");
        orderBy.append(sort.toString().replace(":", " "));
        return orderBy.toString();
    }


}
