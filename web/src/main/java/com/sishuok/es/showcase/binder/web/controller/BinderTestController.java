/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.es.showcase.binder.web.controller;

import com.sishuok.es.sys.user.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 13-6-16 下午5:09
 * <p>Version: 1.0
 */
@Controller
@RequestMapping("/binder")
public class BinderTestController {

    //http://localhost:9080/es-web/binder/test1?user1.id=1&user2.id=2
    @RequestMapping("/test1")
    public String test1(@ModelAttribute("user1") User user1, @ModelAttribute("user2") User user2) {
        System.out.println(user1);
        System.out.println(user2);
        return "";
    }


    //http://localhost:9080/es-web/binder/test2?user3.id=1&user3.username=123
    @RequestMapping("/test2")
    public String test2(@ModelAttribute("user3") User user3) {
        System.out.println(user3);
        return "";
    }


    @RequestMapping("/test3")
    public String test3(@ModelAttribute("user4") User user3) {
        System.out.println(user3);
        return "";
    }

    @InitBinder("user1")
    public void initBinder1(WebDataBinder binder) {
        binder.setFieldDefaultPrefix("user1.");
    }

    @InitBinder("user2")
    public void initBinder2(WebDataBinder binder) {
        binder.setFieldDefaultPrefix("user2.");
    }


    @InitBinder("user3")
    public void initBinder3(WebDataBinder binder) {
        binder.setFieldDefaultPrefix("user3.");
        binder.setAllowedFields("id");
    }


    @InitBinder("user4")
    public void initBinder4(WebDataBinder binder) {
        binder.setFieldDefaultPrefix("user4.");
        binder.setDisallowedFields("id");

    }


}
