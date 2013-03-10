<%@ tag import="com.sishuok.es.common.Constants" %>
<%@ tag pageEncoding="UTF-8"%>
<%@ attribute name="hiddenInput" type="java.lang.Boolean" required="false" description="是否是隐藏展示" %>
<%
    String backURL = (String) request.getAttribute(Constants.BACK_URL);
   if(hiddenInput != null && hiddenInput.equals(Boolean.TRUE)) {
       out.write("<input type=\"hidden\" name=\"BackURL\" value=\"" + backURL + "\">");
       return;
   }
    out.write(backURL);
    return;
%>