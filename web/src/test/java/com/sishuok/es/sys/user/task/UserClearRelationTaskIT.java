/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.es.sys.user.task;

import com.sishuok.es.sys.organization.entity.Job;
import com.sishuok.es.sys.organization.entity.Organization;
import com.sishuok.es.sys.organization.service.JobService;
import com.sishuok.es.sys.organization.service.OrganizationService;
import com.sishuok.es.sys.user.entity.User;
import com.sishuok.es.sys.user.entity.UserOrganizationJob;
import com.sishuok.es.sys.user.service.BaseUserIT;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 13-5-18 下午2:18
 * <p>Version: 1.0
 */
public class UserClearRelationTaskIT extends BaseUserIT {


    @Autowired
    private OrganizationService organizationService;
    @Autowired
    private JobService jobService;

    @Autowired
    private UserClearRelationTask userClearRelationTask;

    @Test
    public void testClearRelation() {
        User user = createDefaultUser();

        Organization organization1 = new Organization();
        organization1.setName("test1");
        Organization organization2 = new Organization();
        organization2.setName("test2");
        organizationService.save(organization1);
        organizationService.save(organization2);

        Job job1 = new Job();
        job1.setName("test1");
        Job job2 = new Job();
        job2.setName("test2");
        jobService.save(job1);
        jobService.save(job2);

        user.addOrganizationJob(new UserOrganizationJob(organization1.getId(), null));
        user.addOrganizationJob(new UserOrganizationJob(organization2.getId(), job1.getId()));
        user.addOrganizationJob(new UserOrganizationJob(organization2.getId(), job2.getId()));
        userService.update(user);

        //清除缓存
        clear();

        organizationService.delete(organization1);
        jobService.delete(job1);

        userClearRelationTask.clearDeletedUserRelation();

        clear();

        user = userService.findOne(user.getId());

        Assert.assertEquals(2, user.getOrganizationJobs().size());

        Assert.assertEquals(null, user.getOrganizationJobs().get(1).getJobId());


    }


}
