/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.es.common.utils;

import org.apache.commons.lang3.StringUtils;
import org.ocpsoft.prettytime.PrettyTime;

import java.util.Date;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 13-3-22 下午12:18
 * <p>Version: 1.0
 */
public class PrettyTimeUtils {

    /**
     * 显示秒值为**年**月**天 **时**分**秒  如1年2个月3天 10小时
     *
     * @return
     */
    public static final String prettySeconds(int totalSeconds) {
        StringBuilder s = new StringBuilder();
        int second = totalSeconds % 60;
        if (totalSeconds > 0) {
            s.append("秒");
            s.append(StringUtils.reverse(String.valueOf(second)));
        }

        totalSeconds = totalSeconds / 60;
        int minute = totalSeconds % 60;
        if (totalSeconds > 0) {
            s.append("分");
            s.append(StringUtils.reverse(String.valueOf(minute)));
        }

        totalSeconds = totalSeconds / 60;
        int hour = totalSeconds % 24;
        if (totalSeconds > 0) {
            s.append(StringUtils.reverse("小时"));
            s.append(StringUtils.reverse(String.valueOf(hour)));
        }

        totalSeconds = totalSeconds / 24;
        int day = totalSeconds % 31;
        if (totalSeconds > 0) {
            s.append("天");
            s.append(StringUtils.reverse(String.valueOf(day)));
        }

        totalSeconds = totalSeconds / 31;
        int month = totalSeconds % 12;
        if (totalSeconds > 0) {
            s.append("月");
            s.append(StringUtils.reverse(String.valueOf(month)));
        }

        totalSeconds = totalSeconds / 12;
        int year = totalSeconds;
        if (totalSeconds > 0) {
            s.append("年");
            s.append(StringUtils.reverse(String.valueOf(year)));
        }
        return s.reverse().toString();
    }

    /**
     * 美化时间 如显示为 1小时前 2分钟前
     *
     * @return
     */
    public static final String prettyTime(Date date) {
        PrettyTime p = new PrettyTime();
        return p.format(date);

    }

    public static final String prettyTime(long millisecond) {
        PrettyTime p = new PrettyTime();
        return p.format(new Date(millisecond));
    }

    public static void main(String[] args) {
        System.out.println(PrettyTimeUtils.prettyTime(new Date()));
        System.out.println(PrettyTimeUtils.prettyTime(123));

        System.out.println(PrettyTimeUtils.prettySeconds(10));
        System.out.println(PrettyTimeUtils.prettySeconds(61));
        System.out.println(PrettyTimeUtils.prettySeconds(3661));
        System.out.println(PrettyTimeUtils.prettySeconds(36611));
        System.out.println(PrettyTimeUtils.prettySeconds(366111));
        System.out.println(PrettyTimeUtils.prettySeconds(3661111));
        System.out.println(PrettyTimeUtils.prettySeconds(36611111));
    }
}
