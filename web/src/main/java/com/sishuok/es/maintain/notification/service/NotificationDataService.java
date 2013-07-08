/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.es.maintain.notification.service;

import com.sishuok.es.common.inject.annotation.BaseComponent;
import com.sishuok.es.common.service.BaseService;
import com.sishuok.es.maintain.notification.entity.NotificationData;
import com.sishuok.es.maintain.notification.repository.NotificationDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 13-5-22 下午2:40
 * <p>Version: 1.0
 */
@Service
public class NotificationDataService extends BaseService<NotificationData, Long> {

    @Autowired
    @BaseComponent
    private NotificationDataRepository notificationDataRepository;


}
