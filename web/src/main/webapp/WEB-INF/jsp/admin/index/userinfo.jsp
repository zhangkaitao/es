<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jspf"%>

<div class="userinfo">
    <shiro:user>
        <a class="btn-view-info" title="点击查看个人资料"><es:username/></a>，欢迎您！
        <a href="${ctx}/logout" title="点击退出系统">
            <i class="icon-signin"></i>
            退出
        </a>
        <div class="btn-group">
            <a class="btn btn-link dropdown-toggle" data-toggle="dropdown" data-hover="dropdown">
                更多
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
        </div>
    </shiro:user>
</div>
