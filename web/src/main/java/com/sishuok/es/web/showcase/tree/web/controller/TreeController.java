/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.es.web.showcase.tree.web.controller;

import com.sishuok.es.Constants;
import com.sishuok.es.common.entity.enums.BooleanEnum;
import com.sishuok.es.common.entity.search.SearchOperator;
import com.sishuok.es.common.entity.search.Searchable;
import com.sishuok.es.common.utils.LogUtils;
import com.sishuok.es.common.web.bind.annotation.PageableDefaults;
import com.sishuok.es.common.web.utils.FileUploadUtils;
import com.sishuok.es.common.web.validate.ValidateResponse;
import com.sishuok.es.web.showcase.sample.entity.Sample;
import com.sishuok.es.web.showcase.tree.entity.Tree;
import com.sishuok.es.web.showcase.tree.service.TreeService;
import org.apache.commons.fileupload.FileUploadBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.util.Date;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 13-1-28 下午4:29
 * <p>Version: 1.0
 */
@Controller
@RequestMapping(value = "/showcase/tree")
public class TreeController {

    @Autowired
    private TreeService treeService;

    public void setCommonData(Model model) {
        model.addAttribute("booleanList", BooleanEnum.values());
    }

    @RequestMapping(value = "main", method = RequestMethod.GET)
    public String main() {
        return "showcase/tree/main";
    }

    @RequestMapping(value = "tree", method = RequestMethod.GET)
    @PageableDefaults(sort = "path=asc")
    public String tree(Searchable searchable, Model model) {
        model.addAttribute("trees", treeService.findAll(searchable));
        return "showcase/tree/tree";
    }

    @RequestMapping(value = "maintain/{id}", method = RequestMethod.GET)
    public String maintain(@PathVariable("id") Tree tree, Model model) {
        setCommonData(model);
        model.addAttribute("tree", tree);
        return "showcase/tree/maintainForm";
    }
    @RequestMapping(value = "update/{id}", method = RequestMethod.POST)
    public String update(
            Model model, HttpServletRequest request,
            @RequestParam(value = "file", required = false) MultipartFile file, @ModelAttribute("tree") Tree tree,
            BindingResult result, RedirectAttributes redirectAttributes) {

        if(!file.isEmpty()) {
            tree.setIcon(FileUploadUtils.upload(request, file, result, FileUploadUtils.IMAGE_CONTENT_TYPE));
        }
        if(result.hasErrors()) {
            return maintain(tree, model);
        }

        treeService.update(tree);
        redirectAttributes.addFlashAttribute(Constants.MESSAGE, "修改成功");
        return "redirect:/showcase/tree/success";
    }

    @RequestMapping(value = "delete/{id}", method = RequestMethod.POST)
    public String deleteSelfAndChildren(@ModelAttribute("tree") Tree tree, RedirectAttributes redirectAttributes) {
        treeService.deleteSelfAndChild(tree);
        redirectAttributes.addFlashAttribute(Constants.MESSAGE, "删除成功");
        return "redirect:/showcase/tree/success";
    }

    @RequestMapping(value = "appendChild/{parentId}", method = RequestMethod.GET)
    public String showAppendChildForm(@PathVariable("parentId") Tree parent, Model model) {
        setCommonData(model);
        model.addAttribute("parent", parent);
        if(!model.containsAttribute("child")) {
            model.addAttribute("child", new Tree());
        }
        return "showcase/tree/appendChildForm";
    }

    @RequestMapping(value = "appendChild/{parentId}", method = RequestMethod.POST)
    public String appendChild(
            Model model, HttpServletRequest request, @RequestParam(value = "file", required = false) MultipartFile file,
            @PathVariable("parentId") Tree parent, @ModelAttribute("child") Tree child, BindingResult result,
            RedirectAttributes redirectAttributes) {

        setCommonData(model);

        if(!file.isEmpty()) {
            child.setIcon(FileUploadUtils.upload(request, file, result, FileUploadUtils.IMAGE_CONTENT_TYPE));
        }
        if (result.hasErrors()) {
            return showAppendChildForm(parent, model);
        }

        treeService.appendChild(parent, child);

        redirectAttributes.addFlashAttribute(Constants.MESSAGE, "添加子节点成功");
        return "redirect:/showcase/tree/success";
    }


    @RequestMapping(value = "move/{sourceId}", method = RequestMethod.GET)
    @PageableDefaults(sort = "path=asc")
    public String showMoveForm(@PathVariable("sourceId") Tree source, Searchable searchable, Model model) {
        model.addAttribute("source", source);
        //排除自己及子子孙孙
        searchable.addSearchFilter("search.path.notLike", "path", SearchOperator.notLike, source.getPath() + "%");
        model.addAttribute("trees", treeService.findAll(searchable));
        return "showcase/tree/moveForm";
    }

    @RequestMapping(value = "move/{sourceId}", method = RequestMethod.POST)
    public String move(
            @PathVariable("sourceId") Tree source, @RequestParam("targetPath") String targetPath,
            @RequestParam("moveType") String moveType, RedirectAttributes redirectAttributes) {

        treeService.move(source, targetPath, moveType);

        redirectAttributes.addFlashAttribute(Constants.MESSAGE, "移动节点成功");
        return "redirect:/showcase/tree/success";
    }



    /////////////////////////////////////ajax///////////////////////////////////////////////
    @RequestMapping(value = "ajax/appendChild/{parentPath}", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public Object ajaxAppendChild(@PathVariable("parentPath") String parentPath) {

        Tree parent = treeService.findByPath(parentPath);

        Tree child = new Tree();
        child.setShow(Boolean.FALSE);
        child.setName("新节点");

        treeService.appendChild(parent, child);

        return child;
    }

    @RequestMapping(value = "ajax/delete/{path}", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public Object ajaxDeleteSelfAndChildren(@PathVariable("path") String path) {
        Tree tree = treeService.findByPath(path);
        treeService.deleteSelfAndChild(tree);
        return tree;
    }

    @RequestMapping(value = "ajax/rename/{path}", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public Object ajaxRename(@PathVariable("path") String path, @RequestParam("newName") String newName) {
        Tree tree = treeService.findByPath(path);
        tree.setName(newName);
        treeService.update(tree);
        return tree;
    }


    @RequestMapping(value = "ajax/move/{sourcePath}/{targetPath}/{moveType}", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public Object ajaxMove(
            @PathVariable("sourcePath") String sourcePath, @PathVariable("targetPath") String targetPath,
            @PathVariable("moveType") String moveType) {

        Tree source = treeService.findByPath(sourcePath);
        treeService.move(source, targetPath, moveType);

        return source;
    }


    @RequestMapping(value = "success")
    public String success() {
        return "showcase/tree/success";
    }



}
