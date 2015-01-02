package com.sishuok.es.front.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sishuok.es.common.web.controller.BaseController;

@Controller("frontIndexController")
@RequestMapping(value = "/view/front")
public class IndexController extends BaseController{

    @RequestMapping(value = "/index")
    public String index(Model model) {
    	model.addAttribute("xxx", "123123");
    	System.out.println("kjkkkkkkkkkkkkkkkkkkkkk");
        return viewName("index");
    }
}
