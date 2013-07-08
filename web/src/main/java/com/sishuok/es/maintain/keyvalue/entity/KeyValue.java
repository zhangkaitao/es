/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.es.maintain.keyvalue.entity;

import com.sishuok.es.common.entity.BaseEntity;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 13-2-4 上午9:38
 * <p>Version: 1.0
 */
@Entity
@Table(name = "maintain_map")
public class KeyValue extends BaseEntity<Long> {

    @Length(min = 1, max = 200, message = "{keyvalue.key.not.valid}")
    @Column(name = "map_key")
    private String key;

    @Length(min = 1, max = 500, message = "{keyvalue.value.not.valid}")
    @Column(name = "map_value")
    private String value;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
