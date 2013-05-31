/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.es.extra.quartz;

import com.sishuok.es.sys.auth.service.AuthRelationClearService;
import com.sishuok.es.sys.group.service.GroupClearRelationService;
import com.sishuok.es.sys.permission.service.RoleClearRelationService;
import com.sishuok.es.sys.user.service.UserClearRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 定时清理对象间的关系
 * <p/>
 * <p>User: Zhang Kaitao
 * <p>Date: 13-5-13 下午5:09
 * <p>Version: 1.0
 */
@Service("relationClearQuartzService")
public class RelationClearQuartzService {


    @Autowired
    private UserClearRelationService userClearRelationService;

    @Autowired
    private GroupClearRelationService groupClearRelationService;

    @Autowired
    private RoleClearRelationService roleClearRelationService;

    @Autowired
    private AuthRelationClearService authRelationClearService;

    public void autoClearRelation() {
        //用户与组织机构/工作职务的关系
        userClearRelationService.clearDeletedUserRelation();

        //分组与组织机构/工作职务的关系
        groupClearRelationService.clearDeletedGroupRelation();


        //角色与资源/权限的关系
        roleClearRelationService.clearDeletedRoleRelation();

        //授权与组织机构、组、角色的关系
        authRelationClearService.clearDeletedAuthRelation();
    }

}
