<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jspf"%>
<table id="historyTable" class="sort-table table table-bordered table-hover" data-async="true">
    <thead>
    <tr>
        <th sort="username" style="width: 60px;">用户</th>
        <th style="width: 100px">用户会话ID</th>
        <th style="width: 100px">用户主机IP</th>
        <th style="width: 100px">系统主机IP</th>
        <th>User-Agent</th>
        <th style="width: 90px">最后登录时间</th>
        <th style="width: 90px">最后退出时间</th>
        <th style="width: 60px">登录次数</th>
        <th style="width: 80px">总在线时长</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${page.content}" var="m">
        <tr>
            <td><a href="${ctx}/admin/sys/user/${m.userId}">${m.username}</a></td>
            <td>${m.uid}</td>
            <td>${m.host}</td>
            <td>${m.systemHost}</td>
            <td>${m.userAgent}</td>
            <td><pretty:prettyTime date="${m.lastLoginTimestamp}"/></td>
            <td><pretty:prettyTime date="${m.lastStopTimestamp}"/></td>
            <td>${m.loginCount}</td>
            <td><pretty:prettySecond seconds="${m.totalOnlineTime}"/></td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<es:page page="${page}"/>

