/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.es.showcase.excel.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.sishuok.es.common.entity.search.SearchOperator;
import com.sishuok.es.common.entity.search.Searchable;
import com.sishuok.es.common.repository.RepositoryHelper;
import com.sishuok.es.common.service.BaseService;
import com.sishuok.es.common.utils.FileCharset;
import com.sishuok.es.maintain.editor.web.controller.utils.CompressUtils;
import com.sishuok.es.maintain.notification.service.NotificationApi;
import com.sishuok.es.showcase.excel.entity.ExcelData;
import com.sishuok.es.showcase.excel.repository.ExcelDataRepository;
import com.sishuok.es.sys.user.entity.User;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.LineIterator;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.poi.hssf.eventusermodel.HSSFEventFactory;
import org.apache.poi.hssf.eventusermodel.HSSFRequest;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import java.io.*;
import java.util.*;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 13-2-4 下午3:01
 * <p>Version: 1.0
 */
@Service
public class ExcelDataService extends BaseService<ExcelData, Long> {

    private final Logger log = LoggerFactory.getLogger(ExcelDataService.class);

    private int batchSize = 1000; //批处理大小
    private int pageSize = 1000;//查询时每页大小


    /**
     * 导出文件的最大大小 超过这个大小会压缩
     */
    private final int MAX_EXPORT_FILE_SIZE = 10 * 1024 * 1024; //10MB


    private ExcelDataRepository getExcelDataRepository() {
        return (ExcelDataRepository) baseRepository;
    }

    @Autowired
    private NotificationApi notificationApi;

    private final String storePath = "upload/excel";
    private final String EXPORT_FILENAME_PREFIX = "excel";

    public void setNotificationApi(final NotificationApi notificationApi) {
        this.notificationApi = notificationApi;
    }

    @Async
    public void initOneMillionData(final User user) {

        ExcelDataService proxy = (ExcelDataService) AopContext.currentProxy();

        long beginTime = System.currentTimeMillis();

        getExcelDataRepository().truncate();

        final int ONE_MILLION = 1000000; //100w
        for(int i = batchSize; i <= ONE_MILLION; i += batchSize) {
            //不能使用AopContext.currentProxy() 因为task:annotation-driven没有暴露proxy。。
            proxy.doBatchSave(i - batchSize);
        }

        long endTime = System.currentTimeMillis();

        Map<String, Object> context = Maps.newHashMap();
        context.put("seconds", (endTime - beginTime) / 1000);
        notificationApi.notify(user.getId(), "excelInitDataSuccess", context);

    }

    public void doBatchSave(final int fromId) {
        for(int i = 1; i <= batchSize; i++) {
            Long id = Long.valueOf(fromId + i);
            String content = "1234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890";
            getExcelDataRepository().save(id, content);
        }
    }

    /**
     * 如果主键冲突 覆盖，否则新增
     * @param dataList
     */
    public void doBatchSave(final List<ExcelData> dataList) {
        for(ExcelData data : dataList) {
            ExcelData dbData = findOne(data.getId());
            if(dbData == null) {
                getExcelDataRepository().save(data.getId(), data.getContent());
            } else {
                dbData.setContent(data.getContent());
                update(dbData);
            }
        }
    }

    /**
     * csv格式
     * @param user
     * @param is
     */
    @Async
    public void importCvs(final User user, final InputStream is) {

        ExcelDataService proxy = ((ExcelDataService)AopContext.currentProxy());
        BufferedInputStream bis = null;
        try {
            long beginTime = System.currentTimeMillis();

            bis = new BufferedInputStream(is);
            String encoding = FileCharset.getCharset(bis);

            LineIterator iterator = IOUtils.lineIterator(bis, encoding);

            String separator = ",";
            int totalSize = 0; //总大小

            final List<ExcelData> dataList = Lists.newArrayList();

            if (iterator.hasNext()) {
                iterator.nextLine();//跳过第一行标题
            }

            while (iterator.hasNext()) {

                totalSize++;

                String line = iterator.nextLine();
                String[] dataArray = StringUtils.split(line, separator);

                ExcelData data = new ExcelData();
                data.setId(Long.valueOf(dataArray[0]));
                data.setContent(dataArray[1]);
                dataList.add(data);

                if (totalSize % batchSize == 0) {
                    try {
                        proxy.doBatchSave(dataList);
                    } catch (Exception e) {
                        Long fromId = dataList.get(0).getId();
                        Long endId = dataList.get(dataList.size() - 1).getId();
                        log.error("from " + fromId + " to " + endId + ", error", e);
                    }
                    dataList.clear();
                }
            }

            if(dataList.size() > 0) {
                proxy.doBatchSave(dataList);
            }

            long endTime = System.currentTimeMillis();

            Map<String, Object> context = Maps.newHashMap();
            context.put("seconds", (endTime - beginTime) / 1000);
            notificationApi.notify(user.getId(), "excelImportSuccess", context);
        } catch (Exception e) {
            log.error("excel import error", e);
            Map<String, Object> context = Maps.newHashMap();
            context.put("error", e.getMessage());
            notificationApi.notify(user.getId(), "excelImportError", context);
        } finally {
            IOUtils.closeQuietly(bis);
        }
    }

    /**
     * 导入 excel 2003 biff格式
     * 如果是xml格式的 可以使用SAX（未测试）
     * @param user
     * @param is
     */
    @Async
    public void importExcel2003(final User user, final InputStream is) {

        ExcelDataService proxy = ((ExcelDataService)AopContext.currentProxy());

        BufferedInputStream bis = null;
        InputStream dis = null;
        try {
            long beginTime = System.currentTimeMillis();

            List<ExcelData> dataList = Lists.newArrayList();

            //输入流
            bis = new BufferedInputStream(is);
            // 创建 org.apache.poi.poifs.filesystem.Filesystem
            POIFSFileSystem poifs = new POIFSFileSystem(bis);
            // 从输入流 得到 Workbook(excel 部分)流
            dis = poifs.createDocumentInputStream("Workbook");
            // 构造 HSSFRequest
            HSSFRequest req = new HSSFRequest();

            // 添加监听器
            req.addListenerForAllRecords(new Excel2003ImportListener(proxy, dataList, batchSize));
            //  创建事件工厂
            HSSFEventFactory factory = new HSSFEventFactory();
            // 根据文档输入流处理事件
            factory.processEvents(req, dis);


            //把最后剩下的不足batchSize大小
            if (dataList.size() > 0) {
                proxy.doBatchSave(dataList);
            }

            long endTime = System.currentTimeMillis();

            Map<String, Object> context = Maps.newHashMap();
            context.put("seconds", (endTime - beginTime) / 1000);
            notificationApi.notify(user.getId(), "excelImportSuccess", context);
        } catch (Exception e) {
            log.error("excel import error", e);
            Map<String, Object> context = Maps.newHashMap();
            context.put("error", e.getMessage());
            notificationApi.notify(user.getId(), "excelImportError", context);
        } finally {
            // 关闭输入流
            IOUtils.closeQuietly(bis);
            // 关闭文档流
            IOUtils.closeQuietly(dis);
        }
    }

    @Async
    public void importExcel2007(final User user, final InputStream is) {

        ExcelDataService proxy = ((ExcelDataService)AopContext.currentProxy());

        BufferedInputStream bis = null;
        try {
            long beginTime = System.currentTimeMillis();

            List<ExcelData> dataList = Lists.newArrayList();

            bis = new BufferedInputStream(is);
            OPCPackage pkg = OPCPackage.open(bis);
            XSSFReader r = new XSSFReader(pkg);

            XMLReader parser =
                    XMLReaderFactory.createXMLReader();
            ContentHandler handler = new Excel2007ImportSheetHandler(proxy, dataList, batchSize);
            parser.setContentHandler(handler);

            Iterator<InputStream> sheets = r.getSheetsData();
            while (sheets.hasNext()) {
                InputStream sheet = null;
                try {
                    sheet = sheets.next();
                    InputSource sheetSource = new InputSource(sheet);
                    parser.parse(sheetSource);
                } catch (Exception e) {
                    throw e;
                } finally {
                    IOUtils.closeQuietly(sheet);
                }
            }

            //把最后剩下的不足batchSize大小
            if (dataList.size() > 0) {
                proxy.doBatchSave(dataList);
            }

            long endTime = System.currentTimeMillis();
            Map<String, Object> context = Maps.newHashMap();
            context.put("seconds", (endTime - beginTime) / 1000);
            notificationApi.notify(user.getId(), "excelImportSuccess", context);
        } catch (Exception e) {
            log.error("excel import error", e);
            Map<String, Object> context = Maps.newHashMap();
            context.put("error", e.getMessage());
            notificationApi.notify(user.getId(), "excelImportError", context);
        } finally {
            IOUtils.closeQuietly(bis);
        }
    }

    @Async
    public void exportCvs(final User user, final String contextRootPath, final Searchable searchable) {
        String encoding = "gbk";
        int perSheetRows = 60000; //每个sheet 6w条
        int totalRows = 0;
        String separator = ",";
        Long maxId = 0L;

        String fileName = generateFilename(user, contextRootPath, "csv");
        File file = new File(fileName);
        BufferedOutputStream out = null;
        try {
            long beginTime = System.currentTimeMillis();

            out = new BufferedOutputStream(new FileOutputStream(file));
            out.write("编号,内容\n".getBytes(encoding));

            while (true) {
                totalRows = 0;
                Page<ExcelData> page = null;
                do {
                    searchable.setPage(0, pageSize);
                    //优化分页性能
                    if(!searchable.containsSearchKey("id_in")) {
                        searchable.addSearchFilter("id", SearchOperator.gt, maxId);
                    }
                    page = findAll(searchable);
                    for (ExcelData data : page.getContent()) {
                        out.write(String.valueOf(data.getId()).getBytes(encoding));
                        out.write(separator.getBytes(encoding));
                        out.write((data.getContent()).replace(separator, ";").getBytes(encoding));
                        out.write("\n".getBytes(encoding));
                        maxId = Math.max(maxId, data.getId());
                        totalRows++;
                    }
                    //clear entity manager
                    RepositoryHelper.clear();
                } while (page.hasNextPage() && totalRows <= perSheetRows);

                if (!page.hasNextPage()) {
                    break;
                }
            }

            IOUtils.closeQuietly(out);

            if(needCompress(file)) {
                fileName = compressAndDeleteOriginal(fileName);
            }

            long endTime = System.currentTimeMillis();

            Map<String, Object> context = Maps.newHashMap();
            context.put("seconds", (endTime - beginTime) / 1000);
            context.put("url", fileName.replace(contextRootPath, ""));
            notificationApi.notify(user.getId(), "excelExportSuccess", context);
        } catch (Exception e) {
            IOUtils.closeQuietly(out);
            log.error("excel export error", e);
            Map<String, Object> context = Maps.newHashMap();
            context.put("error", e.getMessage());
            notificationApi.notify(user.getId(), "excelExportError", context);
        }
    }

    /**
     * 写多个workbook
     * 1、给用户一个vbs 脚本合并
     * 2、给用户写一个c#程序合并
     * 不想这么麻烦 需要时再写吧，还不如直接让用户装office 2007 更简单。
     * @param user
     * @param contextRootPath
     * @param searchable
     */
    @Async
    public void exportExcel2003WithOneSheetPerWorkBook(final User user, final String contextRootPath, final Searchable searchable) {
        int workbookCount = 0;
        List<String> workbookFileNames = new ArrayList<String>();
        int perSheetRows = 60000; //每个sheet 6w条
        int totalRows = 0;
        String extension = "xls";

        int pageSize = 1000;
        Long maxId = 0L;


        BufferedOutputStream out = null;
        try {
            long beginTime = System.currentTimeMillis();

            while (true) {
                workbookCount++;
                String fileName = generateFilename(user, contextRootPath, workbookCount, extension);
                workbookFileNames.add(fileName);
                File file = new File(fileName);

                HSSFWorkbook wb = new HSSFWorkbook();
                Sheet sheet = wb.createSheet();
                Row headerRow = sheet.createRow(0);
                Cell idHeaderCell = headerRow.createCell(0);
                idHeaderCell.setCellValue("编号");
                Cell contentHeaderCell = headerRow.createCell(1);
                contentHeaderCell.setCellValue("内容");

                totalRows = 1;

                Page<ExcelData> page = null;

                do {
                    searchable.setPage(0, pageSize);
                    //优化分页性能
                    if(!searchable.containsSearchKey("id_in")) {
                        searchable.addSearchFilter("id", SearchOperator.gt, maxId);
                    }
                    page = findAll(searchable);

                    for (ExcelData data : page.getContent()) {
                        Row row = sheet.createRow(totalRows);
                        Cell idCell = row.createCell(0);
                        idCell.setCellValue(data.getId());
                        Cell contentCell = row.createCell(1);
                        contentCell.setCellValue(data.getContent());
                        maxId = Math.max(maxId, data.getId());
                        totalRows++;
                    }
                    //clear entity manager
                    RepositoryHelper.clear();
                } while (page.hasNextPage() && totalRows <= perSheetRows);

                out = new BufferedOutputStream(new FileOutputStream(file));
                wb.write(out);

                IOUtils.closeQuietly(out);

                if (!page.hasNextPage()) {
                    break;
                }
            }

            String fileName =  workbookFileNames.get(0);
            if (workbookCount > 1 || needCompress(new File(fileName))) {
                fileName = fileName.substring(0, fileName.lastIndexOf("_")) + ".zip";
                //去掉索引
                compressAndDeleteOriginal(fileName, workbookFileNames.toArray(new String[0]));
            } else {
                String newFileName = fileName.substring(0, fileName.lastIndexOf("_")) + "." + extension;
                FileUtils.moveFile(new File(fileName), new File(newFileName));
                fileName = newFileName;
            }

            long endTime = System.currentTimeMillis();

            Map<String, Object> context = Maps.newHashMap();
            context.put("seconds", (endTime - beginTime) / 1000);
            context.put("url", fileName.replace(contextRootPath, ""));
            notificationApi.notify(user.getId(), "excelExportSuccess", context);
        } catch (Exception e) {
            e.printStackTrace();
            //以防万一
            IOUtils.closeQuietly(out);
            log.error("excel export error", e);
            Map<String, Object> context = Maps.newHashMap();
            context.put("error", e.getMessage());
            notificationApi.notify(user.getId(), "excelExportError", context);
        }
    }

    /**
     * excel 2003
     * 1、生成模板，另存为：XML表格
     * 2、需要在导出之前，先做好模板（即先到excel中定义好格式，不支持复杂格式，如图片等），然后输出数）
     * 3、复杂格式请考虑testExportExcel2003_3
     * <p/>
     * 生成的文件巨大。。
     * <p/>
     * 还一种是写html（缺点不支持多sheet）
     * @param user
     * @param contextRootPath
     * @param searchable
     */
    @Async
    public void exportExcel2003WithXml(final User user, final String contextRootPath, final Searchable searchable) {


        int perSheetRows = 60000; //每个sheet 6w条
        int totalSheets = 0;
        int totalRows = 0;
        Long maxId = 0L;
        String templateEncoding = "utf-8";

        String fileName = generateFilename(user, contextRootPath, "xls");
        File file = new File(fileName);
        BufferedOutputStream out = null;
        try {
            long beginTime = System.currentTimeMillis();

            String workBookHeader = IOUtils.toString(ExcelDataService.class.getResourceAsStream("excel_2003_xml_workbook_header.txt"));
            String workBookFooter = IOUtils.toString(ExcelDataService.class.getResourceAsStream("excel_2003_xml_workbook_footer.txt"));
            String sheetHeader = IOUtils.toString(ExcelDataService.class.getResourceAsStream("excel_2003_xml_sheet_header.txt"));
            String sheetFooter = IOUtils.toString(ExcelDataService.class.getResourceAsStream("excel_2003_xml_sheet_footer.txt"));
            String rowTemplate = IOUtils.toString(ExcelDataService.class.getResourceAsStream("excel_2003_xml_row.txt"));

            out = new BufferedOutputStream(new FileOutputStream(file));

            out.write(workBookHeader.getBytes(templateEncoding));

            while (true) {
                totalSheets++;

                out.write(sheetHeader.replace("{sheetName}", "Sheet" + totalSheets).getBytes(templateEncoding));

                Page<ExcelData> page = null;

                totalRows = 1;
                do {
                    searchable.setPage(0, pageSize);
                    //优化分页性能
                    if(!searchable.containsSearchKey("id_in")) {
                        searchable.addSearchFilter("id", SearchOperator.gt, maxId);
                    }
                    page = findAll(searchable);

                    for (ExcelData data : page.getContent()) {
                        out.write(rowTemplate.replace("{id}", String.valueOf(data.getId())).replace("{content}", data.getContent()).getBytes(templateEncoding));
                        maxId = Math.max(maxId, data.getId());
                        totalRows++;
                    }
                    //clear entity manager
                    RepositoryHelper.clear();
                } while (page.hasNextPage() && totalRows <= perSheetRows);

                out.write(sheetFooter.getBytes(templateEncoding));

                if (!page.hasNextPage()) {
                    break;
                }
            }

            out.write(workBookFooter.getBytes(templateEncoding));

            IOUtils.closeQuietly(out);

            if (needCompress(file)) {
                fileName = compressAndDeleteOriginal(fileName);
            }

            long endTime = System.currentTimeMillis();

            Map<String, Object> context = Maps.newHashMap();
            context.put("seconds", (endTime - beginTime) / 1000);
            context.put("url", fileName.replace(contextRootPath, ""));
            notificationApi.notify(user.getId(), "excelExportSuccess", context);
        } catch (Exception e) {
            e.printStackTrace();
            IOUtils.closeQuietly(out);
            log.error("excel export error", e);
            Map<String, Object> context = Maps.newHashMap();
            context.put("error", e.getMessage());
            notificationApi.notify(user.getId(), "excelExportError", context);
        }
    }

    /**
     * excel 2003
     * 不支持大数据量
     * 每个sheet最多65536行(因为是usermodel模型，数据先写到内存 最后flush出去 不支持大数据量导出)
     * @param user
     * @param contextRootPath
     * @param searchable
     */
    @Async
    public void exportExcel2003WithUsermodel(final User user, final String contextRootPath, final Searchable searchable) {
        int perSheetRows = 60000; //每个sheet 6w条
        int totalRows = 0;
        Long maxId = 0L;

        String fileName = generateFilename(user, contextRootPath, "xls");
        File file = new File(fileName);
        BufferedOutputStream out = null;
        try {
            long beginTime = System.currentTimeMillis();

            HSSFWorkbook wb = new HSSFWorkbook();
            while (true) {
                Sheet sheet = wb.createSheet();
                Row headerRow = sheet.createRow(0);
                Cell idHeaderCell = headerRow.createCell(0);
                idHeaderCell.setCellValue("编号");
                Cell contentHeaderCell = headerRow.createCell(1);
                contentHeaderCell.setCellValue("内容");

                totalRows = 1;
                Page<ExcelData> page = null;
                do {
                    searchable.setPage(0, pageSize);
                    //优化分页性能
                    if(!searchable.containsSearchKey("id_in")) {
                        searchable.addSearchFilter("id", SearchOperator.gt, maxId);
                    }
                    page = findAll(searchable);

                    for (ExcelData data : page.getContent()) {
                        Row row = sheet.createRow(totalRows);
                        Cell idCell = row.createCell(0);
                        idCell.setCellValue(data.getId());
                        Cell contentCell = row.createCell(1);
                        contentCell.setCellValue(data.getContent());
                        maxId = Math.max(maxId, data.getId());
                        totalRows++;
                    }
                    //clear entity manager
                    RepositoryHelper.clear();
                } while (page.hasNextPage() && totalRows <= perSheetRows);

                if (!page.hasNextPage()) {
                    break;
                }
            }

            out = new BufferedOutputStream(new FileOutputStream(file));
            wb.write(out);

            IOUtils.closeQuietly(out);

            if(needCompress(file)) {
                fileName = compressAndDeleteOriginal(fileName);
            }

            long endTime = System.currentTimeMillis();

            Map<String, Object> context = Maps.newHashMap();
            context.put("seconds", (endTime - beginTime) / 1000);
            context.put("url", fileName.replace(contextRootPath, ""));
            notificationApi.notify(user.getId(), "excelExportSuccess", context);
        } catch (Exception e) {
            IOUtils.closeQuietly(out);
            log.error("excel export error", e);
            Map<String, Object> context = Maps.newHashMap();
            context.put("error", e.getMessage());
            notificationApi.notify(user.getId(), "excelExportError", context);
        }
    }


    /**
     * 支持大数据量导出
     * excel 2007 每个sheet最多1048576行
     * @param user
     * @param contextRootPath
     * @param searchable
     */
    @Async
    public void exportExcel2007(final User user, final String contextRootPath, final Searchable searchable) {

        int rowAccessWindowSize = 1000; //内存中保留的行数，超出后会写到磁盘
        int perSheetRows = 100000; //每个sheet 10w条
        int totalRows = 0; //统计总行数
        Long maxId = 0L;//当前查询的数据中最大的id 优化分页的

        String fileName = generateFilename(user, contextRootPath, "xlsx");
        File file = new File(fileName);
        BufferedOutputStream out = null;
        SXSSFWorkbook wb = null;
        try {
            long beginTime = System.currentTimeMillis();

            wb = new SXSSFWorkbook(rowAccessWindowSize);
            wb.setCompressTempFiles(true);//生成的临时文件将进行gzip压缩

            while (true) {

                Sheet sheet = wb.createSheet();
                Row headerRow = sheet.createRow(0);
                Cell idHeaderCell = headerRow.createCell(0);
                idHeaderCell.setCellValue("编号");
                Cell contentHeaderCell = headerRow.createCell(1);
                contentHeaderCell.setCellValue("内容");

                totalRows = 1;

                Page<ExcelData> page = null;

                do {
                    searchable.setPage(0, pageSize);
                    //优化分页性能
                    if(!searchable.containsSearchKey("id_in")) {
                        searchable.addSearchFilter("id", SearchOperator.gt, maxId);
                    }
                    page = findAll(searchable);

                    for (ExcelData data : page.getContent()) {
                        Row row = sheet.createRow(totalRows);
                        Cell idCell = row.createCell(0);
                        idCell.setCellValue(data.getId());
                        Cell contentCell = row.createCell(1);
                        contentCell.setCellValue(data.getContent());
                        maxId = Math.max(maxId, data.getId());
                        totalRows++;
                    }
                    //clear entity manager
                    RepositoryHelper.clear();
                } while (page.hasNextPage() && totalRows <= perSheetRows);

                if (!page.hasNextPage()) {
                    break;
                }
            }
            out = new BufferedOutputStream(new FileOutputStream(file));
            wb.write(out);

            IOUtils.closeQuietly(out);

            if (needCompress(file)) {
                fileName = compressAndDeleteOriginal(fileName);
            }

            long endTime = System.currentTimeMillis();

            Map<String, Object> context = Maps.newHashMap();
            context.put("seconds", (endTime - beginTime) / 1000);
            context.put("url", fileName.replace(contextRootPath, ""));
            notificationApi.notify(user.getId(), "excelExportSuccess", context);
        } catch (Exception e) {
            IOUtils.closeQuietly(out);
            log.error("excel export error", e);
            Map<String, Object> context = Maps.newHashMap();
            context.put("error", e.getMessage());
            notificationApi.notify(user.getId(), "excelExportError", context);
        } finally {
            // 清除本工作簿备份在磁盘上的临时文件
            wb.dispose();
        }
    }





    private String compressAndDeleteOriginal(final String filename) {
        String newFileName = FilenameUtils.removeExtension(filename) + ".zip";
        compressAndDeleteOriginal(newFileName, filename);
        return newFileName;
    }

    private void compressAndDeleteOriginal(final String newFileName, final String... needCompressFilenames) {
        CompressUtils.zip(newFileName, needCompressFilenames);
        for(String needCompressFilename : needCompressFilenames) {
            FileUtils.deleteQuietly(new File(needCompressFilename));
        }
    }

    private boolean needCompress(final File file) {
        return file.length() > MAX_EXPORT_FILE_SIZE;
    }

    /**
     * 生成要导出的文件名
     * @param user
     * @param contextRootPath
     * @param extension
     * @return
     */
    private String generateFilename(final User user, final String contextRootPath, final String extension) {
        return generateFilename(user, contextRootPath, null, extension);
    }
    private String generateFilename(final User user, final String contextRootPath, Integer index, final String extension) {
        String path = FilenameUtils.concat(contextRootPath, storePath);
        path = FilenameUtils.concat(path, user.getUsername());
        path = FilenameUtils.concat(
                path,
                EXPORT_FILENAME_PREFIX + "_" +
                        DateFormatUtils.format(new Date(), "yyyyMMddHHmmssSSS") +
                        (index != null ? ("_" + index) : "") +
                        "." + extension);

        File file = new File(path);
        if(!file.exists()) {
            File parentFile = file.getParentFile();
            if(!parentFile.exists()) {
                parentFile.mkdirs();
            }
            return path;
        }
        return generateFilename(user, contextRootPath, extension);
    }

}
