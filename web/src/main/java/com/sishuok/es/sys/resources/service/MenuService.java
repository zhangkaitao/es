/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.es.sys.resources.service;

import com.sishuok.es.common.plugin.serivce.BaseTreeableService;
import com.sishuok.es.sys.resources.entity.Menu;
import com.sishuok.es.sys.resources.repository.MenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 13-2-4 下午3:01
 * <p>Version: 1.0
 */
@Service
public class MenuService extends BaseTreeableService<Menu, Long> {

    @Autowired
    public void setTreeRepository(MenuRepository treeRepository) {
        setBaseRepository(treeRepository);
    }

}
