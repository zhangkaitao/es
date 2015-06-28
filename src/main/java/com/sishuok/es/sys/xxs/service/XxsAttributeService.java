/**
 * auto code generation
 */
package com.sishuok.es.sys.xxs.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sishuok.es.common.service.BaseService;
import com.sishuok.es.sys.xxs.entity.XxsAttribute;
import com.sishuok.es.sys.xxs.repository.XxsAttributeRepository;

/**
 * 不知道叫什么功能，可耻的用了自己的名字Service
 * @author xxs
 * @version 2015-03-22
 */
 
@Service
public class XxsAttributeService extends BaseService<XxsAttribute, Long> {
	

	private XxsAttributeRepository getXxsAttributeRepository() {
        return (XxsAttributeRepository) baseRepository;
    }
	
	public void save(List<XxsAttribute> attributes) {
		for (int i = 0; i < attributes.size(); i++) {
			baseRepository.save(attributes.get(i));
		}
    }
	
	public List<XxsAttribute> findByName(String classname){
		return getXxsAttributeRepository().findByName(classname);
	};
}
