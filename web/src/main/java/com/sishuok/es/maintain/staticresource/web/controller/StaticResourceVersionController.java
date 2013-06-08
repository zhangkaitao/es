/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.es.maintain.staticresource.web.controller;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.sishuok.es.common.web.controller.BaseController;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * 静态资源版本控制
 * 要求
 *  所有需要版本控制的静态资源 以.jspf为后缀 放到webapp/WEB-INF/jsp/common下
 * 这样即可扫描这些文件实施版本控制
 *
 * <p>User: Zhang Kaitao
 * <p>Date: 13-6-7 下午2:26
 * <p>Version: 1.0
 */
@Controller
@RequestMapping("/admin/maintain/staticResource")
public class StaticResourceVersionController extends BaseController {

    private final String versionedResourcePath ="/WEB-INF/jsp/common";
    private final Pattern scriptPattern =
            Pattern.compile("(.*<script.* src=[\'\"])(.*?)(\\??)(\\d*)([\'\"].*>.*)", Pattern.CASE_INSENSITIVE);
    private final Pattern linkPattern =
            Pattern.compile("(.*<link.* href=[\'\"])(.*?)(\\??)(\\d*)([\'\"].*>.*)", Pattern.CASE_INSENSITIVE);


    @RequestMapping()
    public String list(HttpServletRequest request, Model model) throws IOException {

        ServletContext sc = request.getServletContext();
        String realPath = sc.getRealPath(versionedResourcePath);

        model.addAttribute("resources", findStaticResources(realPath));

        return viewName("list");
    }

    @RequestMapping(value = "incVersion", method = RequestMethod.POST)
    @ResponseBody
    public String incVersion(
            HttpServletRequest request,
            @RequestParam("fileName") String fileName,
            @RequestParam("content") String content,
            @RequestParam("newVersion") String newVersion
    ) throws IOException {


        ServletContext sc = request.getServletContext();
        String realPath = sc.getRealPath(versionedResourcePath);

        return replaceStaticResourceContent(realPath + fileName, content, newVersion);

    }

    @RequestMapping(value = "batchIncVersion", method = RequestMethod.POST)
    @ResponseBody
    public String batchIncVersion(
            HttpServletRequest request,
            @RequestParam("fileNames[]") String[] fileNames,
            @RequestParam("contents[]") String[] contents,
            @RequestParam("newVersions[]") String[] newVersions,
            RedirectAttributes redirectAttributes
    ) throws IOException {

        ServletContext sc = request.getServletContext();
        String realPath = sc.getRealPath(versionedResourcePath);

        for(int i = 0, l = fileNames.length; i < l; i++) {
            replaceStaticResourceContent(realPath + fileNames[i], contents[i], newVersions[i]);
        }

        return "操作成功";

    }

    @RequestMapping(value = "clearVersion", method = RequestMethod.POST)
    @ResponseBody
    public String clearVersion(
            HttpServletRequest request,
            @RequestParam("fileNames[]") String[] fileNames,
            @RequestParam("contents[]") String[] contents
    ) throws IOException {

        ServletContext sc = request.getServletContext();
        String realPath = sc.getRealPath(versionedResourcePath);

        for(int i = 0, l = fileNames.length; i < l; i++) {
            replaceStaticResourceContent(realPath + fileNames[i], contents[i], null);
        }

        return "操作成功";

    }


    private String replaceStaticResourceContent(String fileRealPath, String content, String newVersion) throws IOException {

        content = StringEscapeUtils.unescapeXml(content);
        if(newVersion != null && newVersion.equals("1")) {
            newVersion = "?" + newVersion;
        }

        File file = new File(fileRealPath);

        List<String> contents = FileUtils.readLines(file);

        for(int i = 0, l = contents.size(); i < l; i++) {
            String fileContent = contents.get(i);
            if(content.equals(fileContent)) {
                Matcher matcher = scriptPattern.matcher(content);
                if(!matcher.matches()) {
                    matcher = linkPattern.matcher(content);
                }
                if(newVersion == null) { //删除版本
                    content = matcher.replaceAll("$1$2$5");
                } else {
                    content = matcher.replaceAll("$1$2$3" + newVersion + "$5");
                }
                contents.set(i, content);
                break;
            }
        }
        FileUtils.writeLines(file, contents);

        return content;
    }


    private Map<String, List<StaticResource>> findStaticResources(String realPath) throws IOException {

        final Map<String, List<StaticResource>> resources = Maps.newTreeMap();

        final int realPathLength = realPath.length();

        Collection<File> files = FileUtils.listFiles(new File(realPath), new String[]{"jspf"}, true);

        for (File file : files) {

            String fileName = file.getAbsolutePath().substring(realPathLength);
            List<String> contents = FileUtils.readLines(file);

            List<StaticResource> resourceList = resources.get(fileName);
            if (resourceList == null) {
                resourceList = Lists.newArrayList();
            }

            for (String content : contents) {
                if (!StringUtils.isEmpty(content)) {
                    StaticResource resource = extractResource(fileName, content);
                    if (resource != null) {
                        resourceList.add(resource);
                    }
                }
            }

            if (CollectionUtils.isNotEmpty(resourceList)) {
                resources.put(fileName, resourceList);
            }
        }

        return resources;
    }

    private StaticResource extractResource(String fileName, String content) {

        Matcher matcher = scriptPattern.matcher(content);
        if(!matcher.matches()) {
            matcher = linkPattern.matcher(content);
        }

        if(!matcher.matches()) {
            return null;
        }

        String url = matcher.group(2);
        String version = "";
        version = matcher.group(4);

        StaticResource resource = new StaticResource(fileName, content);
        resource.setUrl(url);
        resource.setVersion(version);

        return resource;
    }


    public static class StaticResource {
        private String fileName;
        private String content;
        private String url;
        private String version;

        private StaticResource(String fileName, String content) {
            this.fileName = fileName;
            this.content = content;
        }


        public String getFileName() {
            return fileName;
        }

        public void setFileName(String fileName) {
            this.fileName = fileName;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        @Override
        public String toString() {
            return "StaticResource{" +
                    "fileName='" + fileName + '\'' +
                    ", content='" + content + '\'' +
                    ", url='" + url + '\'' +
                    ", version=" + version +
                    '}';
        }
    }

}
