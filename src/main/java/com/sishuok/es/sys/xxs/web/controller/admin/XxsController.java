/**
 * auto code generation
 */
package com.sishuok.es.sys.xxs.web.controller.admin;

import java.io.IOException;
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
import com.sishuok.es.sys.xxs.entity.Xxs;
import com.sishuok.es.sys.xxs.entity.XxsAttribute;
import com.sishuok.es.sys.xxs.service.XxsAttributeService;
import com.sishuok.es.sys.xxs.utils.LoadPackageClasses;

/**
 * 不知道叫什么功能，可耻的用了自己的名字Controller
 * @author xxs
 * @version 2015-03-22
 */
 
@Controller("adminXxsController")
@RequestMapping(value = "/admin/sys/xxs/xxs")
public class XxsController extends BaseCRUDController<Xxs, Long> {

	@Autowired
    private ApplicationContext ctx;
	@Autowired
    private XxsAttributeService xxsAttributeService;
	
    public XxsController() {
        setResourceIdentity("sys:xxs");
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
//        	for (int i = 0; i < classLists.size(); i++) {
//        		String str = classLists.get(i).getSimpleName();  
//          	  	//System.out.println("-----------------------------实体类名称："+str+"--------------------------------"+classLists.get(i).getCanonicalName()+"77777777777777777777777777777777");  
//          	  	Field [] fields = classLists.get(i).getDeclaredFields();
//                for(int ii=0; ii< fields.length; ii++)
//                {
//                    Field f = fields[ii];
//                    //System.out.println("属性名："+f.getName()+"                    类型:"+f.getType().getSimpleName());
//                } 
//			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
        List<Xxs> xxsLists = baseService.findAll();
        model.addAttribute("xxsLists", xxsLists);
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        Xxs xxs = new Xxs();
        xxs.setAllclassname(allclassname);
        xxs.setClassname(loadclass.getSimpleName());
        setCommonData(model);
		model.addAttribute("m",xxs);
		model.addAttribute("c",loadclass);
        
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
        
        
        
        Xxs xxs = baseService.findOne(id);
        
        Class<?> loadclass = null ;
        try {
			loadclass = Class.forName(xxs.getAllclassname());
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        model.addAttribute("m",xxs);
		model.addAttribute("c",loadclass);
        
        model.addAttribute(Constants.OP_NAME, "修改");
        if (!model.containsAttribute("m")) {
            model.addAttribute("m", newModel());
        }
        return viewName("editForm");
    }
    
    
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(Xxs xxs,String[] xxsname,String[] xxssimpleName,Model model) {
    	
    	if (permissionList != null) {
    		this.permissionList.assertHasCreatePermission();
    	}
    	//xxs.setName(name);
    	//xxs.setComments(comments);
    	List<XxsAttribute> attributes = new ArrayList<XxsAttribute>();
    	XxsAttribute attribute = new XxsAttribute();
    	for (int i = 0; i < xxsname.length; i++) {
    		attribute.setName(xxsname[i]);
    		attribute.setXxs(xxs);
    		attributes.add(attribute);
    		
    	}
    	baseService.save(xxs);
    	xxsAttributeService.save(attributes);
    	
    	Searchable searchable = null;
    	return list(searchable,model);
    }
    
    @RequestMapping(value = "/updateppp", method = RequestMethod.POST)
    public String update(Xxs xxs,String[] name,String[] simpleName,Model model) {

    	if (permissionList != null) {
            this.permissionList.assertHasCreatePermission();
        }
    	System.out.println(name);
    	
    	List<XxsAttribute> attributes = new ArrayList<XxsAttribute>();
    	XxsAttribute attribute = new XxsAttribute();
    	for (int i = 0; i < name.length; i++) {
    		attribute.setName(name[i]);
    		attributes.add(attribute);
    		
		}
    	baseService.save(xxs);
    	xxsAttributeService.save(attributes);
    	
        Searchable searchable = null;
        return list(searchable,model);
    }
    
}