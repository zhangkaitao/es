/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.es.web.showcase.childchild.service;

import com.sishuok.es.common.service.BaseService;
import com.sishuok.es.web.showcase.parentchild.entity.Child;
import com.sishuok.es.web.showcase.parentchild.repository.ChildRepository;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Arrays;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 13-2-4 下午3:01
 * <p>Version: 1.0
 */
@Service
public class ChildService extends BaseService<Child, Long> {

    private ChildRepository childRepository;

    @Autowired
    public void setChildRepository(ChildRepository childRepository) {
        this.childRepository = childRepository;
        setBaseRepository(childRepository);
    }

    Page<Child> findByParent(Long parentId, Pageable pageable) {
        return childRepository.findByParent(parentId, pageable);
    }

    public void delete(Long[] ids) {
        if(ArrayUtils.isEmpty(ids)) {
            return;
        }
        childRepository.deleteByIds(Arrays.asList(ids));
    }
}
