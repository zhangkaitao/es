/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.es.personal.message.task;

import com.sishuok.es.personal.message.BaseMessageIT;
import com.sishuok.es.personal.message.entity.Message;
import com.sishuok.es.personal.message.entity.MessageState;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 13-5-25 下午11:43
 * <p>Version: 1.0
 */
public class MessageClearTaskIT extends BaseMessageIT {

    @Autowired
    private MessageClearTask messageClearTask;

    private Date today = new Date();
    private Date oneYearAgo = DateUtils.addDays(today, -1 - MessageClearTask.EXPIRE_DAYS_OF_ONE_YEAR);
    private Date oneMonthAgo = DateUtils.addDays(today, -1 - MessageClearTask.EXPIRE_DAYS_OF_ONE_MONTH);


    @Test
    public void testNoClearStoreBox() {

        Long expectedTrashBoxCount =
                messageApi.findUserMessage(senderId, MessageState.trash_box, null).getTotalElements();

        Message message = sendDefaultMessage();
        clear();
        messageApi.store(senderId, message.getId());
        clear();

        message = messageService.findOne(message.getId());
        message.setSenderStateChangeDate(oneYearAgo);
        messageService.update(message);
        clear();

        messageClearTask.autoClearExpiredOrDeletedmMessage();

        Long actualTrashBoxCount = messageApi.findUserMessage(senderId, MessageState.trash_box, null).getTotalElements();
        Assert.assertEquals(expectedTrashBoxCount, actualTrashBoxCount);

    }

    @Test
    public void testNoClearDraftBox() {

        Long expectedTrashBoxCount =
                messageApi.findUserMessage(senderId, MessageState.trash_box, null).getTotalElements();

        Message message = sendDefaultMessage();
        message.setSenderState(MessageState.draft_box);
        message.setSenderStateChangeDate(oneYearAgo);
        messageService.update(message);
        clear();

        messageClearTask.autoClearExpiredOrDeletedmMessage();

        Long actualTrashBoxCount = messageApi.findUserMessage(senderId, MessageState.trash_box, null).getTotalElements();
        Assert.assertEquals(expectedTrashBoxCount, actualTrashBoxCount);

    }


    @Test
    public void testClearOutBox() {

        Long expectedTrashBoxCount =
                messageApi.findUserMessage(senderId, MessageState.trash_box, null).getTotalElements() + 1;

        Message message = sendDefaultMessage();
        clear();

        message = messageService.findOne(message.getId());
        message.setSenderStateChangeDate(oneYearAgo);
        messageService.update(message);
        clear();

        messageClearTask.autoClearExpiredOrDeletedmMessage();

        Long actualTrashBoxCount = messageApi.findUserMessage(senderId, MessageState.trash_box, null).getTotalElements();
        Assert.assertEquals(expectedTrashBoxCount, actualTrashBoxCount);

    }


    @Test
    public void testClearInBox() {

        Long expectedTrashBoxCount =
                messageApi.findUserMessage(receiverId, MessageState.trash_box, null).getTotalElements() + 1;

        Message message = sendDefaultMessage();
        clear();

        message = messageService.findOne(message.getId());
        message.setReceiverStateChangeDate(oneYearAgo);
        messageService.update(message);
        clear();

        messageClearTask.autoClearExpiredOrDeletedmMessage();

        Long actualTrashBoxCount = messageApi.findUserMessage(receiverId, MessageState.trash_box, null).getTotalElements();
        Assert.assertEquals(expectedTrashBoxCount, actualTrashBoxCount);

    }

    @Test
    public void testClearTrashBox() {

        Long expectedDeleteBoxCount =
                messageApi.findUserMessage(receiverId, MessageState.delete_box, null).getTotalElements() + 1;

        Message message = sendDefaultMessage();
        clear();

        message = messageService.findOne(message.getId());
        message.setReceiverState(MessageState.trash_box);
        message.setReceiverStateChangeDate(oneMonthAgo);
        messageService.update(message);
        clear();

        messageClearTask.autoClearExpiredOrDeletedmMessage();
        Long actualDeleteBoxCount = messageApi.findUserMessage(receiverId, MessageState.delete_box, null).getTotalElements();

        Assert.assertEquals(expectedDeleteBoxCount, actualDeleteBoxCount);

    }


    @Test
    public void testClearDeletedMessage() {

        Long expectedDeleteBoxCount =
                messageApi.findUserMessage(receiverId, MessageState.delete_box, null).getTotalElements() + 1;

        Message message = sendDefaultMessage();
        clear();

        message = messageService.findOne(message.getId());
        message.setReceiverState(MessageState.trash_box);
        message.setReceiverStateChangeDate(oneYearAgo);
        messageService.update(message);
        clear();

        messageClearTask.autoClearExpiredOrDeletedmMessage();

        Long actualDeleteBoxCount = messageApi.findUserMessage(receiverId, MessageState.delete_box, null).getTotalElements();
        Assert.assertEquals(expectedDeleteBoxCount, actualDeleteBoxCount);


        expectedDeleteBoxCount = expectedDeleteBoxCount - 1;

        message = messageService.findOne(message.getId());
        message.setSenderState(MessageState.trash_box);
        message.setSenderStateChangeDate(oneMonthAgo);
        messageService.update(message);
        clear();


        messageClearTask.autoClearExpiredOrDeletedmMessage();

        actualDeleteBoxCount = messageApi.findUserMessage(receiverId, MessageState.delete_box, null).getTotalElements();
        Assert.assertEquals(expectedDeleteBoxCount, actualDeleteBoxCount);


    }


}
