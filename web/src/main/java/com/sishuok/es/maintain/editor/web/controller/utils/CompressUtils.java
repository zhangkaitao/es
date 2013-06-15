/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.es.maintain.editor.web.controller.utils;

import com.google.common.collect.Lists;
import org.apache.commons.compress.archivers.zip.Zip64Mode;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
import org.apache.commons.compress.archivers.zip.ZipFile;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.util.Enumeration;
import java.util.List;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 13-6-14 下午3:37
 * <p>Version: 1.0
 */
public class CompressUtils {

    public static final void zip(String compressPath, String[] needCompressPaths) {
        File compressFile = new File(compressPath);

        List<File> files = Lists.newArrayList();
        for (String needCompressPath : needCompressPaths) {
            File needCompressFile = new File(needCompressPath);
            if (!needCompressFile.exists()) {
                continue;
            }
            files.add(needCompressFile);
        }
        try {
            ZipArchiveOutputStream zaos = null;
            try {
                zaos = new ZipArchiveOutputStream(compressFile);
                zaos.setUseZip64(Zip64Mode.AsNeeded);
                zaos.setEncoding("GBK");

                for (File file : files) {
                    addFilesToCompression(zaos, file, "");
                }

            } catch (IOException e) {
                throw e;
            } finally {
                IOUtils.closeQuietly(zaos);
            }
        } catch (Exception e) {
            FileUtils.deleteQuietly(compressFile);
            throw new RuntimeException("压缩失败", e);
        }
    }

    private static void addFilesToCompression(ZipArchiveOutputStream zaos, File file, String dir) throws IOException {

        ZipArchiveEntry zipArchiveEntry = new ZipArchiveEntry(file, dir + file.getName());
        zaos.putArchiveEntry(zipArchiveEntry);

        if (file.isFile()) {
            BufferedInputStream bis = null;
            try {
                bis = new BufferedInputStream(new FileInputStream(file));
                IOUtils.copy(bis, zaos);
                zaos.closeArchiveEntry();
            } catch (IOException e) {
                throw e;
            } finally {
                IOUtils.closeQuietly(bis);
            }
        } else if (file.isDirectory()) {
            zaos.closeArchiveEntry();

            for (File childFile : file.listFiles()) {
                addFilesToCompression(zaos, childFile, dir + file.getName() + File.separator);
            }
        }
    }

    public static void unzip(String path, String descPath, boolean override) {
        File uncompressFile = new File(path);
        File descPathFile = null;

        try {
            descPathFile = new File(descPath);
            unzipFolder(uncompressFile, descPathFile, override);
        } catch (RuntimeException e) {
            throw e;
        }
    }

    @SuppressWarnings("unchecked")
    private static void unzipFolder(File uncompressFile, File descPathFile, boolean override) {

        ZipFile zipFile = null;
        try {
            zipFile = new ZipFile(uncompressFile, "GBK");

            Enumeration<ZipArchiveEntry> entries = zipFile.getEntries();
            while (entries.hasMoreElements()) {
                ZipArchiveEntry zipEntry = entries.nextElement();
                String name = zipEntry.getName();
                name = name.replace("\\", "/");

                File currentFile = new File(descPathFile, name);

                //非覆盖 跳过
                if(currentFile.isFile() && currentFile.exists() && !override) {
                    continue;
                }

                if(name.endsWith("/")) {
                    currentFile.mkdirs();
                    continue;
                } else {
                    currentFile.getParentFile().mkdirs();
                }

                FileOutputStream fos = null;
                try {
                    fos = new FileOutputStream(currentFile);
                    InputStream is = zipFile.getInputStream(zipEntry);
                    IOUtils.copy(is, fos);
                } finally {
                    IOUtils.closeQuietly(fos);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("解压缩失败", e);
        } finally {
            if (zipFile != null) {
                try {
                    zipFile.close();
                } catch (IOException e) {
                }
            }
        }

    }


    public static void main(String[] args) {
//        String[] needCompressFilePath = new String[]{
//                "C:\\Documents and Settings\\Administrator\\桌面\\JEE入门",
//                "C:\\Documents and Settings\\Administrator\\桌面\\learn-spring",
//                "C:\\Documents and Settings\\Administrator\\桌面\\XML.doc"
//        };
//
//        String compressPath = "C:\\Documents and Settings\\Administrator\\桌面\\中文.tar.gz";

//        zip(compressPath, needCompressFilePath);

//        unzip("C:\\Documents and Settings\\Administrator\\桌面\\新建文件夹\\中文.tar.gz", "C:\\Documents and Settings\\Administrator\\桌面\\新建文件夹");

    }
}
