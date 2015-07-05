/**
 * auto code generation
 */
package com.sishuok.es.sys.bean.web.controller.admin;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.sishuok.es.common.entity.search.Searchable;
import com.sishuok.es.common.utils.SpringUtils;
import com.sishuok.es.common.web.bind.annotation.PageableDefaults;
import com.sishuok.es.common.web.controller.BaseCRUDController;
import com.sishuok.es.sys.bean.entity.BeanItem;
import com.sishuok.es.sys.bean.utils.LoadPackageClasses;

/**
 * 不知道叫什么功能，可耻的用了自己的名字Controller
 * @author xxs
 * @version 2015-03-22
 */
 
@Controller("adminBeanItemController")
@RequestMapping(value = "/admin/sys/bean/beanItem")
public class BeanItemController extends BaseCRUDController<BeanItem, Long> {

	@Autowired
    private ApplicationContext ctx;
	
    public BeanItemController() {
        //setResourceIdentity("sys:xxsAttribute");
    }
    @Override
    protected void setCommonData(Model model) {
        LoadPackageClasses loadPackageClasses = (LoadPackageClasses) SpringUtils.getBean("loadPackageClasses");
        try {
        	List<Class<?>> lists = loadPackageClasses.getClassList();
        	for (int i = 0; i < lists.size(); i++) {
        		String str = lists.get(i).getSimpleName();  
          	  	System.out.println("-----------------------------实体类名称："+str+"--------------------------------");  
          	  	Field [] fields = lists.get(i).getDeclaredFields();
                for(int ii=0; ii< fields.length; ii++)
                {
                    Field f = fields[ii];
                    System.out.println("属性名："+f.getName()+"类型:"+f.getType().getSimpleName());
                } 
			}
        	model.addAttribute("entityLists", lists);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    @RequestMapping(method = RequestMethod.GET)
    @PageableDefaults(sort = "id=desc")
    public String list(Searchable searchable, Model model) {

        if (permissionList != null) {
            this.permissionList.assertHasViewPermission();
        }
        LoadPackageClasses loadPackageClasses = (LoadPackageClasses) SpringUtils.getBean("loadPackageClasses");
        try {
        	List<Class<?>> lists = loadPackageClasses.getClassList();
        	for (int i = 0; i < lists.size(); i++) {
        		String str = lists.get(i).getSimpleName();  
          	  	System.out.println("-----------------------------实体类名称："+str+"--------------------------------");  
          	  	Field [] fields = lists.get(i).getDeclaredFields();
                for(int ii=0; ii< fields.length; ii++)
                {
                    Field f = fields[ii];
                    System.out.println("属性名："+f.getName()+"                    类型:"+f.getType().getSimpleName());
                } 
			}
        	model.addAttribute("entityLists", lists);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
        return super.list(searchable, model);
    }
    
}