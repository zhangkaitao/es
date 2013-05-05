/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.es.conf.icon.service;

import com.sishuok.es.common.inject.annotation.BaseComponent;
import com.sishuok.es.common.service.BaseService;
import com.sishuok.es.conf.icon.entity.Icon;
import com.sishuok.es.conf.icon.repository.IconRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 13-2-4 下午3:01
 * <p>Version: 1.0
 */
@Service
public class IconService extends BaseService<Icon, Long> {

    @Autowired
    @BaseComponent
    private IconRepository iconRepository;

    public Icon findByIdentity(String identity) {
        return iconRepository.findByIdentity(identity);
    }
}
