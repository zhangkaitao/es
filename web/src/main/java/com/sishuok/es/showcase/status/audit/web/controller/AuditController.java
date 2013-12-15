/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.es.showcase.status.audit.web.controller;

import com.sishuok.es.common.Constants;
import com.sishuok.es.common.plugin.entity.Stateable;
import com.sishuok.es.common.web.controller.BaseCRUDController;
import com.sishuok.es.showcase.status.audit.entity.Audit;
import com.sishuok.es.showcase.status.audit.service.AuditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 13-1-28 下午4:29
 * <p>Version: 1.0
 */
@Controller
@RequestMapping(value = "/showcase/status/audit")
public class AuditController extends BaseCRUDController<Audit, Long> {

    private AuditService getAuditService() {
        return (AuditService) baseService;
    }

    public AuditController() {
        setListAlsoSetCommonData(true);
        setResourceIdentity("showcase:statusAudit");
    }

    @Override
    protected void setCommonData(Model model) {
        model.addAttribute("statusList", Stateable.AuditStatus.values());
    }


    @RequestMapping(value = "status/{status}", method = RequestMethod.GET)
    public String audit(
            HttpServletRequest request,
            @RequestParam("ids") Long[] ids,
            @PathVariable("status") Stateable.AuditStatus status,
            @RequestParam(value = "comment", required = false) String comment,
            RedirectAttributes redirectAttributes
    ) {

        this.permissionList.assertHasPermission("audit");

        List<Audit> auditList = new ArrayList<Audit>();
        for (Long id : ids) {
            Audit audit = getAuditService().findOne(id);
            if (audit.getStatus() != Stateable.AuditStatus.waiting) {
                redirectAttributes.addFlashAttribute(Constants.ERROR, "数据中有已通过审核的，不能重复审核！");
                return "redirect:" + request.getAttribute(Constants.BACK_URL);
            }
            auditList.add(audit);
        }
        for (Audit audit : auditList) {
            audit.setStatus(status);
            audit.setComment(comment);
            getAuditService().update(audit);
        }

        redirectAttributes.addFlashAttribute(Constants.MESSAGE, "操作成功！");

        return "redirect:" + request.getAttribute(Constants.BACK_URL);
    }

    /**
     * 验证失败返回true
     *
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
