/**
 * auto code generation
 */
package com.sishuok.es.sys.bean.repository;

import org.springframework.stereotype.Repository;

import com.sishuok.es.common.repository.BaseRepository;
import com.sishuok.es.sys.bean.entity.BeanItem;

/**
 * @author xxs
 * @version 2015-03-22
 */
@Repository
public interface BeanItemRepository extends BaseRepository<BeanItem, Long> {
	
}
