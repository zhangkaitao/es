/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.es.showcase.tree.service;

import com.google.common.collect.Sets;
import com.sishuok.es.common.inject.annotation.BaseComponent;
import com.sishuok.es.common.plugin.serivce.BaseTreeableService;
import com.sishuok.es.showcase.tree.entity.Tree;
import com.sishuok.es.showcase.tree.repository.TreeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 13-2-4 下午3:01
 * <p>Version: 1.0
 */
@Service
public class TreeService extends BaseTreeableService<Tree, Long> {

    @Autowired
    @BaseComponent
    private TreeRepository treeRepository;


}
