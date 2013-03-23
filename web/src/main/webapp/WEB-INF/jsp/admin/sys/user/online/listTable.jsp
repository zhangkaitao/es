<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jspf"%>
<table id="table" class="sort-table table table-bordered table-hover table-striped" data-async="true">
    <thead>
    <tr>
        <th sort="username" style="width: 60px;">用户</th>
        <th style="width: 100px">用户会话ID</th>
        <th style="width: 100px">用户主机IP</th>
        <th style="width: 100px">系统主机IP</th>
        <th>User-Agent</th>
        <th style="width: 90px">登录时间</th>
        <th style="width: 90px">最后访问时间</th>
        <th style="width: 60px">状态</th>
        <th style="width: 60px">操作</th>
    </tr>
    <tbody>
    <c:forEach items="${page.content}" var="m">
        <tr>
            <td>
                <c:if test="${m.userId eq 0}">游客</c:if>
                <a href="${ctx}/admin/sys/user/${m.userId}">${m.username}</a>
            </td>
            <td>${m.id}</td>
            <td>${m.host}</td>
            <td>${m.systemHost}</td>
            <td>${m.userAgent}</td>
            <td><time:prettyTime date="${m.startTimestamp}"/></td>
            <td><time:prettyTime date="${m.lastAccessTime}"/></td>
            <td>${m.status.info}</td>
            <td>
                <c:if test="${m.status eq 'on_line'}">
                    <a href="${ctx}/admin/sys/user/online/${m.id}/forceLogout" class="btn btn-link btn-edit">强制退出</a>
                </c:if>
            </td>
        </tr>
    </c:forEach>
    </tbody>
    <tfoot>
        <tr>
            <td colspan="1000">
                <es:page page="${page}" />
            </td>
        </tr>
    </tfoot>
</table>

