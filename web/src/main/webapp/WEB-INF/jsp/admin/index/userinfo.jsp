<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@include file="/WEB-INF/jsp/common/taglibs.jspf"%>

<div id="userinfo">
    <shiro:user>
        <div id="user-navbar" class="navbar">
            <ul class="nav btn-group">
                <li class="btn">
                    <a class="btn-view-info" data-toggle="tooltip" data-placement="bottom" title="点击查看个人资料">
                        <sys:showLoginUsername/>，欢迎您！
                    </a>
                </li>
                <li class="btn">
                    <a class="btn-message" data-unread="${unreadMessageCount}"  data-toggle="tooltip" data-placement="bottom" title="有${unreadMessageCount}封未读消息">
                        <i class="icon-large icon-envelope-alt icon-message"></i>
                    </a>
                </li>
                <li class="btn dropdown">
                        <a class="dropdown-toggle" data-toggle="dropdown" data-hover="dropdown">
                            <i class="icon-large icon-cogs"></i>
                            <i class="icon-caret-down"></i>
                        </a>
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
                </li>
                <li class="btn">
                    <a href="${ctx}/logout" data-toggle="tooltip" data-placement="bottom" title="退出"><i class="icon-large icon-signin"></i></a>
                </li>
            </ul>
        </div>
    </shiro:user>
</div>