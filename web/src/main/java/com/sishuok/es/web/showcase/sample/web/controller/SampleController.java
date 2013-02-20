/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.es.web.showcase.sample.web.controller;

import com.sishuok.es.Constants;
import com.sishuok.es.common.entity.enums.BooleanEnum;
import com.sishuok.es.common.entity.search.Searchable;
import com.sishuok.es.common.web.bind.annotation.PageableDefaults;
import com.sishuok.es.common.web.validate.ValidateResponse;
import com.sishuok.es.web.showcase.sample.entity.Sample;
import com.sishuok.es.web.showcase.sample.entity.Sex;
import com.sishuok.es.web.showcase.sample.service.SampleService;
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
@RequestMapping(value = "/showcase/sample")
public class SampleController {

    @Autowired
    private SampleService sampleService;

    public void setCommonData(Model model) {
        model.addAttribute("sexList", Sex.values());
        model.addAttribute("booleanList", BooleanEnum.values());
    }

    @RequestMapping(method = RequestMethod.GET)
    @PageableDefaults(value = 20, sort = "id=desc")
    public String list(Searchable searchable, Model model) {
        model.addAttribute("samples", sampleService.findAll(searchable));
        return "showcase/sample/list";
    }

    @RequestMapping(value = "create", method = RequestMethod.GET)
    public String showCreateForm(Model model) {
        setCommonData(model);
        model.addAttribute(Constants.OP_NAME, "新增");
        if(!model.containsAttribute("sample")) {
            model.addAttribute("sample", new Sample());
        }
        return "showcase/sample/editForm";
    }



    @RequestMapping(value = "create", method = RequestMethod.POST)
    public String create( Model model, @Valid Sample sample, BindingResult result, RedirectAttributes redirectAttributes) {
        if (hasError(sample, result)) {
            return showCreateForm(model);
        }
        sampleService.save(sample);
        redirectAttributes.addFlashAttribute(Constants.MESSAGE, "创建示例成功");
        return "redirect:/showcase/sample";
    }

    @RequestMapping(value = "update/{id}", method = RequestMethod.GET)
    public String showUpdateForm(@PathVariable("id") Sample sample, Model model) {
        setCommonData(model);
        model.addAttribute(Constants.OP_NAME, "修改");
        model.addAttribute("sample", sample);
        return "showcase/sample/editForm";
    }

    @RequestMapping(value = "update/{id}", method = RequestMethod.POST)
    public String update(Model model, @Valid @ModelAttribute("sample") Sample sample, BindingResult result, RedirectAttributes redirectAttributes) {
        if (hasError(sample, result)) {
            return showUpdateForm(sample, model);
        }
        sampleService.update(sample);
        redirectAttributes.addFlashAttribute(Constants.MESSAGE, "修改示例成功");
        return "redirect:/showcase/sample";
    }

    @RequestMapping(value = "delete/{id}", method = RequestMethod.GET)
    public String showDeleteForm(@PathVariable("id") Sample sample, Model model) {
        setCommonData(model);
        model.addAttribute(Constants.OP_NAME, "删除");
        model.addAttribute("sample", sample);
        return "showcase/sample/editForm";
    }

    @RequestMapping(value = "delete/{id}", method = RequestMethod.POST)
    public String delete(@ModelAttribute("sample") Sample sample, RedirectAttributes redirectAttributes) {
        sampleService.delete(sample);
        redirectAttributes.addFlashAttribute(Constants.MESSAGE, "删除示例成功");
        return "redirect:/showcase/sample";
    }

    @RequestMapping(value = "batch/delete")
    public String deleteInBatch(@RequestParam(value = "ids", required = false) Long[] ids, RedirectAttributes redirectAttributes) {
        sampleService.delete(ids);
        redirectAttributes.addFlashAttribute(Constants.MESSAGE, "批量删除示例成功");
        return "redirect:/showcase/sample";
    }

//ajax 删除方式
//    @RequestMapping(value = "batch/delete", method = RequestMethod.POST)
//    @ResponseBody
//    public AjaxResponse deleteInBatch(@RequestParam(value = "ids", required = false) Long[] ids) {
//        AjaxResponse response = new AjaxResponse();
//        sampleService.delete(ids);
//        return response;
//    }

    /**
     * 验证失败返回true
     *
     * @param sample
     * @param result
     * @return
     */
    private boolean hasError(Sample sample, BindingResult result) {
        Assert.notNull(sample);

        //字段错误 前台使用<es:showFieldError commandName="showcase/sample"/> 显示
        if (sample.getBirthday().after(new Date())) {
            //前台字段名（前台使用[name=字段名]取得dom对象） 错误消息键。。
            result.rejectValue("birthday", "sample.birthday.past");
        }

        //全局错误 前台使用<es:showGlobalError commandName="showcase/sample"/> 显示
        if(sample.getName().contains("admin")) {
            result.reject("name.must.not.admin");
        }

        return result.hasErrors();
    }

    /**
     * 验证返回格式
     * 单个：[fieldId, 1|0, msg]
     * 多个：[[fieldId, 1|0, msg],[fieldId, 1|0, msg]]
     *
     * @param fieldId
     * @param fieldValue
     * @return
     */
    @RequestMapping(value = "validate", method = RequestMethod.GET)
    @ResponseBody
    public Object validate(
            @RequestParam("fieldId") String fieldId, @RequestParam("fieldValue") String fieldValue,
            @RequestParam(value = "id", required = false) Long id) {
        ValidateResponse response = ValidateResponse.newInstance();

        if ("name".equals(fieldId)) {
            Sample sample = sampleService.findByName(fieldValue);
            if (sample == null || (sample.getId().equals(id) && sample.getName().equals(fieldValue))) {
                //如果msg 不为空 将弹出提示框
                response.validateSuccess(fieldId, "");
            } else {
                response.validateFail(fieldId, "该名称已被其他人使用");
            }
        }
        return response.result();
    }


}
