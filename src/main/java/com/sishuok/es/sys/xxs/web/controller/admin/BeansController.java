/**
 * auto code generation
 */
package com.sishuok.es.sys.xxs.web.controller.admin;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.sishuok.es.common.Constants;
import com.sishuok.es.common.entity.search.Searchable;
import com.sishuok.es.common.utils.SpringUtils;
import com.sishuok.es.common.web.bind.annotation.PageableDefaults;
import com.sishuok.es.common.web.controller.BaseCRUDController;
import com.sishuok.es.sys.xxs.entity.BeanColumns;
import com.sishuok.es.sys.xxs.entity.Beans;
import com.sishuok.es.sys.xxs.service.BeanColumnsService;
import com.sishuok.es.sys.xxs.utils.LoadPackageClasses;

/**
 * 不知道叫什么功能，可耻的用了自己的名字Controller
 * @author xxs
 * @version 2015-03-22
 */
 
@Controller("adminBeansController")
@RequestMapping(value = "/admin/sys/beans/beans")
public class BeansController extends BaseCRUDController<Beans, Long> {

	@Autowired
    private ApplicationContext ctx;
	@Autowired
    private BeanColumnsService beanColumnsService;
	
    public BeansController() {
        //setResourceIdentity("sys:beans");
    }
    
    @RequestMapping(method = RequestMethod.GET)
    @PageableDefaults(sort = "id=desc")
    public String list(Searchable searchable, Model model) {
        if (permissionList != null) {
            this.permissionList.assertHasViewPermission();
        }
        LoadPackageClasses loadPackageClasses = (LoadPackageClasses) SpringUtils.getBean("loadPackageClasses");
        List<Class<?>> classLists = new ArrayList<Class<?>>();
        try {
        	classLists = loadPackageClasses.getClassList();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
        List<Beans> beanLists = baseService.findAll();
        model.addAttribute("beanLists", beanLists);
        model.addAttribute("classLists", classLists);
        return viewName("list");
    }
    
    @RequestMapping(value = "{allclassname}/create", method = RequestMethod.GET)
    public String showCreateForm(@PathVariable("allclassname") String allclassname,Model model) {

        if (permissionList != null) {
            this.permissionList.assertHasCreatePermission();
        }
        System.out.println("当前名字："+allclassname);
        Class<?> loadclass = null ;
        try {
			loadclass = Class.forName(allclassname);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
        Beans beans = new Beans();
        beans.setAllclassname(allclassname);
        beans.setClassname(loadclass.getSimpleName());
        setCommonData(model);
		model.addAttribute("m",beans);
		model.addAttribute("c",loadclass);
		
		
		List<BeanColumns> beanColumns = new ArrayList<BeanColumns>();
		Field[] fields = loadclass.getDeclaredFields();
		BeanColumns bc = null;
		
		for (int i = 0; i < fields.length; i++) {
			bc = new BeanColumns();
			bc.setName(fields[i].getName());
			bc.setJavaType(fields[i].getType().getSimpleName());
			beanColumns.add(bc);
		}
		
		model.addAttribute("beanColumns",beanColumns);
		
		
        
        model.addAttribute(Constants.OP_NAME, "新增");
        if (!model.containsAttribute("m")) {
            model.addAttribute("m", newModel());
        }
        return viewName("editForm");
    }
    
    @RequestMapping(value = "{id}/modify", method = RequestMethod.GET)
    public String showModifyForm(@PathVariable("id") Long id,Model model) {
        if (permissionList != null) {
            this.permissionList.assertHasCreatePermission();
        }
        System.out.println("当前修改的xxs的id："+id);
        
        Beans beans = baseService.findOne(id);
        System.out.println("classname:"+beans.getClassname());
        List<BeanColumns> xas = beanColumnsService.findByBeansId(beans.getId());
        System.out.println(xas.size());
        Class<?> loadclass = null ;
        try {
			loadclass = Class.forName(beans.getAllclassname());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
        
        model.addAttribute("m",beans);
		model.addAttribute("c",loadclass);
		
		List<BeanColumns> xxsAttributes = new ArrayList<BeanColumns>();
		Field[] fields = loadclass.getDeclaredFields();
		BeanColumns xa = null;
		for (int i = 0; i < fields.length; i++) {
			xa = new BeanColumns();
			boolean isture = true;
			for (int j = 0; j < xas.size(); j++) {
				if(xas.get(j).getName().equals(loadclass.getDeclaredFields()[i].getName())){
					xxsAttributes.add(xas.get(j));
					isture = false;
					break;
				}
			}
			if(isture){
				xa.setName(fields[i].getName());
				xa.setJavaType(fields[i].getType().getSimpleName());
				xxsAttributes.add(xa);
			}
		}
		
		model.addAttribute("xxsAttributes",xxsAttributes);
        model.addAttribute(Constants.OP_NAME, "修改");
        if (!model.containsAttribute("m")) {
            model.addAttribute("m", newModel());
        }
        setCommonData(model);
        return viewName("editForm");
    }
    
    
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(Beans xxs,Long[]ids, String[] xxsname,String[] xxssimpleName,String[] displayName,Boolean[] isList,Boolean[] isQuery,Model model) {
    	
    	if (permissionList != null) {
    		this.permissionList.assertHasCreatePermission();
    	}
    	List<BeanColumns> attributes = new ArrayList<BeanColumns>();
    	BeanColumns attribute = null;
    	for (int i = 0; i < xxsname.length; i++) {
    		attribute = new BeanColumns();
    		attribute.setName(xxsname[i]);
    		attribute.setClassname(xxs.getClassname());
    		attribute.setJavaType(xxssimpleName[i]);
    		attribute.setDisplayName(displayName[i]);
    		attribute.setList(isList[i]);
    		attribute.setQuery(isQuery[i]);
    		attribute.setBeans(xxs);
    		attributes.add(attribute);
    	}
    	baseService.save(xxs);
    	beanColumnsService.save(ids,attributes);
    	
    	Searchable searchable = null;
    	return list(searchable,model);
    }
}