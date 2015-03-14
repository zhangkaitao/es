/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.es.sys.resource.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sishuok.es.common.Constants;
import com.sishuok.es.common.plugin.web.controller.BaseMovableController;
import com.sishuok.es.sys.resource.entity.Resource;
import com.sishuok.es.sys.resource.entity.ResourceShortcut;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 13-1-28 下午4:29
 * <p>Version: 1.0
 */
@Controller
@RequestMapping(value = "/admin/sys/resource_shortcut")
public class ResourceShortcutController extends BaseMovableController<ResourceShortcut, Long> {

	/**
	 * 添加快捷菜单操作
	 * @param request
	 * @param newStatus
	 * @param ids
	 * @param redirectAttributes
	 * @return
	 */
    @RequestMapping(value = "/add/{r_id}")
    public String add_resource_shortcut(
            HttpServletRequest request,
            @PathVariable("r_id") Integer r_id,
            RedirectAttributes redirectAttributes
    		) {


        redirectAttributes.addFlashAttribute(Constants.MESSAGE, "操作成功！");

        return "redirect:" + request.getAttribute(Constants.BACK_URL);
    }
}
