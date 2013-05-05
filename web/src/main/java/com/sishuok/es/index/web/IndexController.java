/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.es.index.web;

import com.google.common.collect.Lists;
import com.sishuok.es.common.entity.search.SearchOperator;
import com.sishuok.es.common.entity.search.Searchable;
import com.sishuok.es.index.web.entity.Menu;
import com.sishuok.es.index.web.utils.MenuUtils;
import com.sishuok.es.sys.resource.entity.Resource;
import com.sishuok.es.sys.resource.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 13-1-18 下午10:15
 * <p>Version: 1.0
 */
@Controller("adminIndexController")
@RequestMapping("/admin")
public class IndexController {

    @Autowired
    private ResourceService resourceService;

    @RequestMapping(value = {"/{index:index;?.*}"})
    public String index(Model model) {

        Searchable searchable =
                Searchable.newSearchable()
                        .addSearchFilter("show", SearchOperator.eq, true)
                        .addSort(new Sort(Sort.Direction.DESC, "parentId", "weight"));

        List<Resource> resources = Lists.newArrayList(resourceService.findAllWithSort(searchable));
        List<Menu> menus = MenuUtils.convertToMenus(resources);
        model.addAttribute("menus", menus);

        return "admin/index/index";
    }



    @RequestMapping(value = "/welcome")
    public String welcome() {
        return "admin/index/welcome";
    }


}
