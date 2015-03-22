/**
 * Copyright &copy; 2012-2013 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.es.generate;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.DefaultResourceLoader;

import com.google.common.collect.Maps;
import com.sishuok.es.generate.utils.DateUtils;
import com.sishuok.es.generate.utils.FileUtils;
import com.sishuok.es.generate.utils.FreeMarkers;

import freemarker.template.Configuration;
import freemarker.template.Template;

/**
 * 代码生成器
 * @author ThinkGem
 * @version 2013-06-21
 */
public class Generate {
	
	private static Logger logger = LoggerFactory.getLogger(Generate.class);

	public static void main(String[] args) throws Exception {

		// ========== ↓↓↓↓↓↓ 执行前请修改参数，谨慎执行。↓↓↓↓↓↓ ====================

		// 主要提供基本功能模块代码生成。
		// 目录生成结构：{packageName}/{moduleName}/{dao,entity,service,web}/{subModuleName}/{className}
		
		// packageName 包名，这里如果更改包名，请在applicationContext.xml和srping-mvc.xml中配置base-package、packagesToScan属性，来指定多个（共4处需要修改）。
		String packageName = "com.sishuok.es";
		
		String sysName = "sys";			// 所属系统名，例：sys、showcase、maintain、personal、shop
		String moduleName = "xxs";		// 模块名（可选） 
		String tableName = "sys_xxs_attribute";			// 表明，例：user
		String className = "XxsAttribute";			// 类名，例：User
		String permissionName = "sys:xxsAttribute";//权限标志名（此处使用全名，不采用合成方式）
		String folderName = "xxs";//文件夹名
		String classAuthor = "xxs";		// 类作者，例：ThinkGem
		String functionName = "不知道叫什么功能，可耻的用了自己的名字";			// 功能名，例：用户

		// 是否启用生成工具
		//Boolean isEnable = false;
		Boolean isEnable = true;
		
		// ========== ↑↑↑↑↑↑ 执行前请修改参数，谨慎执行。↑↑↑↑↑↑ ====================
		
		if (!isEnable){
			logger.error("请启用代码生成工具，设置参数：isEnable = true");
			return;
		}
		
		if (StringUtils.isBlank(moduleName) || StringUtils.isBlank(moduleName) 
				|| StringUtils.isBlank(className) || StringUtils.isBlank(functionName)){
			logger.error("参数设置错误：包名、模块名、类名、功能名不能为空。");
			return;
		}
		
		// 获取文件分隔符
		String separator = File.separator;
		
		// 获取工程路径
		File projectPath = new DefaultResourceLoader().getResource("").getFile();
		while(!new File(projectPath.getPath()+separator+"src"+separator+"main").exists()){
			projectPath = projectPath.getParentFile();
		}
		logger.info("Project Path: {}", projectPath);
		
		// 模板文件路径
		String tplPath = StringUtils.replace(projectPath+"/src/main/java/com/sishuok/es/generate/template", "/", separator);
		logger.info("Template Path: {}", tplPath);
		
		// Java文件路径
		String javaPath = StringUtils.replaceEach(projectPath+"/src/main/java/"+StringUtils.lowerCase(packageName), 
				new String[]{"/", "."}, new String[]{separator, separator});
		logger.info("Java Path: {}", javaPath);
		
		// 视图文件路径
		String viewPath = StringUtils.replace(projectPath+"/src/main/webapp/WEB-INF/jsp/admin", "/", separator);
		logger.info("View Path: {}", viewPath);
		
		// 代码模板配置
		Configuration cfg = new Configuration();
		cfg.setDefaultEncoding("UTF-8");
		cfg.setDirectoryForTemplateLoading(new File(tplPath));

		// 定义模板变量
		Map<String, String> model = Maps.newHashMap();
		model.put("packageName", StringUtils.lowerCase(packageName));	//包路径
		model.put("sysName", StringUtils.lowerCase(sysName));			//所属系统模块名称
		model.put("moduleName", StringUtils.lowerCase(moduleName));		//模块名称
		model.put("tableName", StringUtils.lowerCase(tableName));	//表明
		model.put("className", StringUtils.uncapitalize(className));	//类名（首字母大写）
		model.put("permissionName", permissionName);		//权限名
		model.put("ClassName", StringUtils.capitalize(className));		//类名
		model.put("classAuthor", StringUtils.isNotBlank(classAuthor)?classAuthor:"Generate Tools");		//作者
		model.put("classVersion", DateUtils.getDate());					//日期
		model.put("functionName", functionName);						//模块名
		model.put("folderName", folderName);						//文件夹名
		model.put("urlPrefix", model.get("moduleName")+"_"+model.get("className"));	//jsp模板中需要的请求字符串
		model.put("viewPrefix", //StringUtils.substringAfterLast(model.get("packageName"),".")+"/"+
				model.get("urlPrefix"));
		model.put("permissionPrefix", model.get("sysName")+":"+model.get("moduleName"));	//权限字符串

		// 生成 Entity
		Template template = cfg.getTemplate("entity.ftl");
		String content = FreeMarkers.renderTemplate(template, model);
		String filePath = javaPath+separator+model.get("sysName")+separator+model.get("moduleName")+separator+"entity"
				+separator+model.get("ClassName")+".java";
		System.out.println("Entity   filePath"+filePath);
		writeFile(content, filePath);
		logger.info("Entity: {}", filePath);
		
		// 生成 Repository
		template = cfg.getTemplate("repository.ftl");
		content = FreeMarkers.renderTemplate(template, model);
		filePath = javaPath+separator+model.get("sysName")+separator+model.get("moduleName")+separator+"repository"+separator
				+separator+model.get("ClassName")+"Repository.java";
		System.out.println("repository   filePath"+filePath);
		writeFile(content, filePath);
		logger.info("Dao: {}", filePath);
		
		// 生成 Service
		template = cfg.getTemplate("service.ftl");
		content = FreeMarkers.renderTemplate(template, model);
		filePath = javaPath+separator+model.get("sysName")+separator+model.get("moduleName")+separator+"service"+separator
				+separator+model.get("ClassName")+"Service.java";
		System.out.println("Service   filePath"+filePath);
		writeFile(content, filePath);
		logger.info("Service: {}", filePath);
		
		// 生成 前台Controller
		template = cfg.getTemplate("frontController.ftl");
		content = FreeMarkers.renderTemplate(template, model);
		filePath = javaPath+separator+model.get("sysName")+separator+model.get("moduleName")+separator+"web"+separator
				+"controller"+separator+"front"+separator+model.get("ClassName")+"Controller.java";
		System.out.println("Controller   filePath"+filePath);
		writeFile(content, filePath);
		logger.info("Controller: {}", filePath);
		
		// 生成 后台Controller
		template = cfg.getTemplate("adminController.ftl");
		content = FreeMarkers.renderTemplate(template, model);
		filePath = javaPath+separator+model.get("sysName")+separator+model.get("moduleName")+separator+"web"+separator
				+"controller"+separator+"admin"+separator+model.get("ClassName")+"Controller.java";
		System.out.println("Controller   filePath"+filePath);
		writeFile(content, filePath);
		logger.info("Controller: {}", filePath);
		
		// 生成 editForm
		template = cfg.getTemplate("editForm.ftl");
		content = FreeMarkers.renderTemplate(template, model);
		filePath = viewPath+separator+model.get("sysName")+separator+model.get("folderName")+separator+"editForm.jsp";
		System.out.println("---------------------------------------------------");
		System.out.println("ViewForm   filePath"+filePath);
		writeFile(content, filePath);
		logger.info("ViewForm: {}", filePath);
		
		// 生成 list
		template = cfg.getTemplate("list.ftl");
		content = FreeMarkers.renderTemplate(template, model);
		filePath = viewPath+separator+model.get("sysName")+separator+model.get("folderName")+separator+"list.jsp";
		writeFile(content, filePath);
		System.out.println("ViewListfilePath"+filePath);
		logger.info("ViewList: {}", filePath);
		
		// 生成 searcheForm
		template = cfg.getTemplate("searchForm.ftl");
		content = FreeMarkers.renderTemplate(template, model);
		filePath = viewPath+separator+model.get("sysName")+separator+model.get("folderName")+separator+"searchForm.jsp";
		writeFile(content, filePath);
		System.out.println("searcheForm filePath"+filePath);
		logger.info("ViewList: {}", filePath);	
		
		// 生成 listTable
		template = cfg.getTemplate("listTable.ftl");
		content = FreeMarkers.renderTemplate(template, model);
		filePath = viewPath+separator+model.get("sysName")+separator+model.get("folderName")+separator+"listTable.jsp";
		writeFile(content, filePath);
		System.out.println("listTable filePath"+filePath);
		logger.info("ViewList: {}", filePath);		
		
		logger.info("Generate Success.");
	}
	
	/**
	 * 将内容写入文件
	 * @param content
	 * @param filePath
	 */
	public static void writeFile(String content, String filePath) {
		try {
			if (FileUtils.createFile(filePath)){
				FileOutputStream fos = new FileOutputStream(filePath);
				Writer writer = new OutputStreamWriter(fos,"UTF-8");
				BufferedWriter bufferedWriter = new BufferedWriter(writer);
				bufferedWriter.write(content);
				bufferedWriter.close();
				writer.close();
			}else{
				logger.info("生成失败，文件已存在！");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
