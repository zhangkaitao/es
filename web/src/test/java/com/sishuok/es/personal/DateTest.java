/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.es.personal;

import org.junit.Assert;
import org.junit.Test;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 13-5-26 下午5:43
 * <p>Version: 1.0
 */
public class DateTest {
    //when only millisecond part is different

    @Test
    public void testDateAfter() {
        Date d1 = new Date(1369461400000L);
        Date d2 = new Date(1369461400001L);
        Assert.assertTrue(d2.after(d1));
    }


    @Test
    public void testTimestampAfterOK() {
        Timestamp d1 = new Timestamp(1369461400000L);
        Timestamp d2 = new Timestamp(1369461400001L);
        Assert.assertTrue(d2.after(d1));
    }

    @Test
    public void testTimestampAfterOK2() {
        Date d1 = new Timestamp(1369461400000L);
        Timestamp d2 = new Timestamp(1369461400001L);
        Assert.assertFalse(d2.after(d1));
    }

    @Test
    public void testTimestampAfterOK3() {
        Timestamp d1 = new Timestamp(1369461400000L);
        Date d2 = new Timestamp(1369461400001L);
        Assert.assertFalse(d2.after(d1));
    }



    @Test
    public void testTimestampCastToDateAfterFail() {
        Date d1 = new Timestamp(1369461400000L);
        Date d2 = new Timestamp(1369461400001L);

        Assert.assertFalse(d2.after(d1));
    }

    @Test
    public void testDateCompare() {
        Date d1 = new Date(1369461400000L);
        Date d2 = new Date(1369461400001L);
        Assert.assertTrue(d2.compareTo(d1) == 1);
    }

    public static void main(String[] args) throws ParseException {
        Date date = new Date();
        java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd");
        java.util.Date beginDate = format.parse("1900-01-01");
        java.util.Date endDate = format.parse("1970-01-01");
        long d = (endDate.getTime() - beginDate.getTime()) / (24 * 60 * 60 * 1000);
        System.out.println("相隔的天数=" + d);


        System.out.println(secondToDate(0L));

        System.out.println(stringToDate("5", "dd"));
    }

    @Test
    public void testDate2Timestamp() throws ParseException {

        Date date = new Date();

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss", Locale.ENGLISH);
        dateFormat.setLenient(false);
        Date timeDate = dateFormat.parse(dateFormat.format(date));
        Timestamp dateTime = new Timestamp(timeDate.getTime());


    }

    //传入0按理应该输出Mon Jan 01 00:00:00 CST 1900，但却输出Mon Jan 01 08:05:52 CST 1900
    public static Date secondToDate(Long l) {
        return new Date(l * 1000 - 2208988800000L);
    }

    public static Date stringToDate(String s, String format)
            throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.parse(s);
    }



    @Test
    public void testTimestampCompareOK() {
        Timestamp d1 = new Timestamp(1369461400000L);
        Timestamp d2 = new Timestamp(1369461400001L);
        Assert.assertTrue(d2.compareTo(d1) == 1);
    }


    @Test
    public void testTimestampCastToDateCompareOK() {
        Date d1 = new Timestamp(1369461400000L);
        Date d2 = new Timestamp(1369461400001L);
        Assert.assertTrue(d2.compareTo(d1) == 1);
    }

    @Test
       public void testTimestampCastToDateCompareOK2() {
        Date d1 = new Timestamp(1369461400000L);
        Timestamp d2 = new Timestamp(1369461400001L);
        Assert.assertTrue(d2.compareTo(d1) == 1);
    }

    @Test
    public void testTimestampCastToDateCompareOK3() {
        Timestamp d1 = new Timestamp(1369461400000L);
        Date d2 = new Timestamp(1369461400001L);
        Assert.assertTrue(d2.compareTo(d1) == 1);
    }
}
