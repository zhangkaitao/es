/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.es.common.entity;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.MappedSuperclass;
import java.io.Serializable;

/**
 * <p> 抽象实体基类，提供统一的ID，和相关的基本功能方法
 * <p> 如果是oracle，可以修改该类的
 * <p/>
 * <p>User: Zhang Kaitao
 * <p>Date: 13-1-12 下午4:05
 * <p>Version: 1.0
 */
@MappedSuperclass
public abstract class BaseEntity<PK extends Serializable> extends AbstractPersistable<PK> {

    @Override
    public PK getId() {
        return super.getId();
    }

    @Override
    public void setId(final PK id) {
        super.setId(id);
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }


}
