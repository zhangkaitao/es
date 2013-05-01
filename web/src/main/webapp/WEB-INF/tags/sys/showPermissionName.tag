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

    if(permission == null) {
        out.write(String.format("编号[%d]数据不存在", id));
    }

    StringBuilder s = new StringBuilder();
    s.append(String.format("<a class='btn btn-link' href='%s/admin/sys/permission/permission/%d'>", request.getContextPath(), id));
    s.append(String.format("<span title='%s'>%s[%s]</span>", permission.getDescription(), permission.getName(), permission.getPermission()));
    s.append("</a>");
    out.write(s.toString());

%>
