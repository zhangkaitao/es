/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.es.sys.organization.service;

import com.sishuok.es.common.plugin.serivce.BaseTreeableService;
import com.sishuok.es.sys.organization.entity.Job;
import com.sishuok.es.sys.organization.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 13-2-4 下午3:01
 * <p>Version: 1.0
 */
@Service
public class JobService extends BaseTreeableService<Job, Long> {

    @Autowired
    public void setTreeRepository(JobRepository jobRepository) {
        setBaseRepository(jobRepository);
    }

}
