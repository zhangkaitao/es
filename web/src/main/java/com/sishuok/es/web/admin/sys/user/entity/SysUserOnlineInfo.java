/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.es.web.admin.sys.user.entity;

import com.sishuok.es.common.entity.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.Date;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 13-3-11 下午3:25
 * <p>Version: 1.0
 */
@Entity
@Table(name = "tbl_sys_user_online_info")
public class SysUserOnlineInfo extends BaseEntity<Long> {

    /**
     * 在线的用户
     */
    @OneToOne
    private SysUser user;


    /**
     * http session id
     */
    private String jsessionId;

    /**
     * user session id (与http session无关的 存储到cookie中)
     */
    private String sid;

    /**
     * 系统ip
     */
    private String systemIp;

    /**
     * 用户ip
     */
    private String userIp;

    /**
     * 最后登录时间
     */
    private Date lastLoginDate;

    /**
     * 最后退出时间
     */
    private Date lastLogoutDate;

    /**
     * 登录次数
     */
    private Integer loginCount;

    /**
     * 总的在线时长（秒为单位）
     */
    private Long totalOnlineTime;

}
