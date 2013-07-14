/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.es.maintain.notification.service;

import com.google.common.collect.Maps;
import com.sishuok.es.common.entity.search.SearchOperator;
import com.sishuok.es.common.entity.search.Searchable;
import com.sishuok.es.common.spring.utils.AopProxyUtils;
import com.sishuok.es.maintain.notification.entity.NotificationData;
import com.sishuok.es.maintain.notification.entity.NotificationSystem;
import com.sishuok.es.maintain.notification.entity.NotificationTemplate;
import com.sishuok.es.maintain.notification.exception.TemplateNotFoundException;
import com.sishuok.es.sys.user.entity.User;
import com.sishuok.es.sys.user.service.BaseUserIT;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;

import java.util.Map;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 13-7-8 下午5:40
 * <p>Version: 1.0
 */
public class NotificationApiIT extends BaseUserIT {

    @Autowired
    private NotificationApi notificationApi;

    @Autowired
    private NotificationTemplateService notificationTemplateService;

    @Autowired
    private NotificationDataService notificationDataService;

    private String templateName = "hello";
    private String notExistsTemplateName = "haha123321";

    private User user;

    @Before
    public void setUp() {
        super.setUp();
        deleteAll(notificationTemplateService.findAll());

        NotificationTemplate template = new NotificationTemplate();
        template.setName(templateName);
        template.setSystem(NotificationSystem.system);
        template.setTitle("hello {userId}");
        template.setTemplate("hello {userId}, say {message}");
        notificationTemplateService.save(template);

        user = createDefaultUser();

        if(AopProxyUtils.isAsync(notificationApi)) {
            AopProxyUtils.removeAsync(notificationApi);
        }

    }

    @Test
    public void testNotifySuccess() {

        Searchable searchable = Searchable.newSearchable();
        searchable.addSearchFilter("userId", SearchOperator.eq, user.getId());
        searchable.addSort(Sort.Direction.DESC, "id");
        Long expectedCount = notificationDataService.count(searchable) + 1;


        String message = "haha";
        Map<String, Object> context = Maps.newHashMap();
        context.put("userId", user.getId());
        context.put("message", message);
        notificationApi.notify(user.getId(), templateName, context);

        Long actualCount = notificationDataService.count(searchable);
        Assert.assertEquals(expectedCount, actualCount);

        NotificationData lastNotification = notificationDataService.findAllWithSort(searchable).get(0);

        String expectedCotent = "hello " + user.getId() + ", say " + message;
        String actualContent = lastNotification.getContent();
        Assert.assertEquals(expectedCotent, actualContent);

        String expectedTitle = "hello " + user.getId();
        String actualTitle = lastNotification.getTitle();
        Assert.assertEquals(expectedTitle, actualTitle);

    }


    @Test(expected = TemplateNotFoundException.class)
    public void testNotifyWithTemplateNotFoundException() {
        String message = "haha";
        Map<String, Object> context = Maps.newHashMap();
        context.put("userId", user.getId());
        context.put("message", message);
        notificationApi.notify(user.getId(), notExistsTemplateName, context);

    }


}
