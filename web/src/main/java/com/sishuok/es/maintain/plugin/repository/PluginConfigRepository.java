/**
 * auto code generation
 */
package com.sishuok.es.maintain.plugin.repository;

import org.springframework.stereotype.Repository;
import com.sishuok.es.common.repository.BaseRepository;
import com.sishuok.es.maintain.plugin.entity.PluginConfig;

/**
 * 插件Repository接口
 * @author xxs
 * @version 2014-11-03
 */
@Repository
public interface PluginConfigRepository extends BaseRepository<PluginConfig, Long> {
	
}
