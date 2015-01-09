/**
 * auto code generation
 */
package com.sishuok.es.shop.memberrank.web.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sishuok.es.common.web.controller.BaseCRUDController;
import com.sishuok.es.shop.memberrank.entity.MemberRank;

/**
 * 会员等级管理Controller
 * @author xxs
 * @version 2015-01-03
 */
 
@Controller("adminMemberRankController")
@RequestMapping(value = "/admin/shop/member_rank")
public class MemberRankController extends BaseCRUDController<MemberRank, Long> {

    public MemberRankController() {
        setResourceIdentity("shop:memberRank");
    }
}