/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.es.web.showcase.upload.web.controller;

import com.sishuok.es.Constants;
import com.sishuok.es.common.entity.search.Searchable;
import com.sishuok.es.common.utils.ImagesUtils;
import com.sishuok.es.common.utils.LogUtils;
import com.sishuok.es.common.utils.MessageUtils;
import com.sishuok.es.common.web.bind.annotation.PageableDefaults;
import com.sishuok.es.common.web.utils.FileUploadUtils;
import com.sishuok.es.web.showcase.upload.entity.AjaxUploadResponse;
import com.sishuok.es.web.showcase.upload.entity.Upload;
import com.sishuok.es.web.showcase.upload.service.UploadService;
import com.sun.imageio.plugins.common.ImageUtil;
import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * ajax批量文件上传/下载
 * <p>User: Zhang Kaitao
 * <p>Date: 13-2-11 上午8:46
 * <p>Version: 1.0
 */
@Controller
public class BatchAjaxUploadController {


    //最大上传大小 字节为单位 默认10M
    private long maxSize = FileUploadUtils.DEFAULT_MAX_SIZE;
    //允许的文件内容类型
    private String[] allowedContentType = FileUploadUtils.DEFAULT_ALLOWED_CONTENT_TYPE;
    //文件上传下载的父目录
    private String baseDir = FileUploadUtils.DEFAULT_BASE_DIR;


    @RequestMapping(value = "ajaxUpload", method = RequestMethod.GET)
    public String showAjaxUploadForm(Model model) {
        model.addAttribute(Constants.OP_NAME, "新增");
        return "showcase/upload/ajax/uploadForm";
    }

    /**
     * @param request
     * @param files
     * @return
     */
    @RequestMapping(value = "ajaxUpload", method = RequestMethod.POST)
    @ResponseBody
    public AjaxUploadResponse ajaxUpload(
            HttpServletRequest request, HttpServletResponse response,
            @RequestParam(value = "files[]", required = false) MultipartFile[] files) {

        //The file upload plugin makes use of an Iframe Transport module for browsers like Microsoft Internet Explorer and Opera, which do not yet support XMLHTTPRequest file uploads.
        response.setContentType("text/plain");

        AjaxUploadResponse ajaxUploadResponse = new AjaxUploadResponse();

        if(ArrayUtils.isEmpty(files)) {
            return ajaxUploadResponse;
        }

        for(MultipartFile file : files) {
            String filename = file.getOriginalFilename();
            long size = file.getSize();

            if(file.getOriginalFilename().length() > 100) {
                ajaxUploadResponse.add(filename, size, MessageUtils.message("upload.filename.exceed.length"));
                continue;
            }
            try {
                String url = FileUploadUtils.upload(request, baseDir, file, allowedContentType, maxSize);
                String deleteURL = request.getContextPath() + "/ajaxUpload/delete?filename=" + URLEncoder.encode(url, "UTF-8");
                if(ImagesUtils.isImage(filename)) {
                    ajaxUploadResponse.add(filename, size, url, url, deleteURL);
                } else {
                    ajaxUploadResponse.add(filename, size, url, deleteURL);
                }
                continue;
            } catch (IOException e) {
                e.printStackTrace();
                LogUtils.error("file upload error", e);
                ajaxUploadResponse.add(filename, size, MessageUtils.message("upload.server.error"));
                continue;
            } catch (FileUploadBase.InvalidContentTypeException e) {
                ajaxUploadResponse.add(filename, size, MessageUtils.message("upload.not.allow.contentType"));
                continue;
            } catch (FileUploadBase.FileSizeLimitExceededException e) {
                ajaxUploadResponse.add(filename, size, MessageUtils.message("upload.exceed.maxSize"));
                continue;
            }
        }

        return ajaxUploadResponse;
    }


    @RequestMapping(value = "ajaxUpload/delete", method = RequestMethod.POST)
    public void ajaxUploadDelete(
            HttpServletRequest request,
            @RequestParam(value = "filename") String filename) throws Exception {

        if (StringUtils.isEmpty(filename) || filename.contains("\\.\\.")) {
            return;
        }
        filename = URLDecoder.decode(filename, "UTF-8");

        String filePath = FileUploadUtils.extractUploadDir(request) + "/" + filename;

        File file = new File(filePath);
        file.deleteOnExit();

    }
}
