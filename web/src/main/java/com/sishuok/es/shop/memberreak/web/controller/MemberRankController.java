/**
 * auto code generation
 */
package com.sishuok.es.shop.memberreak.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sishuok.es.common.web.controller.BaseCRUDController;
import com.sishuok.es.shop.memberreak.entity.MemberRank;

/**
 * 会员等级Controller
 * @author xxs
 * @version 2014-11-05
 */
 
@Controller
@RequestMapping(value = "/admin/shop/member_reak")
public class MemberRankController extends BaseCRUDController<MemberRank, Long> {

    public MemberRankController() {
        setResourceIdentity("shop:memberRank");
    }
}