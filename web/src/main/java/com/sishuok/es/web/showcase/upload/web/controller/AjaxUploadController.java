/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.es.web.showcase.upload.web.controller;

import com.sishuok.es.common.Constants;
import com.sishuok.es.web.showcase.upload.entity.Upload;
import com.sishuok.es.web.showcase.upload.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

/**
 * 文件上传/下载
 * <p>User: Zhang Kaitao
 * <p>Date: 13-2-11 上午8:46
 * <p>Version: 1.0
 */
@Controller
@RequestMapping(value = "showcase/upload/ajax")
public class AjaxUploadController {

    @Autowired
    private UploadService uploadService;

    @RequestMapping(value = "create", method = RequestMethod.GET)
    public String showCreateForm(Model model) {
        model.addAttribute(Constants.OP_NAME, "新增");
        if(!model.containsAttribute("upload")) {
            model.addAttribute("upload", new Upload());
        }
        return "showcase/upload/ajax/editForm";
    }

    @RequestMapping(value = "create", method = RequestMethod.POST)
    public String create(@Valid Upload upload, RedirectAttributes redirectAttributes) {

        uploadService.save(upload);
        redirectAttributes.addFlashAttribute(Constants.MESSAGE, "创建文件成功");
        return "redirect:/showcase/upload";
    }

}
