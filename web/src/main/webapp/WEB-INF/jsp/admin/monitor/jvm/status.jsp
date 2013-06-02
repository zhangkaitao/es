<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ page import="org.springframework.web.context.WebApplicationContext"%>
<%@ page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@ page import="java.lang.management.ManagementFactory" %>
<%@ page import="java.lang.management.MemoryUsage" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%
	try{
		WebApplicationContext applicationContext = WebApplicationContextUtils
			.getWebApplicationContext(request.getSession()
			.getServletContext());
        MemoryUsage memoryUsage = ManagementFactory.getMemoryPoolMXBeans().get(0).getUsage();
        if(memoryUsage.getMax() <= memoryUsage.getCommitted() + 1024L*1024*20) {
            out.println("500");
        }

		out.println("200");
	}catch(Exception e) {
		//出错
		out.println(500);
	}
%>