/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.es.common.repository;

import com.sishuok.es.common.entity.AbstractEntity;
import com.sishuok.es.common.entity.search.Searchable;
import com.sishuok.es.common.repository.callback.SearchCallback;
import com.sishuok.es.common.entity.search.utils.SearchableConvertUtils;
import com.sishuok.es.common.utils.ReflectUtils;
import com.sishuok.es.common.utils.SpringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.orm.jpa.EntityManagerFactoryUtils;
import org.springframework.orm.jpa.ExtendedEntityManagerCreator;
import org.springframework.orm.jpa.SharedEntityManagerCreator;

import javax.persistence.*;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.List;

/**
 * <p>抽象基础Custom Repository 实现</p>
 * <p>User: Zhang Kaitao
 * <p>Date: 13-1-15 下午7:33
 * <p>Version: 1.0
 */
public abstract class BaseRepositoryImpl<M extends AbstractEntity, ID extends Serializable> {

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


    protected BaseRepositoryImpl() {
        this(null);

    }


    protected BaseRepositoryImpl(Class<M> entityClass) {
        if(entityClass == null) {
            entityClass = ReflectUtils.findParameterizedType(getClass(), 0);
        }
        this.entityClass = entityClass;

        Field[] fields = this.entityClass.getDeclaredFields();
        for (Field f : fields) {
            if (f.isAnnotationPresent(Id.class)) {
                this.idName = f.getName();
                break;
            }
        }
        QL_LIST_ALL = String.format("select o from %s o where 1=1 ", this.entityClass.getSimpleName());
        QL_COUNT_ALL = String.format("select count(o) from %s o where 1=1 ", this.entityClass.getSimpleName());

    }





    /**
     * <p>ql条件查询<br/>
     * searchCallback默认实现请参考 {@see com.sishuok.es.common.repository.callback.DefaultSearchCallback}<br/>
     *
     * 测试用例请参考：{@see com.sishuok.es.common.repository.UserRepositoryImplForCustomSearchIT}
     * 和{@see com.sishuok.es.common.repository.UserRepositoryImplForDefaultSearchIT}
     * @param ql
     * @param search 查询条件、分页 排序
     * @param searchCallback 查询回调  自定义设置查询条件和赋值
     * @return
     */
    public List<M> find(final String ql, final Searchable search, SearchCallback searchCallback) {
        convertSearchable(search);

        StringBuilder s = new StringBuilder(ql);
        searchCallback.prepareQL(s, search);
        searchCallback.prepareOrder(s, search);
        Query query = entityManager.createQuery(s.toString());
        searchCallback.setValues(query, search);
        searchCallback.setPageable(query, search);
        return query.getResultList();
    }

    /**
     * <p>按条件统计<br/>
     * 测试用例请参考：{@see com.sishuok.es.common.repository.UserRepositoryImplForCustomSearchIT}
     * 和{@see com.sishuok.es.common.repository.UserRepositoryImplForDefaultSearchIT}
     * @param ql
     * @param search
     * @param searchCallback
     * @return
     */
    protected Long count(final String ql, final Searchable search, SearchCallback searchCallback) {
        convertSearchable(search);
        StringBuilder s = new StringBuilder(ql);
        searchCallback.prepareQL(s, search);
        Query query = entityManager.createQuery(s.toString());
        searchCallback.setValues(query, search);
        return (Long) query.getSingleResult();
    }

    /**
     * 按条件查询一个实体
     * @param ql
     * @param search
     * @param searchCallback
     * @return
     */
    public M findOne(String ql, final Searchable search, SearchCallback searchCallback) {
        convertSearchable(search);
        StringBuilder s = new StringBuilder(ql);
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

    /**
     * @see BaseRepositoryImpl#findAll(String, org.springframework.data.domain.Pageable, Object...) ;
     * @param ql
     * @param params
     * @param <T>
     * @return
     */
    public <T> List<T> findAll(final String ql, final Object... params) {
        return findAll(ql, null, params);
    }

    /**
     * <p>根据ql和按照索引顺序的params执行ql，pageable存储分页信息 null表示不分页<br/>
     * 具体使用请参考测试用例：{@see com.sishuok.es.common.repository.UserRepositoryImplIT#testFindAll()}
     * @param ql
     * @param pageable null表示不分页
     * @param params
     * @param <T>
     * @return
     */
    public <T> List<T> findAll(final String ql, final Pageable pageable, final Object... params) {
        Query query = entityManager.createQuery(ql);
        setParameters(query, params);
        if (pageable != null && pageable.getPageSize() > 0) {
            query.setFirstResult(pageable.getOffset());
            query.setMaxResults(pageable.getPageSize());
        }
        return query.getResultList();
    }

    /**
     * <p>根据ql和按照索引顺序的params执行ql统计<br/>
     * 具体使用请参考测试用例：com.sishuok.es.common.repository.UserRepositoryImplIT#testCountAll()
     * @param ql
     * @param params
     * @return
     */
    protected Long countAll(final String ql, final Object... params) {
           Query query = entityManager.createQuery(ql);
           setParameters(query, params);
          return (Long)query.getSingleResult();
       }

    /**
     * <p>根据ql和按照索引顺序的params查询一个实体<br/>
     * 具体使用请参考测试用例：{@see com.sishuok.es.common.repository.UserRepositoryImplIT#testFindOne()}
     * @param ql
     * @param params
     * @param <T>
     * @return
     */
    public <T> T findOne(final String ql, final Object... params) {
        List<T> list = findAll(ql, new PageRequest(0, 1), params);
        if (list.size() > 0) {
            return list.get(0);
        }

        return null;
    }

    /**
     * <p>执行批处理语句.如 之间insert, update, delete 等.<br/>
     * 具体使用请参考测试用例：{@see com.sishuok.es.common.repository.UserRepositoryImplIT#testBatchUpdate()}
     * @param ql
     * @param params
     * @return
     */
    public int batchUpdate(final String ql, final Object... params) {
        Query query = entityManager.createQuery(ql);
        setParameters(query, params);
        return query.executeUpdate();
    }

    /**
     * 按顺序设置Query参数
     * @param query
     * @param params
     */
    protected void setParameters(Query query, Object[] params) {
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
    protected void convertSearchable(Searchable search) {
        SearchableConvertUtils.convertSearchValueToEntityValue(search, entityClass);
    }


    public static <M extends AbstractEntity, ID extends Serializable> BaseRepositoryImpl<M, ID> defaultBaseRepositoryImpl(Class<M> entityClass) {
        DefaultRepositoryImpl<M, ID> defaultRepository = new DefaultRepositoryImpl<M, ID>(entityClass);
        EntityManagerFactory emf = SpringUtils.getBean(EntityManagerFactory.class);
        EntityManager entityManager = ExtendedEntityManagerCreator.createContainerManagedEntityManager(emf);

        defaultRepository.entityManager = entityManager;
        return defaultRepository;
    }

    private static class DefaultRepositoryImpl<M extends AbstractEntity, ID extends Serializable> extends BaseRepositoryImpl<M, ID> {
        public DefaultRepositoryImpl(Class<M> entityClass) {
            super(entityClass);
        }
    }

}
