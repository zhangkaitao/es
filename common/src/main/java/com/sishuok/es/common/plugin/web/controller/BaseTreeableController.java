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
import com.sishuok.es.common.entity.search.SearchFilter;
import com.sishuok.es.common.entity.search.SearchOperator;
import com.sishuok.es.common.entity.search.Searchable;
import com.sishuok.es.common.entity.search.builder.SearchableBuilder;
import com.sishuok.es.common.plugin.entity.Treeable;
import com.sishuok.es.common.plugin.serivce.BaseTreeableService;
import com.sishuok.es.common.plugin.web.controller.entity.ZTree;
import com.sishuok.es.common.web.bind.annotation.PageableDefaults;
import com.sishuok.es.common.web.controller.BaseController;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
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
            Searchable searchable, Model model) {

        List<M> models = null;

        if(StringUtils.hasLength(searchName)) {
            searchable.addSearchFilter("name_like", searchName);
            models = treeableService.findAllByName(searchable, null);
            if(!async) { //非异步 查自己和子子孙孙
                searchable.removeSearchFilter("name_like");
                List<M> children = treeableService.findChildren(models, searchable);
                models.removeAll(children);
                models.addAll(children);
            } else { //异步模式只查自己

            }
        } else {
            if(!async) {  //非异步 查自己和子子孙孙
                models = treeableService.findAllBySort(searchable);
            } else {  //异步模式只查根 和 根一级节点
                models = treeableService.findRootAndChild(searchable);
            }
        }


        model.addAttribute("trees",
                convertToZtreeList(
                        request.getContextPath(),
                        models,
                        async,
                        true));

        return getViewPrefix() + "/tree";
    }

    @RequestMapping(value = "list/{current}", method = RequestMethod.GET)
    @PageableDefaults(sort = {"parentIds=asc", "weight=asc"})
    public String list(
            @PathVariable("current") M current,
            Searchable searchable, Model model) {


        if(current != null) {
            SearchFilter searchFilter =
                    new SearchFilter("parentIds", SearchOperator.prefixLike, current.makeSelfAsNewParentIds());

            searchFilter.or("id", SearchOperator.eq, current.getId());

            searchable.addSearchFilter(searchFilter);
        }

        model.addAttribute("page", treeableService.findAll(searchable));

        return getViewPrefix() + "/list";
    }


    /**
     * 仅返回表格数据
     * @param searchable
     * @param model
     * @return
     */
    @RequestMapping(value = "list/{current}", headers = "table=true", method = RequestMethod.GET)
    @PageableDefaults(sort = {"parentIds=asc", "weight=asc"})
    public String listTable(
            @PathVariable("current") M current,
            Searchable searchable, Model model) {

        list(current, searchable, model);
        return getViewPrefix() + "/listTable";

    }



    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String view(@PathVariable("id") M m, Model model) {
        setCommonData(model);
        model.addAttribute("m", m);
        model.addAttribute(Constants.OP_NAME, "查看");
        return getViewPrefix() + "/editForm";
    }

    @RequestMapping(value = "update/{id}", method = RequestMethod.GET)
    public String updateForm(@PathVariable("id") M m, Model model) {
        setCommonData(model);
        model.addAttribute("m", m);
        model.addAttribute(Constants.OP_NAME, "修改");
        return getViewPrefix() + "/editForm";
    }
    @RequestMapping(value = "update/{id}", method = RequestMethod.POST)
    public String update(
            @RequestParam(value = Constants.BACK_URL) String backURL,
            Model model, HttpServletRequest request,
            @ModelAttribute("m") M m,
            BindingResult result, RedirectAttributes redirectAttributes) {

        if(result.hasErrors()) {
            return updateForm(m, model);
        }

        treeableService.update(m);
        redirectAttributes.addFlashAttribute(Constants.MESSAGE, "修改成功");
        return redirectToUrl(backURL);
    }

    @RequestMapping(value = "delete/{id}", method = RequestMethod.GET)
    public String deleteForm(@PathVariable("id") M m, Model model) {
        setCommonData(model);
        model.addAttribute("m", m);
        model.addAttribute(Constants.OP_NAME, "删除");
        return getViewPrefix() + "/editForm";
    }

    @RequestMapping(value = "delete/{id}", method = RequestMethod.POST)
    public String deleteSelfAndChildren(
            @RequestParam(value = Constants.BACK_URL) String backURL,
            @ModelAttribute("m") M m, RedirectAttributes redirectAttributes) {

        if(m.isRoot()) {
            redirectAttributes.addFlashAttribute(Constants.ERROR, "您删除的数据中包含根节点，根节点不能删除");
            return redirectToUrl(backURL);
        }

        treeableService.deleteSelfAndChild(m);
        redirectAttributes.addFlashAttribute(Constants.MESSAGE, "删除成功");
        return  redirectToUrl(backURL);
    }


    @RequestMapping(value = "batch/delete")
    public String deleteInBatch(
            @RequestParam(value = "ids", required = false) ID[] ids,
            @RequestParam(value = Constants.BACK_URL, required = false) String backURL,
            RedirectAttributes redirectAttributes) {

        //如果要求不严格 此处可以删除判断 前台已经判断过了
        Searchable searchable = SearchableBuilder.newInstance()
                .addSearchFilter("id", SearchOperator.in, ids)
                .buildSearchable();
        List<M> mList = baseService.findAllByNoPageNoSort(searchable);
        for(M m : mList) {
            if(m.isRoot()) {
                redirectAttributes.addFlashAttribute(Constants.ERROR, "您删除的数据中包含根节点，根节点不能删除");
                return redirectToUrl(backURL);
            }
        }

        baseService.delete(ids);
        redirectAttributes.addFlashAttribute(Constants.MESSAGE, "批量删除成功");
        return redirectToUrl(backURL);
    }


    @RequestMapping(value = "appendChild/{parentId}", method = RequestMethod.GET)
    public String appendChildForm(@PathVariable("parentId") M parent, Model model) {
        setCommonData(model);
        model.addAttribute("parent", parent);
        if(!model.containsAttribute("child")) {
            model.addAttribute("child", newModel());
        }

        model.addAttribute(Constants.OP_NAME, "添加子节点");

        return getViewPrefix() + "/appendChildForm";
    }

    @RequestMapping(value = "appendChild/{parentId}", method = RequestMethod.POST)
    public String appendChild(
            Model model, HttpServletRequest request,
            @RequestParam(value = Constants.BACK_URL) String backURL,
            @PathVariable("parentId") M parent,
            @ModelAttribute("child") M child, BindingResult result,
            RedirectAttributes redirectAttributes) {

        setCommonData(model);

        if (result.hasErrors()) {
            return appendChildForm(parent, model);
        }

        treeableService.appendChild(parent, child);

        redirectAttributes.addFlashAttribute(Constants.MESSAGE, "添加子节点成功");
        return  redirectToUrl(backURL);
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

        List<M> models = null;

        //排除自己及子子孙孙
        searchable.addSearchFilter("id", SearchOperator.ne, source.getId());
        searchable.addSearchFilter(
                "parentIds",
                SearchOperator.notLike,
                source.makeSelfAsNewParentIds()  + "%");

        if(!async) {
            models = treeableService.findAllBySort(searchable);
        } else {
            models = treeableService.findRootAndChild(searchable);
        }

        model.addAttribute("trees", convertToZtreeList(
                request.getContextPath(),
                models,
                async,
                true));

        model.addAttribute(Constants.OP_NAME, "移动节点");

        return getViewPrefix() + "/moveForm";
    }

    @RequestMapping(value = "move/{sourceId}", method = RequestMethod.POST)
    @PageableDefaults(sort = {"parentIds=asc", "weight=asc"})
    public String move(
            HttpServletRequest request,
            @RequestParam(value = Constants.BACK_URL) String backURL,
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
        return redirectToUrl(backURL);
    }



    /////////////////////////////////////ajax///////////////////////////////////////////////

    @RequestMapping(value = "ajax/load")
    @PageableDefaults(sort = {"parentIds=asc", "weight=asc"})
    @ResponseBody
    public Object load(
            HttpServletRequest request,
            @RequestParam(value = "async", defaultValue = "true") boolean async,
            @RequestParam(value = "asyncLoadAll", defaultValue = "false") boolean asyncLoadAll,
            @RequestParam(value = "searchName", required = false) String searchName,
            @RequestParam(value = "id", required = false) ID parentId,
            @RequestParam(value = "excludeId", required = false) ID excludeId,
            @RequestParam(value = "onlyCheckLeaf", required = false, defaultValue = "false") boolean onlyCheckLeaf,
            Searchable searchable) {

        M excludeM = treeableService.findOne(excludeId);

        List<M> models = null;

        if(StringUtils.hasLength(searchName)) {//按name模糊查
            searchable.addSearchFilter("name_like", searchName);
            models = treeableService.findAllByName(searchable, excludeM);
            if(!async || asyncLoadAll) {//非异步模式 查自己及子子孙孙 但排除
                searchable.removeSearchFilter("name_like");
                List<M> children = treeableService.findChildren(models, searchable);
                models.removeAll(children);
                models.addAll(children);
            } else { //异步模式 只查匹配的一级

            }
        } else { //根据有没有parentId加载

            if(parentId != null) { //只查某个节点下的 异步
                searchable.addSearchFilter("parentId", SearchOperator.eq, parentId);
            }

            if(async && !asyncLoadAll) { //异步模式下 且非异步加载所有
                //排除自己 及 子子孙孙
                treeableService.addExcludeSearchFilter(searchable, excludeM);

            }

            if(parentId == null && !asyncLoadAll) {
                models = treeableService.findRootAndChild(searchable);
            } else {
                models = treeableService.findAllBySort(searchable);
            }
        }

        return convertToZtreeList(
                request.getContextPath(),
                models,
                async && !asyncLoadAll && parentId != null,
                onlyCheckLeaf);
    }

    @RequestMapping(value = "ajax/appendChild/{parentId}", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public Object ajaxAppendChild(HttpServletRequest request, @PathVariable("parentId") M parent) {

        M child = newModel();
        child.setName("新节点");
        treeableService.appendChild(parent, child);
        return convertToZtree(child, request.getContextPath(), true, true);
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
    public Object ajaxRename(HttpServletRequest request, @PathVariable("id") M tree, @RequestParam("newName") String newName) {
        tree.setName(newName);
        treeableService.update(tree);
        return convertToZtree(tree, request.getContextPath(), true, true);
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
    @PageableDefaults(value = 30)
    @ResponseBody
    public Set<String> autocomplete(
            Searchable searchable,
            @RequestParam("term") String term,
            @RequestParam(value = "excludeId", required = false) ID excludeId) {

        return treeableService.findNames(searchable, term, excludeId);
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

    private List<ZTree<ID>> convertToZtreeList(String contextPath, List<M> models, boolean async, boolean onlySelectLeaf) {
        List<ZTree<ID>> zTrees = Lists.newArrayList();

        if(models == null || models.isEmpty()) {
            return zTrees;
        }

        for(M m : models) {
            ZTree zTree = convertToZtree(m, contextPath, !async, onlySelectLeaf);
            zTrees.add(zTree);
        }
        return zTrees;
    }

    private ZTree convertToZtree(M m, String contextPath, boolean open, boolean onlyCheckLeaf) {
        ZTree<ID> zTree = new ZTree<ID>();
        zTree.setId(m.getId());
        zTree.setpId(m.getParentId());
        zTree.setName(m.getName());
        zTree.setIconSkin(m.getIcon());
        zTree.setOpen(open);
        zTree.setRoot(m.isRoot());
        zTree.setIsParent(m.isHasChildren());

        if(onlyCheckLeaf && zTree.isIsParent()) {
            zTree.setNocheck(true);
        } else {
            zTree.setNocheck(false);
        }

        return zTree;
    }


}



