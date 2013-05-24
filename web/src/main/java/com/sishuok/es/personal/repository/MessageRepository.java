/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.es.personal.repository;

import com.sishuok.es.common.repository.BaseRepository;
import com.sishuok.es.personal.entity.Message;
import com.sishuok.es.personal.entity.MessageState;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 13-5-22 下午2:39
 * <p>Version: 1.0
 */
public interface MessageRepository extends BaseRepository<Message, Long> {

    @Modifying
    @Query("delete from Message where (senderId=?1 and senderState=?2) or (receiverId=?1 and receiverState=?2)")
    void clearBox(Long userId, MessageState state);

    @Query("select count(o) from Message o where (receiverId=?1 and receiverState=?2 and read=false)")
    Long countUnread(Long userId, MessageState receiverState);
}
