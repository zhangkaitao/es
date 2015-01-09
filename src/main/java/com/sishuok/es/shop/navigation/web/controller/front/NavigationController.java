/**
 * auto code generation
 */
package com.sishuok.es.shop.navigation.web.controller.front;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sishuok.es.common.web.controller.BaseCRUDController;
import com.sishuok.es.shop.navigation.entity.Navigation;

/**
 * 导航管理Controller
 * @author xxs
 * @version 2015-01-04
 */
 
@Controller("frontNavigationController")
@RequestMapping(value = "/front/shop/navigation")
public class NavigationController extends BaseCRUDController<Navigation, Long> {

}