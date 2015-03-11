<%@tag pageEncoding="UTF-8" description="构建子菜单" %>
<%@ attribute name="menu" type="com.sishuok.es.sys.resource.entity.tmp.Menu" required="true" description="当前菜单" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="es" tagdir="/WEB-INF/tags" %>
${menu.name}
<c:choose>
    <c:when test="${!menu.hasChildren}">
    	<a href="<%=menuUrl(request, menu.getUrl())%>" title="${menu.url}" class="list-group-item">
		        <i class="icon-fixed-width ${menu.icon}"></i>
		        ${menu.name}
		</a>
    </c:when>
    <c:otherwise>
    	<div class="panel-heading">
	      <h6 class="panel-title">
	        <span class="badge badge-info">3</span>
	        
				<a href="#" class="accordion-toggle" data-toggle="collapse" data-parent="#nav-accordion" href="#nav-panel-${menu.id}">
					<i class="icon-fixed-width ${menu.icon}"></i>
					${menu.name}
				</a>
			</h6>
		</div>
            <div id="nav-panel-#${menu.id}" class="list-group panel-collapse collapse">
                <c:forEach items="${menu.children}" var="menu2">
                    <es:submenu menu="${menu2}"/>
                </c:forEach>
            </div>
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

