/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.es.maintain.dynamictask.entity;

import com.sishuok.es.common.entity.BaseEntity;

import javax.persistence.*;

/**
 * 定时任务 beanName.beanMethod 和 beanClass.beanMethod 二选一
 * <p>User: Zhang Kaitao
 * <p>Date: 13-4-19 上午7:13
 * <p>Version: 1.0
 */
@Entity
@Table(name = "maintain_task_definition")
public class TaskDefinition extends BaseEntity<Long> {

    @Column(name = "name")
    private String name;

    /**
     * cron表达式
     */
    @Column(name = "cron")
    private String cron;


    /**
     * 要执行的class类名
     */
    @Column(name = "bean_class")
    private String beanClass;

    /**
     * 要执行的bean名字
     */
    @Column(name = "bean_name")
    private String beanName;

    /**
     * 要执行的bean的方法名
     */
    @Column(name = "method_name")
    private String methodName;

    /**
     * 是否已启动
     */
    @Column(name = "is_start")
    private Boolean start = Boolean.FALSE;

    /**
     * 描述
     */
    @Column(name = "description")
    private String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCron() {
        return cron;
    }

    public void setCron(String cron) {
        this.cron = cron;
    }

    public String getBeanClass() {
        return beanClass;
    }

    public void setBeanClass(String beanClass) {
        this.beanClass = beanClass;
    }

    public String getBeanName() {
        return beanName;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Boolean getStart() {
        return start;
    }

    public void setStart(Boolean start) {
        this.start = start;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
