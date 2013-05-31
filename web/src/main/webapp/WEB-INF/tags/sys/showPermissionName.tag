<%@ tag import="com.sishuok.es.common.utils.SpringUtils" %>
<%@ tag import="com.sishuok.es.sys.permission.entity.Permission" %>
<%@ tag import="com.sishuok.es.sys.permission.service.PermissionService" %>
<%@ tag pageEncoding="UTF-8"%>
<%@ attribute name="id" type="java.lang.Long" required="true" description="当前要展示的组织机构的名字" %>
<%!private PermissionService permissionService;%>
<%

    if(permissionService == null) {
        permissionService = SpringUtils.getBean(PermissionService.class);
    }

    Permission permission = permissionService.findOne(id);

    if(permission == null) {
        out.write("<span class='label label-important'>删除的数据，请修改</span>");
        return;
    }

    StringBuilder s = new StringBuilder();
    s.append(String.format("<a class='btn btn-link no-padding' href='%s/admin/sys/permission/permission/%d'>", request.getContextPath(), id));
    s.append(String.format("<span title='%s'>%s[%s]</span>", permission.getDescription(), permission.getName(), permission.getPermission()));
    s.append("</a>");
    out.write(s.toString());

%>
