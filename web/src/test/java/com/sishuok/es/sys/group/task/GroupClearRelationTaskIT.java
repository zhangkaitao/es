/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.es.sys.group.task;

import com.sishuok.es.common.entity.search.SearchOperator;
import com.sishuok.es.common.entity.search.Searchable;
import com.sishuok.es.sys.group.entity.Group;
import com.sishuok.es.sys.group.entity.GroupRelation;
import com.sishuok.es.sys.group.entity.GroupType;
import com.sishuok.es.sys.group.service.GroupRelationService;
import com.sishuok.es.sys.group.service.GroupService;
import com.sishuok.es.sys.organization.entity.Organization;
import com.sishuok.es.sys.organization.service.OrganizationService;
import com.sishuok.es.test.BaseIT;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 13-5-18 下午5:06
 * <p>Version: 1.0
 */
public class GroupClearRelationTaskIT extends BaseIT {

    @Autowired
    private GroupClearRelationTask groupClearRelationTask;

    @Autowired
    private GroupService groupService;
    @Autowired
    private GroupRelationService groupRelationService;
    @Autowired
    private OrganizationService organizationService;

    @Test
    public void testClearDeletedGroupRelation() {
        Group group = new Group();
        group.setType(GroupType.organization);
        group.setShow(false);
        group.setName("123");
        groupService.save(group);

        Organization organization1 = new Organization();
        organization1.setName("test1");
        Organization organization2 = new Organization();
        organization2.setName("test2");
        organizationService.save(organization1);
        organizationService.save(organization2);

        GroupRelation groupRelation1 = new GroupRelation();
        groupRelation1.setGroupId(group.getId());
        groupRelation1.setOrganizationId(organization1.getId());
        GroupRelation groupRelation2 = new GroupRelation();
        groupRelation2.setGroupId(group.getId());
        groupRelation2.setOrganizationId(organization2.getId());

        groupRelationService.save(groupRelation1);
        groupRelationService.save(groupRelation2);

        Searchable searchable = Searchable.newSearchable();
        searchable.addSearchFilter("groupId", SearchOperator.eq, group.getId());
        Assert.assertEquals(2, groupRelationService.count());

        organizationService.delete(organization1);

        groupClearRelationTask.clearDeletedGroupRelation();
        Assert.assertEquals(1, groupRelationService.count());

        groupService.delete(group);
        groupClearRelationTask.clearDeletedGroupRelation();
        Assert.assertEquals(0, groupRelationService.count());

    }


}
