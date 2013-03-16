/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.es.web.admin.sys.user.web.controller;

import com.sishuok.es.common.Constants;
import com.sishuok.es.common.entity.search.Searchable;
import com.sishuok.es.common.web.bind.annotation.PageableDefaults;
import com.sishuok.es.common.web.bind.annotation.SearchableDefaults;
import com.sishuok.es.common.web.controller.BaseCRUDController;
import com.sishuok.es.common.web.validate.ValidateResponse;
import com.sishuok.es.web.admin.sys.user.entity.SysUser;
import com.sishuok.es.web.admin.sys.user.entity.SysUserStatus;
import com.sishuok.es.web.admin.sys.user.service.SysUserService;
import com.sishuok.es.web.showcase.sample.entity.Sample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 13-1-28 下午4:29
 * <p>Version: 1.0
 */
@Controller
@RequestMapping(value = "/admin/sys/user/sysUser")
public class SysUserController extends BaseCRUDController<SysUser, Long> {

    private SysUserService sysUserService;

    @Autowired
    public SysUserController(SysUserService sysUserService) {
        super(sysUserService);
        this.sysUserService = sysUserService;
    }


    @Override
    protected void setCommonData(Model model) {
        model.addAttribute("statusList", SysUserStatus.values());
    }


    @SearchableDefaults(value = "deleted_eq=0")
    @Override
    public String list(Searchable searchable, Model model) {
        setCommonData(model);
        return super.list(searchable, model);    //To change body of overridden methods use File | Settings | File Templates.
    }

    @RequestMapping(value = "changePassword/{id}")
    public String changePassword(
            HttpServletRequest request,
            @PathVariable("id") SysUser sysUser, @RequestParam("newPassword") String newPassword,
            RedirectAttributes redirectAttributes) {
        sysUserService.changePassword(sysUser, newPassword);

        redirectAttributes.addFlashAttribute(Constants.MESSAGE, "改密成功！");

        return "redirect:" + request.getAttribute(Constants.BACK_URL);
    }

    @RequestMapping(value = "changeStatus/{id}/{newStatus}")
    public String changeStatus(
            HttpServletRequest request,
            @PathVariable("id") SysUser sysUser,
            @PathVariable("newStatus") SysUserStatus newStatus,
            @RequestParam("reason") String reason,
            RedirectAttributes redirectAttributes) {

        sysUserService.changeStatus(sysUser, newStatus, reason);

        if(newStatus == SysUserStatus.normal) {
            redirectAttributes.addFlashAttribute(Constants.MESSAGE, "解锁成功！");
        } else if(newStatus == SysUserStatus.blocked) {
            redirectAttributes.addFlashAttribute(Constants.MESSAGE, "锁定成功！");
        }

        return "redirect:" + request.getAttribute(Constants.BACK_URL);
    }

    @RequestMapping(value = "recycle/{id}")
    public String recycle(HttpServletRequest request, @PathVariable("id") SysUser sysUser, RedirectAttributes redirectAttributes) {
        sysUser.setDeleted(Boolean.FALSE);
        sysUserService.update(sysUser);
        redirectAttributes.addFlashAttribute(Constants.MESSAGE, "还原成功！");
        return "redirect:" + request.getAttribute(Constants.BACK_URL);
    }


    /**
     * 验证返回格式
     * 单个：[fieldId, 1|0, msg]
     * 多个：[[fieldId, 1|0, msg],[fieldId, 1|0, msg]]
     *
     * @param fieldId
     * @param fieldValue
     * @return
     */
    @RequestMapping(value = "validate", method = RequestMethod.GET)
    @ResponseBody
    public Object validate(
            @RequestParam("fieldId") String fieldId, @RequestParam("fieldValue") String fieldValue,
            @RequestParam(value = "id", required = false) Long id) {
        ValidateResponse response = ValidateResponse.newInstance();

        if ("username".equals(fieldId)) {
            SysUser sysUser = sysUserService.findByUsername(fieldValue);
            if (sysUser == null || (sysUser.getId().equals(id) && sysUser.getUsername().equals(fieldValue))) {
                //如果msg 不为空 将弹出提示框
                response.validateSuccess(fieldId, "");
            } else {
                response.validateFail(fieldId, "用户名已被其他人使用");
            }
        }
        return response.result();
    }

}
