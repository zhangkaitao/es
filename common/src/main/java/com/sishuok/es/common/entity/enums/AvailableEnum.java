/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.es.common.entity.enums;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 13-2-7 上午11:44
 * <p>Version: 1.0
 */
public enum AvailableEnum {
    TRUE(Boolean.TRUE, "可用"), FALSE(Boolean.FALSE, "不可用");

    private final Boolean value;
    private final String info;

    private AvailableEnum(Boolean value, String info) {
        this.value = value;
        this.info = info;
    }

    public String getInfo() {
        return info;
    }

    public Boolean getValue() {
        return value;
    }
}
