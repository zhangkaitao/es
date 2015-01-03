/**
 * auto code generation
 */
package com.sishuok.es.shop.memberrank.web.controller.front;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sishuok.es.common.web.controller.BaseCRUDController;
import com.sishuok.es.shop.memberrank.entity.MemberRank;

/**
 * 会员等级管理Controller
 * @author xxs
 * @version 2015-01-03
 */
 
@Controller("frontMemberRankController")
@RequestMapping(value = "/front/shop/member_rank")
public class MemberRankController extends BaseCRUDController<MemberRank, Long> {

}