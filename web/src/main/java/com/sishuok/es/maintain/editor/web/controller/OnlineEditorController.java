/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.es.maintain.editor.web.controller;

import com.google.common.collect.Lists;
import com.sishuok.es.common.Constants;
import com.sishuok.es.common.utils.LogUtils;
import com.sishuok.es.common.utils.MessageUtils;
import com.sishuok.es.common.web.controller.BaseController;
import com.sishuok.es.common.web.entity.AjaxUploadResponse;
import com.sishuok.es.common.web.upload.FileUploadUtils;
import com.sishuok.es.common.web.upload.exception.FileNameLengthLimitExceededException;
import com.sishuok.es.common.web.upload.exception.InvalidExtensionException;
import com.sishuok.es.common.web.utils.DownloadUtils;
import com.sishuok.es.maintain.editor.web.controller.utils.CompressUtils;
import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Date;
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
@RequiresPermissions("maintain:onlineEditor:*")
public class OnlineEditorController extends BaseController {


    private final String ROOT_DIR = "/";

    private final long MAX_SIZE = 20000000; //20MB
    private final String[] ALLOWED_EXTENSION = new String[] {
            "bmp","gif","jpeg", "jpg", "png",
            "pdf",
            "docx", "doc", "xlsx", "xls", "pptx", "ppt",
            "zip", "rar",
            "jsp", "jspx", "tag", "tld", "xml", "java", "html", "css", "js"
    };

    //允许\ 即多级目录创建
    private final String VALID_FILENAME_PATTERN = "[^\\s:\\*\\?\\\"<>\\|]?(\\x20|[^\\s:\\*\\?\\\"<>\\|])*[^\\s:\\*\\?\\\"<>\\|\\.]?$";


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
            @RequestParam(value = "paths", required = false) String[] excludePaths,
            @RequestParam("path") String parentPath) throws UnsupportedEncodingException {

        parentPath = URLDecoder.decode(parentPath, Constants.ENCODING);

        String rootPath = sc.getRealPath(ROOT_DIR);

        File parentPathDirectory = new File(rootPath + File.separator + parentPath);

        List<Map> trees = Lists.newArrayList();

        long id = parentId;

        for(File subFile : parentPathDirectory.listFiles()) {
            if(!subFile.isDirectory()) {
                continue;
            }
            String path = URLEncoder.encode(subFile.getAbsolutePath().replace(rootPath, ""), Constants.ENCODING);
            if(isExclude(excludePaths, path)) {
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

        File currentDirectory = new File(rootPath + File.separator + path);

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

    @RequestMapping("select")
    public String showSelectForm(
            @RequestParam("paths") String[] excludePaths,
            Model model) throws UnsupportedEncodingException {

        String rootPath = sc.getRealPath(ROOT_DIR);

        List<Map> trees = Lists.newArrayList();

        long id = 0L;
        File rootDirectory = new File(rootPath);

        Map<Object, Object> root= extractFileInfoMap(rootDirectory, rootPath, id, -1);

        trees.add(root);

        for(File subFile : rootDirectory.listFiles()) {
            if(!subFile.isDirectory()) {
                continue;
            }
            String path = URLEncoder.encode(subFile.getAbsolutePath().replace(rootPath, ""), Constants.ENCODING);
            if(isExclude(excludePaths, path)) {
                continue;
            }
            id++;
            trees.add(extractFileInfoMap(subFile, rootPath, id, (Long)root.get("id")));
        }

        model.addAttribute("trees", trees);
        model.addAttribute("excludePaths", excludePaths);

        return viewName("selectForm");
    }

    private boolean isExclude(String[] excludePaths, String path) {
        if (excludePaths == null) {
            return false;
        }
        for (int i = 0; i < excludePaths.length; i++) {
            String excludePath = excludePaths[i];
            if(path.equals(excludePath)) {
                return true;
            }
        }
        return false;
    }

    @RequestMapping(value = "edit", method = RequestMethod.GET)
    public String showEditForm(
            @RequestParam(value = "path", required = false, defaultValue = "") String path,
            Model model,
            RedirectAttributes redirectAttributes) throws IOException {

        String rootPath = sc.getRealPath(ROOT_DIR);


        path = URLDecoder.decode(path, Constants.ENCODING);
        File file = new File(rootPath + File.separator + path);
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
        File file = new File(rootPath + File.separator + path);
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

        File current = new File(rootPath + File.separator + path);
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
            File current = new File(rootPath + File.separator + path);
            FileUtils.deleteQuietly(current);
        }

        redirectAttributes.addFlashAttribute(Constants.MESSAGE, "删除成功！");

        String path = URLDecoder.decode(paths[0], Constants.ENCODING);
        File file = new File(rootPath + File.separator + path);
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

            File parent = new File(rootPath + File.separator + parentPath);
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

            File parent = new File(rootPath + File.separator + parentPath);
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


    @RequestMapping(value = "/upload", method = RequestMethod.GET)
    public String showUploadForm(
            @RequestParam("parentPath") String parentPath,
            Model model,
            RedirectAttributes redirectAttributes) throws UnsupportedEncodingException {

        String rootPath = sc.getRealPath(ROOT_DIR);
        parentPath = URLDecoder.decode(parentPath, Constants.ENCODING);
        File parent = new File(rootPath + File.separator + parentPath);
        if(!parent.exists()) {
            redirectAttributes.addFlashAttribute(Constants.ERROR, parentPath + "目录不存在！");
            redirectAttributes.addAttribute("path", "");
            return redirectToUrl(viewName("list"));
        }

        model.addAttribute("parentPath", parentPath);
        return viewName("uploadForm");
    }


    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ResponseBody
    public AjaxUploadResponse upload(
            HttpServletRequest request, HttpServletResponse response,
            @RequestParam("parentPath") String parentPath,
            @RequestParam("conflict") String conflict,
            @RequestParam(value = "files[]", required = false) MultipartFile[] files) throws UnsupportedEncodingException {

        String rootPath = sc.getRealPath(ROOT_DIR);
        parentPath = URLDecoder.decode(parentPath, Constants.ENCODING);
        File parent = new File(rootPath + File.separator + parentPath);


        //The file upload plugin makes use of an Iframe Transport module for browsers like Microsoft Internet Explorer and Opera, which do not yet support XMLHTTPRequest file uploads.
        response.setContentType("text/plain");

        AjaxUploadResponse ajaxUploadResponse = new AjaxUploadResponse();

        if (ArrayUtils.isEmpty(files)) {
            return ajaxUploadResponse;
        }

        for (MultipartFile file : files) {
            String filename = file.getOriginalFilename();
            long size = file.getSize();
            try {
                File current = new File(parent, filename);
                if(current.exists() && "ignore".equals(conflict)) {
                    ajaxUploadResponse.add(filename, size, MessageUtils.message("upload.conflict.error"));
                    continue;
                }
                String url = FileUploadUtils.upload(request, parentPath, file, ALLOWED_EXTENSION, MAX_SIZE, false);
                String deleteURL = viewName("/delete") + "?paths=" + URLEncoder.encode(url, Constants.ENCODING);

                ajaxUploadResponse.add(filename, size, url, deleteURL);

                continue;
            } catch (IOException e) {
                LogUtils.logError("file upload error", e);
                ajaxUploadResponse.add(filename, size, MessageUtils.message("upload.server.error"));
                continue;
            } catch (InvalidExtensionException e) {
                ajaxUploadResponse.add(filename, size, MessageUtils.message("upload.not.allow.extension"));
                continue;
            } catch (FileUploadBase.FileSizeLimitExceededException e) {
                ajaxUploadResponse.add(filename, size, MessageUtils.message("upload.exceed.maxSize"));
                continue;
            } catch (FileNameLengthLimitExceededException e) {
                ajaxUploadResponse.add(filename, size, MessageUtils.message("upload.filename.exceed.length"));
                continue;
            }
        }
        return ajaxUploadResponse;
    }

    @RequestMapping("/download")
    public String download(
            HttpServletRequest request, HttpServletResponse response,
            @RequestParam("path") String path) throws Exception {

        String rootPath = sc.getRealPath(ROOT_DIR);
        String filePath = rootPath + File.separator + URLDecoder.decode(path, Constants.ENCODING);
        filePath = filePath.replace("\\", "/");

        DownloadUtils.download(request, response, filePath, "");

        return null;

    }


    //压缩
    @RequestMapping("compress")
    public String compress(
            @RequestParam(value = "parentPath") String parentPath,
            @RequestParam(value = "paths") String[] paths,
            RedirectAttributes redirectAttributes) throws IOException {


        String rootPath = sc.getRealPath(ROOT_DIR);
        parentPath = URLDecoder.decode(parentPath, Constants.ENCODING);

        Date now = new Date();
        String pattern = "yyyyMMddHHmmss";

        String compressPath = parentPath + File.separator + "[系统压缩]" + DateFormatUtils.format(now, pattern) + "-" + System.nanoTime() + ".zip";

        for(int i = 0, l = paths.length; i < l; i++) {
            String path = paths[i];
            path = URLDecoder.decode(path, Constants.ENCODING);
            paths[i] = rootPath + File.separator + path;
        }

        try {
            CompressUtils.zip(rootPath + File.separator + compressPath, paths);
            String msg = "压缩成功，<a href='%s/%s?path=%s' target='_blank' class='btn btn-primary'>点击下载</a>，下载完成后，请手工删除生成的压缩包";
            redirectAttributes.addFlashAttribute(Constants.MESSAGE,
                    String.format(msg,
                            sc.getContextPath(),
                            viewName("download"),
                            URLEncoder.encode(compressPath, Constants.ENCODING)));

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute(Constants.ERROR, e.getMessage());
        }

        redirectAttributes.addAttribute("path", URLEncoder.encode(parentPath, Constants.ENCODING));
        return redirectToUrl(viewName("list"));
    }

    @RequestMapping("uncompress")
    public String uncompress(
            @RequestParam(value = "descPath") String descPath,
            @RequestParam(value = "paths") String[] paths,
            @RequestParam(value = "conflict") String conflict,
            RedirectAttributes redirectAttributes) throws IOException {


        String rootPath = sc.getRealPath(ROOT_DIR);
        descPath = URLDecoder.decode(descPath, Constants.ENCODING);

        for(int i = 0, l = paths.length; i < l; i++) {
            String path = paths[i];
            path = URLDecoder.decode(path, Constants.ENCODING);
            //只保留.zip的
            if(!path.toLowerCase().endsWith(".zip")) {
                continue;
            }
            paths[i] = rootPath + File.separator + path;
        }

        try {

            String descAbsolutePath = rootPath + File.separator + descPath;
            for(String path : paths) {
                CompressUtils.unzip(path, descAbsolutePath, "override".equals(conflict));
            }
            redirectAttributes.addFlashAttribute(Constants.MESSAGE, "解压成功！");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute(Constants.ERROR, e.getMessage());
        }

        redirectAttributes.addAttribute("path", URLEncoder.encode(descPath, Constants.ENCODING));
        return redirectToUrl(viewName("list"));
    }


    @RequestMapping("move")
    public String move(
            @RequestParam(value = "descPath") String descPath,
            @RequestParam(value = "paths") String[] paths,
            @RequestParam(value = "conflict") String conflict,
            RedirectAttributes redirectAttributes) throws IOException {


        String rootPath = sc.getRealPath(ROOT_DIR);
        descPath = URLDecoder.decode(descPath, Constants.ENCODING);

        for(int i = 0, l = paths.length; i < l; i++) {
            String path = paths[i];
            path = URLDecoder.decode(path, Constants.ENCODING);
            paths[i] = (rootPath + File.separator + path).replace("\\", "/");
        }

        try {
            File descPathFile = new File(rootPath + File.separator + descPath);
            for(String path : paths) {
                File sourceFile = new File(path);
                File descFile = new File(descPathFile, sourceFile.getName());
                if(descFile.exists() && "ignore".equals(conflict)) {
                    continue;
                }

                FileUtils.deleteQuietly(descFile);

                if(sourceFile.isDirectory()) {
                    FileUtils.moveDirectoryToDirectory(sourceFile, descPathFile, true);
                } else {
                    FileUtils.moveFileToDirectory(sourceFile, descPathFile, true);
                }

            }
            redirectAttributes.addFlashAttribute(Constants.MESSAGE, "移动成功！");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute(Constants.ERROR, e.getMessage());
        }

        redirectAttributes.addAttribute("path", URLEncoder.encode(descPath, Constants.ENCODING));
        return redirectToUrl(viewName("list"));
    }



    @RequestMapping("copy")
    public String copy(
            @RequestParam(value = "descPath") String descPath,
            @RequestParam(value = "paths") String[] paths,
            @RequestParam(value = "conflict") String conflict,
            RedirectAttributes redirectAttributes) throws IOException {


        String rootPath = sc.getRealPath(ROOT_DIR);
        descPath = URLDecoder.decode(descPath, Constants.ENCODING);

        for(int i = 0, l = paths.length; i < l; i++) {
            String path = paths[i];
            path = URLDecoder.decode(path, Constants.ENCODING);
            paths[i] = (rootPath + File.separator + path).replace("\\", "/");
        }

        try {
            File descPathFile = new File(rootPath + File.separator + descPath);
            for(String path : paths) {
                File sourceFile = new File(path);
                File descFile = new File(descPathFile, sourceFile.getName());
                if(descFile.exists() && "ignore".equals(conflict)) {
                    continue;
                }

                FileUtils.deleteQuietly(descFile);

                if(sourceFile.isDirectory()) {
                    FileUtils.copyDirectoryToDirectory(sourceFile, descPathFile);
                } else {
                    FileUtils.copyFileToDirectory(sourceFile, descPathFile);
                }

            }
            redirectAttributes.addFlashAttribute(Constants.MESSAGE, "复制成功！");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute(Constants.ERROR, e.getMessage());
        }

        redirectAttributes.addAttribute("path", URLEncoder.encode(descPath, Constants.ENCODING));
        return redirectToUrl(viewName("list"));
    }


    private boolean isValidFileName(String fileName) {
        return fileName.matches(VALID_FILENAME_PATTERN);
    }

}
