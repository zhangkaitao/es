/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.es.common.utils;

import org.apache.commons.io.IOUtils;

import java.io.*;

/**
 * 来自网络
 *
 * 判断文件编码 不保证100%正确性 但测试结果是一般的文件没问题
 * <p/>
 * 只能判断常见的GBK，UTF-16LE，UTF-16BE,UTF-8，其分别对应window下的记事本可另存为的编码类型ANSI,Unicode,Unicode big endian,UTF-8
 * <p/>
 * <p/>
 * <p>User: Zhang Kaitao
 * <p>Date: 13-7-5 下午12:09
 * <p>Version: 1.0
 */
public class FileCharset {

    private static final String DEFAULT_CHARSET = "GBK";

    public static String getCharset(String fileName) {
        return getCharset(new File(fileName));
    }

    /**
     * 只能判断常见的GBK，UTF-16LE，UTF-16BE,UTF-8，其分别对应window下的记事本可另存为的编码类型ANSI,Unicode,Unicode big endian,UTF-8
     *
     * @param file
     * @return
     */
    public static String getCharset(File file) {
        InputStream is = null;
        try {
            is = new FileInputStream(file);
            return getCharset(new BufferedInputStream(is));
        } catch (FileNotFoundException e) {
            return DEFAULT_CHARSET;
        } finally {
            IOUtils.closeQuietly(is);
        }
    }
    public static String getCharset(final BufferedInputStream is) {
        String charset = DEFAULT_CHARSET;
        byte[] first3Bytes = new byte[3];
        try {
            boolean checked = false;
            is.mark(0);
            int read = is.read(first3Bytes, 0, 3);
            if (read == -1)
                return charset;
            if (first3Bytes[0] == (byte) 0xFF && first3Bytes[1] == (byte) 0xFE) {
                charset = "UTF-16LE";
                checked = true;
            } else if (first3Bytes[0] == (byte) 0xFE
                    && first3Bytes[1] == (byte) 0xFF) {
                charset = "UTF-16BE";
                checked = true;
            } else if (first3Bytes[0] == (byte) 0xEF
                    && first3Bytes[1] == (byte) 0xBB
                    && first3Bytes[2] == (byte) 0xBF) {
                charset = "UTF-8";
                checked = true;
            }
            is.reset();
            if (!checked) {
                int loc = 0;

                while ((read = is.read()) != -1 && loc < 100) {
                    loc++;
                    if (read >= 0xF0)
                        break;
                    if (0x80 <= read && read <= 0xBF) // 单独出现BF以下的，也算是GBK
                        break;
                    if (0xC0 <= read && read <= 0xDF) {
                        read = is.read();
                        if (0x80 <= read && read <= 0xBF) // 双字节 (0xC0 - 0xDF)
                            // (0x80
                            // - 0xBF),也可能在GB编码内
                            continue;
                        else
                            break;
                    } else if (0xE0 <= read && read <= 0xEF) {// 也有可能出错，但是几率较小
                        read = is.read();
                        if (0x80 <= read && read <= 0xBF) {
                            read = is.read();
                            if (0x80 <= read && read <= 0xBF) {
                                charset = "UTF-8";
                                break;
                            } else
                                break;
                        } else
                            break;
                    }
                }
            }
            is.reset();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return charset;
    }

}
