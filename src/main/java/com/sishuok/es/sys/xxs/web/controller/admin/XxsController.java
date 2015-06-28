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
		
		
		List<XxsAttribute> xxsAttributes = new ArrayList<XxsAttribute>();
		Field[] fields = loadclass.getDeclaredFields();
		XxsAttribute xa = null;
		System.out.println("77777777777777777"+fields.length);
		
		for (int i = 0; i < fields.length; i++) {
			xa = new XxsAttribute();
			xa.setName(fields[i].getName());
			xa.setJavaType(fields[i].getType().getSimpleName());
			xxsAttributes.add(xa);
		}
		
		model.addAttribute("xxsAttributes",xxsAttributes);
		
		
        
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
        System.out.println("classname:"+xxs.getClassname());
        List<XxsAttribute> xas = xxsAttributeService.findByName(xxs.getClassname());
//      List<XxsAttribute> xas = null;
        Class<?> loadclass = null ;
        try {
			loadclass = Class.forName(xxs.getAllclassname());
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        model.addAttribute("m",xxs);
		model.addAttribute("c",loadclass);
		
		List<XxsAttribute> xxsAttributes = new ArrayList<XxsAttribute>();
		Field[] fields = loadclass.getDeclaredFields();
		XxsAttribute xa = null;
		System.out.println("77777777777777777"+fields.length);
		System.out.println("77777777777777777"+xas.size());
		
		for (int i = 0; i < fields.length; i++) {
			xa = new XxsAttribute();
			boolean isture = true;
			for (int j = 0; j < xas.size(); j++) {
				System.out.println("ttttttttttttttttttttttttt"+xas.get(j).getName());
				System.out.println("ttttttttttttttttttttttttt"+loadclass.getDeclaredFields()[i].getName());
				if(xas.get(j).getName().equals(loadclass.getDeclaredFields()[i].getName())){
					xxsAttributes.add(xas.get(j));
					isture = false;
					break;
				}
			}
			if(isture){
				System.out.println("44444444444444444444"+fields[i].getType().getSimpleName());
				xa.setName(fields[i].getName());
				xa.setJavaType(fields[i].getType().getSimpleName());
				//xa.setDisplayName();
				xxsAttributes.add(xa);
			}
		}
		
		model.addAttribute("xxsAttributes",xxsAttributes);
        
        model.addAttribute(Constants.OP_NAME, "修改");
        if (!model.containsAttribute("m")) {
            model.addAttribute("m", newModel());
        }
        return viewName("editForm");
    }
    
    
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(Xxs xxs,String[] xxsname,String[] xxssimpleName,String[] displayName,Model model) {
    	
    	if (permissionList != null) {
    		this.permissionList.assertHasCreatePermission();
    	}
    	System.out.println("5555555555555555555555555555555");
    	//xxs.setName(name);
    	//xxs.setComments(comments);
    	List<XxsAttribute> attributes = new ArrayList<XxsAttribute>();
    	XxsAttribute attribute = null;
    	for (int i = 0; i < xxsname.length; i++) {
    		attribute = new XxsAttribute();
    		attribute.setName(xxsname[i]);
    		attribute.setJavaType(xxssimpleName[i]);
    		attribute.setDisplayName(displayName[i]);
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
    	XxsAttribute attribute = null;
    	for (int i = 0; i < name.length; i++) {
    		attribute = new XxsAttribute();
    		attribute.setName(name[i]);
    		attributes.add(attribute);
    		
		}
    	baseService.save(xxs);
    	xxsAttributeService.save(attributes);
    	
        Searchable searchable = null;
        return list(searchable,model);
    }
    
}