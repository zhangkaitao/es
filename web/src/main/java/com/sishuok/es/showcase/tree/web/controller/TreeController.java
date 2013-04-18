/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.es.showcase.tree.web.controller;

import com.sishuok.es.common.Constants;
import com.sishuok.es.common.plugin.web.controller.BaseTreeableController;
import com.sishuok.es.showcase.tree.entity.Tree;
import com.sishuok.es.showcase.tree.service.TreeService;
import com.sishuok.es.sys.permission.entity.Permission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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

    private TreeService treeService;
    @Autowired
    public TreeController(TreeService treeService) {
        super(treeService);
        this.treeService = treeService;
    }


    @RequestMapping(value = "/changeStatus/{newStatus}")
    public String changeStatus(
            HttpServletRequest request,
            @PathVariable("newStatus") Boolean newStatus,
            @RequestParam("ids") Long[] ids
    ) {


        for(Long id : ids) {
            Tree tree = treeService.findOne(id);
            tree.setShow(newStatus);
            treeService.update(tree);
        }


        return "redirect:" + request.getAttribute(Constants.BACK_URL);
    }



}
