/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.es.index.web.utils;

import com.google.common.collect.Lists;
import com.sishuok.es.index.web.entity.Menu;
import com.sishuok.es.sys.resource.entity.Resource;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.List;

/**
 * Resource---->Menu的工具类
 * <p>User: Zhang Kaitao
 * <p>Date: 13-4-9 下午5:32
 * <p>Version: 1.0
 */
public class MenuUtils {

    public static List<Menu> convertToMenus(List<Resource> resources) {

        if(resources.size() == 0) {
            return Collections.EMPTY_LIST;
        }


        Menu root = convertToMenu(resources.remove(resources.size() - 1));

        recursiveMenu(root, resources);
        List<Menu> menus = root.getChildren();
        removeNoLeafMenu(menus);

        return menus;
    }

    private static void removeNoLeafMenu(List<Menu> menus) {
        if(menus.size() == 0) {
            return;
        }
        for(int i = menus.size() - 1; i >= 0; i--) {
            Menu m = menus.get(i);
                removeNoLeafMenu(m.getChildren());
            if(!m.isHasChildren() && StringUtils.isEmpty(m.getUrl())) {
                menus.remove(i);
            }
        }
    }

    private static void recursiveMenu(Menu menu, List<Resource> resources) {
        for(int i = resources.size() - 1; i >= 0; i--) {
            Resource resource = resources.get(i);
            if(resource.getParentId().equals(menu.getId())) {
                menu.getChildren().add(convertToMenu(resource));
                resources.remove(i);
            }
        }

        for(Menu subMenu : menu.getChildren()) {
            recursiveMenu(subMenu, resources);
        }
    }

    private static Menu convertToMenu(Resource resource) {
        Menu menu = new Menu(resource.getId(), resource.getName(), resource.getIcon(), resource.getUrl());
        return menu;
    }
}
