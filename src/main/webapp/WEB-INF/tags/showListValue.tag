<%@ tag pageEncoding="UTF-8" description="列文本" %>
<%@ tag import="java.util.List" %>
<%@ attribute name="bean" type="com.sishuok.es.sys.bean.entity.Bean" required="true" description="实体对象" %>
<%@ attribute name="m" type="java.lang.Object" required="true" description="实体对象" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:if test="${not empty bean}">
    <c:forEach items="${bean.beanItems}" var="bi">
    	<c:if test="${bi.isShow }">
	    	<c:set var="tname" value="${bi.name}" scope="page" />
			<td>${m[tname]}</td>
    	</c:if>
    </c:forEach>
</c:if>
