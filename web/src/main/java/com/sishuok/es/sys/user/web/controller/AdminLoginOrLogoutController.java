/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.es.sys.user.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 13-3-16 下午1:41
 * <p>Version: 1.0
 */
@Controller
@RequestMapping("/admin")
public class AdminLoginOrLogoutController {

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginForm() {
        return "admin/login";
    }


}
