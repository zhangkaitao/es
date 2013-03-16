/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.es.web.admin.sys.user.entity;

import com.sishuok.es.common.entity.BaseEntity;
import com.sishuok.es.common.plugin.entity.LogicDeleteable;
import org.apache.commons.lang3.RandomStringUtils;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.text.DateFormat;
import java.util.Date;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 13-2-4 上午9:38
 * <p>Version: 1.0
 */
@Entity
@Table(name = "tbl_sys_user")
public class SysUser extends BaseEntity<Long> implements LogicDeleteable {

    @NotNull(message = "{sysUser.username.not.null}")
    @Pattern(regexp = "[\\u4E00-\\u9FA5\\uf900-\\ufa2d\\w]{5,20}", message = "{sysUser.username.not.valid}")
    private String username;

    /**
     * 使用md5(username + original password + salt)加密存储
     */
    @Length(min = 5, max = 50, message = "{sysUser.password.not.valid}")
    private String password;

    /**
     * 加密密码时使用的种子
     */
    private String salt;

    /**
     * 创建时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "create_date")
    private Date createDate;

    /**
     * 系统用户的状态
     */
    @Enumerated(EnumType.STRING)
    private SysUserStatus status = SysUserStatus.normal;

    /**
     * 逻辑删除flag
     */
    private Boolean deleted = Boolean.FALSE;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    /**
     * 生成新的种子
     */
    public void randomSalt() {
        setSalt(RandomStringUtils.randomAlphanumeric(6));
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public SysUserStatus getStatus() {
        return status;
    }

    public void setStatus(SysUserStatus status) {
        this.status = status;
    }

    @Override
    public Boolean getDeleted() {
        return deleted;
    }

    @Override
    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    @Override
    public void markDeleted() {
        this.deleted = Boolean.TRUE;
    }
}
