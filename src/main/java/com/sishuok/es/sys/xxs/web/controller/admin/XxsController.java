/**
 * auto code generation
 */
package com.sishuok.es.sys.xxs.web.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sishuok.es.common.web.controller.BaseCRUDController;
import com.sishuok.es.sys.xxs.entity.Xxs;

/**
 * 不知道叫什么功能，可耻的用了自己的名字Controller
 * @author xxs
 * @version 2015-03-22
 */
 
@Controller("adminXxsController")
@RequestMapping(value = "/admin/sys/xxs")
public class XxsController extends BaseCRUDController<Xxs, Long> {

    public XxsController() {
        setResourceIdentity("sys:xxs");
    }
}