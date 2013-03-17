/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.es.sys.user.entity;

import com.sishuok.es.common.entity.BaseEntity;

import javax.persistence.*;
import java.util.Date;

/**
 * 在线用户最后一次在线信息
 * <p>User: Zhang Kaitao
 * <p>Date: 13-3-11 下午3:25
 * <p>Version: 1.0
 */
@Entity
@Table(name = "tbl_user_last_online_info")
public class UserLastOnlineInfo extends BaseEntity<Long> {

    /**
     * 在线的用户
     */
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;


    /**
     * http session id
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
     * 最后登录时间
     */
    @Column(name = "last_login_date")
    private Date lastLoginDate;

    /**
     * 最后退出时间
     */
    @Column(name = "last_logout_date")
    private Date lastLogoutDate;

    /**
     * 登录次数
     */
    @Column(name = "login_count")
    private Integer loginCount;

    /**
     * 总的在线时长（秒为单位）
     */
    @Column(name = "total_online_time")
    private Long totalOnlineTime;

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

    public Date getLastLoginDate() {
        return lastLoginDate;
    }

    public void setLastLoginDate(Date lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    public Date getLastLogoutDate() {
        return lastLogoutDate;
    }

    public void setLastLogoutDate(Date lastLogoutDate) {
        this.lastLogoutDate = lastLogoutDate;
    }

    public Integer getLoginCount() {
        return loginCount;
    }

    public void setLoginCount(Integer loginCount) {
        this.loginCount = loginCount;
    }

    public Long getTotalOnlineTime() {
        return totalOnlineTime;
    }

    public void setTotalOnlineTime(Long totalOnlineTime) {
        this.totalOnlineTime = totalOnlineTime;
    }
}
