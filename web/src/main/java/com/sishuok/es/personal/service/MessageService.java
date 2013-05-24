/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.es.personal.service;

import com.sishuok.es.common.inject.annotation.BaseComponent;
import com.sishuok.es.common.service.BaseService;
import com.sishuok.es.personal.entity.Message;
import com.sishuok.es.personal.entity.MessageState;
import com.sishuok.es.personal.entity.MessageType;
import com.sishuok.es.personal.repository.MessageRepository;
import com.sishuok.es.sys.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 13-5-22 下午2:40
 * <p>Version: 1.0
 */
@Service
public class MessageService extends BaseService<Message, Long> {

    @Autowired
    @BaseComponent
    private MessageRepository messageRepository;

    public void clearBox(Long userId, MessageState state) {
        messageRepository.clearBox(userId, state);
    }

    public Long countUnread(Long userId) {
        return messageRepository.countUnread(userId, MessageState.in_box);
    }
}
