<%@tag pageEncoding="UTF-8" description="构建子菜单" %>
<%@ attribute name="menu" type="com.sishuok.es.sys.resource.entity.tmp.Menu" required="true" description="当前菜单" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="es" tagdir="/WEB-INF/tags" %>
<c:choose>
    <c:when test="${!menu.hasChildren}">
    	<li>
    		<a href="<%=menuUrl(request, menu.getUrl())%>" title="${menu.url}">
    			<i class="${menu.icon}"></i> ${menu.name}
    		</a>
    	</li>
    </c:when>
    <c:otherwise>
    	<li class="dropdown-submenu">
    		<a href="javascript:;">
    			<i class="icon-plus"></i> ${menu.name}
    		</a>
            <ul class="dropdown-menu">
				 <c:forEach items="${m.children}" var="c">
	                 <es:subnav menu="${menu2}"/>
		         </c:forEach>
	         </ul>
	      </li>
    </c:otherwise>
</c:choose>
<%!
    private static String menuUrl(HttpServletRequest request, String url) {
        if(url.startsWith("http")) {
            return url;
        }
        String ctx = request.getContextPath();

        if(url.startsWith(ctx) || url.startsWith("/" + ctx  )) {
            return url;
        }

        if(!url.startsWith("/")) {
            url = url + "/";
        }
        return ctx + url;

    }
%>

