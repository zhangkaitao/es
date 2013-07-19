/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.es.maintain.push.service;

import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 13-7-17 下午2:28
 * <p>Version: 1.0
 */
@Service
public class PushApiImpl implements PushApi {

    @Autowired
    private PushService pushService;

    @Override
    public void pushUnreadMessage(final Long userId, Long unreadMessageCount) {
        Map<String, Object> data = Maps.newHashMap();
        data.put("unreadMessageCount", unreadMessageCount);
        pushService.push(userId, data);
    }

    @Override
    public void pushNewNotification(final Long userId, List<Map<String, Object>> notifiations) {
        Map<String, Object> data = Maps.newHashMap();
        data.put("notifications", notifiations);
        pushService.push(userId, data);
    }

    @Override
    public void offline(final Long userId) {
        pushService.offline(userId);
    }
}
