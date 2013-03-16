/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.es.web.admin.sys.user.service;

import com.sishuok.es.common.service.BaseService;
import com.sishuok.es.common.utils.security.Md5Utils;
import com.sishuok.es.web.admin.sys.user.entity.SysUser;
import com.sishuok.es.web.admin.sys.user.entity.SysUserStatus;
import com.sishuok.es.web.admin.sys.user.exception.SysUserBlockedException;
import com.sishuok.es.web.admin.sys.user.exception.SysUserNotExistsException;
import com.sishuok.es.web.admin.sys.user.exception.SysUserPasswordNotMatchException;
import com.sishuok.es.web.admin.sys.user.repository.SysUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 13-2-4 下午3:01
 * <p>Version: 1.0
 */
@Service
public class SysUserService extends BaseService<SysUser, Long> {

    private static final Logger log = LoggerFactory.getLogger("es-sys-user");

    private SysUserRepository sysUserRepository;
    private SysUserStatusHistoryService sysUserStatusHistoryService;
    private PasswordService passwordService;

    @Autowired
    public void setSysUserRepository(SysUserRepository sysUserRepository) {
        setBaseRepository(sysUserRepository);
        this.sysUserRepository = sysUserRepository;
    }
    @Autowired
    public void setSysUserStatusHistoryService(SysUserStatusHistoryService sysUserStatusHistoryService) {
        this.sysUserStatusHistoryService = sysUserStatusHistoryService;
    }
    @Autowired
    public void setPasswordService(PasswordService passwordService) {
        this.passwordService = passwordService;
    }

    @Override
    public SysUser save(SysUser sysUser) {
        if(sysUser.getCreateDate() == null) {
            sysUser.setCreateDate(new Date());
        }
        sysUser.randomSalt();
        sysUser.setPassword(passwordService.encryptPassword(sysUser.getUsername(), sysUser.getPassword(), sysUser.getSalt()));

        return super.save(sysUser);
    }




    public SysUser findByUsername(String username) {
        return sysUserRepository.findByUsername(username);
    }

    public void changePassword(SysUser sysUser, String newPassword) {
        sysUser.setPassword(passwordService.encryptPassword(sysUser.getUsername(), newPassword, sysUser.getSalt()));
        update(sysUser);
    }

    public void changeStatus(SysUser sysUser, SysUserStatus newStatus, String reason) {
        sysUser.setStatus(newStatus);
        update(sysUser);
        //TODO 修改为当前登录人
        SysUser opUser = sysUser;
        sysUserStatusHistoryService.log(opUser, sysUser, newStatus, reason);
    }

    public SysUser login(String username, String password) {

        SysUser sysUser = findByUsername(username);

        if(sysUser == null || Boolean.TRUE.equals(sysUser.getDeleted())) {
            throw new SysUserNotExistsException();
        }

        passwordService.validate(sysUser, password);

        if(sysUser.getStatus() == SysUserStatus.blocked) {
            throw new SysUserBlockedException(sysUserStatusHistoryService.findLastHistory(sysUser).getReason());
        }
        return sysUser;
    }
}
