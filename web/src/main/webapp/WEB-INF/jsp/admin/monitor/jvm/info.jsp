<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="java.util.Enumeration"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<%
	
	Enumeration e =  request.getHeaderNames();
    if (e != null) {
    	out.println("heads: <br />");
		while (e.hasMoreElements()) {
			String hehe = e.nextElement().toString();
			out.println(hehe + ":" + request.getHeader(hehe) + "<br/>");
		}
    }	
    
    Cookie [] cookies = request.getCookies();
   	if (cookies != null) {
    	out.println("cookies: <br />");   		
   		for (int i = 0; i < cookies.length; i ++) {
   			out.println("name:" + cookies[i].getName() + "<br/>");
   			out.println("value:" + cookies[i].getValue() + "<br/>");   			
   			out.println("<br/>");
   		}
   	}
   	
%>

</body>
</html>
