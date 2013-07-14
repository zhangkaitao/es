/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.es.showcase.excel.service;

import com.google.common.collect.Lists;
import com.sishuok.es.showcase.excel.entity.ExcelData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.List;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 13-7-12 下午9:50
 * <p>Version: 1.0
 */
class Excel2007ImportSheetHandler extends DefaultHandler {

    private final Logger log = LoggerFactory.getLogger(Excel2007ImportSheetHandler.class);

    private int batchSize; //批处理大小
    private int totalSize = 0; //总行数

    private int rowNumber = 1;
    private String lastContents;
    private List<ExcelData> dataList;
    private ExcelDataService excelDataService;

    private List<String> currentCellData = Lists.newArrayList();


    Excel2007ImportSheetHandler(
            final ExcelDataService excelDataService, final List<ExcelData> dataList, final int batchSize) {
        this.excelDataService = excelDataService;
        this.dataList = dataList;
        this.batchSize = batchSize;

    }

    public void startElement(String uri, String localName, String name, Attributes attributes) throws SAXException {
        if ("row".equals(name)) {//如果是行开始 清空cell数据 重来
            rowNumber = Integer.valueOf(attributes.getValue("r"));//当前行号
            if (rowNumber == 1) {
                return;
            }
            currentCellData.clear();
        }

        lastContents = "";
    }

    public void endElement(String uri, String localName, String name) throws SAXException {

        if ("row".equals(name)) {//如果是行开始 清空cell数据 重来
            if (rowNumber == 1) {
                return;
            }
            ExcelData data = new ExcelData();
            data.setId(Double.valueOf(currentCellData.get(0)).longValue());
            data.setContent(currentCellData.get(1));
            dataList.add(data);

            totalSize++;

            if (totalSize % batchSize == 0) {
                try {
                    excelDataService.doBatchSave(dataList);
                } catch (Exception e) {
                    Long fromId = dataList.get(0).getId();
                    Long endId = dataList.get(dataList.size() - 1).getId();
                    log.error("from " + fromId + " to " + endId + ", error", e);
                }
                dataList.clear();
            }
        }

        if ("c".equals(name)) {//按照列顺序添加数据
            currentCellData.add(lastContents);
        }


    }

    public void characters(char[] ch, int start, int length) throws SAXException {
        lastContents += new String(ch, start, length);
    }
}