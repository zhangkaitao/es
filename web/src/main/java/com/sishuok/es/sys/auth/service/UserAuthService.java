/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.es.sys.auth.service;

import com.google.common.collect.Sets;
import com.sishuok.es.sys.organization.entity.Job;
import com.sishuok.es.sys.organization.entity.Organization;
import com.sishuok.es.sys.permission.entity.Role;
import com.sishuok.es.sys.user.entity.User;
import com.sishuok.es.sys.user.entity.UserOrganizationJob;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 *
 * 分组、组织机构、用户、新增、修改、删除时evict缓存
 *
 * 获取用户授权的角色及组装好的权限
 * <p>User: Zhang Kaitao
 * <p>Date: 13-5-1 下午2:38
 * <p>Version: 1.0
 */
@Service
public class UserAuthService {

//    @Autowired
//    private UserGroupRelationService userGroupRelationService;
//    @Autowired
//    private OrganizationGroupRelationService organizationGroupRelationService;


    public Set<Role> getRoles(User user) {
        Long userId = user.getId();

        Set<Long[]> organizationJobIds = Sets.newHashSet();
        Set<Long> organizationIds = Sets.newHashSet();

        for(UserOrganizationJob o : user.getOrganizationJobs()) {
            Organization organization = o.getOrganization();
            Job job = o.getJob();

            Long organizationId = organization == null ? 0L : organization.getId();
            Long jobId = job == null ? 0L : job.getId();

            organizationJobIds.add(new Long[]{organizationId, jobId});
            organizationIds.add(organizationId);
        }

        //此处需要找组织机构/职务的所有祖先的

//        Set<Group> userGroups = userGroupRelationService.findUserGroup(user);
//        Set<Group> organizationGroups = organizationGroupRelationService.findOrganizationGroup(organizationIds);



        return null;

    }

    public Set<String> getStringRoles(User user) {
        return null;
    }

    public Set<String> getStringPermissions(User user) {

        return null;
    }


}
