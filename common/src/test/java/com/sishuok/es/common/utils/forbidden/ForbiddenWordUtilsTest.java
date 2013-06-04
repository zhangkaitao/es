/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.es.common.utils.forbidden;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.util.resource.Resource;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 13-5-27 下午3:25
 * <p>Version: 1.0
 */
public class ForbiddenWordUtilsTest {

    @Test
    public void testReplaceWithDefaultMask() {

        String input = "12职称英语买答案32";
        String expected = "12***32";
        String actual = ForbiddenWordUtils.replace(input);
        Assert.assertEquals(expected, actual);

    }

    @Test
    public void testReplaceWithDefaultMask2() {

        String input = "1264.*学生运动123";
        String expected = "12***123";
        String actual = ForbiddenWordUtils.replace(input);
        Assert.assertEquals(expected, actual);

    }


    @Test
    public void testReplaceWithDefaultMask3() {

        String input = "freenet123";
        String expected = "***123";
        String actual = ForbiddenWordUtils.replace(input);
        Assert.assertEquals(expected, actual);

    }

    @Test
    public void testReplaceWithDefaultMask4() {

        String input = " 海峰 ";
        String expected = "***";
        String actual = ForbiddenWordUtils.replace(input);
        Assert.assertEquals(expected, actual);

    }


    @Test
    public void testReplaceWithCustomMask() {
        String input = "12职称英语买答案32";
        String expected = "12###32";
        String actual = ForbiddenWordUtils.replace(input, "###");
        Assert.assertEquals(expected, actual);
    }


    @Test
    public void testContainsForbiddenWord() {
        String input = "12职称英语买答案32";
        Assert.assertTrue(ForbiddenWordUtils.containsForbiddenWord(input));
    }

    @Ignore
    @Test
    public void testFetch() throws Exception {
        String input = "12test32";
        Assert.assertFalse(ForbiddenWordUtils.containsForbiddenWord(input));

        ForbiddenWordUtils.setForbiddenWordFetchURL("http://localhost:10090/forbidden-test.txt");
        ForbiddenWordUtils.setReloadInterval(500);
        ForbiddenWordUtils.initRemoteFetch();

        Server server = new Server(10090);
        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setDirectoriesListed(true);
        resourceHandler.setBaseResource(Resource.newClassPathResource("."));
        server.setHandler(resourceHandler);
        server.start();

        Thread.sleep(1500);

        Assert.assertTrue(ForbiddenWordUtils.containsForbiddenWord(input));

        server.stop();
    }


}
