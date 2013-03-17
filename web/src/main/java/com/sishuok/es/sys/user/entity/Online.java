/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.es.sys.user.entity;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import java.io.Serializable;
import java.util.Date;

/**
 * 当前在线信息（当前才能内存表存放在线状态）
 * <p>User: Zhang Kaitao
 * <p>Date: 13-3-16 上午10:43
 * <p>Version: 1.0
 */
public class Online implements Serializable {
    public static enum OnlineStatus {
        on_line("在线"), off_line("离线"), hidden("隐身"), force_logout("强制退出");
        private final String info;
        private OnlineStatus(String info) {
            this.info = info;
        }
        public String getInfo() {
            return info;
        }
    }

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    /**
     * 登录到的系统（systemIp）的http session id
     */
    @Column(name = "jsession_id")
    private String jsessionId;

    /**
     * user session id (与http session无关的 存储到cookie中)
     */
    private String uid;

    /**
     * 系统ip
     */
    @Column(name = "system_ip")
    private String systemIp;

    /**
     * 用户ip
     */
    @Column(name = "user_ip")
    private String userIp;

    /**
     * 用户客户端类型
     */
    @Column(name = "user_agent")
    private String userAgent;


    /**
     * 登录时间
     */
    private Date loginDate;

    /**
     * 最后更新时间
     */
    private Date lastUpdateDate;

    /**
     * 在线状态
     */
    private OnlineStatus status;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getJsessionId() {
        return jsessionId;
    }

    public void setJsessionId(String jsessionId) {
        this.jsessionId = jsessionId;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getSystemIp() {
        return systemIp;
    }

    public void setSystemIp(String systemIp) {
        this.systemIp = systemIp;
    }

    public String getUserIp() {
        return userIp;
    }

    public void setUserIp(String userIp) {
        this.userIp = userIp;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public Date getLoginDate() {
        return loginDate;
    }

    public void setLoginDate(Date loginDate) {
        this.loginDate = loginDate;
    }

    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public OnlineStatus getStatus() {
        return status;
    }

    public void setStatus(OnlineStatus status) {
        this.status = status;
    }
}
