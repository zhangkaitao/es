/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.es.web.showcase.upload.service;

import com.sishuok.es.common.service.BaseService;
import com.sishuok.es.web.showcase.upload.entity.Upload;
import com.sishuok.es.web.showcase.upload.repository.UploadRepository;
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
public class UploadService extends BaseService<Upload, Long> {

    private UploadRepository uploadRepository;

    @Autowired
    public void setUploadRepository(UploadRepository uploadRepository) {
        this.uploadRepository = uploadRepository;
        setBaseRepository(uploadRepository);
    }

    public Upload findByName(String name) {
        return uploadRepository.findByName(name);
    }

    public void delete(Long[] ids) {
        if(ArrayUtils.isEmpty(ids)) {
            return;
        }
        uploadRepository.deleteByIds(Arrays.asList(ids));
    }
}
