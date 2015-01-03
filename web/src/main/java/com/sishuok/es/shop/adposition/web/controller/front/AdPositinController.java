/**
 * auto code generation
 */
package com.sishuok.es.shop.adposition.web.controller.front;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sishuok.es.common.web.controller.BaseCRUDController;
import com.sishuok.es.shop.adposition.entity.AdPositin;

/**
 * 会员管理Controller
 * @author xxs
 * @version 2015-01-03
 */
 
@Controller("frontAdPositinController")
@RequestMapping(value = "/front/shop/ad_position")
public class AdPositinController extends BaseCRUDController<AdPositin, Long> {

}