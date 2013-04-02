/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.es.common.plugin.entity;

/**
 * 系统实体 不能删除
 * 需要加字段　systemed
 * <p>User: Zhang Kaitao
 * <p>Date: 13-3-31 上午10:43
 * <p>Version: 1.0
 */
public interface Systemable {

    public Boolean getSystemed();

    public void setSystemed(Boolean systemed);

}
