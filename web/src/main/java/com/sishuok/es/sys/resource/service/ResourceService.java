/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.es.sys.resource.service;

import com.sishuok.es.common.plugin.serivce.BaseTreeableService;
import com.sishuok.es.sys.resource.entity.Resource;
import com.sishuok.es.sys.resource.repository.ResourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 13-2-4 下午3:01
 * <p>Version: 1.0
 */
@Service
public class ResourceService extends BaseTreeableService<Resource, Long> {

    @Autowired
    public void setResourceRepository(ResourceRepository resourceRepository) {
        setBaseRepository(resourceRepository);
    }

}
