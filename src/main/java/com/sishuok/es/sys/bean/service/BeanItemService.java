/**
 * auto code generation
 */
package com.sishuok.es.sys.bean.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sishuok.es.common.entity.search.SearchOperator;
import com.sishuok.es.common.entity.search.Searchable;
import com.sishuok.es.common.service.BaseService;
import com.sishuok.es.sys.bean.entity.BeanItem;

/**
 * @author xxs
 * @version 2015-03-22
 */

@Service
public class BeanItemService extends BaseService<BeanItem, Long> {

	public void save(Long[] ids, List<BeanItem> attributes) {
		baseRepository.delete(ids);
		for (int i = 0; i < attributes.size(); i++) {
			baseRepository.save(attributes.get(i));
		}
	}

	public List<BeanItem> findByBeansId(Long id) {
		Searchable searchable = Searchable.newSearchable().addSearchFilter(
				"bean.id", SearchOperator.eq, id);
		return baseRepository.findAll(searchable).getContent();
	};
}
