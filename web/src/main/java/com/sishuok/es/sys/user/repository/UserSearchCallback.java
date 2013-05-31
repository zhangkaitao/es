/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.es.sys.user.repository;

import com.sishuok.es.common.entity.search.Searchable;
import com.sishuok.es.common.repository.callback.DefaultSearchCallback;
import com.sishuok.es.sys.organization.entity.Job;
import com.sishuok.es.sys.organization.entity.Organization;

import javax.persistence.Query;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 13-5-5 下午6:17
 * <p>Version: 1.0
 */
public class UserSearchCallback extends DefaultSearchCallback {

    public UserSearchCallback() {
        super("x");
    }

    @Override
    public void prepareQL(StringBuilder ql, Searchable search) {
        super.prepareQL(ql, search);

        boolean hasOrganization = search.containsSearchKey("organization");
        boolean hasJob = search.containsSearchKey("job");
        if (hasOrganization || hasJob) {
            ql.append(" and exists(select 1 from UserOrganizationJob oj");
            if (hasOrganization) {
                ql.append(" left join oj.organization o ");

            }
            if (hasJob) {
                ql.append(" left join oj.job j ");

            }

            ql.append(" where oj.user=x ");
            if (hasOrganization) {
                ql.append(" and (o.id=:organizationId or o.parentIds like :organizationParentIds)");
            }
            if (hasJob) {
                ql.append(" and (j.id=:jobId or j.parentIds like :jobParentIds)");
            }

            ql.append(")");
        }

    }

    @Override
    public void setValues(Query query, Searchable search) {
        super.setValues(query, search);

        if (search.containsSearchKey("organization")) {
            Organization organization = (Organization) search.getValue("organization");
            query.setParameter("organizationId", organization.getId());
            query.setParameter("organizationParentIds", organization.makeSelfAsNewParentIds() + "%");
        }

        if (search.containsSearchKey("job")) {
            Job job = (Job) search.getValue("job");
            query.setParameter("jobId", job.getId());
            query.setParameter("jobParentIds", job.makeSelfAsNewParentIds() + "%");
        }
    }

}
