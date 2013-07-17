/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.es.personal.message.entity;

import com.sishuok.es.common.entity.BaseEntity;
import org.hibernate.annotations.Proxy;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.Valid;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * 系统消息
 * 1、收件箱和发件箱内的消息默认365天后被放入垃圾箱
 * 2、垃圾箱内的消息30天后自动物理删除
 * 3、垃圾箱内的消息只有只有当收件人和发件人 把消息都从垃圾箱中删除后才能物理删除
 * 4、收藏箱的不能删除
 * <p/>
 * 如果type==system_message_all表示是发给所有人的消息 策略如下：
 * 1、首先在展示时（第一页），会会自动查所有的system_message_all
 * 2、如果用户阅读了，直接复制一份 放入它的收件箱 状态改为system_message
 * <p/>
 * 如果消息是草稿 那么收件人状态是null
 * <p/>
 * <p>User: Zhang Kaitao
 * <p>Date: 13-5-22 下午1:51
 * <p>Version: 1.0
 */
@Entity
@Table(name = "personal_message")
@Proxy(lazy = true, proxyClass = Message.class)
public class Message extends BaseEntity<Long> {

    /**
     * 消息发送者Id
     */
    @Column(name = "sender_id")
    private Long senderId;

    /**
     * 消息接收者id
     */
    @Column(name = "receiver_id")
    private Long receiverId;

    /**
     * 消息发送时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "send_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date sendDate;

    /**
     * 标题
     */
    @Length(min = 5, max = 200, message = "{length.not.valid}")
    @Column(name = "title")
    private String title;

    /**
     * OneToOne如果不生成字节码代理不能代理 所以改成OneToMany
     */
    @Valid
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "message", orphanRemoval = true)
    private Set<MessageContent> contents;

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
    @Temporal(TemporalType.TIMESTAMP)
    private Date senderStateChangeDate;

    //收件人状态
    @Column(name = "receiver_state")
    @Enumerated(EnumType.STRING)
    private MessageState receiverState = MessageState.in_box;
    /**
     * 收件人状态改变时间 默认发送时间
     */
    @Column(name = "receiver_state_change_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date receiverStateChangeDate;

    /**
     * 消息类型,默认普通消息
     */
    @Enumerated(EnumType.STRING)
    private MessageType type = MessageType.user_message;

    /**
     * 是否已读
     */
    @Column(name = "is_read")
    private Boolean read = Boolean.FALSE;
    /**
     * 是否已回复
     */
    @Column(name = "is_replied")
    private Boolean replied = Boolean.FALSE;

    /**
     * 父编号
     */
    @Column(name = "parent_id")
    private Long parentId;

    /**
     * 父消息编号列表 如1/2/3/4
     */
    @Column(name = "parent_ids")
    private String parentIds;



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
        if(contents != null && contents.size() > 0) {
            return contents.iterator().next();
        }
        return null;
    }

    public void setContent(MessageContent content) {
        if(contents == null) {
            contents = new HashSet<MessageContent>();
        }
        contents.add(content);
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

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getParentIds() {
        return parentIds;
    }

    public void setParentIds(String parentIds) {
        this.parentIds = parentIds;
    }

    public String makeSelfAsParentIds() {
        return (getParentIds() != null ? getParentIds() : "") + getId() + "/";
    }

}
