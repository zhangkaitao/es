/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.es.sys.user.repository;

import com.sishuok.es.common.entity.search.Searchable;
import com.sishuok.es.common.repository.BaseRepositoryImpl;
import com.sishuok.es.common.repository.callback.DefaultSearchCallback;
import com.sishuok.es.sys.organization.entity.Job;
import com.sishuok.es.sys.organization.entity.Organization;
import com.sishuok.es.sys.user.entity.User;

import javax.persistence.Query;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 13-4-14 下午2:53
 * <p>Version: 1.0
 */
public class UserRepositoryImpl extends BaseRepositoryImpl<User, Long> {


    //此处可以使用exists 来优化 或 放弃关系映射
    public UserRepositoryImpl() {
        String ql = "from User u where 1=1 ";
        setFindAllQL("select u " + ql);
        setCountAllQL("select count(u)" + ql);
        setSearchCallback(new DefaultSearchCallback() {
            @Override
            public void prepareQL(StringBuilder ql, Searchable search) {
                super.prepareQL(ql, search);

                boolean hasOrganization = search.containsSearchProperty("organization");
                boolean hasJob = search.containsSearchProperty("job");
                if(hasOrganization || hasJob) {
                    ql.append(" and exists(select 1 from UserOrganizationJob oj");
                    if(hasOrganization) {
                        ql.append(" left join oj.organization o ");

                    }
                    if(hasJob) {
                        ql.append(" left join oj.job j ");

                    }

                    ql.append(" where oj.user=u ");
                    if(hasOrganization) {
                        ql.append(" and (o.id=:organizationId or o.parentIds like :organizationParentIds)");
                    }
                    if(hasJob) {
                        ql.append(" and (j.id=:jobId or j.parentIds like :jobParentIds)");
                    }

                    ql.append(")");
                }

            }

            @Override
            public void setValues(Query query, Searchable search) {
                super.setValues(query, search);

                if(search.containsSearchProperty("organization")) {
                    Organization organization = (Organization) search.getValue("organization");
                    query.setParameter("organizationId", organization.getId());
                    query.setParameter("organizationParentIds", organization.makeSelfAsNewParentIds() + "%");
                }

                if(search.containsSearchProperty("job")) {
                    Job job = (Job) search.getValue("job");
                    query.setParameter("jobId", job.getId());
                    query.setParameter("jobParentIds", job.makeSelfAsNewParentIds() + "%");
                }
            }

            @Override
            public void prepareOrder(StringBuilder ql, Searchable search) {
                if(search.hashSort()) {
                    ql.append(" order by ");
                    ql.append("u." + search.getSort().toString().replace(":", " "));
                }
            }
        });
    }
}
