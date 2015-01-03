/**
 * auto code generation
 */
package com.sishuok.es.shop.member.web.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sishuok.es.common.web.controller.BaseCRUDController;
import com.sishuok.es.shop.member.entity.Member;

/**
 * 会员管理Controller
 * @author xxs
 * @version 2015-01-03
 */
 
@Controller
@RequestMapping(value = "/admin/shop/member")
public class MemberController extends BaseCRUDController<Member, Long> {

    public MemberController() {
        setResourceIdentity("shop:member");
    }
}