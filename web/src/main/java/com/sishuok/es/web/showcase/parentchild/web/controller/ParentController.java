/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.es.web.showcase.parentchild.web.controller;

import com.sishuok.es.Constants;
import com.sishuok.es.common.entity.enums.BooleanEnum;
import com.sishuok.es.common.entity.search.Searchable;
import com.sishuok.es.common.web.bind.annotation.PageableDefaults;
import com.sishuok.es.web.showcase.parentchild.entity.Child;
import com.sishuok.es.web.showcase.parentchild.entity.Parent;
import com.sishuok.es.web.showcase.parentchild.entity.ParentChildType;
import com.sishuok.es.web.showcase.parentchild.service.ParentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.Date;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 13-1-28 下午4:29
 * <p>Version: 1.0
 */
@Controller
@RequestMapping(value = "/showcase/parentchild/parent")
public class ParentController {

    @Autowired
    private ParentService parentService;

    public void setCommonData(Model model) {
        model.addAttribute("booleanList", BooleanEnum.values());
        model.addAttribute("typeList", ParentChildType.values());
    }

    @RequestMapping(method = RequestMethod.GET)
    @PageableDefaults(value = 20, sort = "id=desc")
    public String list(Searchable searchable, Model model) {
        setCommonData(model);
        model.addAttribute("parents", parentService.findAll(searchable));
        return "showcase/parentchild/parent/list";
    }

    @RequestMapping(value = "create", method = RequestMethod.GET)
    public String showCreateForm(Model model) {
        setCommonData(model);
        model.addAttribute(Constants.OP_NAME, "新增");
        if(!model.containsAttribute("parent")) {
            model.addAttribute("parent", new Parent());
        }
        return "showcase/parentchild/parent/editForm";
    }



    @RequestMapping(value = "create", method = RequestMethod.POST)
    public String create( Model model, @Valid Parent parent, BindingResult result, RedirectAttributes redirectAttributes) {
        if (hasError(parent, result)) {
            return showCreateForm(model);
        }
        parentService.save(parent);
        redirectAttributes.addFlashAttribute(Constants.MESSAGE, "创建父成功");
        return "redirect:/showcase/parentchild/parent";
    }

    @RequestMapping(value = "update/{id}", method = RequestMethod.GET)
    public String showUpdateForm(@PathVariable("id") Parent parent, Model model) {
        setCommonData(model);
        model.addAttribute(Constants.OP_NAME, "修改");
        model.addAttribute("parent", parent);
        return "showcase/parentchild/parent/editForm";
    }

    @RequestMapping(value = "update/{id}", method = RequestMethod.POST)
    public String update(Model model, @Valid @ModelAttribute("parent") Parent parent, BindingResult result, RedirectAttributes redirectAttributes) {
        if (hasError(parent, result)) {
            return showUpdateForm(parent, model);
        }
        parentService.update(parent);
        redirectAttributes.addFlashAttribute(Constants.MESSAGE, "修改父成功");
        return "redirect:/showcase/parentchild/parent";
    }

    @RequestMapping(value = "delete/{id}", method = RequestMethod.GET)
    public String showDeleteForm(@PathVariable("id") Parent parent, Model model) {
        setCommonData(model);
        model.addAttribute(Constants.OP_NAME, "删除");
        model.addAttribute("parent", parent);
        return "showcase/parentchild/parent/editForm";
    }

    @RequestMapping(value = "delete/{id}", method = RequestMethod.POST)
    public String delete(@ModelAttribute("parent") Parent parent, RedirectAttributes redirectAttributes) {
        parentService.delete(parent);
        redirectAttributes.addFlashAttribute(Constants.MESSAGE, "删除父成功");
        return "redirect:/showcase/parentchild/parent";
    }

    @RequestMapping(value = "batch/delete")
    public String deleteInBatch(@RequestParam(value = "ids", required = false) Long[] ids, RedirectAttributes redirectAttributes) {
        parentService.delete(ids);
        redirectAttributes.addFlashAttribute(Constants.MESSAGE, "批量删除父成功");
        return "redirect:/showcase/parentchild/parent";
    }

    /**
     * 验证失败返回true
     * @param parent
     * @param result
     * @return
     */
    private boolean hasError(Parent parent, BindingResult result) {
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


}
