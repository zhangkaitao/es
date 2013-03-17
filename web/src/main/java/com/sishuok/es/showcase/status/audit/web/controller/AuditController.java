/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.es.showcase.status.audit.web.controller;

import com.sishuok.es.common.Constants;
import com.sishuok.es.common.entity.search.Searchable;
import com.sishuok.es.common.plugin.entity.Stateable;
import com.sishuok.es.common.web.controller.BaseCRUDController;
import com.sishuok.es.showcase.status.audit.entity.Audit;
import com.sishuok.es.showcase.status.audit.service.AuditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 13-1-28 下午4:29
 * <p>Version: 1.0
 */
@Controller
@RequestMapping(value = "/showcase/status/audit")
public class AuditController extends BaseCRUDController<Audit, Long> {

    private AuditService auditService;

    @Autowired
    public AuditController(AuditService auditService) {
        super(auditService);
        this.auditService = auditService;
    }

    @Override
    protected void setCommonData(Model model) {
        model.addAttribute("statusList", Stateable.AuditStatus.values());
    }

    @Override
    public String list(Searchable searchable, Model model) {
        setCommonData(model);
        return super.list(searchable, model);
    }


    @RequestMapping(value = "{id}/{status}", method = RequestMethod.GET)
    public String audit(
            HttpServletRequest request,
            @PathVariable("id") Audit audit,
            @PathVariable("status") Stateable.AuditStatus status,
            @RequestParam(value = "comment", required = false) String comment,
            RedirectAttributes redirectAttributes
        ) {

        if(audit.getStatus() != Stateable.AuditStatus.waiting) {
            redirectAttributes.addFlashAttribute(Constants.ERROR, "数据已审核过，不能重复审核！");
            return "redirect:" + request.getAttribute(Constants.BACK_URL);
        }

        audit.setStatus(status);
        audit.setComment(comment);
        auditService.update(audit);

        redirectAttributes.addFlashAttribute(Constants.MESSAGE, "操作成功！");

        return "redirect:" + request.getAttribute(Constants.BACK_URL);
    }

    /**
     * 验证失败返回true
     * @param m
     * @param result
     * @return
     */
    @Override
    protected boolean hasError(Audit m, BindingResult result) {
        Assert.notNull(m);

        return result.hasErrors();
    }

}
