/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.es.front.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 会话心跳检测
 * <p>User: Zhang Kaitao
 * <p>Date: 13-3-23 下午4:22
 * <p>Version: 1.0
 */
@Controller
@RequestMapping(value = "/session/heartbeat")
public class ActiveSessionController {


    @RequestMapping
    @ResponseBody
    public String heartbeat() {
        return "";
    }

}
