/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.es.personal.message;

import com.sishuok.es.personal.message.entity.Message;
import com.sishuok.es.personal.message.entity.MessageContent;
import com.sishuok.es.personal.message.service.MessageApi;
import com.sishuok.es.personal.message.service.MessageService;
import com.sishuok.es.sys.user.service.BaseUserIT;
import com.sishuok.es.sys.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 13-5-26 上午9:59
 * <p>Version: 1.0
 */
public abstract class BaseMessageIT extends BaseUserIT {

    @Autowired
    protected MessageApi messageApi;

    @Autowired
    protected MessageService messageService;

    @Autowired
    protected UserService userService;

    protected Long senderId = 1L;
    protected Long receiverId = 2L;


    protected Message sendDefaultMessage() {
        Message message = new Message();
        message.setSenderId(senderId);
        message.setReceiverId(receiverId);
        message.setTitle("abcded");
        MessageContent content = new MessageContent();
        content.setContent("abcde");
        message.setContent(content);
        messageApi.send(message);

        return message;
    }
}
