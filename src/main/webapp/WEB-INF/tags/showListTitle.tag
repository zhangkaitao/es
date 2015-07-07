<%@ tag pageEncoding="UTF-8" description="列标题"%>
<%@ tag import="java.util.List"%>
<%@ attribute name="bean" type="com.sishuok.es.sys.bean.entity.Bean"
	required="true" description="实体对象"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:if test="${not empty bean}">
	<c:forEach items="${bean.beanItems}" var="bi">
		<c:if test="${bi.isShow }">
			<th sort="${bi.name }">${bi.displayName }</th>
		</c:if>
	</c:forEach>
</c:if>
