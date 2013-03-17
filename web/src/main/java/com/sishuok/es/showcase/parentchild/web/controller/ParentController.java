/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.es.showcase.parentchild.web.controller;

import com.sishuok.es.common.Constants;
import com.sishuok.es.common.entity.enums.BooleanEnum;
import com.sishuok.es.common.entity.search.SearchOperator;
import com.sishuok.es.common.entity.search.Searchable;
import com.sishuok.es.common.web.bind.annotation.FormModel;
import com.sishuok.es.common.web.bind.annotation.PageableDefaults;
import com.sishuok.es.common.web.controller.BaseCRUDController;
import com.sishuok.es.showcase.parentchild.entity.Child;
import com.sishuok.es.showcase.parentchild.entity.Parent;
import com.sishuok.es.showcase.parentchild.entity.ParentChildType;
import com.sishuok.es.showcase.parentchild.service.ChildService;
import com.sishuok.es.showcase.parentchild.service.ParentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 13-1-28 下午4:29
 * <p>Version: 1.0
 */
@Controller
@RequestMapping(value = "/showcase/parentchild/parent")
public class ParentController extends BaseCRUDController<Parent, Long> {

    @Autowired
    private ParentService parentService;
    @Autowired
    private ChildService childService;

    @Autowired
    protected ParentController(ParentService parentService) {
        super(parentService);
        this.parentService = parentService;
    }

    @Override
    protected void setCommonData(Model model) {
        model.addAttribute("booleanList", BooleanEnum.values());
        model.addAttribute("typeList", ParentChildType.values());
    }


    @RequestMapping(method = RequestMethod.GET)
    @PageableDefaults(sort = "id=desc")
    public String list(Searchable searchable, Model model) {
        setCommonData(model);
        model.addAttribute("page", baseService.findAll(searchable));
        return getViewPrefix() + "/list";
    }


    @RequestMapping(value = "create/discard", method = RequestMethod.POST)
    @Override
    public String create(Model model, @Valid @ModelAttribute("m") Parent parent, BindingResult result, RedirectAttributes redirectAttributes) {
        throw new RuntimeException("discarded method");
    }

    @RequestMapping(value = "create", method = RequestMethod.POST)
    public String create(
            Model model,
            @Valid @ModelAttribute("parent") Parent parent, BindingResult result,
            @FormModel("childList") List<Child> childList,
            RedirectAttributes redirectAttributes) {

        if (hasError(parent, result)) {
            return showCreateForm(model);
        }
        parentService.save(parent, childList);
        redirectAttributes.addFlashAttribute(Constants.MESSAGE, "创建成功");
        return "redirect:" + redirectUrl(null);
    }

    @Override
    @RequestMapping(value = "update/{id}", method = RequestMethod.GET)
    public String showUpdateForm(@PathVariable("id") Parent parent, Model model) {
        model.addAttribute("childList", childService.findByParent(parent, null).getContent());
        return super.showUpdateForm(parent, model);
    }

    @RequestMapping(value = "update/discard/{id}", method = RequestMethod.POST)
    @Override
    public String update(Model model, @Valid @ModelAttribute("m") Parent parent, BindingResult result, @RequestParam(value = "BackURL", required = false) String backURL, RedirectAttributes redirectAttributes) {
        throw new RuntimeException("discarded method");
    }

    @RequestMapping(value = "update/{id}", method = RequestMethod.POST)
    public String update(
            Model model,
            @Valid @ModelAttribute("parent") Parent parent, BindingResult result,
            @FormModel("childList") List<Child> childList,
            @RequestParam(value = "BackURL", required = false) String backURL,
            RedirectAttributes redirectAttributes) {

        if (hasError(parent, result)) {
            return showUpdateForm(parent, model);
        }
        parentService.update(parent, childList);
        redirectAttributes.addFlashAttribute(Constants.MESSAGE, "修改成功");
        return "redirect:" + redirectUrl(backURL);
    }

    @RequestMapping(value = "delete/{id}", method = RequestMethod.GET)
    @Override
    public String showDeleteForm(@PathVariable("id") Parent parent, Model model) {
        model.addAttribute("childList", childService.findByParent(parent, null).getContent());
        return super.showDeleteForm(parent, model);
    }

    /**
     * 验证失败返回true
     * @param parent
     * @param result
     * @return
     */
    protected boolean hasError(Parent parent, BindingResult result) {
        Assert.notNull(parent);

        //全局错误 前台使用<es:showGlobalError commandName="showcase/parent"/> 显示
        if(parent.getName().contains("admin")) {
            result.reject("name.must.not.admin");
        }

        return result.hasErrors();
    }




    //////////////////////////////////child////////////////////////////////////
    @RequestMapping(value = "child/create", method = RequestMethod.GET)
    public String showChildCreateForm(Model model) {
        setCommonData(model);
        model.addAttribute(Constants.OP_NAME, "新增");
        if(!model.containsAttribute("child")) {
            model.addAttribute("child", new Child());
        }
        return "showcase/parentchild/child/editForm";
    }
    @RequestMapping(value = "child/update/{id}", method = RequestMethod.GET)
    public String showChildUpdateForm(
            Model model,
            @PathVariable("id") Child child,
            @RequestParam(value = "copy", defaultValue = "false") boolean isCopy) {
        setCommonData(model);
        model.addAttribute(Constants.OP_NAME, isCopy ? "复制" : "修改");
        if(!model.containsAttribute("child")) {
            if(child == null) {
                child = new Child();
            }
            if(isCopy) {
                child.setId(null);
            }
            model.addAttribute("child", child);
        }
        return "showcase/parentchild/child/editForm";
    }

    @RequestMapping(value = "child/delete/{id}", method = RequestMethod.POST)
    @ResponseBody
    public Child deleteChild(@PathVariable("id") Child child) {
        childService.delete(child);
        return child;
    }


    @RequestMapping(value = "child/batch/delete")
    @ResponseBody
    public Object deleteChildInBatch(@RequestParam(value = "ids", required = false) Long[] ids) {
        childService.delete(ids);
        return ids;
    }

    @RequestMapping(value = "child/{parentId}", method = RequestMethod.GET)
    @PageableDefaults(value = Integer.MAX_VALUE, sort = "id=desc")
    public String listChild(Model model, @PathVariable("parentId") Long parentId, Searchable searchable) {

        searchable.addSearchFilter("parent.id", SearchOperator.eq, parentId);

        model.addAttribute("page", childService.findAll(searchable));

        return "showcase/parentchild/child/list";
    }

}
