/**
 * auto code generation
 */
package com.sishuok.es.sys.site.repository;

import org.springframework.stereotype.Repository;
import com.sishuok.es.common.repository.BaseRepository;
import com.sishuok.es.sys.site.entity.Site;

/**
 * 站点管理Repository接口
 * @author xxs
 * @version 2015-03-17
 */
@Repository
public interface SiteRepository extends BaseRepository<Site, Long> {
	
}
