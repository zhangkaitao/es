/**
 * auto code generation
 */
package com.sishuok.es.sys.site.web.controller.front;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sishuok.es.common.web.controller.BaseCRUDController;
import com.sishuok.es.sys.site.entity.Site;

/**
 * 站点管理Controller
 * @author xxs
 * @version 2015-03-17
 */
 
@Controller("frontSiteController")
@RequestMapping(value = "/front/sys/site")
public class SiteController extends BaseCRUDController<Site, Long> {

}