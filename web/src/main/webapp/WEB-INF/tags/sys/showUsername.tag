<%@ tag import="com.sishuok.es.common.utils.SpringUtils" %>
<%@ tag import="com.sishuok.es.sys.user.entity.User" %>
<%@ tag import="com.sishuok.es.sys.user.service.UserService" %>
<%@ tag pageEncoding="UTF-8"%>
<%@ attribute name="id" type="java.lang.Long" required="true" description="当前要展示的用户的id" %>
<%@ attribute name="needLink" type="java.lang.Boolean" required="false" description="是否需要链接" %>
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
    String username = user.getUsername();
    String deletedInfo = (Boolean.TRUE.equals(user.getDeleted()) ? "<span class='label label-important'>[用户已删除]</span>" : "");
    if(Boolean.FALSE.equals(needLink)) {
        out.write(username + deletedInfo);
        return;
    }
    out.write(
            String.format(
                    "<a class='btn btn-link no-padding' href='%s/admin/sys/user/%d'>%s</a>%s",
                    request.getContextPath(),
                    id,
                    username,
                    deletedInfo));
%>
