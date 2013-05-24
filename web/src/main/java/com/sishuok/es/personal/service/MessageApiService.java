/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.es.personal.service;

import com.google.common.collect.Lists;
import com.sishuok.es.common.entity.search.SearchOperator;
import com.sishuok.es.common.entity.search.Searchable;
import com.sishuok.es.common.entity.search.filter.SearchFilter;
import com.sishuok.es.common.entity.search.filter.SearchFilterHelper;
import com.sishuok.es.common.repository.RepositoryHelper;
import com.sishuok.es.common.utils.LogUtils;
import com.sishuok.es.personal.entity.Message;
import com.sishuok.es.personal.entity.MessageContent;
import com.sishuok.es.personal.entity.MessageState;
import com.sishuok.es.personal.entity.MessageType;
import com.sishuok.es.sys.user.entity.User;
import com.sishuok.es.sys.user.entity.UserStatus;
import com.sishuok.es.sys.user.service.UserService;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 13-5-22 下午2:52
 * <p>Version: 1.0
 */
@Service
public class MessageApiService implements MessageApi {

    @Autowired
    private MessageService messageService;

    @Autowired
    private UserService userService;

    @Override
    public Page<Message> findUserMessage(Long userId, MessageState state, Pageable pageable) {
        Searchable searchable = Searchable.newSearchable();
        searchable.setPage(pageable);

        switch (state) {
            //sender的
            case draft_box:
            case out_box:
                searchable.addSearchFilter("senderId", SearchOperator.eq, userId);
                searchable.addSearchFilter("senderState", SearchOperator.eq, state);
                break;
            //receiver的
            case in_box:
                searchable.addSearchFilter("receiverId", SearchOperator.eq, userId);
                searchable.addSearchFilter("receiverState", SearchOperator.eq, state);
                break;
            //sender or receiver的
            case store_box:
            case trash_box:
                //sender

                SearchFilter senderFilter = SearchFilterHelper.newCondition("senderId", SearchOperator.eq, userId);
                SearchFilter senderStateFilter = SearchFilterHelper.newCondition("senderState", SearchOperator.eq, state);
                SearchFilterHelper.and(senderFilter, senderStateFilter);

                //receiver
                SearchFilter receiverFilter = SearchFilterHelper.newCondition("receiverId", SearchOperator.eq, userId);
                SearchFilter receiverStateFilter = SearchFilterHelper.newCondition("receiverState", SearchOperator.eq, state);
                SearchFilterHelper.and(receiverFilter, receiverStateFilter);

                searchable.or(senderFilter, receiverFilter);
        }


        return messageService.findAll(searchable);
    }

    @Override
    public void send(Message message) {
        Date now = new Date();
        message.setSendDate(now);
        message.setSenderStateChangeDate(now);
        message.setReceiverStateChangeDate(now);

        message.getContent().setMessage(message);

        messageService.save(message);
    }

    @Override
    public void sendSystemMessage(Long[] receiverIds, Message message) {
        message.setType(MessageType.system_message);

        for(Long receiverId : receiverIds) {
            if(receiverId == null) {
                continue;
            }
            Message copyMessage = new Message();
            MessageContent copyMessageContent = new MessageContent();
            copyMessageContent.setContent(message.getContent().getContent());

            BeanUtils.copyProperties(message, copyMessage);

            copyMessage.setContent(copyMessageContent);
            copyMessageContent.setMessage(copyMessage);

            copyMessage.setReceiverId(receiverId);

            send(copyMessage);

        }
    }

    @Async
    @Override
    public void sendSystemMessageToAllUser(Message message) {
        //TODO 变更实现策略 使用异步发送

        int pn = 0;
        int pageSize = 100;

        Pageable pageable = null;
        Page<User> page = null;

        do {
            pageable = new PageRequest(pn++, pageSize);
            page = userService.findAll(pageable);

            try {
                ((MessageApiService)AopContext.currentProxy()).doSendSystemMessage(page.getContent(), message);
            } catch (Exception e) {
                LogUtils.logError("send system message to all user error", e);
            }
            RepositoryHelper.clear();
        } while (page.hasNextPage());


    }

    public void doSendSystemMessage(List<User> receivers, Message message) {
        List<Long> receiverIds = Lists.newArrayList();

        for(User receiver : receivers) {
            if(Boolean.TRUE.equals(receiver.getDeleted()) || receiver.getStatus() != UserStatus.normal) {
                continue;
            }

            receiverIds.add(receiver.getId());
        }

        sendSystemMessage(receiverIds.toArray(new Long[0]), message);
    }

    @Override
    public void recycle(Long userId, Long messageId) {
        changeState(userId, messageId, MessageState.trash_box);
    }
    @Override
    public void recycle(Long userId, Long[] messageIds) {
        for(Long messageId : messageIds) {
            recycle(userId, messageId);
        }
    }

    @Override
    public void store(Long userId, Long messageId) {
        changeState(userId, messageId, MessageState.store_box);
    }

    @Override
    public void store(Long userId, Long[] messageIds) {
        for(Long messageId : messageIds) {
            store(userId, messageId);
        }
    }

    @Override
    public void delete(Long userId, Long messageId) {
        changeState(userId, messageId, MessageState.delete_box);
    }

    @Override
    public void delete(Long userId, Long[] messageIds) {
        for(Long messageId : messageIds) {
            delete(userId, messageId);
        }
    }


    /**
     * 变更状态
     * 根据用户id是收件人/发件人 决定更改哪个状态
     * @param userId
     * @param messageId
     * @param state
     */
    private void changeState(Long userId, Long messageId, MessageState state) {
        Message message = messageService.findOne(messageId);
        if(message == null) {
            return;
        }
        if(userId.equals(message.getSenderId())) {
            message.changeSenderState(state);
        } else {
            message.changeReceiverState(state);
        }
        messageService.update(message);
    }


    @Override
    public void clearDraftBox(Long userId) {
        clearBox(userId, MessageState.draft_box);
    }

    @Override
    public void clearInBox(Long userId) {
        clearBox(userId, MessageState.in_box);
    }

    @Override
    public void clearOutBox(Long userId) {
        clearBox(userId, MessageState.out_box);
    }

    @Override
    public void clearStoreBox(Long userId) {
        clearBox(userId, MessageState.store_box);
    }

    @Override
    public void clearTrashBox(Long userId) {
        clearBox(userId, MessageState.trash_box);
    }

    private void clearBox(Long userId, MessageState state) {
        messageService.clearBox(userId, state);
    }

    @Override
    public Long countUnread(Long userId) {
        return messageService.countUnread(userId);
    }
}
