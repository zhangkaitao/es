/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.es.maintain.editor.web.controller.utils;

import com.google.common.collect.Maps;
import com.sishuok.es.common.Constants;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.data.domain.Sort;

import java.io.File;
import java.io.FileFilter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 13-6-10 下午12:25
 * <p>Version: 1.0
 */
public class OnlineEditorUtils {

    private static final String DATE_PATTERN = "yyyy-MM-dd hh:mm:ss";
    private static final String CSS_DIRECTORY = "ztree_folder";
    private static final String CSS_FILE = "ztree_file";
    private static final FileFilter DIRECTORY_FILTER = new FileFilter() {
        @Override
        public boolean accept(File file) {
            return file.isDirectory();
        }
    };


    public static Map<Object, Object> extractFileInfoMap(File currentFile, String rootPath, long id, long parentId)
            throws UnsupportedEncodingException {
        Map<Object, Object> info = extractFileInfoMap(currentFile, rootPath);
        info.put("id", id);
        info.put("pId", parentId);
        return info;
    }

    public static Map<Object, Object> extractFileInfoMap(File currentFile, String rootPath)
            throws UnsupportedEncodingException {
        Map<Object, Object> info = Maps.newHashMap();
        String name = currentFile.getName();
        info.put("name", name);
        info.put("path", URLEncoder.encode(currentFile.getAbsolutePath().replace(rootPath, ""), Constants.ENCODING));
        info.put("canEdit", canEdit(name));
        info.put("hasParent", !currentFile.getPath().equals(rootPath));
        info.put("isParent", hasSubFiles(currentFile));
        info.put("isDirectory", currentFile.isDirectory());
        info.put("root", info.get("path").equals(""));
        info.put("open", info.get("path").equals(""));
        info.put("iconSkin", currentFile.isDirectory() ? CSS_DIRECTORY : CSS_FILE);
        info.put("size", currentFile.length());
        Date modifiedDate = new Date(currentFile.lastModified());
        info.put("lastModified", DateFormatUtils.format(modifiedDate, DATE_PATTERN));
        info.put("lastModifiedForLong", currentFile.lastModified());
        return info;
    }

    private static final String[] CAN_EDIT_EXTENSION = new String[] {
        "js", "css", "html", "htm", "jsp", "jspx", "tld", "tag", "xml", "properties", "txt"
    };

    private static boolean canEdit(String name) {
        name = name.toLowerCase();
       for(String extension : CAN_EDIT_EXTENSION) {
           if(name.endsWith(extension)) {
               return true;
           }
       }
        return false;
    }

    public static boolean hasParent(File currentFile, String rootPath) {
        return !currentFile.getPath().equals(rootPath);
    }

    public static boolean hasSubFiles(File file) {
        File[] subFiles = file.listFiles(DIRECTORY_FILTER);
        return subFiles != null && subFiles.length > 0;
    }


    public static void sort(final List<Map<Object, Object>> files, final Sort sort) {

        Collections.sort(files, new Comparator<Map<Object, Object>>() {
            @Override
            public int compare(Map<Object, Object> o1, Map<Object, Object> o2) {
                if (sort == null) {
                    return 0;
                }
                Sort.Order nameOrder = sort.getOrderFor("name");
                if (nameOrder != null) {
                    String n1 = (String) o1.get("name");
                    String n2 = (String) o2.get("name");
                    Boolean n1IsDirecoty = (Boolean)o1.get("isDirectory");
                    Boolean n2IsDirecoty = (Boolean)o2.get("isDirectory");

                    if(n1IsDirecoty.equals(Boolean.TRUE) && n2IsDirecoty.equals(Boolean.FALSE)) {
                        return -1;
                    } else if(n1IsDirecoty.equals(Boolean.FALSE) && n2IsDirecoty.equals(Boolean.TRUE)) {
                        return 1;
                    }

                    if (nameOrder.getDirection() == Sort.Direction.ASC) {
                        return n1.compareTo(n2);
                    } else {
                        return -n1.compareTo(n2);
                    }
                }

                Sort.Order lastModifiedOrder = sort.getOrderFor("lastModified");
                if (lastModifiedOrder != null) {
                    Long l1 = (Long) o1.get("lastModifiedForLong");
                    Long l2 = (Long) o2.get("lastModifiedForLong");
                    if (lastModifiedOrder.getDirection() == Sort.Direction.ASC) {
                        return l1.compareTo(l2);
                    } else {
                        return -l1.compareTo(l2);
                    }
                }

                Sort.Order sizeOrder = sort.getOrderFor("size");
                if (sizeOrder != null) {
                    Long s1 = (Long) o1.get("size");
                    Long s2 = (Long) o2.get("size");
                    if (sizeOrder.getDirection() == Sort.Direction.ASC) {
                        return s1.compareTo(s2);
                    } else {
                        return -s1.compareTo(s2);
                    }
                }


                return 0;
            }
        });

    }


}
