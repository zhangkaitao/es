/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.es.web.admin.sys.user.entity;

import com.sishuok.es.common.entity.BaseEntity;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.Date;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 13-3-11 下午3:23
 * <p>Version: 1.0
 */
@Entity
@Table(name = "tbl_sys_user_status_history")
public class SysUserStatusHistory extends BaseEntity<Long> {

    /**
     * 锁定的用户
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    private SysUser user;

    /**
     * 备注信息
     */
    private String reason;

    /**
     * 操作的状态
     */
    @Enumerated(EnumType.STRING)
    private SysUserStatus status;

    /**
     * 操作的管理员
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    @JoinColumn(name = "op_user_id")
    private SysUser opUser;

    /**
     * 操作时间
     */
    @Column(name = "op_date")
    private Date opDate;


    public SysUser getUser() {
        return user;
    }

    public void setUser(SysUser user) {
        this.user = user;
    }


    public SysUserStatus getStatus() {
        return status;
    }

    public void setStatus(SysUserStatus status) {
        this.status = status;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public SysUser getOpUser() {
        return opUser;
    }

    public void setOpUser(SysUser opUser) {
        this.opUser = opUser;
    }

    public Date getOpDate() {
        return opDate;
    }

    public void setOpDate(Date opDate) {
        this.opDate = opDate;
    }
}
