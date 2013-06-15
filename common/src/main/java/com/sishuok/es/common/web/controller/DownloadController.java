/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.es.common.web.controller;

import com.sishuok.es.common.Constants;
import com.sishuok.es.common.web.upload.FileUploadUtils;
import com.sishuok.es.common.web.utils.DownloadUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLDecoder;

/**
 * 文件上传/下载
 * <p>User: Zhang Kaitao
 * <p>Date: 13-2-11 上午8:46
 * <p>Version: 1.0
 */
@Controller
public class DownloadController {

    /**
     * 下载内容前边显示的前缀
     */
    private String prefixFilename = "[es脚手架]";


    @RequestMapping(value = "/download")
    public String download(
            HttpServletRequest request, HttpServletResponse response,
            @RequestParam(value = "filename") String filename) throws Exception {


        filename = filename.replace("/", "\\");

        if (StringUtils.isEmpty(filename) || filename.contains("\\.\\.")) {
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().write("您下载的文件不存在！");
            return null;
        }
        filename = URLDecoder.decode(filename, Constants.ENCODING);

        String filePath = FileUploadUtils.extractUploadDir(request) + "/" + filename;

        DownloadUtils.download(request, response, filePath, prefixFilename + filename);

        return null;
    }




}
