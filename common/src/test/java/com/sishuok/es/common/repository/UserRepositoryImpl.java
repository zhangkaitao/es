/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.es.common.repository;

import com.sishuok.es.common.entity.BaseInfo;
import com.sishuok.es.common.entity.SchoolInfo;
import com.sishuok.es.common.entity.User;
import com.sishuok.es.common.entity.search.Searchable;
import com.sishuok.es.common.repository.callback.DefaultSearchCallback;
import com.sishuok.es.common.repository.callback.SearchCallback;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * <p>跟以前普通DAO实现一样，无需加@Repository，系统会自动扫描，实现格式：</p>
 * <pre>
 *     JpaRepository接口+<jpa:repositories repository-impl-postfix="Impl"></jpa:repositories>中的repository-impl-postfix
 *     repository-impl-postfix默认为Impl
 * </pre>
 * <p>User: Zhang Kaitao
 * <p>Date: 13-1-14 下午4:30
 * <p>Version: 1.0
 */
public class UserRepositoryImpl extends BaseRepositoryImpl<User, Long> {

    @PersistenceContext
    private EntityManager entityManager;

    public BaseInfo findBaseInfoByUserId(Long userId) {
        String ql = "select bi from BaseInfo bi where bi.user.id=?1";
        Query query = entityManager.createQuery(ql);
        query.setParameter(1, userId);
        query.setMaxResults(1);
        List<BaseInfo> baseInfoList = query.getResultList();
        if (baseInfoList.size() > 0) {
            return baseInfoList.get(0);
        }
        return null;
    }

    public List<SchoolInfo> findAllSchoolTypeByUserId(Long userId) {
        String ql = "select si from SchoolInfo si where si.user.id=?1";
        Query query = entityManager.createQuery(ql);
        query.setParameter(1, userId);
        return query.getResultList();
    }


    /**
     * 按条件分页/排序查询，
     * @param search
     * @return
     */
    public Page<User> findAllByDefault(final Searchable search) {
        long total = countAllByDefault(search);
        List<User> contentList = find(QL_LIST_ALL, search, SearchCallback.DEFAULT);
        return new PageImpl(contentList, search.getPage(), total);
    }

    /**
     * 按条件统计
     *
     * @param search
     * @return
     */
    public long countAllByDefault(final Searchable search) {
        return count(QL_COUNT_ALL, search, SearchCallback.DEFAULT);
    }


    private SearchCallback customSearchCallback = new DefaultSearchCallback() {
        @Override
        public void prepareQL(StringBuilder hql, Searchable search) {
            if(search.containsSearchProperty("realname")) {
                hql.append(" and exists(select 1 from BaseInfo bi where o = bi.user and bi.realname like :realname )");
            }
        }
        @Override
        public void setValues(Query query, Searchable search) {
            if(search.containsSearchProperty("realname")) {
                query.setParameter("realname", "%" + search.getValue("realname") + "%");
            }
        }
    };

    /**
     * 按条件统计
     *
     * @param search
     * @return
     */
    public long countAllByCustom(final Searchable search) {
        return count(QL_COUNT_ALL, search, customSearchCallback);
    }

    /**
     * 按条件分页/排序查询，
     *
     * @param search
     * @return
     */
    public Page<User> findAllByCustom(final Searchable search) {
        long total = countAllByCustom(search);
        List<User> contentList = find(QL_LIST_ALL, search, customSearchCallback);
        return new PageImpl(contentList, search.getPage(), total);
    }


}
