/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.es.web.showcase.upload.web.controller;

import com.sishuok.es.common.web.upload.FileUploadUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.String;
import java.net.URLDecoder;
import java.net.URLEncoder;

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


        if (StringUtils.isEmpty(filename) || filename.contains("\\.\\.")) {
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().write("您下载的文件不存在！");
            return null;
        }


        filename = URLDecoder.decode(filename, "UTF-8");


        String filePath = FileUploadUtils.extractUploadDir(request) + "/" + filename;
        File file = new File(filePath);
        if (!file.exists() || !file.canRead()) {
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().write("您下载的文件不存在！");
            return null;
        }

        String userAgent = request.getHeader("User-Agent");
        boolean isIE = (userAgent != null) && (userAgent.toLowerCase().indexOf("msie") != -1);


        response.reset();
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "must-revalidate, no-transform");
        response.setDateHeader("Expires", 0L);

        response.setContentType("application/x-download");
        response.setContentLength((int) file.length());

        String displayFilename = filename.substring(filename.lastIndexOf("_") + 1);
        displayFilename = displayFilename.replace(" ", "_");
        displayFilename = prefixFilename + displayFilename;
        if (isIE) {
            displayFilename = URLEncoder.encode(displayFilename, "UTF-8");
            response.setHeader("Content-Disposition", "attachment;filename=\"" + displayFilename + "\"");
        } else {
            displayFilename = new String(displayFilename.getBytes("UTF-8"), "ISO8859-1");
            response.setHeader("Content-Disposition", "attachment;filename=" + displayFilename);
        }
        byte[] buf = new byte[1024];
        int len = 0;
        BufferedInputStream is = null;
        OutputStream os = null;
        try {

            is = new BufferedInputStream(new FileInputStream(file));
            os = response.getOutputStream();
            while ((len = is.read(buf)) != -1)
                os.write(buf, 0, len);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            is.close();
        }

        return null;
    }


}
