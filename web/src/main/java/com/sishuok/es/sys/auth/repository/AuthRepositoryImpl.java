/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.es.sys.auth.repository;

import com.google.common.collect.Sets;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import java.util.Set;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 13-5-2 下午3:31
 * <p>Version: 1.0
 */
public class AuthRepositoryImpl {

    @PersistenceContext
    private EntityManager em;


    public Set<Long> findRoleIds(Long userId, Set<Long> groupIds, Set<Long> organizationIds, Set<Long> jobIds, Set<Long[]> organizationJobIds) {

        boolean hasGroupIds = groupIds.size() > 0;
        boolean hasOrganizationIds = organizationIds.size() > 0;
        boolean hasJobIds = jobIds.size() > 0;
        boolean hasOrganizationJobIds = organizationJobIds.size() > 0;

        StringBuilder hql = new StringBuilder("select roleIds from Auth where ");
        hql.append(" (userId=:userId) ");

        if (hasGroupIds) {
            hql.append(" or ");
            hql.append(" (groupId in (:groupIds)) ");
        }

        if (hasOrganizationIds) {
            hql.append(" or ");
            hql.append(" (( organizationId in (:organizationIds) and jobId=0 )) ");
        }

        if (hasJobIds) {
            hql.append(" or ");
            hql.append(" (( organizationId=0 and jobId in (:jobIds) )) ");
        }
        if (hasOrganizationJobIds) {
            int i = 0, l = organizationJobIds.size();
            while (i < l) {
                hql.append(" or ");
                hql.append(" ( organizationId=:organizationId_" + i + " and jobId=:jobId_" + i + " ) ");
                i++;
            }
        }

        Query q = em.createQuery(hql.toString());

        q.setParameter("userId", userId);

        if (hasGroupIds) {
            q.setParameter("groupIds", groupIds);
        }

        if (hasOrganizationIds) {
            q.setParameter("organizationIds", organizationIds);
        }

        if (hasJobIds) {
            q.setParameter("jobIds", jobIds);
        }
        if (hasOrganizationJobIds) {
            int i = 0;
            for (Long[] organizationJobId : organizationJobIds) {
                q.setParameter("organizationId_" + i, organizationJobId[0]);
                q.setParameter("jobId_" + i, organizationJobId[1]);
                i++;
            }
        }

        List<Set<Long>> roleIdSets = (List<Set<Long>>) q.getResultList();

        Set<Long> roleIds = Sets.newHashSet();
        for (Set<Long> set : roleIdSets) {
            roleIds.addAll(set);
        }

        return roleIds;
    }
}
