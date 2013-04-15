/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.es.sys.user.entity;

import com.google.common.collect.Lists;
import com.sishuok.es.common.entity.BaseEntity;
import com.sishuok.es.sys.organization.entity.Job;
import com.sishuok.es.sys.organization.entity.Organization;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

/**
 * 为了提高连表性能 使用数据冗余 而不是 组织机构(1)-----(*)职务的中间表
 * 即在该表中 用户--组织机构--职务 是唯一的  但 用户-组织机构可能重复
 * <p>User: Zhang Kaitao
 * <p>Date: 13-4-5 下午2:04
 * <p>Version: 1.0
 */
@Entity
@Table(name = "sys_user_organization_job")
public class UserOrganizationJob extends BaseEntity<Long> {

    @ManyToOne(optional = true, fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    private User user;

    @ManyToOne(optional = true, fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT) //后期 加二级缓存优化
    private Organization organization;

    @ManyToOne(optional = true, fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT) //后期 加二级缓存优化
    private Job job;


    public UserOrganizationJob() {
    }

    public UserOrganizationJob(Long id) {
        setId(id);
    }

    public UserOrganizationJob(Organization organization) {
        this.organization = organization;
    }

    public UserOrganizationJob(Organization organization, Job job) {
        this.organization = organization;
        this.job = job;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    @Override
    public String toString() {
        return "UserOrganizationJob{user=" + (user == null ? "null" : user.getId())  + ", " +
                "organization=" + organization +
                ", job=" + job +
                '}';
    }
}
