/**
 * auto code generation
 */
package com.sishuok.es.shop.member.repository;

import org.springframework.stereotype.Repository;
import com.sishuok.es.common.repository.BaseRepository;
import com.sishuok.es.shop.member.entity.Member;

/**
 * 会员Repository接口
 * @author xxs
 * @version 2014-11-03
 */
@Repository
public interface MemberRepository extends BaseRepository<Member, Long> {
	
}
