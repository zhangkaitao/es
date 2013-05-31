/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.es.sys.group.entity;

import com.sishuok.es.common.entity.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 分组与 用户/组织机构关系表
 * <p/>
 * 将用户/组织机构放一张表目的是提高查询性能
 * <p/>
 * <p>User: Zhang Kaitao
 * <p>Date: 13-4-19 下午3:44
 * <p>Version: 1.0
 */
@Entity
@Table(name = "sys_group_relation")
//@EnableQueryCache
//@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class GroupRelation extends BaseEntity<Long> {

    @Column(name = "group_id")
    private Long groupId;

    @Column(name = "organization_id")
    private Long organizationId;

    /**
     * 关联的单个用户
     */
    @Column(name = "user_id")
    private Long userId;

    /**
     * 关联的 区间user id 作为单个关联的一种优化
     * 和user二者选一
     * [startUserId, endUserId]闭区间
     */
    @Column(name = "start_user_id")
    private Long startUserId;
    @Column(name = "end_user_id")
    private Long endUserId;

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public Long getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(Long organizationId) {
        this.organizationId = organizationId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getStartUserId() {
        return startUserId;
    }

    public void setStartUserId(Long startUserId) {
        this.startUserId = startUserId;
    }

    public Long getEndUserId() {
        return endUserId;
    }

    public void setEndUserId(Long endUserId) {
        this.endUserId = endUserId;
    }
}
