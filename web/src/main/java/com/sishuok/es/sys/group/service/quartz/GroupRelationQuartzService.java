/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.es.sys.group.service.quartz;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.sishuok.es.sys.group.entity.Group;
import com.sishuok.es.sys.group.repository.GroupRelationRepository;
import com.sishuok.es.sys.group.service.GroupRelationService;
import com.sishuok.es.sys.group.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * 定时任务 定时清理无关联的关系
 * 1、Group-GroupRelation
 * 2、GroupRelation-User
 * 3、GroupRelation-Organization
 * 4、GroupRelation-Job
 * <p>User: Zhang Kaitao
 * <p>Date: 13-5-13 下午5:35
 * <p>Version: 1.0
 */
@DependsOn("groupRelationRepository")
@Service("groupQuartzService")
public class GroupRelationQuartzService {

    @Autowired
    private GroupRelationRepository groupRelationRepository;

    /**
     * 自动清除删除的分组对应的关系
     */
    public void autoClearDeletedGroupRelation() {
        groupRelationRepository.clearDeletedGroupRelation();
    }

}
