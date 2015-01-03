/**
 * auto code generation
 */
package com.sishuok.es.shop.ad.repository;

import org.springframework.stereotype.Repository;
import com.sishuok.es.common.repository.BaseRepository;
import com.sishuok.es.shop.ad.entity.Ad;

/**
 * 广告管理Repository接口
 * @author xxs
 * @version 2015-01-03
 */
@Repository
public interface AdRepository extends BaseRepository<Ad, Long> {
	
}
