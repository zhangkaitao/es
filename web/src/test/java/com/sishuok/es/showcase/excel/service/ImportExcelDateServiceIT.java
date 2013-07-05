/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.es.showcase.excel.service;

import com.google.common.collect.Lists;
import com.sishuok.es.showcase.excel.entity.ExcelData;
import com.sishuok.es.test.BaseIT;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.LineIterator;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.eventusermodel.HSSFEventFactory;
import org.apache.poi.hssf.eventusermodel.HSSFListener;
import org.apache.poi.hssf.eventusermodel.HSSFRequest;
import org.apache.poi.hssf.record.*;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.model.SharedStringsTable;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;
import org.xml.sax.*;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

import java.io.*;
import java.util.Iterator;
import java.util.List;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 13-7-3 下午3:43
 * <p>Version: 1.0
 */
public class ImportExcelDateServiceIT extends BaseIT {

    @Autowired
    private ExcelDataService excelDataService;

    @Autowired
    private PlatformTransactionManager transactionManager;

    private final Logger log = LoggerFactory.getLogger(ImportExcelDateServiceIT.class);


    /**
     * csv格式
     */
    @Test
    public void testImportCsv() throws IOException {

        long beginTime = System.currentTimeMillis();

        File file = new File("D:\\Backup\\test.csv");
        InputStream is = new BufferedInputStream(new FileInputStream(file));
        String encoding = FileCharset.getCharset(file);

        LineIterator iterator = IOUtils.lineIterator(is, encoding);

        String separator = ",";
        int batchSize = 100; //批处理大小
        int totalSize = 1; //总大小

        final List<ExcelData> dataList = Lists.newArrayList();

        if (iterator.hasNext()) {
            iterator.nextLine();//跳过第一行标题
        }

        while (iterator.hasNext()) {
            String line = iterator.nextLine();

            String[] dataArray = StringUtils.split(line, separator);

            ExcelData data = new ExcelData();
            data.setId(Long.valueOf(dataArray[0]));
            data.setContent(dataArray[1]);
            dataList.add(data);

            if (totalSize % batchSize == 0) {
                try {
                    doBatchSave(dataList);
                } catch (Exception e) {
                    Long fromId = dataList.get(0).getId();
                    Long endId = dataList.get(dataList.size() - 1).getId();
                    log.error("from " + fromId + " to " + endId + ", error", e);
                }
                dataList.clear();
            }
            totalSize++;
        }
        IOUtils.closeQuietly(is);

        long endTime = System.currentTimeMillis();
        log.info("耗时(秒):" + (endTime - beginTime) / 1000);

    }


    /**
     * 导入 excel 2003 biff格式
     * 如果是xml格式的 可以使用SAX（未测试）
     */
    @Test
    public void testImportExcel2003() throws Exception {

        long beginTime = System.currentTimeMillis();

        String fileName = "D:\\Backup\\Book1.xls";

        List<ExcelData> dataList = Lists.newArrayList();

        //输入流
        InputStream fis = new BufferedInputStream(new FileInputStream(fileName));
        // 创建 org.apache.poi.poifs.filesystem.Filesystem
        POIFSFileSystem poifs = new POIFSFileSystem(fis);
        // 从输入流 得到 Workbook(excel 部分)流
        InputStream din = poifs.createDocumentInputStream("Workbook");
        // 构造 HSSFRequest
        HSSFRequest req = new HSSFRequest();

        // 添加监听器
        req.addListenerForAllRecords(new Excel2003Listener(dataList));
        //  创建事件工厂
        HSSFEventFactory factory = new HSSFEventFactory();
        // 根据文档输入流处理事件
        factory.processEvents(req, din);
        // 关闭输入流
        fis.close();
        // 关闭文档流
        din.close();

        //把最后剩下的不足batchSize大小
        if(dataList.size() > 0) {
            doBatchSave(dataList);
        }

        long endTime = System.currentTimeMillis();
        log.info("耗时(秒):" + (endTime - beginTime) / 1000);
    }

    class Excel2003Listener implements HSSFListener {



        int batchSize = 100; //批处理大小
        int totalSize = 1; //总大小
        private SSTRecord sstrec;

        List<ExcelData> dataList;
        ExcelData current = null;

        Excel2003Listener(final List<ExcelData> dataList) {
            this.dataList = dataList;
        }

        @Override
        public void processRecord(final Record record) {
            switch (record.getSid()) {
                case BOFRecord.sid :
                    //开始解析到workboot sheet 等
                    BOFRecord bof = (BOFRecord) record;
                    if (bof.getType() == bof.TYPE_WORKBOOK) {
                        //workbook
                    } else if (bof.getType() == bof.TYPE_WORKSHEET) {
                        //sheet
                    }
                    break;
                case BoundSheetRecord.sid :
                    //开始解析BundleSheet
                    BoundSheetRecord bsr = (BoundSheetRecord) record;
                    //bsr.getSheetname() 得到sheet name
                    break;
                case RowRecord.sid :
                    //开始解析行
                    RowRecord rowrec = (RowRecord) record;

                    break;
                case NumberRecord.sid :
                    //解析一个Number类型的单元格值
                    NumberRecord numrec = (NumberRecord) record;
                    //numrec.getRow()  numrec.getColumn()   numrec.getValue()

                    //非第一行 第一列
                    if(numrec.getRow() > 1 && numrec.getColumn() == 0) {
                        current = new ExcelData();
                        current.setId(Double.valueOf(numrec.getValue()).longValue());
                    }
                    break;
                case SSTRecord.sid :
                    // SSTRecords存储了在Excel中使用的所有唯一String的数组
                    sstrec = (SSTRecord) record;
                    break;
                case LabelSSTRecord.sid :
                    //解析一个String类型的单元格值（存储在SSTRecord）
                    LabelSSTRecord lrec = (LabelSSTRecord) record;

                    if(lrec.getRow() > 1 && lrec.getColumn() == 1) {
                        current.setContent(sstrec.getString(lrec.getSSTIndex()).getString());
                        dataList.add(current);
                        totalSize++;
                        //最后一个单元格时 判断是否该写了
                        if(totalSize % batchSize == 0) {
                            doBatchSave(dataList);
                            dataList.clear();
                        }
                    }
                    break;
            }

        }
    }



    @Test
    public void testImportExcel2007() throws Exception {

        long beginTime = System.currentTimeMillis();

        String fileName = "D:\\Backup\\test.xlsx";

        List<ExcelData> dataList = Lists.newArrayList();


        OPCPackage pkg = OPCPackage.open(fileName);
        XSSFReader r = new XSSFReader( pkg );
        SharedStringsTable sst = r.getSharedStringsTable();

        XMLReader parser =
                XMLReaderFactory.createXMLReader();
        ContentHandler handler = new Excel2007SheetHandler(sst, dataList);
        parser.setContentHandler(handler);

        Iterator<InputStream> sheets = r.getSheetsData();
        while(sheets.hasNext()) {
            InputStream sheet = sheets.next();
            InputSource sheetSource = new InputSource(sheet);
            parser.parse(sheetSource);
            sheet.close();
        }

        long endTime = System.currentTimeMillis();
        log.info("耗时(秒):" + (endTime - beginTime) / 1000);
    }

    class Excel2007SheetHandler extends DefaultHandler {
        private SharedStringsTable sst;
        private String lastContents;
        private boolean nextIsString;

        int batchSize = 100; //批处理大小
        int totalSize = 1; //总大小

        List<ExcelData> dataList;
        ExcelData current = null;

        private Excel2007SheetHandler(SharedStringsTable sst, List<ExcelData> dataList) {
            this.sst = sst;
            this.dataList = dataList;
        }

        public void startElement(String uri, String localName, String name, Attributes attributes) throws SAXException {
            // c => cell
            if(name.equals("c")) {
                // Print the cell reference
                System.out.print(attributes.getValue("r") + " - ");
                // Figure out if the value is an index in the SST
                String cellType = attributes.getValue("t");
                if(cellType != null && cellType.equals("s")) {
                    nextIsString = true;
                } else {
                    nextIsString = false;
                }
            }
            // Clear contents cache
            lastContents = "";
        }

        public void endElement(String uri, String localName, String name) throws SAXException {
            // Process the last contents as required.
            // Do now, as characters() may be called more than once
            if(nextIsString) {
                int idx = Integer.parseInt(lastContents);
                lastContents = new XSSFRichTextString(sst.getEntryAt(idx)).toString();
                nextIsString = false;
            }

            // v => contents of a cell
            // Output after we've seen the string contents
            if(name.equals("v")) {
                System.out.println(lastContents);
            }
        }

        public void characters(char[] ch, int start, int length) throws SAXException {
            lastContents += new String(ch, start, length);
        }
    }

    private void doBatchSave(final List<ExcelData> dataList) {
        DefaultTransactionDefinition td = new DefaultTransactionDefinition();
        td.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        new TransactionTemplate(transactionManager, td).execute(new TransactionCallback<Object>() {
            @Override
            public Object doInTransaction(final TransactionStatus status) {
                for (ExcelData data : dataList) {
                    excelDataService.save(data);
                }
                return null;
            }
        });
    }


}
