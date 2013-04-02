/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.es.showcase.tree.web.controller;

import com.sishuok.es.common.plugin.web.controller.BaseTreeableController;
import com.sishuok.es.showcase.tree.entity.Tree;
import com.sishuok.es.showcase.tree.service.TreeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 13-1-28 下午4:29
 * <p>Version: 1.0
 */
@Controller
@RequestMapping(value = "/showcase/tree")
public class TreeController extends BaseTreeableController<Tree, Long> {

    @Autowired
    public TreeController(TreeService treeService) {
        super(treeService);
    }

}
