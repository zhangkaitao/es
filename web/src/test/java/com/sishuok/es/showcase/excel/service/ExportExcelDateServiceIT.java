/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.es.showcase.excel.service;

import com.sishuok.es.common.entity.search.SearchOperator;
import com.sishuok.es.common.entity.search.Searchable;
import com.sishuok.es.common.repository.RepositoryHelper;
import com.sishuok.es.showcase.excel.entity.ExcelData;
import com.sishuok.es.test.BaseIT;
import org.apache.commons.io.IOUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 13-7-3 下午3:43
 * <p>Version: 1.0
 */
public class ExportExcelDateServiceIT extends BaseIT {

    @Autowired
    private ExcelDataService excelDataService;

    private final Logger log = LoggerFactory.getLogger(ExcelDataService.class);


    /**
     * csv格式 (","分隔)
     */

    @Test
    public void testExportCSV() throws IOException {
        long beginTime = System.currentTimeMillis();
        String encoding = "gbk";

        OutputStream out = new BufferedOutputStream(new FileOutputStream("D:\\Backup\\test.csv"));

        int perSheetRows = 60000; //每个sheet 6w条
        int totalRows = 1;

        int pageSize = 1000;
        Searchable searchable = Searchable.newSearchable();
        Long maxId = 0L;
        String separator = ",";


        out.write("编号,内容\n".getBytes(encoding));

        while (true) {
            Page<ExcelData> page = null;

            do {
                searchable.setPage(0, pageSize);
                //优化分页性能
                searchable.addSearchFilter("id", SearchOperator.gt, maxId);
                page = excelDataService.findAll(searchable);

                for (ExcelData data : page.getContent()) {
                    out.write(String.valueOf(data.getId()).getBytes(encoding));
                    out.write(separator.getBytes(encoding));
                    out.write((data.getContent()).replace(separator, ";").getBytes(encoding));
                    out.write("\n".getBytes(encoding));
                    totalRows++;
                    maxId = Math.max(maxId, data.getId());
                }
                //clear entity manager
                RepositoryHelper.clear();
            } while (page.hasNextPage() && totalRows <= perSheetRows);

            totalRows = 1;

            if (!page.hasNextPage()) {
                break;
            }
        }

        IOUtils.closeQuietly(out);

        long endTime = System.currentTimeMillis();
        log.info("耗时(秒):" + (endTime - beginTime) / 1000);
    }


    /**
     * 支持大数据量导出
     * excel 2007 每个sheet最多1048576行
     *
     * @throws IOException
     */
    @Test
    public void testExportExcel2007() throws IOException {
        long beginTime = System.currentTimeMillis();

        int rowAccessWindowSize = 1000; //内存中保留的行数，超出后会写到磁盘
        SXSSFWorkbook wb = new SXSSFWorkbook(rowAccessWindowSize);
        wb.setCompressTempFiles(true);//生成的临时文件将进行gzip压缩

        int perSheetRows = 100000; //每个sheet 10w条
        int totalRows = 1; //统计总行数

        int pageSize = 1000;
        Searchable searchable = Searchable.newSearchable();
        Long maxId = 0L;//当前查询的数据中最大的id 优化分页的
        while (true) {
            Sheet sheet = wb.createSheet();
            Row headerRow = sheet.createRow(0);
            Cell idHeaderCell = headerRow.createCell(0);
            idHeaderCell.setCellValue("编号");
            Cell contentHeaderCell = headerRow.createCell(1);
            contentHeaderCell.setCellValue("内容");

            Page<ExcelData> page = null;

            do {
                searchable.setPage(0, pageSize);
                //优化分页性能
                searchable.addSearchFilter("id", SearchOperator.gt, maxId);
                page = excelDataService.findAll(searchable);

                for (ExcelData data : page.getContent()) {
                    Row row = sheet.createRow(totalRows++);
                    Cell idCell = row.createCell(0);
                    idCell.setCellValue(data.getId());
                    Cell contentCell = row.createCell(1);
                    contentCell.setCellValue(data.getContent());
                    maxId = Math.max(maxId, data.getId());
                }
                //clear entity manager
                RepositoryHelper.clear();
            } while (page.hasNextPage() && totalRows <= perSheetRows);

            totalRows = 1;

            if (!page.hasNextPage()) {
                break;
            }
        }


        OutputStream out = new BufferedOutputStream(new FileOutputStream("D:\\Backup\\test.xlsx"));
        wb.write(out);

        IOUtils.closeQuietly(out);

        // 清除本工作簿备份在磁盘上的临时文件
        wb.dispose();

        long endTime = System.currentTimeMillis();

        log.info("耗时(秒):" + (endTime - beginTime) / 1000);
    }


    /**
     * excel 2003
     * 方法1 不支持大数据量
     * 每个sheet最多65536行(因为是usermodel模型，数据先写到内存 最后flush出去 不支持大数据量导出)
     */
    @Test
    public void testExportExcel2003_1() throws IOException {
        long beginTime = System.currentTimeMillis();

        HSSFWorkbook wb = new HSSFWorkbook();
        int perSheetRows = 60000; //每个sheet 6w条
        int totalRows = 1;

        int pageSize = 1000;
        Searchable searchable = Searchable.newSearchable();
        Long maxId = 0L;
        while (true) {
            Sheet sheet = wb.createSheet();
            Row headerRow = sheet.createRow(0);
            Cell idHeaderCell = headerRow.createCell(0);
            idHeaderCell.setCellValue("编号");
            Cell contentHeaderCell = headerRow.createCell(1);
            contentHeaderCell.setCellValue("内容");

            Page<ExcelData> page = null;

            do {
                searchable.setPage(0, pageSize);
                //优化分页性能
                searchable.addSearchFilter("id", SearchOperator.gt, maxId);
                page = excelDataService.findAll(searchable);

                for (ExcelData data : page.getContent()) {
                    Row row = sheet.createRow(totalRows++);
                    Cell idCell = row.createCell(0);
                    idCell.setCellValue(data.getId());
                    Cell contentCell = row.createCell(1);
                    contentCell.setCellValue(data.getContent());
                    maxId = Math.max(maxId, data.getId());
                }
                //clear entity manager
                RepositoryHelper.clear();
            } while (page.hasNextPage() && totalRows <= perSheetRows);

            totalRows = 1;

            if (!page.hasNextPage()) {
                break;
            }
        }

        OutputStream out = new BufferedOutputStream(new FileOutputStream("D:\\Backup\\test_1.xls"));
        wb.write(out);

        IOUtils.closeQuietly(out);
        long endTime = System.currentTimeMillis();

        log.info("耗时(秒):" + (endTime - beginTime) / 1000);
    }

    /**
     * excel 2003
     * 方法2 写xml表格数据
     * 1、生成模板，另存为：XML表格
     * 2、需要在导出之前，先做好模板（即先到excel中定义好格式，不支持复杂格式，如图片等），然后输出数）
     * 3、复杂格式请考虑testExportExcel2003_3
     *
     * 生成的文件巨大。。
     *
     * 还一种是写html（缺点不支持多sheet）
     *
     */
    @Test
    public void testExportExcel2003_2() throws IOException {
        long beginTime = System.currentTimeMillis();
        String encoding = "utf-8";
        String workBookHeader = IOUtils.toString(ExportExcelDateServiceIT.class.getResourceAsStream("excel_2003_xml_workbook_header.txt"));
        String workBookFooter = IOUtils.toString(ExportExcelDateServiceIT.class.getResourceAsStream("excel_2003_xml_workbook_footer.txt"));
        String sheetHeader = IOUtils.toString(ExportExcelDateServiceIT.class.getResourceAsStream("excel_2003_xml_sheet_header.txt"));
        String sheetFooter = IOUtils.toString(ExportExcelDateServiceIT.class.getResourceAsStream("excel_2003_xml_sheet_footer.txt"));
        String rowTemplate = IOUtils.toString(ExportExcelDateServiceIT.class.getResourceAsStream("excel_2003_xml_row.txt"));

        OutputStream out = new BufferedOutputStream(new FileOutputStream("D:\\Backup\\test_2003_2.xls"));
        out.write(workBookHeader.getBytes("utf-8"));


        int perSheetRows = 60000; //每个sheet 6w条
        int totalRows = 1;
        int totalSheets = 1;

        int pageSize = 1000;
        Searchable searchable = Searchable.newSearchable();
        Long maxId = 0L;


        while (true) {
            out.write(sheetHeader.replace("{sheetName}", "Sheet" + totalSheets).getBytes(encoding));
            Page<ExcelData> page = null;

            do {
                searchable.setPage(0, pageSize);
                //优化分页性能
                searchable.addSearchFilter("id", SearchOperator.gt, maxId);
                page = excelDataService.findAll(searchable);

                for (ExcelData data : page.getContent()) {
                    out.write(rowTemplate.replace("{id}", String.valueOf(data.getId())).replace("{content}", data.getContent()).getBytes(encoding));
                    totalRows++;
                    maxId = Math.max(maxId, data.getId());
                }
                //clear entity manager
                RepositoryHelper.clear();
            } while (page.hasNextPage() && totalRows <= perSheetRows);

            totalRows = 1;

            totalSheets++;
            out.write(sheetFooter.getBytes(encoding));

            if (!page.hasNextPage()) {
                break;
            }
        }

        out.write(workBookFooter.getBytes(encoding));

        IOUtils.closeQuietly(out);

        long endTime = System.currentTimeMillis();

        log.info("耗时(秒):" + (endTime - beginTime) / 1000);
    }

    /**
     * 方法3  写多个workbook
     * 1、给用户一个vbs 脚本合并
     * 2、给用户写一个c#程序合并
     *
     * 不想这么麻烦 需要时再写吧，还不如直接让用户装office 2007 更简单。
     *
     */
    @Test
    public void testExportExcel2003_3() throws IOException {
        long beginTime = System.currentTimeMillis();

        int workbookCount = 1;
        int perSheetRows = 60000; //每个sheet 6w条
        int totalRows = 1;

        int pageSize = 1000;
        Searchable searchable = Searchable.newSearchable();
        Long maxId = 0L;

        while (true) {
            HSSFWorkbook wb = new HSSFWorkbook();

            Sheet sheet = wb.createSheet();
            Row headerRow = sheet.createRow(0);
            Cell idHeaderCell = headerRow.createCell(0);
            idHeaderCell.setCellValue("编号");
            Cell contentHeaderCell = headerRow.createCell(1);
            contentHeaderCell.setCellValue("内容");

            Page<ExcelData> page = null;

            do {
                searchable.setPage(0, pageSize);
                //优化分页性能
                searchable.addSearchFilter("id", SearchOperator.gt, maxId);
                page = excelDataService.findAll(searchable);

                for (ExcelData data : page.getContent()) {
                    Row row = sheet.createRow(totalRows++);
                    Cell idCell = row.createCell(0);
                    idCell.setCellValue(data.getId());
                    Cell contentCell = row.createCell(1);
                    contentCell.setCellValue(data.getContent());
                    maxId = Math.max(maxId, data.getId());
                }
                //clear entity manager
                RepositoryHelper.clear();
            } while (page.hasNextPage() && totalRows <= perSheetRows);


            OutputStream out = new BufferedOutputStream(new FileOutputStream("D:\\Backup\\test_" + workbookCount + ".xls"));
            wb.write(out);

            IOUtils.closeQuietly(out);

            totalRows = 1;
            workbookCount++;

            if (!page.hasNextPage()) {
                break;
            }

        }


        long endTime = System.currentTimeMillis();

        log.info("耗时(秒):" + (endTime - beginTime) / 1000);
    }

    /**
     * excel 2003 更苦逼的是写biff（http://www.openoffice.org/sc/excelfileformat.pdf）
     */


}
