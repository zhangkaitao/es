<%@tag pageEncoding="UTF-8"%>
<%@ attribute name="resourceIdentity" type="java.lang.String" required="true" description="资源标识" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
{
<shiro:hasPermission name="${resourceIdentity}:create">
    create: true,
</shiro:hasPermission>
<shiro:hasPermission name="${resourceIdentity}:update">
    update: true,
</shiro:hasPermission>
<shiro:hasPermission name="${resourceIdentity}:delete">
    remove: true,
</shiro:hasPermission>
<shiro:hasPermission name="${resourceIdentity}:create or ${resourceIdentity}:update or ${resourceIdentity}:delete">
    move: true,
</shiro:hasPermission>
    end: true
}

