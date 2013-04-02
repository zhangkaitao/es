/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.es.common.plugin.web.controller;

import com.google.common.collect.Lists;
import com.sishuok.es.common.Constants;
import com.sishuok.es.common.entity.BaseEntity;
import com.sishuok.es.common.entity.enums.BooleanEnum;
import com.sishuok.es.common.entity.search.SearchOperator;
import com.sishuok.es.common.entity.search.Searchable;
import com.sishuok.es.common.entity.search.builder.SearchableBuilder;
import com.sishuok.es.common.plugin.entity.Treeable;
import com.sishuok.es.common.plugin.serivce.BaseTreeableService;
import com.sishuok.es.common.plugin.web.controller.entity.ZTree;
import com.sishuok.es.common.web.bind.annotation.PageableDefaults;
import com.sishuok.es.common.web.controller.BaseController;
import com.sishuok.es.common.web.upload.FileUploadUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 13-2-22 下午4:15
 * <p>Version: 1.0
 */
public abstract class BaseTreeableController<M extends BaseEntity<ID> & Treeable<ID>, ID extends Serializable>
    extends BaseController<M, ID> {

    private BaseTreeableService<M, ID> treeableService;

    protected <S extends BaseTreeableService<M, ID>> BaseTreeableController(S treeableService) {
        super(treeableService);
        this.treeableService = treeableService;
    }

    protected void setCommonData(Model model) {
        model.addAttribute("booleanList", BooleanEnum.values());
    }


    @RequestMapping(value = {"", "main"}, method = RequestMethod.GET)
    public String main() {
        return getViewPrefix() + "/main";
    }

    @RequestMapping(value = "tree", method = RequestMethod.GET)
    @PageableDefaults(sort = {"parentIds=asc", "weight=asc"})
    public String tree(
            HttpServletRequest request,
            @RequestParam(value = "searchName", required = false) String searchName,
            @RequestParam(value = "async", required = false, defaultValue = "false") boolean async,
            Searchable searchable,Model model) {

        List<M> models = null;

        if(!async) {
            if(StringUtils.hasLength(searchName)) {//按name模糊查
                models = treeableService.findSelfAndChildren(searchName, searchable.getPage().getSort());
            } else {
                models = treeableService.findAllBySort(searchable);
            }
        } else {
            if(StringUtils.hasLength(searchName)) {//按name模糊查
                models = treeableService.findAllByName(searchName, searchable.getPage().getSort());
            } else {
                searchable.addSearchFilter("parentId", SearchOperator.eq, 0);
                models = treeableService.findAllBySort(searchable);
            }
        }

        model.addAttribute("trees",
                convertToZtreeList(
                        request.getContextPath(),
                        models,
                        false));

        return getViewPrefix() + "/tree";
    }


    @RequestMapping(value = "ajax/asyncLoad")
    @PageableDefaults(sort = {"parentIds=asc", "weight=asc"})
    @ResponseBody
    public Object asyncLoad(
            HttpServletRequest request,
            @RequestParam(value = "id", required = false, defaultValue = "0") Long parentId,
            @RequestParam(value = "excludeId", required = false) Long excludeId,
            Pageable pageable,Model model) {

        SearchableBuilder searchableBuilder = SearchableBuilder.newInstance();
        searchableBuilder.addSearchFilter("parentId", SearchOperator.eq, parentId);

        if(excludeId != null) {
            searchableBuilder.addSearchFilter("id", SearchOperator.notEq, excludeId);
        }
        searchableBuilder.setPage(pageable);

        List<M> models = treeableService.findAllByNoPage(searchableBuilder.buildSearchable());
        return convertToZtreeList(request.getContextPath(), models, true);
    }

    @RequestMapping(value = "maintain/{id}", method = RequestMethod.GET)
    public String maintain(@PathVariable("id") M m, Model model) {
        setCommonData(model);
        model.addAttribute("m", m);
        return getViewPrefix() + "/maintainForm";
    }
    @RequestMapping(value = "update/{id}", method = RequestMethod.POST)
    public String update(
            Model model, HttpServletRequest request,
            @RequestParam(value = "file", required = false) MultipartFile file, @ModelAttribute("m") M m,
            BindingResult result, RedirectAttributes redirectAttributes) {

        if(!file.isEmpty()) {
            m.setIcon(FileUploadUtils.upload(request, file, result, FileUploadUtils.IMAGE_EXTENSION));
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
        return getViewPrefix() + "/appendChildForm";
    }

    @RequestMapping(value = "appendChild/{parentId}", method = RequestMethod.POST)
    public String appendChild(
            Model model, HttpServletRequest request, @RequestParam(value = "file", required = false) MultipartFile file,
            @PathVariable("parentId") M parent, @ModelAttribute("child") M child, BindingResult result,
            RedirectAttributes redirectAttributes) {

        setCommonData(model);

        if(!file.isEmpty()) {
            child.setIcon(FileUploadUtils.upload(request, file, result, FileUploadUtils.IMAGE_EXTENSION));
        }
        if (result.hasErrors()) {
            return showAppendChildForm(parent, model);
        }

        treeableService.appendChild(parent, child);

        redirectAttributes.addFlashAttribute(Constants.MESSAGE, "添加子节点成功");
        return "redirect:" + redirectUrl(null);
    }


    @RequestMapping(value = "move/{sourceId}", method = RequestMethod.GET)
    @PageableDefaults(sort = {"parentIds=asc", "weight=asc"})
    public String showMoveForm(
            HttpServletRequest request,
            @RequestParam(value = "async", required = false, defaultValue = "false") boolean async,
            @PathVariable("sourceId") M source,
            Searchable searchable,
            Model model) {

        model.addAttribute("source", source);

        if(!async) {
            //排除自己及子子孙孙
            searchable.addSearchFilter("search.id.notEq", "id", SearchOperator.notEq, source.getId());
            searchable.addSearchFilter(
                    "search.parentIds.notLike", "parentIds",
                    SearchOperator.notLike,
                    source.makeSelfAsNewParentIds()  + "%");

            model.addAttribute("trees", convertToZtreeList(
                    request.getContextPath(),
                    treeableService.findAllBySort(searchable),
                    false));
        }

        return getViewPrefix() + "/moveForm";
    }

    @RequestMapping(value = "move/{sourceId}", method = RequestMethod.POST)
    @PageableDefaults(sort = {"parentIds=asc", "weight=asc"})
    public String move(
            HttpServletRequest request,
            @RequestParam(value = "async", required = false, defaultValue = "false") boolean async,
            @PathVariable("sourceId") M source,
            @RequestParam("targetId") M target,
            @RequestParam("moveType") String moveType,
            Searchable searchable,
            Model model,
            RedirectAttributes redirectAttributes) {

        if(target.isRoot() && !moveType.equals("inner")) {
            model.addAttribute(Constants.ERROR, "不能移动到根节点之前或之后");
            return showMoveForm(request, async, source, searchable, model);
        }

        treeableService.move(source, target, moveType);

        redirectAttributes.addFlashAttribute(Constants.MESSAGE, "移动节点成功");
        return "redirect:" + redirectUrl(null);
    }

    /////////////////////////////////////ajax///////////////////////////////////////////////
    @RequestMapping(value = "ajax/appendChild/{parentId}", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public Object ajaxAppendChild(@PathVariable("parentId") M parent) {

        M child = newModel();
        child.setName("新节点");
        treeableService.appendChild(parent, child);
        return child;
    }

    @RequestMapping(value = "ajax/delete/{id}", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public Object ajaxDeleteSelfAndChildren(@PathVariable("id") ID id) {
        M tree = treeableService.findOne(id);
        treeableService.deleteSelfAndChild(tree);
        return tree;
    }

    @RequestMapping(value = "ajax/rename/{id}", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public Object ajaxRename(@PathVariable("id") M tree, @RequestParam("newName") String newName) {
        tree.setName(newName);
        treeableService.update(tree);
        return tree;
    }


    @RequestMapping(value = "ajax/move/{sourceId}/{targetId}/{moveType}", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public Object ajaxMove(
            @PathVariable("sourceId") M source, @PathVariable("targetId") M target,
            @PathVariable("moveType") String moveType) {

        treeableService.move(source, target, moveType);

        return source;
    }



    @RequestMapping("ajax/autocomplete")
    @ResponseBody
    public Set<String> autocomplete(@RequestParam("term") String term) {
        return treeableService.findNames(term);
    }


    @RequestMapping(value = "success")
    public String success() {
        return getViewPrefix() + "/success";
    }

    @Override
    protected String redirectUrl(String backURL) {
        if(StringUtils.hasLength(backURL)) {
            return backURL;
        }
        return redirectUrl("/" + getViewPrefix() + "/success");
    }

    private List<ZTree<ID>> convertToZtreeList(String contextPath, List<M> models, boolean async) {
        List<ZTree<ID>> zTrees = Lists.newArrayList();

        if(models == null || models.isEmpty()) {
            return zTrees;
        }

        for(M m : models) {
            ZTree<ID> zTree = new ZTree<ID>();
            zTree.setId(m.getId());
            zTree.setpId(m.getParentId());
            zTree.setName(m.getName());
            zTree.setIcon(contextPath + "/" + m.getIcon());
            zTree.setOpen(!async);
            zTree.setRoot(m.isRoot());
            zTree.setIsParent(m.isHasChildren());
            zTree.setClick("treeNodeClick(this, " + m.getId() + ", "+ m.getParentId() +")");

            zTrees.add(zTree);
        }
        return zTrees;
    }


}



