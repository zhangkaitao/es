/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.es.common.repository.hibernate.type;

import com.google.common.collect.Maps;

import java.io.Serializable;
import java.util.Map;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 13-3-20 下午7:45
 * <p>Version: 1.0
 */
public class JsonMap implements Serializable {

    private Map<Object, Object> map;

    public JsonMap() {
    }

    public JsonMap(Map<Object, Object> map) {
        this.map = map;
    }

    public Map<Object, Object> getMap() {
        if (map == null) {
            map = Maps.newHashMap();
        }
        return map;
    }

    public void setMap(Map<Object, Object> map) {
        this.map = map;
    }
}
