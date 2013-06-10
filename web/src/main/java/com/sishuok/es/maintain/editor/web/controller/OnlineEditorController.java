/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.es.maintain.editor.web.controller;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.sishuok.es.common.web.controller.BaseController;
import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileFilter;
import java.util.List;
import java.util.Map;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 13-6-9 下午8:09
 * <p>Version: 1.0
 */
@Controller
@RequestMapping("/admin/maintain/editor")
public class OnlineEditorController extends BaseController {

    private final String CSS_DIRECTORY = "ztree_folder";
    private final String CSS_FILE = "ztree_file";
    private final String ROOT_DIR = "/";
    private final FileFilter DIRECTORY_FILTER = new FileFilter() {
        @Override
        public boolean accept(File file) {
            return file.isDirectory();
        }
    };

    @RequestMapping(value = {"", "main"}, method = RequestMethod.GET)
    public String main() {
        return viewName("main");
    }

    @RequestMapping(value = "tree", method = RequestMethod.GET)
    public String tree(
            HttpServletRequest request, Model model) {

        ServletContext sc = request.getServletContext();
        String rootPath = sc.getRealPath(ROOT_DIR);

        long id = 0L;
        File rootDirectory = new File(rootPath);

        Map<Object, Object> root = Maps.newHashMap();
        root.put("id", id);
        root.put("pId", -1L);
        root.put("name", rootDirectory.getName());
        root.put("path", "");
        root.put("isParent", true);
        root.put("root", true);
        root.put("open", true);
        root.put("iconSkin", CSS_DIRECTORY);

        List<Map> trees = Lists.newArrayList();
        trees.add(root);

        for(File subFile : rootDirectory.listFiles()) {
            if(!subFile.isDirectory()) {
                continue;
            }
            id++;
            Map<Object, Object> sub = Maps.newHashMap();
            sub.put("id", id);
            sub.put("pId", root.get("id"));
            sub.put("name", subFile.getName());
            sub.put("path", StringEscapeUtils.escapeJava(subFile.getAbsolutePath().replace(rootPath, "")));
            sub.put("isParent", subFile.listFiles(DIRECTORY_FILTER).length > 0);
            sub.put("root", false);
            sub.put("open", false);
            sub.put("iconSkin", subFile.isDirectory() ? CSS_DIRECTORY : CSS_FILE);
            trees.add(sub);
        }

         model.addAttribute("trees", trees);

        return viewName("tree");
    }


    @RequestMapping(value = "ajax/load", method = RequestMethod.GET)
    @ResponseBody
    public Object ajaxLoad(
            HttpServletRequest request,
            @RequestParam("id") long parentId,
            @RequestParam("path") String parentPath) {

        ServletContext sc = request.getServletContext();
        String rootPath = sc.getRealPath(ROOT_DIR);

        File parentPathDirectory = new File(rootPath + "/" + parentPath);

        List<Map> trees = Lists.newArrayList();

        long id = parentId;

        for(File subFile : parentPathDirectory.listFiles()) {
            if(!subFile.isDirectory()) {
                continue;
            }
            id++;
            Map<Object, Object> sub = Maps.newHashMap();
            sub.put("id", id);
            sub.put("pId", parentId);
            sub.put("name", subFile.getName());
            sub.put("path", StringEscapeUtils.escapeJava(subFile.getAbsolutePath().replace(rootPath, "")));
            sub.put("isParent", subFile.listFiles(DIRECTORY_FILTER).length > 0);
            sub.put("root", false);
            sub.put("open", false);
            sub.put("iconSkin", subFile.isDirectory() ? CSS_DIRECTORY : CSS_FILE);
            trees.add(sub);
        }
        return trees;
    }


    @RequestMapping(value = "list", method = RequestMethod.GET)
    public String listFile(
            HttpServletRequest request,
            @RequestParam(value = "path", required = false, defaultValue = "") String path,
            Model model) {

        ServletContext sc = request.getServletContext();
        String rootPath = sc.getRealPath(ROOT_DIR);

        File directory = new File(rootPath + "/" + path);

        List<File> files = Lists.newArrayList();

        for(File subFile : directory.listFiles()) {
            files.add(subFile);
        }

        model.addAttribute("files", files);

        return viewName("list");
    }


}
