<%@ tag import="com.sishuok.es.common.utils.SpringUtils" %>
<%@ tag import="com.sishuok.es.sys.permission.service.PermissionService" %>
<%@ tag import="com.sishuok.es.sys.permission.entity.Permission" %>
<%@ tag import="com.sishuok.es.sys.permission.service.RoleService" %>
<%@ tag import="com.sishuok.es.sys.permission.entity.Role" %>
<%@ tag pageEncoding="UTF-8"%>
<%@ attribute name="id" type="java.lang.Long" required="true" description="当前要展示的组织机构的名字" %>
<%!private RoleService roleService;%>
<%

    if(roleService == null) {
        roleService = SpringUtils.getBean(RoleService.class);
    }

    Role role = roleService.findOne(id);

    if(role == null) {
        return;
    }

    StringBuilder s = new StringBuilder();
    s.append(String.format("<a class='btn btn-link' href='%s/admin/sys/permission/role/%d'>", request.getContextPath(), id));
    s.append(String.format("<span title='%s'>%s[%s]</span>", role.getDescription(), role.getName(), role.getRole()));
    s.append("</a>");
    out.write(s.toString());

%>
