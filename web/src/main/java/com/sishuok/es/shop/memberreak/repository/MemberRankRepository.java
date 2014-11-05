/**
 * auto code generation
 */
package com.sishuok.es.shop.memberreak.repository;

import org.springframework.stereotype.Repository;
import com.sishuok.es.common.repository.BaseRepository;
import com.sishuok.es.shop.memberreak.entity.MemberRank;

/**
 * 会员等级Repository接口
 * @author xxs
 * @version 2014-11-05
 */
@Repository
public interface MemberRankRepository extends BaseRepository<MemberRank, Long> {
	
}
