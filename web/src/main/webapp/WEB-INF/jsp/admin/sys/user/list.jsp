<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jspf"%>
<es:contentHeader/>

<c:set var="organizationId" value="${empty organization ? 0 : organization.id}"/>
<c:set var="jobId" value="${empty job ? 0 : job.id}"/>

<div data-table="table" class="panel">

    <ul class="nav nav-tabs">
        <li ${param['search.deleted_eq'] ne 'true' and param['search.status_eq'] ne 'blocked' ? 'class="active"' : ''}>
            <a href="${ctx}/admin/sys/user/${organizationId}/${jobId}">
                <i class="icon-table"></i>
                所有用户列表
            </a>
        </li>
        <li ${param['search.deleted_eq'] eq 'true' ? 'class="active"' : ''}>
            <a href="${ctx}/admin/sys/user/${organizationId}/${jobId}?search.deleted_eq=true">
                <i class="icon-table"></i>
                已删除用户列表
            </a>
        </li>
        <li ${param['search.status_eq'] eq 'blocked' ? 'class="active"' : ''}>
            <a href="${ctx}/admin/sys/user/${organizationId}/${jobId}?search.status_eq=blocked">
                <i class="icon-table"></i>
                已封禁用户列表
            </a>
        </li>
    </ul>

    <es:showMessage/>


    <div class="row-fluid tool ui-toolbar">
        <div class="span3">
            <div class="btn-group">
                <shiro:hasPermission name="sys:user:create">
                <a class="btn no-disabled btn-create">
                    <span class="icon-file-alt"></span>
                    新增
                </a>
                </shiro:hasPermission>
                <shiro:hasPermission name="sys:user:update">
                <a id="update" class="btn btn-update">
                    <span class="icon-edit"></span>
                    修改
                </a>
                </shiro:hasPermission>
                <shiro:hasPermission name="sys:user:delete">
                <a class="btn btn-delete">
                    <span class="icon-trash"></span>
                    删除
                </a>
                </shiro:hasPermission>
                <shiro:hasPermission name="sys:user;*"><%-- 当拥有所有权限时才能使用 --%>
                <div class="btn-group last">
                    <a class="btn dropdown-toggle" data-toggle="dropdown" href="#">
                        <i class="icon-wrench"></i>
                        更多操作
                        <span class="caret"></span>
                    </a>
                    <ul class="dropdown-menu">
                        <li>
                            <a class="btn btn-link change-password">
                                <i class="icon-key"></i>
                                改密
                            </a>
                        </li>
                        <li>
                            <a class="btn btn-link block-user">
                                <i class="icon-lock"></i>
                                封禁用户
                            </a>
                        </li>
                        <li>
                            <a class="btn btn-link unblocked-user">
                                <i class="icon-unlock"></i>
                                解封用户
                            </a>
                        </li>
                        <li>
                            <a class="btn btn-link recycle">
                                <i class="icon-ok"></i>
                                还原删除的用户
                            </a>
                        </li>
                        <li class="divider"></li>
                        <li>
                            <a class="btn btn-link status-history">
                                <i class="icon-table"></i>
                                状态变更历史
                            </a>
                        </li>
                        <li>
                            <a class="btn btn-link last-online-info">
                                <i class="icon-table"></i>
                                最后在线历史
                            </a>
                        </li>
                    </ul>
                </div>
                </shiro:hasPermission>
            </div>
        </div>
        <div class="span9">
            <%@include file="searchForm.jsp" %>
        </div>
    </div>
    <table id="table" class="sort-table table table-bordered table-hover" data-prefix-url="${ctx}/admin/sys/user">
        <thead>
        <tr>
            <th style="width: 20px;">&nbsp;</th>
            <th style="width: 80px;">
                <a class="check-all" href="javascript:;">全选</a>
                |
                <a class="reverse-all" href="javascript:;">反选</a>
            </th>
            <th sort="id">编号</th>
            <th sort="username">用户名</th>
            <th sort="email">邮箱</th>
            <th sort="mobilePhoneNumber">手机号</th>
            <th>创建时间</th>
            <th>帐户状态</th>
            <th>管理员</th>
        </tr>
        <tbody>
        <c:forEach items="${page.content}" var="m">
            <tr>
                <td>
                    <a data-id="${m.id}"
                       class="btn-link toggle-child icon-plus-sign"
                       title="点击查看/隐藏组织机构和工作职务">
                    </a>
                </td>

                <td class="check">
                    <input type="checkbox" name="ids" value="${m.id}" data-status="${m.status}"
                           data-deleted="${m.deleted}"/>
                </td>
                <td>
                    <a href="${ctx}/admin/sys/user/${m.id}">
                        ${m.id}
                    </a>
                </td>
                <td>${m.username}</td>
                <td>${m.email}</td>
                <td>${m.mobilePhoneNumber}</td>
                <td><spring:eval expression="m.createDate"/></td>
                <td>${m.status.info}</td>
                <td>${m.admin?'是' : '否'}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <es:page page="${page}"/>
</div>
<es:contentFooter/>
<%@include file="/WEB-INF/jsp/common/admin/import-sys-js.jspf"%>
<script type="text/javascript">
    $(function() {
        $.sys.user.initUserListButton();
        $.app.toggleLoadTable($("#table"), "${ctx}/admin/sys/user/{parentId}/organizations")
    });
</script>

