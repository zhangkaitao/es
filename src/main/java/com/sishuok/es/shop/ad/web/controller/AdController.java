/**
 * auto code generation
 */
package com.sishuok.es.shop.ad.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sishuok.es.common.web.controller.BaseCRUDController;
import com.sishuok.es.shop.ad.entity.Ad;

/**
 * 广告管理Controller
 * @author xxs
 * @version 2015-01-03
 */
 
@Controller
@RequestMapping(value = "/admin/shop/ad")
public class AdController extends BaseCRUDController<Ad, Long> {

    public AdController() {
        setResourceIdentity("shop:ad");
    }
}