/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.es.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 13-2-12 上午9:25
 * <p>Version: 1.0
 */
public class LogUtils {

    private static final Logger ACCESS_LOG = LoggerFactory.getLogger("access-log");
    private static final Logger ERROR_LOG = LoggerFactory.getLogger("error-log");


    public static final void error(String message, Exception e) {
        ERROR_LOG.error(message, e);
    }

}
