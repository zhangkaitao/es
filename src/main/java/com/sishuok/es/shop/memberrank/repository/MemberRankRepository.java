/**
 * auto code generation
 */
package com.sishuok.es.shop.memberrank.repository;

import org.springframework.stereotype.Repository;
import com.sishuok.es.common.repository.BaseRepository;
import com.sishuok.es.shop.memberrank.entity.MemberRank;

/**
 * 会员等级管理Repository接口
 * @author xxs
 * @version 2015-01-03
 */
@Repository
public interface MemberRankRepository extends BaseRepository<MemberRank, Long> {
	
}
