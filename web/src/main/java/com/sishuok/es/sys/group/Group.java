/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.es.sys.group;

import com.sishuok.es.common.entity.BaseEntity;

import javax.persistence.*;

/**
 * 分组超类
 * <p>User: Zhang Kaitao
 * <p>Date: 13-4-19 上午7:13
 * <p>Version: 1.0
 */
@Entity
@Table(name = "sys_group")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type")
public class Group extends BaseEntity<Long> {
    /**
     * 分组名称
     */
    private String name;

    /**
     * 是否显示
     */
    @Column(name = "`show`")
    private Boolean show = Boolean.FALSE;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getShow() {
        return show;
    }

    public void setShow(Boolean show) {
        this.show = show;
    }
}
