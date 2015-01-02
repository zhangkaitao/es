package com.sishuok.es.front;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller("frontIndexController")
@RequestMapping("/view")
public class IndexController {

    @RequestMapping(value = "/index")
    public String index(Model model) {
    	model.addAttribute("xxx", "123123");
    	System.out.println("kjkkkkkkkkkkkkkkkkkkkkk");
        return "front/index";
    }
}
