/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.es.showcase.excel.service;

import com.sishuok.es.common.spring.utils.AopProxyUtils;
import com.sishuok.es.maintain.notification.service.NotificationApi;
import com.sishuok.es.sys.user.entity.User;
import com.sishuok.es.test.BaseIT;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.transaction.TransactionConfiguration;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 13-7-3 下午3:43
 * <p>Version: 1.0
 */

@Ignore
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
public class ImportExcelDateServiceIT extends BaseIT {

    @Autowired
    private ExcelDataService excelDataService;

    private User user;

    @Before
    public void setUp() throws Exception {
        //模拟一个假的NotificationApi
        excelDataService.setNotificationApi(Mockito.mock(NotificationApi.class));
        //移除异步支持
        AopProxyUtils.removeAsync(excelDataService);

        user = new User();
        user.setId(1L);
        user.setUsername("zhang");
    }

    /**
     * csv格式
     */
    @Test
    public void testImportCsv() throws IOException {
        File file = new File("D:\\Backup\\test.csv");
        excelDataService.importCvs(user, new FileInputStream(file));
    }


    /**
     * 导入 excel 2003 biff格式
     * 如果是xml格式的 可以使用SAX（未测试）
     */
    @Test
    public void testImportExcel2003() throws Exception {
        String fileName = "D:\\Backup\\test.xls";
        excelDataService.importExcel2003(user , new FileInputStream(fileName));
    }


    @Test
    public void testImportExcel2007() throws Exception {
        String fileName = "D:\\Backup\\test.xlsx";
        excelDataService.importExcel2007(user, new FileInputStream(fileName));
    }

}
