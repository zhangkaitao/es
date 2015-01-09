/**
 * auto code generation
 */
package com.sishuok.es.shop.navigation.web.controller.admin;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sishuok.es.common.Constants;
import com.sishuok.es.common.plugin.web.controller.BaseTreeableController;
import com.sishuok.es.shop.navigation.entity.Navigation;

/**
 * 导航管理Controller
 * @author xxs
 * @version 2015-01-04
 */
 
@Controller("adminNavigationController")
@RequestMapping(value = "/admin/shop/navigation")
public class NavigationController extends BaseTreeableController<Navigation, Long> {

    public NavigationController() {
    	setResourceIdentity("shop:navigation");
    }
    
    @RequestMapping(value = "/changeStatus/{newStatus}")
    public String changeStatus(
            HttpServletRequest request,
            @PathVariable("newStatus") Boolean newStatus,
            @RequestParam("ids") Long[] ids,
            RedirectAttributes redirectAttributes
    ) {


        this.permissionList.assertHasUpdatePermission();

        for (Long id : ids) {
        	Navigation navigation = baseService.findOne(id);
        	navigation.setShow(newStatus);
            baseService.update(navigation);
        }
        redirectAttributes.addFlashAttribute(Constants.MESSAGE, "操作成功！");

        return "redirect:" + request.getAttribute(Constants.BACK_URL);
    }
}