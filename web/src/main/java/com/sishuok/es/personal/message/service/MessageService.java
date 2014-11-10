/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.es.personal.message.service;

import com.sishuok.es.common.service.BaseService;
import com.sishuok.es.personal.message.entity.Message;
import com.sishuok.es.personal.message.entity.MessageState;
import com.sishuok.es.personal.message.repository.MessageRepository;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 13-5-22 下午2:40
 * <p>Version: 1.0
 */
@Service
public class MessageService extends BaseService<Message, Long> {

    private MessageRepository getMessageRepository() {
        return (MessageRepository) baseRepository;
    }

    /**
     * 改变发件人 消息的原状态为目标状态
     *
     * @param senderId
     * @param oldState
     * @param newState
     */
    public Integer changeSenderState(Long senderId, MessageState oldState, MessageState newState) {
        Date changeDate = new Date();
        return getMessageRepository().changeSenderState(senderId, oldState, newState, changeDate);
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
        return getMessageRepository().changeReceiverState(receiverId, oldState, newState, changeDate);
    }

    /**
     * 物理删除那些已删除的（即收件人和发件人 同时都删除了的）
     *
     * @param deletedState
     */
    public Integer clearDeletedMessage(MessageState deletedState) {
        return getMessageRepository().clearDeletedMessage(deletedState);
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
        Integer count = getMessageRepository().changeSenderState(oldStates, newState, changeDate, DateUtils.addDays(changeDate, -expireDays));
        count += getMessageRepository().changeReceiverState(oldStates, newState, changeDate, DateUtils.addDays(changeDate, -expireDays));
        return count;
    }

    /**
     * 统计用户收件箱未读消息
     *
     * @param userId
     * @return
     */
    public Long countUnread(Long userId) {
        return getMessageRepository().countUnread(userId, MessageState.in_box);
    }


    public void markRead(final Long userId, final Long[] ids) {
        if(ArrayUtils.isEmpty(ids)) {
            return;
        }
        getMessageRepository().markRead(userId, Arrays.asList(ids));
    }
}
