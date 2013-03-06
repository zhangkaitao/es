/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.es.common.plugin.entity;

/**
 * <p>实体实现该接口表示想要调整数据的顺序
 * <p>优先级值越大则展示时顺序越靠前 比如 2 排在 1 前边
 * <p/>
 * <p>User: Zhang Kaitao
 * <p>Date: 13-1-12 下午4:09
 * <p>Version: 1.0
 */
public interface Movable {

    public Integer getWeight();

    public void setWeight(Integer weight);

}
