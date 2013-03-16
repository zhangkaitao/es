/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.es.common.web.controller;

import com.sishuok.es.common.Constants;
import com.sishuok.es.common.entity.BaseEntity;
import com.sishuok.es.common.entity.search.Searchable;
import com.sishuok.es.common.service.BaseService;
import com.sishuok.es.common.web.bind.annotation.PageableDefaults;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.Serializable;

/**
 * 基础CRUD 控制器
 * <p>User: Zhang Kaitao
 * <p>Date: 13-2-23 下午1:20
 * <p>Version: 1.0
 */
public class BaseCRUDController<M extends BaseEntity, ID extends Serializable> extends BaseController<M, ID> {


    protected  <S extends BaseService<M, ID>> BaseCRUDController(S baseService) {
        super(baseService);
    }


    /**
     * 设置通用数据
     * @param model
     */
    protected void setCommonData(Model model) {
    }

    @RequestMapping(method = RequestMethod.GET)
    @PageableDefaults(sort = "id=desc")
    public String list(Searchable searchable, Model model) {
        model.addAttribute("page", baseService.findAll(searchable));
        return getViewPrefix() + "/list";
    }

    /**
     * 仅返回表格数据
     * @param searchable
     * @param model
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, headers = "table=true")
    @PageableDefaults(sort = "id=desc")
    public String listTable(Searchable searchable, Model model) {
        list(searchable, model);
        return getViewPrefix() + "/listTable";
    }



    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String view(Model model, @PathVariable("id") M m) {
        setCommonData(model);
        model.addAttribute("m", m);
        model.addAttribute(Constants.OP_NAME, "查看");
        return getViewPrefix() + "/editForm";
    }

    @RequestMapping(value = "create", method = RequestMethod.GET)
    public String showCreateForm(Model model) {
        setCommonData(model);
        model.addAttribute(Constants.OP_NAME, "新增");
        if(!model.containsAttribute("m")) {
            model.addAttribute("m", newModel());
        }
        return getViewPrefix() + "/editForm";
    }


    @RequestMapping(value = "create", method = RequestMethod.POST)
    public String create(
            Model model, @Valid @ModelAttribute("m") M m, BindingResult result,
            RedirectAttributes redirectAttributes) {

        if (hasError(m, result)) {
            return showCreateForm(model);
        }
        baseService.save(m);
        redirectAttributes.addFlashAttribute(Constants.MESSAGE, "新增成功");
        return "redirect:" + redirectUrl(null);
    }


    @RequestMapping(value = "update/{id}", method = RequestMethod.GET)
    public String showUpdateForm(@PathVariable("id") M m, Model model) {
        setCommonData(model);
        model.addAttribute(Constants.OP_NAME, "修改");
        model.addAttribute("m", m);
        return getViewPrefix() + "/editForm";
    }

    @RequestMapping(value = "update/{id}", method = RequestMethod.POST)
    public String update(
            Model model, @Valid @ModelAttribute("m") M m, BindingResult result,
            @RequestParam(value = Constants.BACK_URL, required =false) String backURL,
            RedirectAttributes redirectAttributes) {

        if (hasError(m, result)) {
            return showUpdateForm(m, model);
        }
        baseService.update(m);
        redirectAttributes.addFlashAttribute(Constants.MESSAGE, "修改成功");
        return "redirect:" + redirectUrl(backURL);
    }

    @RequestMapping(value = "delete/{id}", method = RequestMethod.GET)
    public String showDeleteForm(@PathVariable("id") M m, Model model) {
        setCommonData(model);
        model.addAttribute(Constants.OP_NAME, "删除");
        model.addAttribute("m", m);
        return getViewPrefix() + "/editForm";
    }

    @RequestMapping(value = "delete/{id}", method = RequestMethod.POST)
    public String delete(
            @ModelAttribute("m") M m,
            @RequestParam(value = Constants.BACK_URL, required = false) String backURL,
            RedirectAttributes redirectAttributes) {

        baseService.delete(m);
        redirectAttributes.addFlashAttribute(Constants.MESSAGE, "删除成功");
        return "redirect:" + redirectUrl(backURL);
    }

    @RequestMapping(value = "batch/delete")
    public String deleteInBatch(
            @RequestParam(value = "ids", required = false) ID[] ids,
            @RequestParam(value = Constants.BACK_URL, required = false) String backURL,
            RedirectAttributes redirectAttributes) {

        baseService.delete(ids);
        redirectAttributes.addFlashAttribute(Constants.MESSAGE, "批量删除成功");
        return "redirect:" + redirectUrl(backURL);
    }

//ajax 删除方式
//    @RequestMapping(value = "batch/delete", method = RequestMethod.POST)
//    @ResponseBody
//    public AjaxResponse deleteInBatch(@RequestParam(value = "ids", required = false) ID[] ids) {
//        AjaxResponse response = new AjaxResponse();
//        baseService.delete(ids);
//        return response;
//    }




}
