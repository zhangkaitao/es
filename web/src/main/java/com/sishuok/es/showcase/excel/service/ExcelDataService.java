/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.es.showcase.excel.service;

import com.sishuok.es.common.entity.search.Searchable;
import com.sishuok.es.common.inject.annotation.BaseComponent;
import com.sishuok.es.common.service.BaseService;
import com.sishuok.es.common.utils.SpringUtils;
import com.sishuok.es.showcase.excel.entity.ExcelData;
import com.sishuok.es.showcase.excel.repository.ExcelDataRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.File;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 13-2-4 下午3:01
 * <p>Version: 1.0
 */
@Service
public class ExcelDataService extends BaseService<ExcelData, Long> {

    private final Logger log = LoggerFactory.getLogger(ExcelDataService.class);

    @Autowired
    @BaseComponent
    private ExcelDataRepository excelDataRepository;


    @Async
    public void initOneMillionData() {

        long beginTime = System.currentTimeMillis();

        log.info("begin init excel data.");

        excelDataRepository.truncate();

        final int ONE_MILLION = 1000000; //100w
        for(int i = 100; i <= ONE_MILLION; i += 100) {
            //不能使用AopContext.currentProxy() 因为task:annotation-driven没有暴露proxy。。
            SpringUtils.getBean(ExcelDataService.class).doBatchSave(100);
        }

        long endTime = System.currentTimeMillis();
        log.info("end init excel data, elapsed time(seconds):" + (endTime - beginTime) / 1000);
    }

    public void doBatchSave(final int size) {
        for(int i = 0; i < size; i++) {
            ExcelData data = new ExcelData();
            data.setContent("1234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890");
            excelDataRepository.save(data);
        }
    }


    public void importExcel(File file) {

    }

    public String exportExcel(Searchable searchable) {
        return "";
    }
}
