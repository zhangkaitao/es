/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.es.web.showcase.move.web.controller;

import com.sishuok.es.Constants;
import com.sishuok.es.common.entity.enums.BooleanEnum;
import com.sishuok.es.common.entity.search.Searchable;
import com.sishuok.es.common.entity.validate.group.Create;
import com.sishuok.es.common.utils.MessageUtils;
import com.sishuok.es.common.web.bind.annotation.PageableDefaults;
import com.sishuok.es.common.web.validate.AjaxResponse;
import com.sishuok.es.web.showcase.move.entity.Move;
import com.sishuok.es.web.showcase.move.service.MoveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 13-1-28 下午4:29
 * <p>Version: 1.0
 */
@Controller
@RequestMapping(value = "/showcase/move")
public class MoveController {

    @Autowired
    private MoveService moveService;

    public void setCommonData(Model model) {
        model.addAttribute("booleanList", BooleanEnum.values());
    }

    @RequestMapping(method = RequestMethod.GET)
    @PageableDefaults(value = 20, sort = "weight=desc")
    public String list(Searchable searchable, Model model) {
        model.addAttribute("moves", moveService.findAll(searchable));
        return "showcase/move/list";
    }

    @RequestMapping(value = "create", method = RequestMethod.GET)
    public String showCreateForm(Model model) {
        setCommonData(model);
        model.addAttribute(Constants.OP_NAME, "新增");
        if(!model.containsAttribute("sample")) {
            model.addAttribute("move", new Move());
        }
        return "showcase/move/editForm";
    }



    @RequestMapping(value = "create", method = RequestMethod.POST)
    public String create(Model model,
                         //表示只验证Create.class分组
                         @Validated(value = Create.class) @Valid Move move,
                         BindingResult result, RedirectAttributes redirectAttributes) {
        if (hasError(move, result)) {
            return showCreateForm(model);
        }

        moveService.save(move);
        redirectAttributes.addFlashAttribute(Constants.MESSAGE, "创建可移动成功");
        return "redirect:/showcase/sample";
    }

    @RequestMapping(value = "update/{id}", method = RequestMethod.GET)
    public String showUpdateForm(@PathVariable("id") Move move, Model model) {
        setCommonData(model);
        model.addAttribute(Constants.OP_NAME, "修改");
        model.addAttribute("move", move);
        return "showcase/move/editForm";
    }

    @RequestMapping(value = "update/{id}", method = RequestMethod.POST)
    public String update(Model model, @Valid @ModelAttribute("move") Move move, BindingResult result, RedirectAttributes redirectAttributes) {
        if (hasError(move, result)) {
            return showUpdateForm(move, model);
        }
        moveService.update(move);
        redirectAttributes.addFlashAttribute(Constants.MESSAGE, "修改可移动成功");
        return "redirect:/showcase/sample";
    }

    @RequestMapping(value = "delete/{id}", method = RequestMethod.GET)
    public String showDeleteForm(@PathVariable("id") Move move, Model model) {
        setCommonData(model);
        model.addAttribute(Constants.OP_NAME, "删除");
        model.addAttribute("move", move);
        return "showcase/move/editForm";
    }

    @RequestMapping(value = "delete/{id}", method = RequestMethod.POST)
    public String delete(@ModelAttribute("move") Move move, RedirectAttributes redirectAttributes) {
        moveService.delete(move);
        redirectAttributes.addFlashAttribute(Constants.MESSAGE, "删除可移动成功");
        return "redirect:/showcase/sample";
    }

    @RequestMapping(value = "batch/delete")
    public String deleteInBatch(@RequestParam(value = "ids", required = false) Long[] ids, RedirectAttributes redirectAttributes) {
        moveService.delete(ids);
        redirectAttributes.addFlashAttribute(Constants.MESSAGE, "批量删除可移动成功");
        return "redirect:/showcase/sample";
    }

    @RequestMapping(value = "up/{fromId}/{toId}")
    @ResponseBody
    public AjaxResponse up(@PathVariable("fromId") Long fromId, @PathVariable("toId") Long toId) {
        AjaxResponse ajaxResponse = new AjaxResponse("移动位置成功");
        try {
            moveService.up(fromId, toId);
        } catch (IllegalStateException e) {
            ajaxResponse.setSuccess(Boolean.FALSE);
            ajaxResponse.setMessage(MessageUtils.message("move.not.enough"));
        }
        return ajaxResponse;
    }

    @RequestMapping(value = "down/{fromId}/{toId}")
    @ResponseBody
    public AjaxResponse down(@PathVariable("fromId") Long fromId, @PathVariable("toId") Long toId) {
        AjaxResponse ajaxResponse = new AjaxResponse("移动位置成功");
                try {
                    moveService.down(fromId, toId);
                } catch (IllegalStateException e) {
                    ajaxResponse.setSuccess(Boolean.FALSE);
                    ajaxResponse.setMessage(MessageUtils.message("move.not.enough"));
                }
                return ajaxResponse;
    }

    @RequestMapping(value = "reweight")
    @ResponseBody
    public AjaxResponse reweight() {
        AjaxResponse ajaxResponse = new AjaxResponse("优化权重成功！");
        try {
            moveService.reweight();
        } catch (IllegalStateException e) {
            ajaxResponse.setSuccess(Boolean.FALSE);
            ajaxResponse.setMessage("优化权重失败了！");
        }
        return ajaxResponse;
    }

    /**
     * 验证失败返回true
     *
     * @param move
     * @param result
     * @return
     */
    private boolean hasError(Move move, BindingResult result) {
        return result.hasErrors();
    }


}
