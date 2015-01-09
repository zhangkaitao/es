/**
 * auto code generation
 */
package com.sishuok.es.shop.adposition.web.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sishuok.es.common.web.controller.BaseCRUDController;
import com.sishuok.es.shop.adposition.entity.AdPositin;

/**
 * 会员管理Controller
 * @author xxs
 * @version 2015-01-03
 */
 
@Controller("adminAdPositinController")
@RequestMapping(value = "/admin/shop/ad_position")
public class AdPositinController extends BaseCRUDController<AdPositin, Long> {

    public AdPositinController() {
        setResourceIdentity("shop:adPosition");
    }
}