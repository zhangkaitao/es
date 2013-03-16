/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.es.web.admin.sys.user.service;

import com.sishuok.es.common.service.BaseService;
import com.sishuok.es.web.admin.sys.user.entity.SysUser;
import com.sishuok.es.web.admin.sys.user.entity.SysUserOnlineInfo;
import com.sishuok.es.web.admin.sys.user.repository.SysUserOnlineInfoRepository;
import com.sishuok.es.web.admin.sys.user.repository.SysUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 13-2-4 下午3:01
 * <p>Version: 1.0
 */
@Service
public class SysUserOnlineService extends BaseService<SysUserOnlineInfo, Long> {

    private SysUserOnlineInfoRepository sysUserOnlineInfoRepository;

    @Autowired
    public void setSysUserOnlineInfoRepository(SysUserOnlineInfoRepository sysUserOnlineInfoRepository) {
        setBaseRepository(sysUserOnlineInfoRepository);
        this.sysUserOnlineInfoRepository = sysUserOnlineInfoRepository;
    }

}
