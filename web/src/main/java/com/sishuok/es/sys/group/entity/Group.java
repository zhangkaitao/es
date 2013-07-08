/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.es.sys.group.entity;

import com.sishuok.es.common.entity.BaseEntity;
import com.sishuok.es.common.repository.support.annotation.EnableQueryCache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

/**
 * 分组超类
 * <p>User: Zhang Kaitao
 * <p>Date: 13-4-19 上午7:13
 * <p>Version: 1.0
 */
@Entity
@Table(name = "sys_group")
@EnableQueryCache
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Group extends BaseEntity<Long> {
    /**
     * 分组名称
     */
    private String name;

    /**
     * 分组类型 如 用户分组/组织机构分组
     */
    @Enumerated(EnumType.STRING)
    private GroupType type;

    /**
     * 是否是默认分组
     */
    @Column(name = "default_group")
    private Boolean defaultGroup = Boolean.FALSE;

    /**
     * 是否显示/可用
     */
    @Column(name = "is_show")
    private Boolean show = Boolean.FALSE;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public GroupType getType() {
        return type;
    }

    public void setType(GroupType type) {
        this.type = type;
    }

    public Boolean getDefaultGroup() {
        return defaultGroup;
    }

    public void setDefaultGroup(Boolean defaultGroup) {
        this.defaultGroup = defaultGroup;
    }

    public Boolean getShow() {
        return show;
    }

    public void setShow(Boolean show) {
        this.show = show;
    }

}
