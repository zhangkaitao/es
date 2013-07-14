/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.es.showcase.excel.service;

import com.sishuok.es.common.entity.search.Searchable;
import com.sishuok.es.common.spring.utils.AopProxyUtils;
import com.sishuok.es.maintain.notification.service.NotificationApi;
import com.sishuok.es.sys.user.entity.User;
import com.sishuok.es.test.BaseIT;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 13-7-3 下午3:43
 * <p>Version: 1.0
 */

@Ignore
public class ExportExcelDateServiceIT extends BaseIT {

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

    @Test
    public void testExportCSV() throws IOException {
        excelDataService.exportCvs(user, "D:\\Backup", Searchable.newSearchable());
    }


    /**
     * 支持大数据量导出
     * excel 2007 每个sheet最多1048576行
     *
     * @throws IOException
     */
    @Test
    public void testExportExcel2007() throws IOException {
        excelDataService.exportExcel2007(user, "D:\\Backup", Searchable.newSearchable());
    }


    /**
     * excel 2003
     * 方法1 不支持大数据量
     * 每个sheet最多65536行(因为是usermodel模型，数据先写到内存 最后flush出去 不支持大数据量导出)
     */
    @Test
    public void testExportExcel2003WithUsermodel() throws IOException {
        excelDataService.exportExcel2003WithUsermodel(user, "D:\\Backup", Searchable.newSearchable());
    }

    /**
     * excel 2003
     * 方法2 写xml表格数据
     * 1、生成模板，另存为：XML表格
     * 2、需要在导出之前，先做好模板（即先到excel中定义好格式，不支持复杂格式，如图片等），然后输出数）
     * 3、复杂格式请考虑testExportExcel2003_3
     * <p/>
     * 生成的文件巨大。。
     * <p/>
     * 还一种是写html（缺点不支持多sheet）
     */
    @Test
    public void testExportExcel2003WithXml() throws IOException {
        excelDataService.exportExcel2003WithXml(user, "D:\\Backup", Searchable.newSearchable());
    }

    /**
     * 方法3  写多个workbook
     * 1、给用户一个vbs 脚本合并
     * 2、给用户写一个c#程序合并
     * <p/>
     * 不想这么麻烦 需要时再写吧，还不如直接让用户装office 2007 更简单。
     */
    @Test
    public void testExportExcel2003WithOneSheetPerWorkBook() throws IOException {
        excelDataService.exportExcel2003WithOneSheetPerWorkBook(user, "D:\\Backup", Searchable.newSearchable());
    }

    /**
     * excel 2003 更苦逼的是写biff（http://www.openoffice.org/sc/excelfileformat.pdf）
     */


}
