/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.es.common.web.utils;


import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 13-6-15 下午8:00
 * <p>Version: 1.0
 */
public class DownloadUtils {

    public static void download(HttpServletRequest request, HttpServletResponse response, String filePath) throws IOException {
        download(request, response, filePath, "");
    }

    public static void download(HttpServletRequest request, HttpServletResponse response, String filePath, String displayName) throws IOException {
        File file = new File(filePath);

        if(StringUtils.isEmpty(displayName)) {
            displayName = file.getName();
        }

        if (!file.exists() || !file.canRead()) {
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().write("您下载的文件不存在！");
            return;
        }

        String userAgent = request.getHeader("User-Agent");
        boolean isIE = (userAgent != null) && (userAgent.toLowerCase().indexOf("msie") != -1);

        response.reset();
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "must-revalidate, no-transform");
        response.setDateHeader("Expires", 0L);

        response.setContentType("application/x-download");
        response.setContentLength((int) file.length());

        String displayFilename = displayName.substring(displayName.lastIndexOf("_") + 1);
        displayFilename = displayFilename.replace(" ", "_");
        if (isIE) {
            displayFilename = URLEncoder.encode(displayFilename, "UTF-8");
            response.setHeader("Content-Disposition", "attachment;filename=\"" + displayFilename + "\"");
        } else {
            displayFilename = new String(displayFilename.getBytes("UTF-8"), "ISO8859-1");
            response.setHeader("Content-Disposition", "attachment;filename=" + displayFilename);
        }
        BufferedInputStream is = null;
        OutputStream os = null;
        try {

            os = response.getOutputStream();
            is = new BufferedInputStream(new FileInputStream(file));
            IOUtils.copy(is, os);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(is);
        }
    }


    public static void download(HttpServletRequest request, HttpServletResponse response, String displayName, byte[] bytes) throws IOException {


        if (ArrayUtils.isEmpty(bytes)) {
            response.setContentType("text/html;charset=utf-8");
            response.setCharacterEncoding("utf-8");
            response.getWriter().write("您下载的文件不存在！");
            return;
        }

        String userAgent = request.getHeader("User-Agent");
        boolean isIE = (userAgent != null) && (userAgent.toLowerCase().indexOf("msie") != -1);

        response.reset();
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "must-revalidate, no-transform");
        response.setDateHeader("Expires", 0L);

        response.setContentType("application/x-download");
        response.setContentLength((int) bytes.length);

        String displayFilename = displayName.substring(displayName.lastIndexOf("_") + 1);
        displayFilename = displayFilename.replace(" ", "_");
        if (isIE) {
            displayFilename = URLEncoder.encode(displayFilename, "UTF-8");
            response.setHeader("Content-Disposition", "attachment;filename=\"" + displayFilename + "\"");
        } else {
            displayFilename = new String(displayFilename.getBytes("UTF-8"), "ISO8859-1");
            response.setHeader("Content-Disposition", "attachment;filename=" + displayFilename);
        }
        BufferedInputStream is = null;
        OutputStream os = null;
        try {

            os = response.getOutputStream();
            is = new BufferedInputStream(new ByteArrayInputStream(bytes));
            IOUtils.copy(is, os);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(is);
        }
    }
}
