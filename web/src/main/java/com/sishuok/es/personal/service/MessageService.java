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
import com.sishuok.es.personal.repository.MessageRepository;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 13-5-22 下午2:40
 * <p>Version: 1.0
 */
@DependsOn("messageRepository")
@Service
public class MessageService extends BaseService<Message, Long> {

    @Autowired
    @BaseComponent
    private MessageRepository messageRepository;

    /**
     * 改变发件人 消息的原状态为目标状态
     *
     * @param senderId
     * @param oldState
     * @param newState
     */
    public Integer changeSenderState(Long senderId, MessageState oldState, MessageState newState) {
        Date changeDate = new Date();
        return messageRepository.changeSenderState(senderId, oldState, newState, changeDate);
    }

    /**
     * 改变收件人人 消息的原状态为目标状态
     *
     * @param receiverId
     * @param oldState
     * @param newState
     */
    public Integer changeReceiverState(Long receiverId, MessageState oldState, MessageState newState) {
        Date changeDate = new Date();
        return messageRepository.changeReceiverState(receiverId, oldState, newState, changeDate);
    }

    /**
     * 物理删除那些已删除的（即收件人和发件人 同时都删除了的）
     *
     * @param deletedState
     */
    public Integer clearDeletedMessage(MessageState deletedState) {
        return messageRepository.clearDeletedMessage(deletedState);
    }

    /**
     * 更改状态
     *
     * @param oldStates
     * @param newState
     * @param expireDays 当前时间-过期天数 时间之前的消息将改变状态
     */
    public Integer changeState(ArrayList<MessageState> oldStates, MessageState newState, int expireDays) {
        Date changeDate = new Date();
        Integer count = messageRepository.changeSenderState(oldStates, newState, changeDate, DateUtils.addDays(changeDate, -expireDays));
        count += messageRepository.changeReceiverState(oldStates, newState, changeDate, DateUtils.addDays(changeDate, -expireDays));
        return count;
    }

    /**
     * 统计用户收件箱未读消息
     *
     * @param userId
     * @return
     */
    public Long countUnread(Long userId) {
        return messageRepository.countUnread(userId, MessageState.in_box);
    }


}
