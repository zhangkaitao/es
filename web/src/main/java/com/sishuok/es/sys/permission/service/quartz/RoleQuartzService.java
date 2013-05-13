/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.es.sys.permission.service.quartz;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.sishuok.es.sys.auth.entity.Auth;
import com.sishuok.es.sys.auth.repository.AuthRepository;
import com.sishuok.es.sys.auth.service.AuthService;
import com.sishuok.es.sys.permission.entity.Role;
import com.sishuok.es.sys.permission.repository.RoleRepository;
import com.sishuok.es.sys.permission.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * 定时任务 定时清理无关联的Role-Auth关系
 * <p>User: Zhang Kaitao
 * <p>Date: 13-5-13 下午5:09
 * <p>Version: 1.0
 */
@Service("roleQuartzService")
public class RoleQuartzService {

    @Autowired
    private RoleService roleService;

    @Autowired
    private AuthService authService;

    /**
     * 自动清除删除的角色对应的关系
     */
    public void autoClearDeletedRoleRelation() {
        Set<Long> roleIds = Sets.newHashSet(Lists.transform(roleService.findAll(), new Function<Role, Long>() {
            @Override
            public Long apply(Role input) {
                return input.getId();
            }
        }));

        final int PAGE_SIZE = 100;
        int pn = 0;
        Pageable pageable = new PageRequest(pn, PAGE_SIZE);

        Page<Auth> authPage = authService.findAll(pageable);

        while(authPage.hasContent()) {
            for(Auth auth : authPage.getContent()) {
                for(Long authRoleId : auth.getRoleIds()) {
                    if(!roleIds.contains(authRoleId)) {
                        authService.delete(auth);
                        break;
                    }
                }
            }

            if(authPage.hasNextPage()) {
                pageable = new PageRequest(++pn, PAGE_SIZE);
                authPage = authService.findAll(pageable);
            }

        }
    }

}
