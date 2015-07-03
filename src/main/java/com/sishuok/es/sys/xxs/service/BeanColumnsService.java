/**
 * auto code generation
 */
package com.sishuok.es.sys.xxs.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sishuok.es.common.entity.search.SearchOperator;
import com.sishuok.es.common.entity.search.Searchable;
import com.sishuok.es.common.service.BaseService;
import com.sishuok.es.sys.xxs.entity.BeanColumns;

/**
 * @author xxs
 * @version 2015-03-22
 */

@Service
public class BeanColumnsService extends BaseService<BeanColumns, Long> {

	public void save(Long[] ids, List<BeanColumns> attributes) {
		baseRepository.delete(ids);
		for (int i = 0; i < attributes.size(); i++) {
			baseRepository.save(attributes.get(i));
		}
	}

	public List<BeanColumns> findByBeansId(Long id) {
		Searchable searchable = Searchable.newSearchable().addSearchFilter(
				"beans.id", SearchOperator.eq, id);
		return baseRepository.findAll(searchable).getContent();
	};
}
