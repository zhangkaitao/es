/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.es.common.repository;

import com.sishuok.es.common.entity.BaseEntity;
import com.sishuok.es.common.entity.search.SearchPropertyMappingDefinition;
import com.sishuok.es.common.entity.search.Searchable;
import com.sishuok.es.common.repository.callback.SearchCallback;
import com.sishuok.es.common.utils.SearchConvertUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import javax.persistence.EntityManager;
import javax.persistence.Id;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.List;

/**
 * <p>抽象基础Custom Repository 实现</p>
 * <p>User: Zhang Kaitao
 * <p>Date: 13-1-15 下午7:33
 * <p>Version: 1.0
 */
public abstract class BaseRepositoryImpl<M extends BaseEntity, ID extends Serializable> {

    @PersistenceContext
    protected EntityManager entityManager;

    /**
     * 实体类
     */
    protected final Class<M> entityClass;
    /**
     * ID名字
     */
    protected String idName = null;
    /**
     * 查询所有的QL
     */
    protected final String QL_LIST_ALL;
    /**
     * 统计QL
     */
    protected final String QL_COUNT_ALL;


    public BaseRepositoryImpl() {
        this.entityClass = (Class<M>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        Field[] fields = this.entityClass.getDeclaredFields();
        for (Field f : fields) {
            if (f.isAnnotationPresent(Id.class)) {
                this.idName = f.getName();
                break;
            }
        }


        QL_LIST_ALL = "from " + this.entityClass.getSimpleName() + " o where 1=1 ";
        QL_COUNT_ALL = " select count(o) from " + this.entityClass.getSimpleName() + " o where 1=1 ";

    }


    /**
     * @param hql
     * @param search
     * @return
     */
    protected List<M> find(final String hql, final Searchable search, SearchCallback searchCallback) {
        convertSearchable(search);

        StringBuilder s = new StringBuilder(hql);
        searchCallback.prepareQL(s, search);
        searchCallback.prepareOrder(s, search);
        Query query = entityManager.createQuery(s.toString());
        searchCallback.setValues(query, search);
        searchCallback.setPageable(query, search);
        return query.getResultList();
    }

    protected Long count(final String hql, final Searchable search, SearchCallback searchCallback) {
        convertSearchable(search);
        StringBuilder s = new StringBuilder(hql);
        searchCallback.prepareQL(s, search);
        Query query = entityManager.createQuery(s.toString());
        searchCallback.setValues(query, search);
        return (Long) query.getSingleResult();
    }

    protected M findOne(String hql, final Searchable search, SearchCallback searchCallback) {
        convertSearchable(search);

        StringBuilder s = new StringBuilder(hql);
        searchCallback.prepareQL(s, search);
        searchCallback.prepareOrder(s, search);
        Query query = entityManager.createQuery(s.toString());
        searchCallback.setValues(query, search);
        searchCallback.setPageable(query, search);
        query.setMaxResults(1);
        List<M> result = query.getResultList();

        if(result.size() > 0) {
            return result.get(0);
        }
        return null;
    }

    protected void convertSearchable(Searchable search) {
        SearchConvertUtils.convertSearchValueToDomainValue(search, entityClass);
    }

}
