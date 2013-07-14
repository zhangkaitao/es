/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.es.showcase.excel.service;

import com.sishuok.es.showcase.excel.entity.ExcelData;
import org.apache.poi.hssf.eventusermodel.HSSFListener;
import org.apache.poi.hssf.record.*;

import java.util.List;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 13-7-12 下午9:47
 * <p>Version: 1.0
 */
class Excel2003ImportListener implements HSSFListener {


    int batchSize; //批处理大小
    int totalSize = 0; //总大小
    private SSTRecord sstrec;

    List<ExcelData> dataList;
    ExcelData current = null;
    private ExcelDataService excelDataService;

    Excel2003ImportListener(
            final ExcelDataService excelDataService, final List<ExcelData> dataList, final int batchSize) {
        this.excelDataService = excelDataService;
        this.dataList = dataList;
        this.batchSize = batchSize;
    }

    @Override
    public void processRecord(final Record record) {
        switch (record.getSid()) {
            case BOFRecord.sid:
                //开始解析到workboot sheet 等
                BOFRecord bof = (BOFRecord) record;
                if (bof.getType() == bof.TYPE_WORKBOOK) {
                    //workbook
                } else if (bof.getType() == bof.TYPE_WORKSHEET) {
                    //sheet
                }
                break;
            case BoundSheetRecord.sid:
                //开始解析BundleSheet
                BoundSheetRecord bsr = (BoundSheetRecord) record;
                //bsr.getSheetname() 得到sheet name
                break;
            case RowRecord.sid:
                //开始解析行
                RowRecord rowrec = (RowRecord) record;
                break;
            case SSTRecord.sid:
                // SSTRecords存储了在Excel中使用的所有唯一String的数组
                sstrec = (SSTRecord) record;
                break;
            case NumberRecord.sid:
            case LabelSSTRecord.sid:
                if(record instanceof NumberRecord) {
                    //解析一个Number类型的单元格值
                    NumberRecord numrec = (NumberRecord) record;
                    //numrec.getRow()  numrec.getColumn()   numrec.getValue()

                    if(numrec.getRow() == 0) {
                        //第一行 跳过
                        break;
                    } else if(numrec.getColumn() == 0) { //第一列
                        current = new ExcelData();
                        current.setId(Double.valueOf(numrec.getValue()).longValue());
                    } else if(numrec.getColumn() == 1) {//第二列
                        current.setContent(String.valueOf(Double.valueOf(numrec.getValue()).longValue()));
                        add(current);
                    }
                    break;

                } else if(record instanceof LabelSSTRecord) {
                    //解析一个String类型的单元格值（存储在SSTRecord）
                    LabelSSTRecord lrec = (LabelSSTRecord) record;

                    if(lrec.getRow() == 0) {
                        //第一行 跳过
                        break;
                    } else if(lrec.getColumn() == 0) { //第一列
                        current = new ExcelData();
                        String value = sstrec.getString(lrec.getSSTIndex()).getString();
                        current.setId(Double.valueOf(value).longValue());
                    } else if(lrec.getColumn() == 1) {//第二列
                        String value = sstrec.getString(lrec.getSSTIndex()).getString();
                        current.setContent(value);
                        add(current);
                    }
                    break;
                }
                break;
        }

    }

    private void add(final ExcelData current) {

        dataList.add(current);

        totalSize++;

        //最后一个单元格时 判断是否该写了
        if (totalSize % batchSize == 0) {
            excelDataService.doBatchSave(dataList);
            dataList.clear();
        }
    }
}
