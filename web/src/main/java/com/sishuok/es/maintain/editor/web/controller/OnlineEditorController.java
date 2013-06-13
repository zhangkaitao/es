/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.es.maintain.editor.web.controller;

import com.google.common.collect.Lists;
import com.sishuok.es.common.Constants;
import com.sishuok.es.common.web.controller.BaseController;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import static com.sishuok.es.maintain.editor.web.controller.utils.OnlineEditorUtils.*;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 13-6-9 下午8:09
 * <p>Version: 1.0
 */
@Controller
@RequestMapping("/admin/maintain/editor")
public class OnlineEditorController extends BaseController {


    private final String ROOT_DIR = "/";

    @Autowired
    private ServletContext sc;
    

    @RequestMapping(value = {"", "main"}, method = RequestMethod.GET)
    public String main() {
        return viewName("main");
    }

    @RequestMapping(value = "tree", method = RequestMethod.GET)
    public String tree(Model model) throws UnsupportedEncodingException {

        String rootPath = sc.getRealPath(ROOT_DIR);

        long id = 0L;
        File rootDirectory = new File(rootPath);

        Map<Object, Object> root= extractFileInfoMap(rootDirectory, rootPath, id, -1);

        List<Map> trees = Lists.newArrayList();
        trees.add(root);

        for(File subFile : rootDirectory.listFiles()) {
            if(!subFile.isDirectory()) {
                continue;
            }
            id++;
            trees.add(extractFileInfoMap(subFile, rootPath, id, (Long)root.get("id")));
        }

         model.addAttribute("trees", trees);

        return viewName("tree");
    }


    @RequestMapping(value = "ajax/load", method = RequestMethod.GET)
    @ResponseBody
    public Object ajaxLoad(
            @RequestParam("id") long parentId,
            @RequestParam("path") String parentPath) throws UnsupportedEncodingException {

        parentPath = URLDecoder.decode(parentPath, Constants.ENCODING);

        String rootPath = sc.getRealPath(ROOT_DIR);

        File parentPathDirectory = new File(rootPath + "/" + parentPath);

        List<Map> trees = Lists.newArrayList();

        long id = parentId;

        for(File subFile : parentPathDirectory.listFiles()) {
            if(!subFile.isDirectory()) {
                continue;
            }
            id++;
            trees.add(extractFileInfoMap(subFile, rootPath, id, parentId));
        }
        return trees;
    }


    @RequestMapping(value = "list", method = RequestMethod.GET)
    public String listFile(
            @RequestParam(value = "path", required = false, defaultValue = "") String path,
            Pageable pageable,
            Model model) throws UnsupportedEncodingException {

        path = URLDecoder.decode(path, Constants.ENCODING);

        Sort sort = pageable.getSort();

        String rootPath = sc.getRealPath(ROOT_DIR);

        File currentDirectory = new File(rootPath + "/" + path);

        Map<Object, Object> current = extractFileInfoMap(currentDirectory, rootPath);
        current.put("name", currentDirectory.getName());

        Map<Object, Object> parent = null;
        if(hasParent(currentDirectory, rootPath)) {
            File parentDirectory = currentDirectory.getParentFile();
            parent = extractFileInfoMap(parentDirectory, rootPath);
            parent.put("name", parentDirectory.getName());
        }


        List<Map<Object, Object>> files = Lists.newArrayList();
        for(File subFile : currentDirectory.listFiles()) {
            files.add(extractFileInfoMap(subFile, rootPath));
        }

        sort(files, sort);

        model.addAttribute("current", current);
        model.addAttribute("parent", parent);
        model.addAttribute("files", files);

        return viewName("list");
    }

    @RequestMapping(value = "edit", method = RequestMethod.GET)
    public String showEditForm(
            @RequestParam(value = "path", required = false, defaultValue = "") String path,
            Model model,
            RedirectAttributes redirectAttributes) throws IOException {

        String rootPath = sc.getRealPath(ROOT_DIR);


        path = URLDecoder.decode(path, Constants.ENCODING);
        File file = new File(rootPath + "/" + path);
        String parentPath = file.getParentFile().getAbsolutePath().replace(rootPath, "");

        boolean hasError = false;
        if(file.isDirectory()) {
            hasError = true;
            redirectAttributes.addFlashAttribute(Constants.ERROR, path + "是目录，不能编辑！");
        }

        if(!file.exists()) {
            hasError = true;
            redirectAttributes.addFlashAttribute(Constants.ERROR, path + "文件不存在，不能编辑！");
        }

        if(!file.canWrite()) {
            hasError = true;
            redirectAttributes.addFlashAttribute(Constants.ERROR, path + "文件是只读的，不能编辑，请修改文件权限！");
        }
        if(!file.canRead()) {
            hasError = true;
            redirectAttributes.addFlashAttribute(Constants.ERROR, path + "文件不能读取，不能编辑，请修改文件权限！");
        }


        if(hasError) {
            redirectAttributes.addAttribute("path", parentPath);
            return redirectToUrl(viewName("list"));
        }

        String content = FileUtils.readFileToString(file);
        model.addAttribute("content", content);
        model.addAttribute("path", URLEncoder.encode(path, Constants.ENCODING));
        model.addAttribute("parentPath", URLEncoder.encode(parentPath, Constants.ENCODING));

        return viewName("editForm");
    }



    @RequestMapping(value = "edit", method = RequestMethod.POST)
    public String edit(
            @RequestParam(value = "path") String path,
            @RequestParam(value = "content") String content,
            RedirectAttributes redirectAttributes) throws IOException {

        String rootPath = sc.getRealPath(ROOT_DIR);

        path = URLDecoder.decode(path, Constants.ENCODING);
        File file = new File(rootPath + "/" + path);
        String parentPath = file.getParentFile().getAbsolutePath().replace(rootPath, "");

        FileUtils.write(file, content);

        redirectAttributes.addFlashAttribute(Constants.MESSAGE, "编辑成功！");
        redirectAttributes.addAttribute("path", parentPath);
        return redirectToUrl(viewName("list"));
    }


    ////////////////////////////////////////////////////////////
    //CRUD操作
    ////////////////////////////////////////////////////////////

    @RequestMapping("/rename")
    public String rename(
            @RequestParam(value = "path") String path,
            @RequestParam(value = "newName") String newName,
            RedirectAttributes redirectAttributes) throws IOException {

        String rootPath = sc.getRealPath(ROOT_DIR);
        path = URLDecoder.decode(path, Constants.ENCODING);

        File current = new File(rootPath + "/" + path);
        File parent = current.getParentFile();
        String parentPath = parent.getAbsolutePath().replace(rootPath, "");

        File renameToFile = new File(parent, newName);
        boolean result = current.renameTo(renameToFile);
        if(result == false) {
            redirectAttributes.addFlashAttribute(Constants.ERROR, "名称为[" + newName + "]的文件/目录已经存在");
        } else {
            redirectAttributes.addFlashAttribute(Constants.MESSAGE, "重命名成功");
        }

        redirectAttributes.addAttribute("path", parentPath);
        return redirectToUrl(viewName("list"));
    }


    @RequestMapping("/delete")
    public String delete(
            @RequestParam(value = "paths") String[] paths,
            RedirectAttributes redirectAttributes) throws IOException {

        String rootPath = sc.getRealPath(ROOT_DIR);

        for(String path : paths) {
            path = URLDecoder.decode(path, Constants.ENCODING);
            File current = new File(rootPath + "/" + path);
            FileUtils.deleteQuietly(current);
        }

        redirectAttributes.addFlashAttribute(Constants.MESSAGE, "删除成功！");

        String path = URLDecoder.decode(paths[0], Constants.ENCODING);
        File file = new File(rootPath + "/" + path);
        String parentPath = file.getParentFile().getAbsolutePath().replace(rootPath, "");
        redirectAttributes.addAttribute("path", parentPath);
        return redirectToUrl(viewName("list"));
    }



    @RequestMapping("/create/directory")
    public String createDirectory(
            @RequestParam(value = "parentPath") String parentPath,
            @RequestParam(value = "name") String name,
            RedirectAttributes redirectAttributes) throws IOException {

        //删除最后的/
        name = FilenameUtils.normalizeNoEndSeparator(name);

        if(isValidFileName(name)) {
            String rootPath = sc.getRealPath(ROOT_DIR);
            parentPath = URLDecoder.decode(parentPath, Constants.ENCODING);

            File parent = new File(rootPath + "/" + parentPath);
            File currentDirectory = new File(parent, name);
            boolean result = currentDirectory.mkdirs();
            if(result == false) {
                redirectAttributes.addFlashAttribute(Constants.ERROR, "名称为[" + name + "]的文件/目录已经存在");
            } else {
                redirectAttributes.addFlashAttribute(Constants.MESSAGE, "创建成功！");
            }
        } else {
            redirectAttributes.addFlashAttribute(Constants.ERROR, "名称为[" + name + "]不是合法的文件名，请重新命名");
        }

        redirectAttributes.addAttribute("path", parentPath);
        return redirectToUrl(viewName("list"));
    }


    @RequestMapping("/create/file")
    public String createFile(
            @RequestParam(value = "parentPath") String parentPath,
            @RequestParam(value = "name") String name,
            RedirectAttributes redirectAttributes) throws IOException {

        if(isValidFileName(name)) {
            String rootPath = sc.getRealPath(ROOT_DIR);
            parentPath = URLDecoder.decode(parentPath, Constants.ENCODING);

            File parent = new File(rootPath + "/" + parentPath);
            File currentFile = new File(parent, name);
            currentFile.getParentFile().mkdirs();
            boolean result = currentFile.createNewFile();
            if(result == false) {
                redirectAttributes.addFlashAttribute(Constants.ERROR, "名称为[" + name + "]的文件/目录已经存在");
            } else {
                redirectAttributes.addFlashAttribute(Constants.MESSAGE, "创建成功！");
            }
        } else {
            redirectAttributes.addFlashAttribute(Constants.ERROR, "名称为[" + name + "]不是合法的文件名，请重新命名");
        }

        redirectAttributes.addAttribute("path", parentPath);
        return redirectToUrl(viewName("list"));
    }


    //允许\ 即多级目录创建
    private final String VALID_FILENAME_PATTERN = "[^\\s:\\*\\?\\\"<>\\|]?(\\x20|[^\\s:\\*\\?\\\"<>\\|])*[^\\s:\\*\\?\\\"<>\\|\\.]?$";
    private boolean isValidFileName(String fileName) {
        return fileName.matches(VALID_FILENAME_PATTERN);
    }
//
//    public static void main(String[] args) {
//        final String VALID_FILENAME_PATTERN = "[^\\s:\\*\\?\\\"<>\\|]?(\\x20|[^\\s:\\*\\?\\\"<>\\|])*[^\\s:\\*\\?\\\"<>\\|\\.]?$";
//        System.out.println("a".matches(VALID_FILENAME_PATTERN));
//    }

}
