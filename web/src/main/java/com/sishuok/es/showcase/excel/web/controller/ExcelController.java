/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.es.showcase.excel.web.controller;

import com.sishuok.es.common.inject.annotation.BaseComponent;
import com.sishuok.es.common.web.controller.BaseCRUDController;
import com.sishuok.es.showcase.excel.entity.ExcelData;
import com.sishuok.es.showcase.excel.service.ExcelDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 13-1-28 下午4:29
 * <p>Version: 1.0
 */
@Controller
@RequestMapping(value = "/showcase/excel")
public class ExcelController extends BaseCRUDController<ExcelData, Long> {

    @Autowired
    @BaseComponent
    private ExcelDataService excelDataService;

    public ExcelController() {
        setResourceIdentity("showcase:excel");
    }

    /**
     * 初始化100w数据
     * @return
     */
    @RequestMapping("/init")
    public String initOneMillionData(RedirectAttributes redirectAttributes) {
        excelDataService.initOneMillionData();
        redirectAttributes.addAttribute("正在初始化，请稍候");
        return redirectToUrl(null);
    }

}
