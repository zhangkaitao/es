/**
 * auto code generation
 */
package com.sishuok.es.shop.plugin.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sishuok.es.common.web.controller.BaseCRUDController;
import com.sishuok.es.shop.plugin.entity.PluginConfig;

/**
 * 插件Controller
 * @author xxs
 * @version 2014-11-03
 */
 
@Controller
@RequestMapping(value = "/admin/plugin")
public class PluginConfigController extends BaseCRUDController<PluginConfig, Long> {

    public PluginConfigController() {
        setResourceIdentity("admin:plugin");
    }
}