<%@ tag import="com.sishuok.es.common.utils.SpringUtils" %>
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
        out.write("<span class='label label-important'>删除的数据，请修改</span>");
        return;
    }
    out.write(
            String.format(
                    "<a class='btn btn-link no-padding' href='%s/admin/sys/user/%d'>%s</a>%s",
                    request.getContextPath(),
                    id,
                    user.getUsername(),
                    Boolean.TRUE.equals(user.getDeleted()) ? "<span class='label label-important'>[用户已删除]</span>" : ""));
%>
