/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.es.common.entity;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

/**
 * <p>用户基本信息</p>
 * <p>User: Zhang Kaitao
 * <p>Date: 13-1-14 下午2:08
 * <p>Version: 1.0
 */
@Entity
@Table(name = "user_baseinfo")
public class BaseInfo extends BaseEntity<Long> {

    @OneToOne()
    @JoinColumn(name = "user_id", unique = true, nullable = false, updatable = false)
    private User user;

    /**
     * 真实名称
     */
    @Column(name = "realname", length = 100)
    private String realname;

    /**
     * 性别
     */
    @Column(name = "sex", length = 2)
    @Enumerated(EnumType.ORDINAL)
    private Sex sex;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "birthday")
    private Date birthday;

    @Column(name = "age")
    private int age;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
