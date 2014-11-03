/**
 * auto code generation
 */
package com.sishuok.es.maintain.plugin.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sishuok.es.common.web.controller.BaseCRUDController;
import com.sishuok.es.maintain.plugin.entity.PluginConfig;

/**
 * 插件Controller
 * @author xxs
 * @version 2014-11-03
 */
 
@Controller
@RequestMapping(value = "/maintain/plugin")
public class PluginConfigController extends BaseCRUDController<PluginConfig, Long> {

    public PluginConfigController() {
        setResourceIdentity("maintain:plugin");
    }
}