/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.es.web.showcase.upload.web.controller;

import com.sishuok.es.Constants;
import com.sishuok.es.common.entity.search.Searchable;
import com.sishuok.es.common.utils.LogUtils;
import com.sishuok.es.common.utils.MessageUtils;
import com.sishuok.es.common.web.bind.annotation.PageableDefaults;
import com.sishuok.es.common.web.utils.FileUploadUtils;
import com.sishuok.es.web.showcase.upload.entity.Upload;
import com.sishuok.es.web.showcase.upload.service.UploadService;
import org.apache.commons.fileupload.FileUploadBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;

/**
 * 文件上传/下载
 * <p>User: Zhang Kaitao
 * <p>Date: 13-2-11 上午8:46
 * <p>Version: 1.0
 */
@Controller
@RequestMapping(value = "showcase/upload")
public class UploadController {

    @Autowired
    private UploadService uploadService;

    //最大上传大小 字节为单位 默认10M
    private long maxSize = FileUploadUtils.DEFAULT_MAX_SIZE;
    //允许的文件内容类型
    private String[] allowedContentType = FileUploadUtils.DEFAULT_ALLOWED_CONTENT_TYPE;
    //文件上传下载的父目录
    private String baseDir = FileUploadUtils.DEFAULT_BASE_DIR;



    @RequestMapping(method = RequestMethod.GET)
    @PageableDefaults(value = 20, sort = "id=desc")
    public String list(Searchable searchable, Model model) {
        model.addAttribute("uploads", uploadService.findAll(searchable));
        return "showcase/upload/list";
    }

    @RequestMapping(value = "create", method = RequestMethod.GET)
    public String showCreateForm(Model model) {
        model.addAttribute(Constants.OP_NAME, "新增");
        if(!model.containsAttribute("upload")) {
            model.addAttribute("upload", new Upload());
        }
        return "showcase/upload/editForm";
    }



    @RequestMapping(value = "create", method = RequestMethod.POST)
    public String create(Model model, HttpServletRequest request, @RequestParam(value = "file", required = false) MultipartFile file,
                         @Valid Upload upload, BindingResult result, RedirectAttributes redirectAttributes) {

        if(!file.isEmpty()) {
            upload.setSrc(FileUploadUtils.upload(request, file, result));
        }
        if(result.hasErrors()) {
            return showCreateForm(model);
        }

        uploadService.save(upload);
        redirectAttributes.addFlashAttribute(Constants.MESSAGE, "创建文件成功");
        return "redirect:/showcase/upload";
    }

    @RequestMapping(value = "update/{id}", method = RequestMethod.GET)
    public String showUpdateForm(@PathVariable("id") Upload upload, Model model) {
        model.addAttribute(Constants.OP_NAME, "修改");
        model.addAttribute("upload", upload);
        return "showcase/upload/editForm";
    }

    @RequestMapping(value = "update/{id}", method = RequestMethod.POST)
    public String update(
            Model model, HttpServletRequest request, @RequestParam(value = "file", required = false) MultipartFile file,
            @Valid @ModelAttribute("upload") Upload upload, BindingResult result, RedirectAttributes redirectAttributes) {


        if(!file.isEmpty()) {
            upload.setSrc(FileUploadUtils.upload(request, file, result));
        }
        if(result.hasErrors()) {
            return showUpdateForm(upload, model);
        }

        uploadService.update(upload);
        redirectAttributes.addFlashAttribute(Constants.MESSAGE, "修改文件成功");
        return "redirect:/showcase/upload";
    }

    @RequestMapping(value = "delete/{id}", method = RequestMethod.GET)
    public String showDeleteForm(@PathVariable("id") Upload upload, Model model) {
        model.addAttribute(Constants.OP_NAME, "删除");
        model.addAttribute("upload", upload);
        return "showcase/upload/editForm";
    }

    @RequestMapping(value = "delete/{id}", method = RequestMethod.POST)
    public String delete(
            HttpServletRequest request, @ModelAttribute("upload") Upload upload, RedirectAttributes redirectAttributes)
            throws IOException {
        uploadService.delete(upload);
        FileUploadUtils.delete(request, upload.getSrc());
        redirectAttributes.addFlashAttribute(Constants.MESSAGE, "删除文件成功");
        return "redirect:/showcase/upload";
    }

    @RequestMapping(value = "batch/delete")
    public String deleteInBatch(@RequestParam(value = "ids", required = false) Long[] ids, RedirectAttributes redirectAttributes) {
        uploadService.delete(ids);
        redirectAttributes.addFlashAttribute(Constants.MESSAGE, "批量删除文件成功");
        return "redirect:/showcase/upload";
    }

}
