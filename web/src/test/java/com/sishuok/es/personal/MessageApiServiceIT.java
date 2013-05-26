/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.es.personal;

import com.sishuok.es.common.entity.search.SearchOperator;
import com.sishuok.es.common.entity.search.Searchable;
import com.sishuok.es.common.repository.hibernate.HibernateUtils;
import com.sishuok.es.personal.entity.Message;
import com.sishuok.es.personal.entity.MessageContent;
import com.sishuok.es.personal.entity.MessageState;
import com.sishuok.es.personal.entity.MessageType;
import com.sishuok.es.personal.service.MessageApi;
import com.sishuok.es.personal.service.MessageService;
import com.sishuok.es.sys.user.entity.User;
import com.sishuok.es.sys.user.service.BaseUserIT;
import com.sishuok.es.sys.user.service.UserService;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import java.sql.Timestamp;
import java.util.Date;

import javax.validation.ConstraintViolationException;
import java.util.List;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 13-5-24 下午6:17
 * <p>Version: 1.0
 */
public class MessageApiServiceIT extends BaseMessageIT {


    @Test
    public void testSend() {

        Long expectedCount = messageService.count() + 1;

        Message message = new Message();
        message.setSenderId(senderId);
        message.setReceiverId(receiverId);
        message.setTitle("abcded");
        MessageContent content = new MessageContent();
        content.setContent("abcde");
        message.setContent(content);
        messageApi.send(message);

        Long actualCount = messageService.count();
        Assert.assertEquals(expectedCount, actualCount);

        Page<Message> page =  messageApi.findUserMessage(receiverId, MessageState.in_box, null);
        Assert.assertTrue(page.getTotalElements() >= 1);
        Assert.assertTrue(messageApi.countUnread(receiverId) >= 1);
    }


    @Test
    public void testSendWithParent() {

        Long expectedCount = 3 + messageService.count();

        Message message1 = new Message();
        message1.setSenderId(senderId);
        message1.setReceiverId(receiverId);
        message1.setTitle("abcded");
        MessageContent content1 = new MessageContent();
        content1.setContent("abcde");
        message1.setContent(content1);
        messageApi.send(message1);

        Message message2 = new Message();
        message2.setSenderId(senderId);
        message2.setReceiverId(receiverId);
        message2.setTitle("abcded");
        MessageContent content2 = new MessageContent();
        content2.setContent("abcde");
        message2.setContent(content2);
        message2.setParentId(message1.getId());
        messageApi.send(message2);


        Message message3 = new Message();
        message3.setSenderId(senderId);
        message3.setReceiverId(receiverId);
        message3.setTitle("abcded");
        MessageContent content3 = new MessageContent();
        content3.setContent("abcde");
        message3.setContent(content3);
        message3.setParentId(message2.getId());
        messageApi.send(message3);


        Long actualCount = messageService.count();
        Assert.assertEquals(expectedCount, actualCount);


        Assert.assertEquals(message1.getId() + "/" + message2.getId() + "/", message3.getParentIds());


        Page<Message> page =  messageApi.findUserMessage(receiverId, MessageState.in_box, null);
        Assert.assertTrue(page.getTotalElements() >= 1);
        Assert.assertTrue(messageApi.countUnread(receiverId) >= 1);
    }



    @Test
    public void testSendSystemMessageSuccess() {

        Long expectedCount = 3 + messageService.count();

        Message message = new Message();
        message.setSenderId(senderId);
        message.setTitle("abcded");
        MessageContent content = new MessageContent();
        content.setContent("abcde");
        message.setContent(content);
        Long[] userIds = new Long[]{1L, 2L, 3L};
        messageApi.sendSystemMessage(userIds, message);

        Long actualCount = messageService.count();
        Assert.assertEquals(expectedCount, actualCount);

        Searchable searchable = Searchable.newSearchable();
        searchable.addSearchFilter("receiverId", SearchOperator.in, userIds);
        List<Message> list = messageService.findAllWithNoPageNoSort(searchable);
        Assert.assertEquals(userIds.length, list.size());
        Assert.assertEquals(MessageType.system_message, list.get(0).getType());

        Page<Message> page =  messageApi.findUserMessage(receiverId, MessageState.in_box, null);
        Assert.assertTrue(page.getTotalElements() >= 1);
        Assert.assertTrue(messageApi.countUnread(receiverId) >= 1);
    }


    @Test
    public void testSendSystemMessageToAllUserSuccess() {

        User user1 = createDefaultUser();

        Long expectedCount = userService.count();

        Message message = new Message();
        message.setSenderId(senderId);
        message.setTitle("abcded");
        MessageContent content = new MessageContent();
        content.setContent("abcde");
        message.setContent(content);

        messageApi.sendSystemMessageToAllUser(message);

        Long actualCount = messageService.count();
        Assert.assertEquals(expectedCount, actualCount);

        Long[] userIds = new Long[] {user1.getId()};
        Searchable searchable = Searchable.newSearchable();
        searchable.addSearchFilter("receiverId", SearchOperator.in, userIds);
        List<Message> list = messageService.findAllWithNoPageNoSort(searchable);
        Assert.assertEquals(userIds.length, list.size());
        Assert.assertEquals(MessageType.system_message, list.get(0).getType());
    }



    @Test
    public void testRecycleSenderSingleMessage() {
        Long expectedTrashBoxCount =
                messageApi.findUserMessage(senderId, MessageState.trash_box, null).getTotalElements() + 1;
        Long expectedUnreadCount = messageApi.countUnread(receiverId) + 1;

        Message message = sendDefaultMessage();
        messageApi.recycle(senderId, message.getId());

        Assert.assertEquals(MessageState.trash_box, message.getSenderState());
        Assert.assertEquals(MessageState.in_box, message.getReceiverState());

        Long actualTrashBoxCount =  messageApi.findUserMessage(senderId, MessageState.trash_box, null).getTotalElements();
        Long actualUnreadCount = messageApi.countUnread(receiverId);
        Assert.assertEquals(expectedTrashBoxCount, actualTrashBoxCount);
        Assert.assertEquals(expectedUnreadCount, actualUnreadCount);
    }


    @Test
    public void testRecycleReceiverSingleMessage() {
        Long expectedTrashBoxCount =
                messageApi.findUserMessage(receiverId, MessageState.trash_box, null).getTotalElements() + 1;
        Long expectedUnreadCount = messageApi.countUnread(receiverId);

        Message message = sendDefaultMessage();
        messageApi.recycle(receiverId, message.getId());

        Assert.assertEquals(MessageState.out_box, message.getSenderState());
        Assert.assertEquals(MessageState.trash_box, message.getReceiverState());

        Long actualTrashBoxCount =  messageApi.findUserMessage(receiverId, MessageState.trash_box, null).getTotalElements();
        Long actualUnreadCount = messageApi.countUnread(receiverId);

        Assert.assertEquals(expectedTrashBoxCount, actualTrashBoxCount);
        Assert.assertEquals(expectedUnreadCount, actualUnreadCount);
    }


    @Test
    public void testBatchRecycleSenderMessage() {
        Long expectedTrashBoxCount = 3 + messageApi.findUserMessage(senderId, MessageState.trash_box, null).getTotalElements();
        Long expectedUnreadCount = 3 + messageApi.countUnread(receiverId);

        Message message1 = sendDefaultMessage();
        Message message2 = sendDefaultMessage();
        Message message3 = sendDefaultMessage();
        Long[] messageIds = new Long[] {message1.getId(), message2.getId(), message3.getId()};

        messageApi.recycle(senderId, messageIds);

        message1 = messageService.findOne(message1.getId());
        Assert.assertEquals(MessageState.trash_box, message1.getSenderState());
        Assert.assertEquals(MessageState.in_box, message1.getReceiverState());

        Long actualTrashBoxCount =  messageApi.findUserMessage(senderId, MessageState.trash_box, null).getTotalElements();
        Long actualUnreadCount = messageApi.countUnread(receiverId);
        Assert.assertEquals(expectedTrashBoxCount, actualTrashBoxCount);
        Assert.assertEquals(expectedUnreadCount, actualUnreadCount);
    }


    @Test
    public void testBatchRecycleReceiverMessage() {
        Long expectedTrashBoxCount = 3 + messageApi.findUserMessage(receiverId, MessageState.trash_box, null).getTotalElements();
        Long expectedUnreadCount = messageApi.countUnread(receiverId);

        Message message1 = sendDefaultMessage();
        Message message2 = sendDefaultMessage();
        Message message3 = sendDefaultMessage();
        Long[] messageIds = new Long[] {message1.getId(), message2.getId(), message3.getId()};

        messageApi.recycle(receiverId, messageIds);

        message1 = messageService.findOne(message1.getId());
        Assert.assertEquals(MessageState.out_box, message1.getSenderState());
        Assert.assertEquals(MessageState.trash_box, message1.getReceiverState());

        Long actualTrashBoxCount =  messageApi.findUserMessage(receiverId, MessageState.trash_box, null).getTotalElements();
        Long actualUnreadCount = messageApi.countUnread(receiverId);

        Assert.assertEquals(expectedTrashBoxCount, actualTrashBoxCount);
        Assert.assertEquals(expectedUnreadCount, actualUnreadCount);
    }


    @Test
    public void testStoreSenderSingleMessage() {
        Long expectedStoreBoxCount =
                messageApi.findUserMessage(senderId, MessageState.trash_box, null).getTotalElements() + 1;
        Long expectedUnreadCount = messageApi.countUnread(receiverId) + 1;

        Message message = sendDefaultMessage();
        messageApi.store(senderId, message.getId());

        Assert.assertEquals(MessageState.store_box, message.getSenderState());
        Assert.assertEquals(MessageState.in_box, message.getReceiverState());

        Long actualStoreBoxCount =  messageApi.findUserMessage(senderId, MessageState.store_box, null).getTotalElements();
        Long actualUnreadCount = messageApi.countUnread(receiverId);
        Assert.assertEquals(expectedStoreBoxCount, actualStoreBoxCount);
        Assert.assertEquals(expectedUnreadCount, actualUnreadCount);
    }


    @Test
    public void testStoreReceiverSingleMessage() {
        Long expectedStoreBoxCount =
                messageApi.findUserMessage(receiverId, MessageState.trash_box, null).getTotalElements() + 1;
        Long expectedUnreadCount = messageApi.countUnread(receiverId);

        Message message = sendDefaultMessage();
        messageApi.store(receiverId, message.getId());

        Assert.assertEquals(MessageState.out_box, message.getSenderState());
        Assert.assertEquals(MessageState.store_box, message.getReceiverState());

        Long actualStoreBoxCount =  messageApi.findUserMessage(receiverId, MessageState.store_box, null).getTotalElements();
        Long actualUnreadCount = messageApi.countUnread(receiverId);

        Assert.assertEquals(expectedStoreBoxCount, actualStoreBoxCount);
        Assert.assertEquals(expectedUnreadCount, actualUnreadCount);
    }


    @Test
    public void testBatchStoreSenderMessage() {
        Long expectedStoreBoxCount = 3 + messageApi.findUserMessage(senderId, MessageState.store_box, null).getTotalElements();
        Long expectedUnreadCount = 3 + messageApi.countUnread(receiverId);

        Message message1 = sendDefaultMessage();
        Message message2 = sendDefaultMessage();
        Message message3 = sendDefaultMessage();
        Long[] messageIds = new Long[] {message1.getId(), message2.getId(), message3.getId()};

        messageApi.store(senderId, messageIds);

        message1 = messageService.findOne(message1.getId());
        Assert.assertEquals(MessageState.store_box, message1.getSenderState());
        Assert.assertEquals(MessageState.in_box, message1.getReceiverState());

        Long actualStoreBoxCount =  messageApi.findUserMessage(senderId, MessageState.store_box, null).getTotalElements();
        Long actualUnreadCount = messageApi.countUnread(receiverId);
        Assert.assertEquals(expectedStoreBoxCount, actualStoreBoxCount);
        Assert.assertEquals(expectedUnreadCount, actualUnreadCount);
    }


    @Test
    public void testBatchStoreReceiverMessage() {
        Long expectedStoreBoxCount = 3 + messageApi.findUserMessage(receiverId, MessageState.trash_box, null).getTotalElements();
        Long expectedUnreadCount = messageApi.countUnread(receiverId);

        Message message1 = sendDefaultMessage();
        Message message2 = sendDefaultMessage();
        Message message3 = sendDefaultMessage();
        Long[] messageIds = new Long[] {message1.getId(), message2.getId(), message3.getId()};

        messageApi.store(receiverId, messageIds);

        message1 = messageService.findOne(message1.getId());
        Assert.assertEquals(MessageState.out_box, message1.getSenderState());
        Assert.assertEquals(MessageState.store_box, message1.getReceiverState());

        Long actualStoreBoxCount =  messageApi.findUserMessage(receiverId, MessageState.store_box, null).getTotalElements();
        Long actualUnreadCount = messageApi.countUnread(receiverId);

        Assert.assertEquals(expectedStoreBoxCount, actualStoreBoxCount);
        Assert.assertEquals(expectedUnreadCount, actualUnreadCount);
    }




    @Test
    public void testDeleteSenderSingleMessage() {
        Long expectedDeleteBoxCount =
                messageApi.findUserMessage(senderId, MessageState.trash_box, null).getTotalElements() + 1;
        Long expectedUnreadCount = messageApi.countUnread(receiverId) + 1;

        Message message = sendDefaultMessage();
        messageApi.delete(senderId, message.getId());

        Assert.assertEquals(MessageState.delete_box, message.getSenderState());
        Assert.assertEquals(MessageState.in_box, message.getReceiverState());

        Long actualDeleteBoxCount =  messageApi.findUserMessage(senderId, MessageState.delete_box, null).getTotalElements();
        Long actualUnreadCount = messageApi.countUnread(receiverId);
        Assert.assertEquals(expectedDeleteBoxCount, actualDeleteBoxCount);
        Assert.assertEquals(expectedUnreadCount, actualUnreadCount);
    }


    @Test
    public void testDeleteReceiverSingleMessage() {
        Long expectedDeleteBoxCount =
                messageApi.findUserMessage(receiverId, MessageState.delete_box, null).getTotalElements() + 1;
        Long expectedUnreadCount = messageApi.countUnread(receiverId);

        Message message = sendDefaultMessage();
        messageApi.delete(receiverId, message.getId());

        Assert.assertEquals(MessageState.out_box, message.getSenderState());
        Assert.assertEquals(MessageState.delete_box, message.getReceiverState());

        Long actualDeleteBoxCount =  messageApi.findUserMessage(receiverId, MessageState.delete_box, null).getTotalElements();
        Long actualUnreadCount = messageApi.countUnread(receiverId);

        Assert.assertEquals(expectedDeleteBoxCount, actualDeleteBoxCount);
        Assert.assertEquals(expectedUnreadCount, actualUnreadCount);
    }


    @Test
    public void testBatchDeleteSenderMessage() {
        Long expectedDeleteBoxCount = 3 + messageApi.findUserMessage(senderId, MessageState.delete_box, null).getTotalElements();
        Long expectedUnreadCount = 3 + messageApi.countUnread(receiverId);

        Message message1 = sendDefaultMessage();
        Message message2 = sendDefaultMessage();
        Message message3 = sendDefaultMessage();
        Long[] messageIds = new Long[] {message1.getId(), message2.getId(), message3.getId()};

        messageApi.delete(senderId, messageIds);

        message1 = messageService.findOne(message1.getId());
        Assert.assertEquals(MessageState.delete_box, message1.getSenderState());
        Assert.assertEquals(MessageState.in_box, message1.getReceiverState());

        Long actualDeleteBoxCount =  messageApi.findUserMessage(senderId, MessageState.delete_box, null).getTotalElements();
        Long actualUnreadCount = messageApi.countUnread(receiverId);
        Assert.assertEquals(expectedDeleteBoxCount, actualDeleteBoxCount);
        Assert.assertEquals(expectedUnreadCount, actualUnreadCount);
    }


    @Test
    public void testBatchDeleteReceiverMessage() {
        Long expectedDeleteBoxCount = 3 + messageApi.findUserMessage(receiverId, MessageState.delete_box, null).getTotalElements();
        Long expectedUnreadCount = messageApi.countUnread(receiverId);

        Message message1 = sendDefaultMessage();
        Message message2 = sendDefaultMessage();
        Message message3 = sendDefaultMessage();
        Long[] messageIds = new Long[] {message1.getId(), message2.getId(), message3.getId()};

        messageApi.delete(receiverId, messageIds);

        message1 = messageService.findOne(message1.getId());
        Assert.assertEquals(MessageState.out_box, message1.getSenderState());
        Assert.assertEquals(MessageState.delete_box, message1.getReceiverState());

        Long actualDeleteBoxCount =  messageApi.findUserMessage(receiverId, MessageState.delete_box, null).getTotalElements();
        Long actualUnreadCount = messageApi.countUnread(receiverId);

        Assert.assertEquals(expectedDeleteBoxCount, actualDeleteBoxCount);
        Assert.assertEquals(expectedUnreadCount, actualUnreadCount);
    }

    @Test
    public void testClearDraftBox() {
        Long expectedDraftBoxCount =
                messageApi.findUserMessage(senderId, MessageState.draft_box, null).getTotalElements() + 1;
        Long expectedTrashBoxCount =
                messageApi.findUserMessage(senderId, MessageState.trash_box, null).getTotalElements() + 1;

        Message message = new Message();
        message.setSenderId(senderId);
        message.setReceiverId(receiverId);
        message.setTitle("ded");
        MessageContent content = new MessageContent();
        content.setContent("abcde");
        message.setContent(content);
        message.setSenderState(MessageState.draft_box);

        messageService.save(message);

        Long actualDraftBoxCount =  messageApi.findUserMessage(senderId, MessageState.draft_box, null).getTotalElements();
        Assert.assertEquals(expectedDraftBoxCount, actualDraftBoxCount);

        messageApi.clearDraftBox(senderId);

        actualDraftBoxCount =  messageApi.findUserMessage(senderId, MessageState.draft_box, null).getTotalElements();
        Assert.assertEquals(Long.valueOf(0L), actualDraftBoxCount);

        Long actualTrashBoxCount =  messageApi.findUserMessage(senderId, MessageState.trash_box, null).getTotalElements();
        Assert.assertEquals(expectedTrashBoxCount, actualTrashBoxCount);

    }

    @Test
    public void testClearInBox() {
        Long expectedInBoxCount =
                messageApi.findUserMessage(receiverId, MessageState.in_box, null).getTotalElements() + 1;
        Long expectedTrashBoxCount =
                messageApi.findUserMessage(receiverId, MessageState.trash_box, null).getTotalElements() + 1;

        Message message = new Message();
        message.setSenderId(senderId);
        message.setReceiverId(receiverId);
        message.setTitle("ded");
        MessageContent content = new MessageContent();
        content.setContent("abcde");
        message.setContent(content);
        messageApi.send(message);

        Long actualInBoxCount =  messageApi.findUserMessage(receiverId, MessageState.in_box, null).getTotalElements();
        Assert.assertEquals(expectedInBoxCount, actualInBoxCount);

        messageApi.clearInBox(receiverId);

        actualInBoxCount =  messageApi.findUserMessage(receiverId, MessageState.in_box, null).getTotalElements();
        Assert.assertEquals(Long.valueOf(0L), actualInBoxCount);

        Long actualTrashBoxCount =  messageApi.findUserMessage(receiverId, MessageState.trash_box, null).getTotalElements();
        Assert.assertEquals(expectedTrashBoxCount, actualTrashBoxCount);

    }


    @Test
    public void testClearOutBox() {
        Long expectedOutBoxCount =
                messageApi.findUserMessage(senderId, MessageState.out_box, null).getTotalElements() + 1;
        Long expectedTrashBoxCount =
                messageApi.findUserMessage(senderId, MessageState.trash_box, null).getTotalElements() + 1;

        Message message = new Message();
        message.setSenderId(senderId);
        message.setReceiverId(receiverId);
        message.setTitle("ded");
        MessageContent content = new MessageContent();
        content.setContent("abcde");
        message.setContent(content);
        messageApi.send(message);

        Long actualOutBoxCount =  messageApi.findUserMessage(senderId, MessageState.out_box, null).getTotalElements();
        Assert.assertEquals(expectedOutBoxCount, actualOutBoxCount);

        messageApi.clearOutBox(senderId);

        actualOutBoxCount =  messageApi.findUserMessage(senderId, MessageState.out_box, null).getTotalElements();
        Assert.assertEquals(Long.valueOf(0L), actualOutBoxCount);

        Long actualTrashBoxCount =  messageApi.findUserMessage(senderId, MessageState.trash_box, null).getTotalElements();
        Assert.assertEquals(expectedTrashBoxCount, actualTrashBoxCount);

    }



    @Test
    public void testClearStoreBox() {
        Long expectedStoreBoxCount =
                messageApi.findUserMessage(senderId, MessageState.store_box, null).getTotalElements() + 1;
        Long expectedTrashBoxCount =
                messageApi.findUserMessage(senderId, MessageState.trash_box, null).getTotalElements() + 1;

        Message message = new Message();
        message.setSenderId(senderId);
        message.setReceiverId(receiverId);
        message.setTitle("ded");
        MessageContent content = new MessageContent();
        content.setContent("abcde");
        message.setContent(content);
        messageApi.send(message);

        messageApi.store(senderId, message.getId());

        Long actualStoreBoxCount =  messageApi.findUserMessage(senderId, MessageState.store_box, null).getTotalElements();
        Assert.assertEquals(expectedStoreBoxCount, actualStoreBoxCount);

        messageApi.clearStoreBox(senderId);

        actualStoreBoxCount =  messageApi.findUserMessage(senderId, MessageState.store_box, null).getTotalElements();
        Assert.assertEquals(Long.valueOf(0L), actualStoreBoxCount);

        Long actualTrashBoxCount =  messageApi.findUserMessage(senderId, MessageState.trash_box, null).getTotalElements();
        Assert.assertEquals(expectedTrashBoxCount, actualTrashBoxCount);

    }


    @Test
    public void testClearTrashBox() throws InterruptedException {
        Long expectedTrashBoxCount =
                messageApi.findUserMessage(senderId, MessageState.trash_box, null).getTotalElements() + 1;
        Long expectedDeleteBoxCount = messageApi.findUserMessage(senderId, MessageState.delete_box, null).getTotalElements() + 1;

        Date lastDate = new Date();
        Thread.sleep(1500L);  //暂停1500毫秒 保证时间差(因为mysql只支持到秒。。)

        Message message = new Message();
        message.setSenderId(senderId);
        message.setReceiverId(receiverId);
        message.setTitle("ded");
        MessageContent content = new MessageContent();
        content.setContent("abcde");
        message.setContent(content);
        messageApi.send(message);

        clear();
        message = messageService.findOne(message.getId());

        Assert.assertTrue(message.getSendDate().getTime() > lastDate.getTime());
        Assert.assertTrue(message.getSendDate().getTime() == message.getSenderStateChangeDate().getTime());
        Assert.assertTrue(message.getSendDate().getTime() == message.getReceiverStateChangeDate().getTime());

        lastDate = message.getSendDate();

        Thread.sleep(1500L);
        messageApi.recycle(senderId, message.getId());
        clear();

        message = messageService.findOne(message.getId());
        Assert.assertTrue(message.getSendDate().getTime() == message.getReceiverStateChangeDate().getTime());
        Assert.assertFalse(message.getSendDate().getTime() == message.getSenderStateChangeDate().getTime());
        Assert.assertTrue(message.getSenderStateChangeDate().getTime() > lastDate.getTime());

        lastDate = message.getSenderStateChangeDate();

        Long actualTrashBoxCount =  messageApi.findUserMessage(senderId, MessageState.trash_box, null).getTotalElements();
        Assert.assertEquals(expectedTrashBoxCount, actualTrashBoxCount);

        Thread.sleep(1500L);
        messageApi.clearTrashBox(senderId);
        clear();

        message = messageService.findOne(message.getId());
        Assert.assertTrue(message.getSendDate().getTime() == message.getReceiverStateChangeDate().getTime());
        Assert.assertFalse(message.getSendDate().getTime() == message.getSenderStateChangeDate().getTime());
        Assert.assertTrue(message.getSenderStateChangeDate().getTime() > lastDate.getTime());

        actualTrashBoxCount =  messageApi.findUserMessage(senderId, MessageState.trash_box, null).getTotalElements();
        Assert.assertEquals(Long.valueOf(0L), actualTrashBoxCount);

        Long actuaDeleteBoxCount =  messageApi.findUserMessage(senderId, MessageState.delete_box, null).getTotalElements();
        Assert.assertEquals(expectedDeleteBoxCount, actuaDeleteBoxCount);

    }



    @Test
    public void testMarkRead() {

        Message message = sendDefaultMessage();

        Assert.assertEquals(Boolean.FALSE, message.getRead());

        messageApi.markRead(message);
        clear();
        message = messageService.findOne(message.getId());

        Assert.assertEquals(Boolean.TRUE, message.getRead());

    }

    @Test
    public void testMarkReplied() {

        Message message = sendDefaultMessage();

        Assert.assertEquals(Boolean.FALSE, message.getReplied());

        messageApi.markReplied(message);
        clear();
        message = messageService.findOne(message.getId());

        Assert.assertEquals(Boolean.TRUE, message.getReplied());

    }




}
