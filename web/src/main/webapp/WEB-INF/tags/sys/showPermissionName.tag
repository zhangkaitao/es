<%@ tag import="com.sishuok.es.common.utils.SpringUtils" %>
<%@ tag import="com.sishuok.es.sys.permission.service.PermissionService" %>
<%@ tag import="com.sishuok.es.sys.permission.entity.Permission" %>
<%@ tag pageEncoding="UTF-8"%>
<%@ attribute name="id" type="java.lang.Long" required="true" description="当前要展示的组织机构的名字" %>
<%!private PermissionService permissionService;%>
<%

    if(permissionService == null) {
        permissionService = SpringUtils.getBean(PermissionService.class);
    }

    Permission permission = permissionService.findOne(id);

    out.write(String.format(
            "<span title='%s'>%s&nbsp;&nbsp;(%s)</span>",
            permission.getDescription(), permission.getName(), permission.getPermission()));
%>
