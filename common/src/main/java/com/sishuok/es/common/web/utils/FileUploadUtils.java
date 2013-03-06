/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.es.common.web.utils;

import com.sishuok.es.common.utils.LogUtils;
import com.sishuok.es.common.utils.security.Coder;
import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Date;

import static org.apache.commons.fileupload.FileUploadBase.FileSizeLimitExceededException;
import static org.apache.commons.fileupload.FileUploadBase.InvalidContentTypeException;

/**
 * 文件上传工具类
 * <p>User: Zhang Kaitao
 * <p>Date: 13-2-12 上午8:32
 * <p>Version: 1.0
 */
public class FileUploadUtils {

    public static final long DEFAULT_MAX_SIZE = 10485760;

    public static final String[] IMAGE_CONTENT_TYPE = {
            "image/bmp", "image/gif", "image/jpeg", "image/png", "image/tiff"
    };
    public static final String[] DEFAULT_ALLOWED_CONTENT_TYPE = {
                //图片
                "image/bmp", "image/gif", "image/jpg", "image/pjpeg", "image/jpeg", "image/png", "image/tiff",
                //word excel powerpoint
                "application/msword", "application/vnd.ms-excel", "application/vnd.ms-powerpoint",
                //压缩文件
                "application/x-gzip", "application/zip", "application/rar",
                //pdf
                "application/pdf",
                "application/octet-stream"
        };
    public static final String DEFAULT_BASE_DIR = "upload";

    private static int counter = 0;


    /**
     * 以默认配置进行文件上传
     * @param request 当前请求
     * @param file 上传的文件
     * @param result 添加出错信息
     * @return
     */
    public static final String upload(HttpServletRequest request, MultipartFile file, BindingResult result) {
        return upload(request, file, result, DEFAULT_ALLOWED_CONTENT_TYPE);
    }


    /**
     * 以默认配置进行文件上传
     * @param request 当前请求
     * @param file 上传的文件
     * @param result 添加出错信息
     * @param allowedContentTypes 允许上传的文件类型
     * @return
     */
    public static final String upload(HttpServletRequest request, MultipartFile file, BindingResult result, String[] allowedContentTypes) {
        if(file.getOriginalFilename().length() > 100) {
            result.reject("upload.filename.exceed.length");
        }
        try {
            return upload(request, DEFAULT_BASE_DIR, file, allowedContentTypes, DEFAULT_MAX_SIZE);
        } catch (IOException e) {
            LogUtils.error("file upload error", e);
            result.reject("upload.server.error");
        } catch (FileUploadBase.InvalidContentTypeException e) {
            if(allowedContentTypes == IMAGE_CONTENT_TYPE) {
                result.reject("upload.not.allow.image.contentType");
            } else {
                result.reject("upload.not.allow.contentType");
            }
        } catch (FileUploadBase.FileSizeLimitExceededException e) {
            result.reject("upload.exceed.maxSize");
        }
        return null;
    }


    /**
     * 文件上传
     * @param request 当前请求 从请求中提取 应用上下文根
     * @param baseDir 相对应用的基目录
     * @param file  上传的文件
     * @param allowedContentType 允许的文件类型 null 表示允许所有
     * @param maxSize  最大上传的大小 -1 表示不限制
     * @return  返回上传成功的文件名
     * @throws InvalidContentTypeException 如果MIME类型不允许
     * @throws FileSizeLimitExceededException  如果超出最大大小
     * @throws IOException 比如读写文件出错时
     */
    public static final String upload(
            HttpServletRequest request, String baseDir, MultipartFile file, String[] allowedContentType, long maxSize)
            throws InvalidContentTypeException, FileSizeLimitExceededException, IOException {

        assertAllowed(file, allowedContentType, maxSize);
        String filename = extractFilename(file, baseDir);

        File desc = getAbsoluteFile(extractUploadDir(request), filename);

        file.transferTo(desc);
        return filename;
    }

    private static final File getAbsoluteFile(String uploadDir, String filename) throws IOException {

        if(uploadDir.endsWith("/")) {
            uploadDir = uploadDir.substring(0, uploadDir.length() - 1);
        }
        if(filename.startsWith("/")) {
            filename = filename.substring(0, uploadDir.length() - 1);
        }

        File desc = new File(uploadDir + "/" + filename);

        if(!desc.getParentFile().exists()) {
            desc.getParentFile().mkdirs();
        }
        if(!desc.exists()) {
            desc.createNewFile();
        }
        return desc;
    }


    public static final String extractFilename(MultipartFile file, String baseDir) throws UnsupportedEncodingException {
        String filename = file.getOriginalFilename();
        int slashIndex = filename.indexOf("/");
        if (slashIndex >= 0) {
            filename = filename.substring(slashIndex + 1);
        }
        filename = baseDir + "/" + datePath()  + "/" + encodingFilename(filename);

        return filename;
    }

    /**
     * 编码文件名,默认格式为：
     * 1、'_'替换为 ' '
     * 2、hex(md5(filename + now nano time + counter++))
     * 3、[2]_原始文件名
     * @param filename
     * @return
     */
    private static final String encodingFilename(String filename) {
        filename = filename.replace("_", " ");
        filename = Coder.encryptMD5(filename + System.nanoTime() + counter++) + "_" + filename;
        return filename;
    }

    /**
     * 日期路径 即年/月/日  如2013/01/03
     * @return
     */
    private static final String datePath() {
        Date now = new Date();
        return DateFormatUtils.format(now, "yyyy/MM/dd");
    }


    /**
     * 是否允许文件上传
     *
     * @param file               上传的文件
     * @param allowedContentType 文件类型  null 表示允许所有
     * @param maxSize            最大大小 字节为单位 -1表示不限制
     * @return
     * @throws InvalidContentTypeException 如果MIME类型不允许
     * @throws FileSizeLimitExceededException  如果超出最大大小
     */
    public static final void assertAllowed(MultipartFile file, String[] allowedContentType, long maxSize)
            throws InvalidContentTypeException, FileSizeLimitExceededException {

        String contentType = file.getContentType();

        long size = file.getSize();

        if (allowedContentType != null && !isAllowedContentType(contentType, allowedContentType)) {
            throw new InvalidContentTypeException(
                    "not allowed upload upload, contentType:" + contentType +
                    ", allowed contentType:" + Arrays.toString(allowedContentType));
        }
        if (maxSize != -1 && size > maxSize) {
            throw new FileSizeLimitExceededException("not allowed upload upload", size, maxSize);
        }
    }

    /**
     * 判断MIME类型是否是允许的MIME类型
     * @param contentType
     * @param allowedContentType
     * @return
     */
    public static final boolean isAllowedContentType(String contentType, String[] allowedContentType) {
        for (String str : allowedContentType) {
            if (str.equalsIgnoreCase(contentType)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 提取上传的根目录 默认是应用的根
     * @param request
     * @return
     */
    public static final String extractUploadDir(HttpServletRequest request) {
        return request.getServletContext().getRealPath(".");
    }


    public static final void delete(HttpServletRequest request, String filename) throws IOException {
        if(StringUtils.isEmpty(filename)) {
            return;
        }
        File desc = getAbsoluteFile(extractUploadDir(request), filename);
        if(desc.exists()) {
            desc.delete();
        }
    }
}
