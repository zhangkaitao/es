<%@ tag pageEncoding="UTF-8" description="列头信息" %>
<%@ tag import="java.util.List" %>
<%@ attribute name="commonXxs" type="com.sishuok.es.sys.xxs.entity.XxsAttribute" required="true" description="xxs集合" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:if test="${not empty commonXxs}">
    <c:forEach items="${commonXxs}" var="x">
       	<th sort="${x.name }">${x.displayName }</th>
    </c:forEach>
</c:if>
