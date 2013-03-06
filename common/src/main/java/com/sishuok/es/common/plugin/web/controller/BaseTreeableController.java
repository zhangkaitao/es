/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.es.common.plugin.web.controller;

import com.sishuok.es.common.Constants;
import com.sishuok.es.common.entity.BaseEntity;
import com.sishuok.es.common.entity.enums.BooleanEnum;
import com.sishuok.es.common.entity.search.SearchOperator;
import com.sishuok.es.common.entity.search.Searchable;
import com.sishuok.es.common.plugin.entity.Treeable;
import com.sishuok.es.common.plugin.serivce.BaseTreeableService;
import com.sishuok.es.common.web.bind.annotation.PageableDefaults;
import com.sishuok.es.common.web.controller.BaseController;
import com.sishuok.es.common.web.utils.FileUploadUtils;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 13-2-22 下午4:15
 * <p>Version: 1.0
 */
public abstract class BaseTreeableController<M extends BaseEntity & Treeable, ID extends Serializable>
    extends BaseController<M, ID> {

    private BaseTreeableService<M, ID> treeableService;

    protected <S extends BaseTreeableService<M, ID>> BaseTreeableController(S treeableService) {
        super(treeableService);
        this.treeableService = treeableService;
    }

    protected void setCommonData(Model model) {
        model.addAttribute("booleanList", BooleanEnum.values());
    }

    @RequestMapping(value = "main", method = RequestMethod.GET)
    public String main() {
        return viewPrefix + "/main";
    }

    @RequestMapping(value = "tree", method = RequestMethod.GET)
    @PageableDefaults(sort = "path=asc")
    public String tree(Searchable searchable, Model model) {
        model.addAttribute("page", treeableService.findAll(searchable));
        return viewPrefix + "/tree";
    }

    @RequestMapping(value = "maintain/{id}", method = RequestMethod.GET)
    public String maintain(@PathVariable("id") M m, Model model) {
        setCommonData(model);
        model.addAttribute("m", m);
        return viewPrefix + "/maintainForm";
    }
    @RequestMapping(value = "update/{id}", method = RequestMethod.POST)
    public String update(
            Model model, HttpServletRequest request,
            @RequestParam(value = "file", required = false) MultipartFile file, @ModelAttribute("m") M m,
            BindingResult result, RedirectAttributes redirectAttributes) {

        if(!file.isEmpty()) {
            m.setIcon(FileUploadUtils.upload(request, file, result, FileUploadUtils.IMAGE_CONTENT_TYPE));
        }
        if(result.hasErrors()) {
            return maintain(m, model);
        }

        treeableService.update(m);
        redirectAttributes.addFlashAttribute(Constants.MESSAGE, "修改成功");
        return "redirect:" + redirectUrl(null);
    }

    @RequestMapping(value = "delete/{id}", method = RequestMethod.POST)
    public String deleteSelfAndChildren(@ModelAttribute("m") M m, RedirectAttributes redirectAttributes) {
        treeableService.deleteSelfAndChild(m);
        redirectAttributes.addFlashAttribute(Constants.MESSAGE, "删除成功");
        return "redirect:" + redirectUrl(null);
    }

    @RequestMapping(value = "appendChild/{parentId}", method = RequestMethod.GET)
    public String showAppendChildForm(@PathVariable("parentId") M parent, Model model) {
        setCommonData(model);
        model.addAttribute("parent", parent);
        if(!model.containsAttribute("child")) {
            model.addAttribute("child", newModel());
        }
        return viewPrefix + "/appendChildForm";
    }

    @RequestMapping(value = "appendChild/{parentId}", method = RequestMethod.POST)
    public String appendChild(
            Model model, HttpServletRequest request, @RequestParam(value = "file", required = false) MultipartFile file,
            @PathVariable("parentId") M parent, @ModelAttribute("child") M child, BindingResult result,
            RedirectAttributes redirectAttributes) {

        setCommonData(model);

        if(!file.isEmpty()) {
            child.setIcon(FileUploadUtils.upload(request, file, result, FileUploadUtils.IMAGE_CONTENT_TYPE));
        }
        if (result.hasErrors()) {
            return showAppendChildForm(parent, model);
        }

        treeableService.appendChild(parent, child);

        redirectAttributes.addFlashAttribute(Constants.MESSAGE, "添加子节点成功");
        return "redirect:" + redirectUrl(null);
    }


    @RequestMapping(value = "move/{sourceId}", method = RequestMethod.GET)
    @PageableDefaults(sort = "path=asc")
    public String showMoveForm(@PathVariable("sourceId") M source, Searchable searchable, Model model) {
        model.addAttribute("source", source);
        //排除自己及子子孙孙
        searchable.addSearchFilter("search.path.notLike", "path", SearchOperator.notLike, source.getPath() + "%");
        model.addAttribute("page", treeableService.findAll(searchable));
        return viewPrefix + "/moveForm";
    }

    @RequestMapping(value = "move/{sourceId}", method = RequestMethod.POST)
    public String move(
            @PathVariable("sourceId") M source, @RequestParam("targetPath") String targetPath,
            @RequestParam("moveType") String moveType, RedirectAttributes redirectAttributes) {

        treeableService.move(source, targetPath, moveType);

        redirectAttributes.addFlashAttribute(Constants.MESSAGE, "移动节点成功");
        return "redirect:" + redirectUrl(null);
    }

    /////////////////////////////////////ajax///////////////////////////////////////////////
    @RequestMapping(value = "ajax/appendChild/{parentPath}", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public Object ajaxAppendChild(@PathVariable("parentPath") String parentPath) {

        M parent = treeableService.findByPath(parentPath);

        M child = newModel();

        child.setName("新节点");

        treeableService.appendChild(parent, child);

        return child;
    }

    @RequestMapping(value = "ajax/delete/{path}", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public Object ajaxDeleteSelfAndChildren(@PathVariable("path") String path) {
        M tree = treeableService.findByPath(path);
        treeableService.deleteSelfAndChild(tree);
        return tree;
    }

    @RequestMapping(value = "ajax/rename/{path}", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public Object ajaxRename(@PathVariable("path") String path, @RequestParam("newName") String newName) {
        M tree = treeableService.findByPath(path);
        tree.setName(newName);
        treeableService.update(tree);
        return tree;
    }


    @RequestMapping(value = "ajax/move/{sourcePath}/{targetPath}/{moveType}", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public Object ajaxMove(
            @PathVariable("sourcePath") String sourcePath, @PathVariable("targetPath") String targetPath,
            @PathVariable("moveType") String moveType) {

        M source = treeableService.findByPath(sourcePath);
        treeableService.move(source, targetPath, moveType);

        return source;
    }

    @RequestMapping(value = "success")
    public String success() {
        return viewPrefix + "/success";
    }

    @Override
    protected String redirectUrl(String backURL) {
        if(StringUtils.hasLength(backURL)) {
            return backURL;
        }
        return redirectUrl("/" + viewPrefix + "/success");
    }
}
