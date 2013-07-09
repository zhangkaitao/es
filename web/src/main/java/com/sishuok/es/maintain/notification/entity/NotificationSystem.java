/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.es.maintain.notification.entity;

/**
 * 触发的子系统
 * <p>User: Zhang Kaitao
 * <p>Date: 13-7-8 下午2:19
 * <p>Version: 1.0
 */
public enum NotificationSystem {

    inner("系统"), message("消息"),excel("excel");

    private final String info;

    private NotificationSystem(final String info) {
        this.info = info;
    }

    public String getInfo() {
        return info;
    }

}
