/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.es.sys.resources.web.controller;

import com.sishuok.es.common.entity.search.Searchable;
import com.sishuok.es.common.web.bind.annotation.PageableDefaults;
import com.sishuok.es.common.web.controller.BaseCRUDController;
import com.sishuok.es.common.web.controller.BaseController;
import com.sishuok.es.sys.resources.entity.Menu;
import com.sishuok.es.sys.resources.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 13-1-28 下午4:29
 * <p>Version: 1.0
 */
@Controller("menuController")
@RequestMapping(value = "/admin/sys/resources/menu")
public class MenuController extends BaseCRUDController<Menu, Long> {

    @Autowired
    public MenuController(MenuService treeService) {
        super(treeService);
    }

    @RequestMapping(method = RequestMethod.GET)
    @PageableDefaults(sort = "id=desc")
    @Override
    public String list(Searchable searchable, Model model) {
        model.addAttribute("content", baseService.findAllByNoPage(searchable));
        return getViewPrefix() + "/list";
    }
}
