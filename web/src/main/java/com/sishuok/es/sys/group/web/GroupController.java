/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.es.sys.group.web;

import com.sishuok.es.common.Constants;
import com.sishuok.es.common.entity.enums.BooleanEnum;
import com.sishuok.es.common.entity.search.Searchable;
import com.sishuok.es.common.web.bind.annotation.PageableDefaults;
import com.sishuok.es.common.web.controller.BaseCRUDController;
import com.sishuok.es.sys.group.entity.Group;
import com.sishuok.es.sys.group.entity.GroupType;
import com.sishuok.es.sys.group.service.GroupRelationService;
import com.sishuok.es.sys.group.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Map;
import java.util.Set;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 13-1-28 下午4:29
 * <p>Version: 1.0
 */
@Controller
@RequestMapping(value = "/admin/sys/group")
public class GroupController extends BaseCRUDController<Group, Long> {

    private GroupService groupService;

    @Autowired
    private GroupRelationService groupRelationService;


    @Autowired
    public GroupController(GroupService groupService) {
        super(groupService);
        setListAlsoSetCommonData(true);
        this.groupService = groupService;
    }

    @Override
    protected void setCommonData(Model model) {
        super.setCommonData(model);
        model.addAttribute("types", GroupType.values());
        model.addAttribute("booleanList", BooleanEnum.values());
    }


    @RequestMapping(value = "list/{type}", method = RequestMethod.GET)
    @PageableDefaults(sort = "id=desc")
    public String list(@PathVariable("type") GroupType type, Searchable searchable, Model model) {

        searchable.addSearchFilter("type_eq", type);

        return list(searchable, model);
    }

    @RequestMapping(value = "create/discard", method = RequestMethod.GET)
    public String showCreateForm(Model model) {
        throw new RuntimeException("discarded method");
    }



    @RequestMapping(value = "create/{type}", method = RequestMethod.GET)
    public String showCreateFormWithType(@PathVariable("type") GroupType type, Model model) {
        if(!model.containsAttribute("m")) {
            Group group = new Group();
            group.setType(type);
            model.addAttribute("m", group);
        }
        return super.showCreateForm(model);
    }

    @RequestMapping(value = "create/{type}", method = RequestMethod.POST)
    public String create(
            Model model, @Valid @ModelAttribute("m") Group m, BindingResult result,
            RedirectAttributes redirectAttributes) {

        return super.create(model, m, result, redirectAttributes);
    }



    @RequestMapping(value = "/changeStatus/{newStatus}")
    public String changeShowStatus(
            HttpServletRequest request,
            @PathVariable("newStatus") Boolean newStatus,
            @RequestParam("ids") Long[] ids
    ) {

        for(Long id : ids) {
            Group group = groupService.findOne(id);
            group.setShow(newStatus);
            groupService.update(group);
        }
        return "redirect:" + request.getAttribute(Constants.BACK_URL);
    }


    @RequestMapping(value = "/changeDefaultGroup/{newStatus}")
    public String changeDefaultGroup(
            HttpServletRequest request,
            @PathVariable("newStatus") Boolean newStatus,
            @RequestParam("ids") Long[] ids
    ) {

        for(Long id : ids) {
            Group group = groupService.findOne(id);
            if(group.getType() != GroupType.user) {//只有用户组 可设置为默认 其他无效
                continue;
            }
            group.setDefaultGroup(newStatus);
            groupService.update(group);
        }
        return "redirect:" + request.getAttribute(Constants.BACK_URL);
    }



    @RequestMapping("ajax/autocomplete")
    @PageableDefaults(value = 30)
    @ResponseBody
    public Set<Map<String, Object>> autocomplete(
            Searchable searchable,
            @RequestParam("term") String term) {

        return groupService.findIdAndNames(searchable, term);
    }


    ///////////////////////////////分组 内//////////////////////////////////////
    @RequestMapping(value = "/{group}/listRelation", method = RequestMethod.GET)
    @PageableDefaults(sort = "id=desc")
    public String listGroupRelation(@PathVariable("group") Group group, Searchable searchable, Model model) {

        searchable.addSearchFilter("groupId_eq", group.getId());

        Page page = null;
        if(group.getType() == GroupType.user) {
            page = groupRelationService.findAll(searchable);
        }

        if(group.getType() == GroupType.organization) {
            page = groupRelationService.findAll(searchable);
        }

        model.addAttribute("page", page);

        return getViewPrefix() + "/relation/relationList";
    }


    @RequestMapping(value = "/{group}/listRelation", headers = "table=true", method = RequestMethod.GET)
    @PageableDefaults(sort = "id=desc")
    public String listGroupRelationTable(@PathVariable("group") Group group, Searchable searchable, Model model) {

        this.listGroupRelation(group, searchable, model);
        return getViewPrefix() + "/relation/relationListTable";

    }

    @RequestMapping(value = "{group}/batch/delete", method = RequestMethod.GET)
    public String batchDeleteGroupRelation(
            @PathVariable("group") Group group,
            @RequestParam("ids") Long[] ids,
            @RequestParam(value = Constants.BACK_URL, required = false) String backURL,
            RedirectAttributes redirectAttributes) {

        if (group.getType() == GroupType.user) {
            groupRelationService.delete(ids);
        }

        if (group.getType() == GroupType.organization) {
            groupRelationService.delete(ids);
        }

        redirectAttributes.addFlashAttribute(Constants.MESSAGE, "批量删除成功");
        return redirectToUrl(backURL);

    }

    @RequestMapping(value = "{group}/batch/append", method = RequestMethod.GET)
    public String showBatchAppendGroupRelationForm(@PathVariable("group") Group group) {

        if(group.getType() == GroupType.user) {
            return getViewPrefix() + "/relation/appendUserGroupRelation";
        }

        if(group.getType() == GroupType.organization) {
            return getViewPrefix() + "/relation/appendOrganizationGroupRelation";
        }

        throw new RuntimeException("group type error");
    }

    @RequestMapping(value = "{group}/batch/append", method = RequestMethod.POST)
    public String batchAppendGroupRelation(
            @PathVariable("group") Group group,
            @RequestParam("ids") Long[] ids,
            //只有用户组 有fromIds endIds
            @RequestParam(value = "startIds", required = false) Long[] startIds,
            @RequestParam(value = "endIds", required = false) Long[] endIds,
            @RequestParam(value = Constants.BACK_URL, required = false) String backURL,
            RedirectAttributes redirectAttributes) {

        if(group.getType() == GroupType.organization) {
            groupRelationService.appendRelation(group.getId(), ids);
        }

        if(group.getType() == GroupType.user) {
            groupRelationService.appendRelation(group.getId(), ids, startIds, endIds);
        }



        redirectAttributes.addFlashAttribute(Constants.MESSAGE, "批量添加成功");

        return redirectToUrl(backURL);
    }





}
