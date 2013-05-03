<%@ tag import="com.sishuok.es.common.utils.SpringUtils" %>
<%@ tag import="com.sishuok.es.sys.organization.entity.Job" %>
<%@ tag import="java.util.List" %>
<%@ tag import="com.google.common.collect.Lists" %>
<%@ tag import="com.sishuok.es.sys.user.service.UserService" %>
<%@ tag import="com.sishuok.es.sys.user.entity.User" %>
<%@ tag pageEncoding="UTF-8"%>
<%@ attribute name="id" type="java.lang.Long" required="true" description="当前要展示的用户的id" %>
<%!private UserService userService;%>
<%

    if(userService == null) {
        userService = SpringUtils.getBean(UserService.class);
    }

    User user = userService.findOne(id);

    if(user == null) {
        return;
    }
    out.write(
            String.format(
                    "<a class='btn btn-link' href='%s/admin/sys/user/%d'>%s</a>",
                    request.getContextPath(),
                    id,
                    user.getUsername()));
%>
