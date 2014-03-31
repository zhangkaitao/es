/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.es.maintain.task.entity;

import com.sishuok.es.common.entity.BaseEntity;
import com.sishuok.es.maintain.icon.entity.IconType;

import javax.persistence.*;

/**
 * 定时任务 beanName.beanMethod 和 groovyScript 二选一
 * <p>User: Zhang Kaitao
 * <p>Date: 13-4-19 上午7:13
 * <p>Version: 1.0
 */
@Entity
@Table(name = "maintain_dynamic_task")
public class DynamicTask extends BaseEntity<Long> {

    @Column(name = "name")
    private String name;

    /**
     * cron表达式
     */
    @Column(name = "cron")
    private String cron;


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
     * groovy脚本
     */
    @Column(name = "groovy_script")
    private String groovyScript;

    /**
     * 是否已启动
     */
    @Column(name = "is_start")
    private Boolean start = Boolean.FALSE;


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

    public String getGroovyScript() {
        return groovyScript;
    }

    public void setGroovyScript(String groovyScript) {
        this.groovyScript = groovyScript;
    }

    public Boolean getStart() {
        return start;
    }

    public void setStart(Boolean start) {
        this.start = start;
    }
}
