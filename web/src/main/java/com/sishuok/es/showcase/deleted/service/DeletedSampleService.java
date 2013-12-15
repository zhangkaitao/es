/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.es.showcase.deleted.service;

import com.sishuok.es.common.service.BaseService;
import com.sishuok.es.showcase.deleted.entity.DeletedSample;
import com.sishuok.es.showcase.deleted.repository.DeletedSampleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 13-2-4 下午3:01
 * <p>Version: 1.0
 */
@Service
public class DeletedSampleService extends BaseService<DeletedSample, Long> {

    private DeletedSampleRepository getSampleRepository() {
        return (DeletedSampleRepository) baseRepository;
    }

    public DeletedSample findByName(String name) {
        return getSampleRepository().findByName(name);
    }

}
