/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.es.showcase.excel.web.controller;

import com.sishuok.es.common.Constants;
import com.sishuok.es.common.entity.search.Searchable;
import com.sishuok.es.common.web.controller.BaseCRUDController;
import com.sishuok.es.showcase.excel.entity.ExcelData;
import com.sishuok.es.showcase.excel.service.ExcelDataService;
import com.sishuok.es.showcase.excel.web.controller.entity.ExcelDataType;
import com.sishuok.es.sys.user.entity.User;
import com.sishuok.es.sys.user.web.bind.annotation.CurrentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import java.io.IOException;
import java.io.InputStream;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 13-1-28 下午4:29
 * <p>Version: 1.0
 */
@Controller
@RequestMapping(value = "/showcase/excel")
public class ExcelController extends BaseCRUDController<ExcelData, Long> {

    private ExcelDataService getExcelDataService() {
        return (ExcelDataService) baseService;
    }

    @Autowired
    private ServletContext servletContext;

    /**
     * 导出excel存储的目录
     */
    private String contextRootPath;

    public ExcelController() {
        setResourceIdentity("showcase:excel");
    }

    @PostConstruct
    public void afterPropertiesSet() {
        contextRootPath = servletContext.getRealPath("/");
    }

    /**
     * 初始化100w数据
     * @return
     */
    @RequestMapping("/init")
    public String initOneMillionData(@CurrentUser User user, RedirectAttributes redirectAttributes) {
        getExcelDataService().initOneMillionData(user);
        redirectAttributes.addFlashAttribute(Constants.MESSAGE, "任务已经提交，正在执行，数据量比较大，执行完成后会在页面右上角的“我的通知”中通知你");
        return redirectToUrl(null);
    }

    @RequestMapping(value = "/import", method = RequestMethod.GET)
    public String showImportExcelForm(@RequestParam("type") ExcelDataType type, Model model) {
        model.addAttribute("type", type);
        return viewName("importForm");
    }

    @RequestMapping(value = "/import", method = RequestMethod.POST)
    public String importExcel(
            @CurrentUser User user,
            @RequestParam("type") ExcelDataType type,
            @RequestParam("file") MultipartFile file,
            Model model,
            RedirectAttributes redirectAttributes) throws IOException {

        if(!canImport(file, model)) {
            return showImportExcelForm(type, model);
        }

        InputStream is = file.getInputStream();

        switch (type) {
            case csv:
                getExcelDataService().importCvs(user, is);
                break;
            case excel2003:
                getExcelDataService().importExcel2003(user, is);
                break;
            case excel2007:
                getExcelDataService().importExcel2007(user, is);
                break;
            default:
                //none
        }

        redirectAttributes.addFlashAttribute(Constants.MESSAGE, "导入任务已提交，任务执行完成后会在页面右上角的“我的通知”中通知你");
        return redirectToUrl(null);
    }

    private boolean canImport(final MultipartFile file, final Model model) {
        if(file == null || file.isEmpty()) {
            model.addAttribute(Constants.ERROR, "请选择要导入的文件");
            return false;
        }

        String filename = file.getOriginalFilename().toLowerCase();
        if(!(filename.endsWith("csv") || filename.endsWith("xls") || filename.endsWith("xlsx"))) {
            model.addAttribute(Constants.ERROR, "导入的文件格式错误，允许的格式：csv、xls、xlsx");
            return false;
        }

        return true;
    }


    @RequestMapping(value = "/export", method = RequestMethod.GET)
    public String exportExcel(
            @CurrentUser User user,
            Searchable searchable,
            @RequestParam("type") ExcelDataType type,
            RedirectAttributes redirectAttributes) throws IOException {

        switch (type) {
            case csv:
                getExcelDataService().exportCvs(user, contextRootPath, searchable);
                break;
            case excel2003_sheet:
                getExcelDataService().exportExcel2003WithOneSheetPerWorkBook(user, contextRootPath, searchable);
                break;
            case excel2003_xml:
                getExcelDataService().exportExcel2003WithXml(user, contextRootPath, searchable);
                break;
            case excel2003_usermodel:
                getExcelDataService().exportExcel2003WithUsermodel(user, contextRootPath, searchable);
                break;
            case excel2007:
                getExcelDataService().exportExcel2007(user, contextRootPath, searchable);
                break;
            default:
                //none
        }

        redirectAttributes.addFlashAttribute(Constants.MESSAGE, "导出任务已提交，任务执行完成后会在页面右上角的“我的通知”中通知你");
        return redirectToUrl(null);
    }

}
