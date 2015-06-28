/**
 * auto code generation
 */
package com.sishuok.es.sys.xxs.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.sishuok.es.common.repository.BaseRepository;
import com.sishuok.es.sys.xxs.entity.XxsAttribute;

/**
 * 不知道叫什么功能，可耻的用了自己的名字Repository接口
 * @author xxs
 * @version 2015-03-22
 */
@Repository
public interface XxsAttributeRepository extends BaseRepository<XxsAttribute, Long> {
	
	@Query("from XxsAttribute o where o.xxs.name=?1")
	List<XxsAttribute> findByName(String name);
}
