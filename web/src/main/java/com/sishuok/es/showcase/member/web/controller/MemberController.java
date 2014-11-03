/**
 * auto code generation
 */
package com.sishuok.es.showcase.member.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sishuok.es.common.web.controller.BaseCRUDController;
import com.sishuok.es.showcase.member.entity.Member;

/**
 * 会员Controller
 * @author xxs
 * @version 2014-11-01
 */
 
@Controller
@RequestMapping(value = "/showcase/member")
public class MemberController extends BaseCRUDController<Member, Long> {

    public MemberController() {
        setResourceIdentity("showcase:member");
    }
}