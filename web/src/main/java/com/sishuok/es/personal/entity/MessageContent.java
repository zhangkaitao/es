/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.es.personal.entity;

import com.sishuok.es.common.entity.BaseEntity;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * 消息内容
 * <p>User: Zhang Kaitao
 * <p>Date: 13-5-22 下午1:55
 * <p>Version: 1.0
 */
@Entity
@Table(name = "personal_message_content")
public class MessageContent extends BaseEntity<Long> {


    @OneToOne(fetch = FetchType.LAZY)
    @Fetch(FetchMode.SELECT)
    private Message message;

    /**
     * 消息内容
     */
    @Length(min = 5, max = 50000, message = "{message.content.length.not.valid}")
    private String content;


    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
