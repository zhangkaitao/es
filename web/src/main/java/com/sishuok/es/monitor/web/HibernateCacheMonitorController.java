/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.es.monitor.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 13-5-27 下午6:50
 * <p>Version: 1.0
 */
@Controller
public class HibernateCacheMonitorController {

    @RequestMapping("/monitor")
    public String monitor(Long userId) {
        System.out.println(userId);
        return "monitor";
    }


}
