<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@include file="/WEB-INF/jsp/common/taglibs.jspf"%>

<div class="userinfo ">
    <shiro:user>
        <a class="btn btn-link no-padding btn-view-info" data-toggle="tooltip" data-placement="bottom" title="点击查看个人资料"><sys:showLoginUsername/></a>，欢迎您！

        <a class="btn btn-link btn-message no-padding" data-unread="${unreadMessageCount}"  data-toggle="tooltip" data-placement="bottom" title="有${unreadMessageCount}封未读消息"><i class="icon-large icon-envelope-alt"></i></a>

        <div class="btn-group">
            <a class="btn btn-link  dropdown-toggle" data-toggle="dropdown" data-hover="dropdown"><i class="icon-large icon-cogs"></i></a>
            <ul class="dropdown-menu pull-right">
                <li>
                    <a class="btn-view-info">
                        <i class="icon-user"></i>
                        个人资料
                    </a>
                </li>
                <li>
                    <a class="btn-change-password">
                        <i class="icon-key"></i>
                        修改密码
                    </a>
                </li>
            </ul>
        </div>
        <a href="${ctx}/logout" class="btn btn-link no-padding" data-toggle="tooltip" data-placement="bottom" title="退出"><i class="icon-large icon-signin"></i></a>
        &nbsp;&nbsp;&nbsp;
    </shiro:user>
</div>