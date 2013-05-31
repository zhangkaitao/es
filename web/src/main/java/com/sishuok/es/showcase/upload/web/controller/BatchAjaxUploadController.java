/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.es.showcase.upload.web.controller;

import com.sishuok.es.common.Constants;
import com.sishuok.es.common.web.upload.FileUploadUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * ajax批量文件上传/下载
 * <p>User: Zhang Kaitao
 * <p>Date: 13-2-11 上午8:46
 * <p>Version: 1.0
 */
@Controller
public class BatchAjaxUploadController {


    //最大上传大小 字节为单位
    private long maxSize = FileUploadUtils.DEFAULT_MAX_SIZE;
    //允许的文件内容类型
    private String[] allowedExtension = FileUploadUtils.DEFAULT_ALLOWED_EXTENSION;
    //文件上传下载的父目录
    private String baseDir = FileUploadUtils.getDefaultBaseDir();


    @RequiresPermissions("showcase:upload:create")
    @RequestMapping(value = "ajaxUpload", method = RequestMethod.GET)
    public String showAjaxUploadForm(Model model) {
        model.addAttribute(Constants.OP_NAME, "新增");
        return "showcase/upload/ajax/uploadForm";
    }

}
