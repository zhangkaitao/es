/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.es.personal;

import com.sishuok.es.personal.entity.Message;
import com.sishuok.es.personal.entity.MessageContent;
import com.sishuok.es.personal.service.MessageApi;
import com.sishuok.es.personal.service.MessageService;
import com.sishuok.es.sys.user.service.BaseUserIT;
import com.sishuok.es.sys.user.service.UserService;
import com.sishuok.es.test.BaseIT;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 13-5-26 上午9:59
 * <p>Version: 1.0
 */
public class BaseMessageIT extends BaseUserIT {

    @Autowired
    MessageApi messageApi;

    @Autowired
    MessageService messageService;

    @Autowired
    UserService userService;

    Long senderId = 1L;
    Long receiverId = 2L;



    Message sendDefaultMessage() {
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
