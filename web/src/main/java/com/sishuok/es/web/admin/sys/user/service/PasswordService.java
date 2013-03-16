/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.es.web.admin.sys.user.service;

import com.sishuok.es.common.utils.security.Md5Utils;
import com.sishuok.es.web.admin.sys.user.entity.SysUser;
import com.sishuok.es.web.admin.sys.user.exception.SysUserPasswordNotMatchException;
import com.sishuok.es.web.admin.sys.user.exception.SysUserPasswordRetryLimitExceedException;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 13-3-12 上午7:18
 * <p>Version: 1.0
 */
@Service
public class PasswordService {

    private static final Logger log = LoggerFactory.getLogger("es-sys-user");

    @Autowired
    private CacheManager ehcacheManager;

    private Cache loginRecordCache;

    @Value(value = "${sysUser.password.maxRetryCount}")
    private int maxRetryCount = 10;

    public void setMaxRetryCount(int maxRetryCount) {
        this.maxRetryCount = maxRetryCount;
    }

    @PostConstruct
    public void init() {
        loginRecordCache = ehcacheManager.getCache("loginRecordCache");
    }

    public void validate(SysUser sysUser, String password) {
        String username = sysUser.getUsername();

        int retryCount = 0;

        Element cacheElement = loginRecordCache.get(username);
        if(cacheElement != null) {
            retryCount = (Integer) cacheElement.getValue();
            if(retryCount >= maxRetryCount) {
                throw new SysUserPasswordRetryLimitExceedException(maxRetryCount);
            }
        }

        if(!sysUser.getPassword().equals(encryptPassword(sysUser.getUsername(), password, sysUser.getSalt()))) {
            loginRecordCache.put(new Element(username, ++retryCount));
            throw new SysUserPasswordNotMatchException();
        } else {
            loginRecordCache.remove(username);
        }
    }


    public String encryptPassword(String username, String password, String salt) {
        return Md5Utils.hash(username + password + salt);
    }






}
