<%@tag pageEncoding="UTF-8" description="构建子菜单" %>
<%@ attribute name="menu" type="com.sishuok.es.sys.resource.entity.tmp.Menu" required="true" description="当前菜单" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="es" tagdir="/WEB-INF/tags" %>
<c:choose>
    <c:when test="${!menu.hasChildren}">
        <li>
        	<a class="dropdown-toggle" href="<%=menuUrl(request, menu.getUrl())%>">
        		<i class="icon-double-angle-right"></i>
					${menu.name}(<%=menuUrl(request, menu.getUrl())%>)lllllll
        	</a>
        </li>
    </c:when>
    <c:otherwise>
        <li>
            <a href="#">${menu.name}</a>
            <ul class="submenu">
                <c:forEach items="${menu.children}" var="menu2">
                    <es:submenu menu="${menu2}"/>
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

