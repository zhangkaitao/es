/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.es.extra.task;

import com.sishuok.es.sys.auth.task.AuthRelationClearTask;
import com.sishuok.es.sys.group.task.GroupClearRelationTask;
import com.sishuok.es.sys.permission.task.RoleClearRelationTask;
import com.sishuok.es.sys.user.task.UserClearRelationTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 定时清理对象间的关系
 * <p/>
 * <p>User: Zhang Kaitao
 * <p>Date: 13-5-13 下午5:09
 * <p>Version: 1.0
 */
@Service("relationClearTask")
public class RelationClearTask {


    @Autowired
    private UserClearRelationTask userClearRelationTask;

    @Autowired
    private GroupClearRelationTask groupClearRelationTask;

    @Autowired
    private RoleClearRelationTask roleClearRelationTask;

    @Autowired
    private AuthRelationClearTask authRelationClearTask;

    public void autoClearRelation() {

        //用户与组织机构/工作职务的关系
        userClearRelationTask.clearDeletedUserRelation();

        //分组与组织机构/工作职务的关系
        groupClearRelationTask.clearDeletedGroupRelation();


        //角色与资源/权限的关系
        roleClearRelationTask.clearDeletedRoleRelation();

        //授权与组织机构、组、角色的关系
        authRelationClearTask.clearDeletedAuthRelation();
    }

}
