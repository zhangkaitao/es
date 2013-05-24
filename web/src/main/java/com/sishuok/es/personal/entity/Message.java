/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.es.personal.entity;

import com.sishuok.es.common.entity.BaseEntity;
import com.sishuok.es.common.repository.support.annotation.EnableQueryCache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.Valid;
import java.util.Date;

/**
 * 系统消息
 * 1、收件箱和发件箱内的消息默认365天后被放入垃圾箱
 * 2、垃圾箱内的消息30天后自动物理删除
 * 3、垃圾箱内的消息只有只有当收件人和发件人 把消息都从垃圾箱中删除后才能物理删除
 * 4、收藏箱的不能删除
 *
 * <p>User: Zhang Kaitao
 * <p>Date: 13-5-22 下午1:51
 * <p>Version: 1.0
 */
@Entity
@Table(name = "personal_message")
@EnableQueryCache
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Message extends BaseEntity<Long> {

    /**
     * 消息发送者Id
     */
    @Column(name = "sender_id")
    private Long senderId;

    /**
     *  消息接收者id
     */
    @Column(name = "receiver_id")
    private Long receiverId;

    /**
     * 消息发送时间
     */
    @Column(name = "send_date")
    private Date sendDate;

    /**
     * 标题
     */
    @Length(min = 5, max = 200, message = "{message.title.ilegal.length}")
    @Column(name = "title")
    private String title;

    @Valid
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy="message")
    private MessageContent content;

    /**
     * 发件人状态
     */
    @Column(name = "sender_state")
    @Enumerated(EnumType.STRING)
    private MessageState senderState = MessageState.out_box;
    /**
     * 发件人状态改变时间 默认发送时间
     */
    @Column(name = "sender_state_change_date")
    private Date senderStateChangeDate;

    //收件人状态
    @Column(name = "receiver_state")
    @Enumerated(EnumType.STRING)
    private MessageState receiverState = MessageState.in_box;
    /**
     * 收件人状态改变时间 默认发送时间
     */
    @Column(name = "receiver_state_change_date")
    private Date receiverStateChangeDate;

    /**
     * 消息类型,默认普通消息
     */
    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private MessageType type = MessageType.user_message;

    /**
     * 是否已读
     */
    private Boolean read = Boolean.FALSE;
    /**
     * 是否已回复
     */
    private Boolean replied = Boolean.FALSE;

    /**
     * 父消息编号列表 如1/2/3/4
     */
    @Column(name = "parent_ids")
    private Long parentIds;

    public Long getSenderId() {
        return senderId;
    }

    public void setSenderId(Long senderId) {
        this.senderId = senderId;
    }

    public Long getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(Long receiverId) {
        this.receiverId = receiverId;
    }

    public Date getSendDate() {
        return sendDate;
    }

    public void setSendDate(Date sendDate) {
        this.sendDate = sendDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public MessageContent getContent() {
        return content;
    }

    public void setContent(MessageContent content) {
        this.content = content;
    }

    public MessageState getSenderState() {
        return senderState;
    }

    public void setSenderState(MessageState senderState) {
        this.senderState = senderState;
    }

    public Date getSenderStateChangeDate() {
        return senderStateChangeDate;
    }

    public void setSenderStateChangeDate(Date senderStateChangeDate) {
        this.senderStateChangeDate = senderStateChangeDate;
    }

    public MessageState getReceiverState() {
        return receiverState;
    }

    public void setReceiverState(MessageState receiverState) {
        this.receiverState = receiverState;
    }

    public Date getReceiverStateChangeDate() {
        return receiverStateChangeDate;
    }

    public void setReceiverStateChangeDate(Date receiverStateChangeDate) {
        this.receiverStateChangeDate = receiverStateChangeDate;
    }

    public MessageType getType() {
        return type;
    }

    public void setType(MessageType type) {
        this.type = type;
    }


    public Boolean getRead() {
        return read;
    }

    public void setRead(Boolean read) {
        this.read = read;
    }

    public Boolean getReplied() {
        return replied;
    }

    public void setReplied(Boolean replied) {
        this.replied = replied;
    }

    public Long getParentIds() {
        return parentIds;
    }

    public void setParentIds(Long parentIds) {
        this.parentIds = parentIds;
    }

    public void markRead() {
        if(Boolean.TRUE.equals(this.read)) {
            return;
        }
        this.read = Boolean.TRUE;
    }

    public void markReplied() {
        if(Boolean.TRUE.equals(this.replied)) {
            return;
        }
        this.replied = Boolean.TRUE;
    }

    public void changeSenderState(MessageState state) {
        setSenderState(state);
        setSenderStateChangeDate(new Date());
    }

    public void changeReceiverState(MessageState state) {
        setReceiverState(state);
        setReceiverStateChangeDate(new Date());
    }
}
