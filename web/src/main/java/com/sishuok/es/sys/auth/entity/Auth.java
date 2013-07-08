/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.es.sys.auth.entity;

import com.google.common.collect.Sets;
import com.sishuok.es.common.entity.BaseEntity;
import com.sishuok.es.common.repository.hibernate.type.CollectionToStringUserType;
import com.sishuok.es.common.repository.support.annotation.EnableQueryCache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.util.Set;

/**
 * 组织机构 工作职位  用户  角色 关系表
 * 1、授权的五种情况
 * 只给组织机构授权 (orgnizationId=? and jobId=0)
 * 只给工作职务授权 (orgnizationId=0 and jobId=?)
 * 给组织机构和工作职务都授权 (orgnizationId=? and jobId=?)
 * 给用户授权  (userId=?)
 * 给组授权 (groupId=?)
 * <p/>
 * 因此查询用户有没有权限 就是
 * where (orgnizationId=? and jobId=0) or (organizationId = 0 and jobId=?) or (orgnizationId=? and jobId=?) or (userId=?) or (groupId=?)
 * <p/>
 * <p/>
 * 2、为了提高性能
 * 放到一张表
 * 此处不做关系映射（这样需要配合缓存）
 * <p/>
 * 3、如果另一方是可选的（如只选组织机构 或 只选工作职务） 那么默认0 使用0的目的是为了也让走索引
 * <p/>
 * <p>User: Zhang Kaitao
 * <p>Date: 13-4-24 下午2:14
 * <p>Version: 1.0
 */
@TypeDef(
        name = "SetToStringUserType",
        typeClass = CollectionToStringUserType.class,
        parameters = {
                @Parameter(name = "separator", value = ","),
                @Parameter(name = "collectionType", value = "java.util.HashSet"),
                @Parameter(name = "elementType", value = "java.lang.Long")
        }
)
@Entity
@Table(name = "sys_auth")
@EnableQueryCache
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Auth extends BaseEntity<Long> {

    /**
     * 组织机构
     */
    @Column(name = "organization_id")
    private Long organizationId = 0L;

    /**
     * 工作职务
     */
    @Column(name = "job_id")
    private Long jobId = 0L;

    /**
     * 用户
     */
    @Column(name = "user_id")
    private Long userId = 0L;

    /**
     * 组
     */
    @Column(name = "group_id")
    private Long groupId = 0L;

    @Type(type = "SetToStringUserType")
    @Column(name = "role_ids")
    private Set<Long> roleIds;

    @Enumerated(EnumType.STRING)
    private AuthType type;


    public Long getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(Long organizationId) {
        this.organizationId = organizationId;
    }

    public Long getJobId() {
        return jobId;
    }

    public void setJobId(Long jobId) {
        this.jobId = jobId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Set<Long> getRoleIds() {
        if (roleIds == null) {
            roleIds = Sets.newHashSet();
        }
        return roleIds;
    }

    public void setRoleIds(Set<Long> roleIds) {
        this.roleIds = roleIds;
    }

    public void addRoleId(Long roleId) {
        getRoleIds().add(roleId);
    }


    public void addRoleIds(Set<Long> roleIds) {
        getRoleIds().addAll(roleIds);
    }

    public AuthType getType() {
        return type;
    }

    public void setType(AuthType type) {
        this.type = type;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

}
