/**
 * auto code generation
 */
package com.sishuok.es.shop.member.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sishuok.es.common.web.controller.BaseCRUDController;
import com.sishuok.es.shop.member.entity.Member;

/**
 * 会员Controller
 * @author xxs
 * @version 2014-11-03
 */
 
@Controller
@RequestMapping(value = "/shop/member")
public class MemberController extends BaseCRUDController<Member, Long> {

    public MemberController() {
        setResourceIdentity("shop:member");
    }
}