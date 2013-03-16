/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.es.common.service;

import com.sishuok.es.common.entity.BaseEntity;
import com.sishuok.es.common.entity.search.Searchable;
import com.sishuok.es.common.plugin.entity.LogicDeleteable;
import com.sishuok.es.common.repository.BaseRepository;
import com.sishuok.es.common.repository.BaseRepositoryImpl;
import com.sishuok.es.common.utils.ReflectUtils;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <p>抽象service层基类 提供一些简便方法
 * <p/>
 * <p>泛型 ： M 表示实体类型；ID表示主键类型
 * <p/>
 * <p>User: Zhang Kaitao
 * <p>Date: 13-1-12 下午4:43
 * <p>Version: 1.0
 */
public abstract class BaseService<M extends BaseEntity, ID extends Serializable> implements InitializingBean {


    protected final Class<M> entityClass;
    protected BaseRepository<M, ID> baseRepository;

    protected BaseRepositoryImpl baseDefaultRepositoryImpl;

    public BaseService() {
        this.entityClass = ReflectUtils.findParameterizedType(getClass(), 0);
        this.baseDefaultRepositoryImpl = BaseRepositoryImpl.defaultBaseRepositoryImpl(entityClass);
    }

    public void setBaseRepository(BaseRepository<M, ID> baseRepository) {
        this.baseRepository = baseRepository;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(baseRepository, "BaseRepository required, Class is:" + getClass());
    }


    /**
     * 保存单个实体
     *
     * @param m   实体
     * @return 返回保存的实体
     */
    public M save(M m) {
        return baseRepository.save(m);
    }

    public M saveAndFlush(M m) {
        m = save(m);
        baseRepository.flush();
        return m;
    }

    /**
     * 更新单个实体
     * @param m   实体
     * @return 返回更新的实体
     */
    public M update(M m) {
        return baseRepository.save(m);
    }

    /**
     * 根据主键删除相应实体
     *
     * @param id 主键
     */
    @Transactional
    public void delete(ID id) {
        M m = findOne(id);
        delete(m);
    }

    /**
     * 根据主键删除相应实体
     *
     * @param ids 实体
     */
    @Transactional
    public void delete(ID[] ids) {
        if(ArrayUtils.isEmpty(ids)) {
            return;
        }
        List<M> models = new ArrayList<M>();
        for(ID id : ids) {
            M model = null;
            try {
                model = (M)entityClass.newInstance();
            }catch (Exception e) {
                throw new RuntimeException("batch delete " + entityClass.getName() + " error", e);
            }
            try {
                BeanUtils.setProperty(model, "id", id);
            } catch (Exception e) {
                throw new RuntimeException("batch delete " + entityClass.getName() + " error, can not set id", e);
            }

            models.add(model);
        }

        if(models.get(0) instanceof LogicDeleteable) {
            String hql = String.format("update %s o set o.deleted=true where o in (?1)", this.entityClass.getSimpleName());
            baseDefaultRepositoryImpl.batchUpdate(hql, models);
        } else {
            baseRepository.deleteInBatch(models);
        }

    }

    /**
     * 删除实体
     *
     * @param m 实体
     */
    @Transactional
    public void delete(M m) {
        if(m == null) {
            return;
        }
        if(m != null && m instanceof LogicDeleteable) {
            ((LogicDeleteable) m).markDeleted();
            baseRepository.save(m);
        } else {
            baseRepository.delete(m);
        }
    }


    /**
     * 按照主键查询
     *
     * @param id 主键
     * @return 返回id对应的实体
     */
    public M findOne(ID id) {
        return baseRepository.findOne(id);
    }

    /**
     * 实体是否存在
     *
     * @param id 主键
     * @return 存在 返回true，否则false
     */
    public boolean exists(ID id) {
        return baseRepository.exists(id);
    }

    /**
     * 统计实体总数
     *
     * @return 实体总数
     */
    public long count() {
        return baseRepository.count();
    }

    /**
     * 查询所有实体
     *
     * @return
     */
    public List<M> findAll() {
        return baseRepository.findAll();
    }

    /**
     * 按照顺序查询所有实体
     *
     * @param sort
     * @return
     */
    public List<M> findAll(Sort sort) {
        return baseRepository.findAll(sort);
    }

    /**
     * 分页及排序查询实体
     *
     * @param pageable 分页及排序数据
     * @return
     */
    public Page<M> findAll(Pageable pageable) {
        return baseRepository.findAll(pageable);
    }

    /**
     * 按条件不分页查询实体
     *
     * @param searchable 条件
     * @return
     */
    public List<M> findAllByNoPage(Searchable searchable) {
        return baseRepository.findAll(searchable.getSpecifications(entityClass));
    }

    /**
     * 按条件排序查询实体
     *
     * @param searchable 条件
     * @return
     */
    public List<M> findAllBySort(Searchable searchable) {
        return baseRepository.findAll(searchable.getSpecifications(entityClass), searchable.getSort());
    }


    /**
     * 按条件分页并排序查询实体
     *
     * @param searchable 条件
     * @return
     */
    public Page<M> findAll(Searchable searchable) {
        return baseRepository.findAll(searchable.getSpecifications(entityClass), searchable.getPage());
    }

    /**
     * 按条件分页并排序统计实体数量
     *
     * @param searchable 条件
     * @return
     */
    public Long count(Searchable searchable) {
        return baseRepository.count(searchable.getSpecifications(entityClass));
    }


}
