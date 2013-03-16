/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.es.web.admin.index.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 13-1-18 下午10:15
 * <p>Version: 1.0
 */
@Controller
@RequestMapping("/admin")
public class IndexController {

    @RequestMapping(value = "/index")
    public String index() {
        return "admin/index/index";
    }

    @RequestMapping(value = "/welcome")
    public String welcome() {
        return "admin/index/welcome";
    }


}
