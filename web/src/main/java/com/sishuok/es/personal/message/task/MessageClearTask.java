/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.es.personal.message.task;

import com.google.common.collect.Lists;
import com.sishuok.es.personal.message.entity.MessageState;
import com.sishuok.es.personal.message.service.MessageService;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 清理 过期的/删除的消息
 * <p>User: Zhang Kaitao
 * <p>Date: 13-5-25 上午11:23
 * <p>Version: 1.0
 */
@Service
public class MessageClearTask {

    public static final int EXPIRE_DAYS_OF_ONE_YEAR = 366;
    public static final int EXPIRE_DAYS_OF_ONE_MONTH = 31;

    @Autowired
    private MessageService messageService;

    public void autoClearExpiredOrDeletedmMessage() {
        MessageClearTask messageClearTask = (MessageClearTask) AopContext.currentProxy();
        //1、收件箱、发件箱状态修改为垃圾箱状态
        messageClearTask.doClearInOrOutBox();
        //2、垃圾箱状态改为已删除状态
        messageClearTask.doClearTrashBox();
        //3、物理删除那些已删除的（即收件人和发件人 同时都删除了的）
        messageClearTask.doClearDeletedMessage();
    }


    public void doClearDeletedMessage() {
        messageService.clearDeletedMessage(MessageState.delete_box);
    }


    public void doClearInOrOutBox() {
        messageService.changeState(
                Lists.newArrayList(MessageState.in_box, MessageState.out_box),
                MessageState.trash_box,
                EXPIRE_DAYS_OF_ONE_YEAR
        );

    }

    public void doClearTrashBox() {
        messageService.changeState(
                Lists.newArrayList(MessageState.trash_box),
                MessageState.delete_box,
                EXPIRE_DAYS_OF_ONE_MONTH
        );
    }

}
