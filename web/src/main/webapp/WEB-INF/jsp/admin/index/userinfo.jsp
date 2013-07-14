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
                <li class="btn" data-toggle="tooltip" data-placement="bottom" title="我的通知">
                    <a class="btn-notification">
                        <i class="icon-large icon-envelope-alt icon-volume-up"></i>
                    </a>
                </li>
                <div class="notification-list popover fade bottom">
                    <div class="arrow"></div>
                    <div class="content">
                        <div class="loading">
                            <div class="popover-title title"><a class="btn btn-link no-padding view-all-notification"><i class="icon-table"></i> 查看所有通知</a><span class="pull-right"><a  class="btn btn-link no-padding close-notification-list"><i class="icon-remove"></i></a></span></div>
                            <div class="popover-content list">
                                <img src="${ctx}/static/images/loading.gif" width="20px">&nbsp;&nbsp;&nbsp;加载中...
                            </div>
                        </div>
                        <div class="no-comment">
                            <div class="popover-title title"><a class="btn btn-link no-padding view-all-notification"><i class="icon-table"></i> 查看所有通知</a><span class="pull-right"><a  class="btn btn-link no-padding close-notification-list"><i class="icon-remove"></i></a></span></div>
                            <div class="popover-content list">
                                <i class="icon-comment"></i>&nbsp;&nbsp;&nbsp;暂无新通知
                            </div>
                        </div>
                        <div class="detail">
                            <div class="popover-title title">
                                <a class="btn btn-link no-padding back-notification-list"><i class="icon-reply"></i> 返回通知列表</a><span class="pull-right"><a class="pre">&lt; 上一条</a> | <a class="next">下一条 &gt;</a></span>
                            </div>
                            <div class="popover-content list">
                            </div>
                        </div>

                        <div class="menu">
                            <div class="popover-title title"><a class="btn btn-link no-padding view-all-notification"><i class="icon-table"></i> 查看所有通知</a><span class="pull-right"><a  class="btn btn-link no-padding close-notification-list"><i class="icon-remove"></i></a></span></div>
                            <div class="popover-content list">
                            </div>
                        </div>
                        <div class="clearfix"></div>
                    </div>
                </div>
                <li class="btn" data-toggle="tooltip" data-placement="bottom" title="我的消息">
                    <a class="btn-message">
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