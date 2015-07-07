/**
 * auto code generation
 */
package com.sishuok.es.sys.bean.web.controller.admin;

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
import com.sishuok.es.sys.bean.entity.Bean;
import com.sishuok.es.sys.bean.entity.BeanItem;
import com.sishuok.es.sys.bean.service.BeanItemService;
import com.sishuok.es.sys.bean.utils.LoadPackageClasses;

/**
 * 不知道叫什么功能，可耻的用了自己的名字Controller
 * @author xxs
 * @version 2015-03-22
 */
 
@Controller("adminBeanController")
@RequestMapping(value = "/admin/sys/bean/bean")
public class BeanController extends BaseCRUDController<Bean, Long> {

	@Autowired
    private ApplicationContext ctx;
	@Autowired
    private BeanItemService beanItemService;
	
    public BeanController() {
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
        List<Bean> beanLists = baseService.findAll();
        model.addAttribute("beanLists", beanLists);
        model.addAttribute("classLists", classLists);
        setCommonData(model);
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
        Bean beans = new Bean();
        beans.setAllclassname(allclassname);
        beans.setCname(loadclass.getSimpleName());
        setCommonData(model);
		model.addAttribute("m",beans);
		model.addAttribute("c",loadclass);
		
		
		List<BeanItem> beanColumns = new ArrayList<BeanItem>();
		Field[] fields = loadclass.getDeclaredFields();
		BeanItem bc = null;
		
		for (int i = 0; i < fields.length; i++) {
			bc = new BeanItem();
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
        
        Bean bean = baseService.findOne(id);
        System.out.println("classname:"+bean.getCname());
        System.out.println(bean.getBeanItems().size());
        List<BeanItem> xas = beanItemService.findByBeansId(bean.getId());
        System.out.println(xas.size());
        Class<?> loadclass = null ;
        try {
			loadclass = Class.forName(bean.getAllclassname());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
        
        model.addAttribute("m",bean);
		model.addAttribute("c",loadclass);
		
		List<BeanItem> beanColumns = new ArrayList<BeanItem>();
		Field[] fields = loadclass.getDeclaredFields();
		BeanItem xa = null;
		for (int i = 0; i < fields.length; i++) {
			xa = new BeanItem();
			boolean isture = true;
			for (int j = 0; j < xas.size(); j++) {
				if(xas.get(j).getName().equals(loadclass.getDeclaredFields()[i].getName())){
					beanColumns.add(xas.get(j));
					isture = false;
					break;
				}
			}
			if(isture){
				xa.setName(fields[i].getName());
				xa.setJavaType(fields[i].getType().getSimpleName());
				beanColumns.add(xa);
			}
		}
		
		model.addAttribute("beanColumns",beanColumns);
        model.addAttribute(Constants.OP_NAME, "修改");
        if (!model.containsAttribute("m")) {
            model.addAttribute("m", newModel());
        }
        setCommonData(model);
        return viewName("editForm");
    }
    
    
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(Bean bean,Long[]ids, String[] xxsname,String[] xxssimpleName,String[] displayName,Boolean[] isShow,Boolean[] isQuery,Model model) {
    	
    	if (permissionList != null) {
    		this.permissionList.assertHasCreatePermission();
    	}
    	List<BeanItem> beanItems = new ArrayList<BeanItem>();
    	BeanItem beanItem = null;
    	for (int i = 0; i < xxsname.length; i++) {
    		beanItem = new BeanItem();
    		beanItem.setName(xxsname[i]);
    		beanItem.setCname(bean.getCname());
    		beanItem.setJavaType(xxssimpleName[i]);
    		beanItem.setDisplayName(displayName[i]);
    		beanItem.setIsShow(isShow[i]);
    		beanItem.setIsQuery(isQuery[i]);
    		beanItem.setBean(bean);
    		beanItems.add(beanItem);
    	}
    	baseService.save(bean);
    	beanItemService.save(ids,beanItems);
    	
    	Searchable searchable = null;
    	return list(searchable,model);
    }
}