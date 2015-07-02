/**
 * auto code generation
 */
package com.sishuok.es.sys.xxs.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sishuok.es.common.entity.search.SearchOperator;
import com.sishuok.es.common.entity.search.Searchable;
import com.sishuok.es.common.service.BaseService;
import com.sishuok.es.sys.xxs.entity.XxsAttribute;

/**
 * 不知道叫什么功能，可耻的用了自己的名字Service
 * 
 * @author xxs
 * @version 2015-03-22
 */

@Service
public class XxsAttributeService extends BaseService<XxsAttribute, Long> {

	public void save(Long[] ids, List<XxsAttribute> attributes) {
		baseRepository.delete(ids);
		for (int i = 0; i < attributes.size(); i++) {
			baseRepository.save(attributes.get(i));
		}
	}

	public List<XxsAttribute> findByXxsId(Long id) {
		Searchable searchable = Searchable.newSearchable().addSearchFilter(
				"xxs.id", SearchOperator.eq, id);
		return baseRepository.findAll(searchable).getContent();
	};
}
