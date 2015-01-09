/**
 * auto code generation
 */
package com.sishuok.es.shop.navigation.repository;

import org.springframework.stereotype.Repository;
import com.sishuok.es.common.repository.BaseRepository;
import com.sishuok.es.shop.navigation.entity.Navigation;

/**
 * 导航管理Repository接口
 * @author xxs
 * @version 2015-01-04
 */
@Repository
public interface NavigationRepository extends BaseRepository<Navigation, Long> {
	
}
