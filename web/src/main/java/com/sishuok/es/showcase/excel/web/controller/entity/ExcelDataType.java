package com.sishuok.es.showcase.excel.web.controller.entity;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 13-7-12 下午8:55
 * <p>Version: 1.0
 */
public enum ExcelDataType {

    csv("CSV"),
    excel2003("Excel 2003"),
    excel2003_sheet("Excel 2003 one sheet per workbook"),
    excel2003_xml("Excel 2003 XML存储"),
    excel2003_usermodel("Excel 2003 usermodel模型"),
    excel2007("Excel 2007");

    private final String info;

    private ExcelDataType(final String info) {
        this.info = info;
    }

    public String getInfo() {
        return info;
    }
}
