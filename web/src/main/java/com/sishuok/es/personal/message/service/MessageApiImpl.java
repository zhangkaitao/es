/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.es.personal.message.service;

import com.google.common.collect.Lists;
import com.sishuok.es.common.entity.search.SearchOperator;
import com.sishuok.es.common.entity.search.Searchable;
import com.sishuok.es.common.entity.search.filter.SearchFilter;
import com.sishuok.es.common.entity.search.filter.SearchFilterHelper;
import com.sishuok.es.common.repository.RepositoryHelper;
import com.sishuok.es.common.utils.LogUtils;
import com.sishuok.es.maintain.push.service.PushApi;
import com.sishuok.es.personal.message.entity.Message;
import com.sishuok.es.personal.message.entity.MessageContent;
import com.sishuok.es.personal.message.entity.MessageState;
import com.sishuok.es.personal.message.entity.MessageType;
import com.sishuok.es.sys.user.entity.User;
import com.sishuok.es.sys.user.entity.UserStatus;
import com.sishuok.es.sys.user.service.UserService;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 13-5-22 下午2:52
 * <p>Version: 1.0
 */
@Service
public class MessageApiImpl implements MessageApi {

    @Autowired
    private MessageService messageService;

    @Autowired
    private UserService userService;

    @Autowired
    private PushApi pushApi;

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
                SearchFilter and1 = SearchFilterHelper.and(senderFilter, senderStateFilter);

                //receiver
                SearchFilter receiverFilter = SearchFilterHelper.newCondition("receiverId", SearchOperator.eq, userId);
                SearchFilter receiverStateFilter = SearchFilterHelper.newCondition("receiverState", SearchOperator.eq, state);
                SearchFilter and2 = SearchFilterHelper.and(receiverFilter, receiverStateFilter);

                searchable.or(and1, and2);
        }


        return messageService.findAll(searchable);
    }

    @Override
    public List<Message> findAncestorsAndDescendants(Message message) {
        Searchable searchable = Searchable.newSearchable();

        searchable.addSort(Sort.Direction.ASC, "id");

        SearchFilter filter = null;
        //祖先 不为空 从祖先查起
        if (!StringUtils.isEmpty(message.getParentIds())) {
            String ancestorsId = message.getParentIds().split("/")[0];
            filter = SearchFilterHelper.or(
                    SearchFilterHelper.newCondition("parentIds", SearchOperator.prefixLike, ancestorsId + "/"),
                    SearchFilterHelper.newCondition("id", SearchOperator.eq, ancestorsId)
            );
        } else {
            //祖先为空 查自己的后代
            String descendantsParentIds = message.makeSelfAsParentIds();
            filter = SearchFilterHelper.newCondition("parentIds", SearchOperator.prefixLike, descendantsParentIds);
        }

        searchable.addSearchFilter(filter);
        List<Message> result = messageService.findAllWithSort(searchable);
        //把自己排除
        result.remove(message);

        //删除 不可见的消息 如垃圾箱/已删除
        for (int i = result.size() - 1; i >= 0; i--) {
            Message m = result.get(i);

            if (m.getSenderId() == message.getSenderId() &&
                    (m.getSenderState() == MessageState.trash_box || m.getSenderState() == MessageState.delete_box)) {
                result.remove(i);
            }

            if (m.getReceiverId() == message.getSenderId() &&
                    (m.getSenderState() == MessageState.trash_box || m.getSenderState() == MessageState.delete_box)) {
                result.remove(i);
            }
        }

        return result;
    }

    @Override
    public void saveDraft(Message message) {
        message.setSenderState(MessageState.draft_box);
        message.setReceiverState(null);
        if (message.getContent() != null) {
            message.getContent().setMessage(message);
        }
        messageService.save(message);
    }

    @Override
    public void send(Message message) {
        Date now = new Date();
        message.setSendDate(now);
        message.setSenderState(MessageState.out_box);
        message.setSenderStateChangeDate(now);
        message.setReceiverState(MessageState.in_box);
        message.setReceiverStateChangeDate(now);

        message.getContent().setMessage(message);

        if (message.getParentId() != null) {
            Message parent = messageService.findOne(message.getParentId());
            if (parent != null) {
                message.setParentIds(parent.makeSelfAsParentIds());
            }
        }

        messageService.save(message);


        pushApi.pushUnreadMessage(message.getReceiverId(), countUnread(message.getReceiverId()));

    }

    @Override
    public void sendSystemMessage(Long[] receiverIds, Message message) {
        message.setType(MessageType.system_message);

        for (Long receiverId : receiverIds) {
            if (receiverId == null) {
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
                ((MessageApiImpl) AopContext.currentProxy()).doSendSystemMessage(page.getContent(), message);
            } catch (Exception e) {
                LogUtils.logError("send system message to all user error", e);
            }
            RepositoryHelper.clear();
        } while (page.hasNextPage());

    }

    public void doSendSystemMessage(List<User> receivers, Message message) {
        List<Long> receiverIds = Lists.newArrayList();

        for (User receiver : receivers) {
            if (Boolean.TRUE.equals(receiver.getDeleted()) || receiver.getStatus() != UserStatus.normal) {
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
        for (Long messageId : messageIds) {
            recycle(userId, messageId);
        }
    }

    @Override
    public void store(Long userId, Long messageId) {
        changeState(userId, messageId, MessageState.store_box);
    }

    @Override
    public void store(Long userId, Long[] messageIds) {
        for (Long messageId : messageIds) {
            store(userId, messageId);
        }
    }

    @Override
    public void delete(Long userId, Long messageId) {
        changeState(userId, messageId, MessageState.delete_box);
    }

    @Override
    public void delete(Long userId, Long[] messageIds) {
        for (Long messageId : messageIds) {
            delete(userId, messageId);
        }
    }


    /**
     * 变更状态
     * 根据用户id是收件人/发件人 决定更改哪个状态
     *
     * @param userId
     * @param messageId
     * @param state
     */
    private void changeState(Long userId, Long messageId, MessageState state) {
        Message message = messageService.findOne(messageId);
        if (message == null) {
            return;
        }
        if (userId.equals(message.getSenderId())) {
            changeSenderState(message, state);
        } else {
            changeReceiverState(message, state);
        }
        messageService.update(message);
    }

    @Override
    public void clearBox(Long userId, MessageState state) {
        switch (state) {
            case draft_box:
                clearBox(userId, MessageState.draft_box, MessageState.trash_box);
                break;
            case in_box:
                clearBox(userId, MessageState.in_box, MessageState.trash_box);
                break;
            case out_box:
                clearBox(userId, MessageState.out_box, MessageState.trash_box);
                break;
            case store_box:
                clearBox(userId, MessageState.store_box, MessageState.trash_box);
                break;
            case trash_box:
                clearBox(userId, MessageState.trash_box, MessageState.delete_box);
                break;
            default:
                //none;
                break;
        }
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

    private void clearBox(Long userId, MessageState oldState, MessageState newState) {
        if (oldState == MessageState.draft_box
                || oldState == MessageState.out_box
                || oldState == MessageState.store_box
                || oldState == MessageState.trash_box) {

            messageService.changeSenderState(userId, oldState, newState);
        }

        if (oldState == MessageState.in_box
                || oldState == MessageState.store_box
                || oldState == MessageState.trash_box) {
            messageService.changeReceiverState(userId, oldState, newState);
        }

    }

    @Override
    public Long countUnread(Long userId) {
        return messageService.countUnread(userId);
    }

    @Override
    public void markRead(Message message) {
        if (Boolean.TRUE.equals(message.getRead())) {
            return;
        }
        message.setRead(Boolean.TRUE);
        messageService.update(message);
    }

    @Override
    public void markReplied(Message message) {
        if (Boolean.TRUE.equals(message.getReplied())) {
            return;
        }
        message.setReplied(Boolean.TRUE);
        messageService.update(message);
    }

    @Override
    public void markRead(final Long userId, final Long[] ids) {
        messageService.markRead(userId, ids);
    }

    private void changeSenderState(Message message, MessageState state) {
        message.setSenderState(state);
        message.setSenderStateChangeDate(new Date());
    }

    private void changeReceiverState(Message message, MessageState state) {
        message.setReceiverState(state);
        message.setReceiverStateChangeDate(new Date());
    }

}
