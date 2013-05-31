<%@ tag import="com.sishuok.es.common.utils.SpringUtils" %>
<%@ tag import="com.sishuok.es.sys.group.entity.Group" %>
<%@ tag import="com.sishuok.es.sys.group.service.GroupService" %>
<%@ tag pageEncoding="UTF-8"%>
<%@ attribute name="id" type="java.lang.Long" required="true" description="当前要展示的用户的id" %>
<%!private GroupService groupService;%>
<%

    if(groupService == null) {
        groupService = SpringUtils.getBean(GroupService.class);
    }

    Group group = groupService.findOne(id);

    if(group == null) {
        out.write("<span class='label label-important'>删除的数据，请修改</span>");
        return;
    }

    StringBuilder s = new StringBuilder();
    s.append(String.format("<a class='btn btn-link no-padding' href='%s/admin/sys/group/%d'>", request.getContextPath(), id));
    s.append(group.getName());
    s.append("</a>");
    out.write(s.toString());
%>
