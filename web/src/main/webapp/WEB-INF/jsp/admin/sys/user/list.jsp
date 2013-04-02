<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jspf"%>
<es:contentHeader/>

<div data-table="table" class="panel">

    <ul class="nav nav-tabs">
        <li <c:if test="${param['search.deleted_eq'] ne 'true' and param['search.status_eq'] ne 'blocked'}">class="active"</c:if>>
            <a href="${ctx}/admin/sys/user">
                <i class="icon-table"></i>
                所有用户列表
            </a>
        </li>
        <li <c:if test="${param['search.deleted_eq'] eq 'true'}">class="active"</c:if>>
            <a href="${ctx}/admin/sys/user?search.deleted_eq=true">
                <i class="icon-table"></i>
                已删除用户列表
            </a>
        </li>
        <li <c:if test="${param['search.status_eq'] eq 'blocked'}">class="active"</c:if>>
            <a href="${ctx}/admin/sys/user?search.status_eq=blocked">
                <i class="icon-table"></i>
                已封禁用户列表
            </a>
        </li>
    </ul>

    <es:showMessage/>


    <div class="row-fluid tool ui-toolbar">
        <div class="span4">
            <div class="btn-group">
                <a class="btn btn-create">
                    <span class="icon-file"></span>
                    新增
                </a>
                <a id="update" class="btn btn-update">
                    <span class="icon-edit"></span>
                    修改
                </a>
                <a class="btn btn-batch-delete">
                    <span class="icon-trash"></span>
                    删除
                </a>
                <div class="btn-group">
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
            </div>
        </div>
        <div class="span8">
            <%@include file="searchForm.jsp" %>
        </div>
    </div>

    <table id="table" class="sort-table table table-bordered table-hover">
          <thead>
            <tr>
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
                <td class="check">
                    <input type="checkbox" name="ids" value="${m.id}" data-status="${m.status}" data-deleted="${m.deleted}"/>
                </td>
                <td>
                    <a href="${ctx}/admin/sys/user/${m.id}">${m.id}</a>
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
<%@include file="include/import-js.jspf"%>
<script type="text/javascript">
    $(function() {
        initUserListButton();
    });
</script>
