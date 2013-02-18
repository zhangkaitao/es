/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.es.web.showcase.sample.service;

import com.sishuok.es.common.service.BaseService;
import com.sishuok.es.web.showcase.sample.entity.Sample;
import com.sishuok.es.web.showcase.sample.repository.SampleRepository;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 13-2-4 下午3:01
 * <p>Version: 1.0
 */
@Service
public class SampleService extends BaseService<Sample, Long> {

    private SampleRepository sampleRepository;

    @Autowired
    public void setSampleRepository(SampleRepository sampleRepository) {
        this.sampleRepository = sampleRepository;
        setBaseRepository(sampleRepository);
    }

    public Sample findByName(String name) {
        return sampleRepository.findByName(name);
    }

    public void delete(Long[] ids) {
        if(ArrayUtils.isEmpty(ids)) {
            return;
        }
        sampleRepository.deleteByIds(Arrays.asList(ids));
    }
}
