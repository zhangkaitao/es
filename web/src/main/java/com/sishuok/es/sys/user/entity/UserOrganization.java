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
 * 用户---组织机构 ---- 工作职务 关系表
 * <p>User: Zhang Kaitao
 * <p>Date: 13-4-5 下午2:04
 * <p>Version: 1.0
 */
@Entity
@Table(name = "sys_user_organization")
public class UserOrganization extends BaseEntity<Long> {

    @ManyToOne
    private User user;

    @ManyToOne(optional = true, fetch = FetchType.EAGER)
    private Organization organization;

    @OneToMany(targetEntity = Job.class, fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    @Basic(optional = true, fetch = FetchType.EAGER)
    @JoinTable(
        name = "sys_user_organization_job",
        joinColumns = {
                @JoinColumn(name = "user_organization_id")
        },
        inverseJoinColumns = {
                @JoinColumn(name = "job_id")
        }
    )
    @OrderBy
    private List<Job> jobs;

    public UserOrganization() {
    }

    public UserOrganization(Long id) {
        setId(id);
    }

    public UserOrganization(Organization organization) {
        this.organization = organization;
    }

    public UserOrganization(Organization organization, List<Job> jobs) {
        this.organization = organization;
        this.jobs = jobs;
    }

    public Organization getOrganization() {
        return organization;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    public List<Job> getJobs() {
        if(jobs == null) {
            jobs = Lists.newArrayList();
        }
        return jobs;
    }

    public void setJobs(List<Job> jobs) {
        this.jobs = jobs;
    }

    public void addJob(Job job) {
        getJobs().add(job);
    }

    @Transient
    public String getJobIds() {
        StringBuilder jobIds = new StringBuilder("");
        int index = 0;
        for(Job job : getJobs()) {
            if(index++ > 0) jobIds.append(",");
            jobIds.append(job.getId());

        }
        return jobIds.toString();
    }
}
