/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.es.common.repository;

import com.sishuok.es.common.entity.AbstractEntity;
import com.sishuok.es.common.entity.search.Searchable;
import com.sishuok.es.common.repository.callback.SearchCallback;
import com.sishuok.es.common.utils.ReflectUtils;
import com.sishuok.es.common.utils.SpringUtils;
import org.springframework.data.domain.*;
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
    protected String findAllQL;
    /**
     * 统计QL
     */
    protected String countAllQL;

    private SearchCallback searchCallback = SearchCallback.DEFAULT;

    public void setSearchCallback(SearchCallback searchCallback) {
        this.searchCallback = searchCallback;
    }

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
        findAllQL = String.format("select o from %s o where 1=1 ", this.entityClass.getSimpleName());
        countAllQL = String.format("select count(o) from %s o where 1=1 ", this.entityClass.getSimpleName());
    }

    public void setFindAllQL(String findAllQL) {
        this.findAllQL = findAllQL;
    }

    public void setCountAllQL(String countAllQL) {
        this.countAllQL = countAllQL;
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
        BaseRepositoryImplHelper.convertSearchable(search, entityClass);

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
    protected long count(final String ql, final Searchable search, SearchCallback searchCallback) {
        BaseRepositoryImplHelper.convertSearchable(search, entityClass);
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
        BaseRepositoryImplHelper.convertSearchable(search, entityClass);
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
        return findAll(ql, (Pageable) null, params);
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
        Query query = entityManager.createQuery(ql + BaseRepositoryImplHelper.prepareOrder(pageable != null ? pageable.getSort() : null));
        BaseRepositoryImplHelper.setParameters(query, params);
        if (pageable != null) {
            query.setFirstResult(pageable.getOffset());
            query.setMaxResults(pageable.getPageSize());
        }

        return query.getResultList();
    }

    /**
     * <p>根据ql和按照索引顺序的params执行ql，sort存储排序信息 null表示不排序<br/>
     * 具体使用请参考测试用例：{@see com.sishuok.es.common.repository.UserRepositoryImplIT#testFindAll()}
     * @param ql
     * @param sort null表示不排序
     * @param params
     * @param <T>
     * @return
     */
    public <T> List<T> findAll(final String ql, final Sort sort, final Object... params) {

        Query query = entityManager.createQuery(ql + BaseRepositoryImplHelper.prepareOrder(sort));
        BaseRepositoryImplHelper.setParameters(query, params);
        return query.getResultList();
    }


    /**
     * <p>根据ql和按照索引顺序的params执行ql统计<br/>
     * 具体使用请参考测试用例：com.sishuok.es.common.repository.UserRepositoryImplIT#testCountAll()
     * @param ql
     * @param params
     * @return
     */
    protected long countAll(final String ql, final Object... params) {
        Query query = entityManager.createQuery(ql);
        BaseRepositoryImplHelper.setParameters(query, params);
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
        BaseRepositoryImplHelper.setParameters(query, params);
        return query.executeUpdate();
    }


    /** 与Spring Data Jpa类似的实现 */

    public List<M> findAll() {
        return findAll(findAllQL);
    }

    public List<M> findAll(Sort sort) {
        return findAll(findAllQL, sort);
    }

    public Page<M> findAll(Pageable pageable) {
        return new PageImpl<M>(
            this.<M>findAll(findAllQL, pageable),
            pageable,
            countAll(countAllQL)
        );
    }

    public Page<M> findAll(Searchable searchable) {
        return new PageImpl<M>(
                find(findAllQL, searchable, searchCallback),
                searchable.getPage(),
                count(countAllQL, searchable, searchCallback)
        );
    }

    public List<M> findAllByNoPageNoSort(Searchable searchable) {
        searchable.removePageable();
        searchable.removeSort();
        return find(findAllQL, searchable, searchCallback);
    }

    public List<M> findAllBySort(Searchable searchable) {
        searchable.removePageable();
        return find(findAllQL, searchable, searchCallback);
    }

    public long count(Searchable searchable) {
        return count(countAllQL, searchable, searchCallback);
    }


    public static <M extends AbstractEntity, ID extends Serializable> BaseRepositoryImpl<M, ID> defaultBaseRepositoryImpl(Class<M> entityClass) {
        DefaultRepositoryImpl<M, ID> defaultRepository = new DefaultRepositoryImpl<M, ID>(entityClass);
        EntityManagerFactory emf = SpringUtils.getBean(EntityManagerFactory.class);

        defaultRepository.entityManager = SharedEntityManagerCreator.createSharedEntityManager(emf);
        return defaultRepository;
    }


    private static class DefaultRepositoryImpl<M extends AbstractEntity, ID extends Serializable> extends BaseRepositoryImpl<M, ID> {
        public DefaultRepositoryImpl(Class<M> entityClass) {
            super(entityClass);
        }
    }


}
