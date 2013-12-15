/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.es.showcase.upload.web.controller;

import com.sishuok.es.common.web.controller.BaseCRUDController;
import com.sishuok.es.common.web.upload.FileUploadUtils;
import com.sishuok.es.showcase.upload.entity.Upload;
import com.sishuok.es.showcase.upload.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * 文件上传/下载
 * <p>User: Zhang Kaitao
 * <p>Date: 13-2-11 上午8:46
 * <p>Version: 1.0
 */
@Controller
@RequestMapping(value = "showcase/upload")
public class UploadController extends BaseCRUDController<Upload, Long> {

    public UploadController() {
        setResourceIdentity("showcase:upload");
    }

    //不再是默认的create，因为下边的create具有多个参数，因此无法覆盖默认的create，因此为了使用该url 我们把父类的url改掉
    @RequestMapping(value = "create/discard", method = RequestMethod.POST)
    @Override
    public String create(Model model, @Valid @ModelAttribute("m") Upload upload, BindingResult result, RedirectAttributes redirectAttributes) {
        throw new RuntimeException("discarded method");
    }

    @RequestMapping(value = "create", method = RequestMethod.POST)
    public String create(
            Model model,
            HttpServletRequest request, @RequestParam(value = "file", required = false) MultipartFile file,
            @Valid @ModelAttribute("m") Upload upload, BindingResult result,
            RedirectAttributes redirectAttributes) {

        if (!file.isEmpty()) {
            upload.setSrc(FileUploadUtils.upload(request, file, result));
        }
        return super.create(model, upload, result, redirectAttributes);
    }


    @RequestMapping(value = "{id}/update/discard", method = RequestMethod.POST)
    @Override
    public String update(Model model, @Valid @ModelAttribute("m") Upload upload, BindingResult result, @RequestParam(value = "BackURL", required = false) String backURL, RedirectAttributes redirectAttributes) {
        throw new RuntimeException("discarded method");
    }

    @RequestMapping(value = "{id}/update", method = RequestMethod.POST)
    public String update(
            Model model,
            HttpServletRequest request, @RequestParam(value = "file", required = false) MultipartFile file,
            @Valid @ModelAttribute("m") Upload upload, BindingResult result,
            @RequestParam(value = "BackURL") String backURL,
            RedirectAttributes redirectAttributes) {

        if (!file.isEmpty()) {
            upload.setSrc(FileUploadUtils.upload(request, file, result));
        }
        return super.update(model, upload, result, backURL, redirectAttributes);
    }


}
