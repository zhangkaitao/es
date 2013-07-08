/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.es.showcase.parentchild.entity;

import com.sishuok.es.common.entity.BaseEntity;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 13-2-19 上午8:59
 * <p>Version: 1.0
 */
@Entity
@Table(name = "showcase_parent")
public class Parent extends BaseEntity<Long> {

    @NotNull(message = "not.null")
    @Column(name = "name")
    private String name;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private ParentChildType type;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "beginDate")
    @Temporal(TemporalType.DATE)
    private Date beginDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "endDate")
    @Temporal(TemporalType.DATE)
    private Date endDate;

    @Column(name = "is_show")
    private Boolean show;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ParentChildType getType() {
        return type;
    }

    public void setType(ParentChildType type) {
        this.type = type;
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Boolean getShow() {
        return show;
    }

    public void setShow(Boolean show) {
        this.show = show;
    }
}
