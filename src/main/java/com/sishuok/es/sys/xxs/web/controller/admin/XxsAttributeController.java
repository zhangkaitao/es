/**
 * auto code generation
 */
package com.sishuok.es.sys.xxs.web.controller.admin;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sishuok.es.common.entity.enums.BooleanEnum;
import com.sishuok.es.common.utils.SpringUtils;
import com.sishuok.es.common.web.controller.BaseCRUDController;
import com.sishuok.es.sys.xxs.entity.XxsAttribute;
import com.sishuok.es.sys.xxs.utils.LoadPackageClasses;

/**
 * 不知道叫什么功能，可耻的用了自己的名字Controller
 * @author xxs
 * @version 2015-03-22
 */
 
@Controller("adminXxsAttributeController")
@RequestMapping(value = "/admin/sys/xxs/xxsAttribute")
public class XxsAttributeController extends BaseCRUDController<XxsAttribute, Long> {

	@Autowired
    private ApplicationContext ctx;
	
    public XxsAttributeController() {
        setResourceIdentity("sys:xxsAttribute");
    }
    @Override
    protected void setCommonData(Model model) {
    	Field [] fields = entityClass.getDeclaredFields();
        for(int i=0; i< fields.length; i++)
        {
            Field f = fields[i];
            System.out.println(f.getName()+"---------------------"+f.getType()+"-----------------"+f.getType().getSimpleName()+"-----------------");
        } 
        
        LoadPackageClasses loadPackageClasses = (LoadPackageClasses) SpringUtils.getBean("loadPackageClasses");
        try {
        	Set<Class<?>> dd = loadPackageClasses.getClassSet();
        	
        	Iterator<Class<?>> it = dd.iterator();  
        	while (it.hasNext()) {  
        	  String str = it.next().getSimpleName();  
        	  System.out.println(str);  
        	}  
        	
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        //String [] strs = SpringContextHolder.getApplicationContext().getBeanNamesForType(Entity.class);
        //SpringUtils.getAliases(Entity.class);
//        String [] strs = ctx.getBeanNamesForAnnotation(Entity.class);
//        
//        
//        try {
//            // 获取所有beanNames
//        	String[] beanNames = ctx.getBeanDefinitionNames();
//            System.out.println("eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee:"+ctx.getBeanDefinitionCount());
//            for (String beanName : beanNames) {
//                 System.out.println(beanName);    
//                //Entity en = ctx.findAnnotationOnBean(beanName,Entity.class);
//                //System.out.println(en.name());
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        
//        
//        System.out.println(strs);
        
       
        model.addAttribute("booleanList", BooleanEnum.values());
    }
    
}