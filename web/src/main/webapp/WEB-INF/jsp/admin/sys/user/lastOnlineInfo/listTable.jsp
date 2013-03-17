<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jspf"%>
<table id="historyTable" class="sort-table table table-bordered table-hover table-striped" data-async="true">
    <thead>
    <tr>
        <th sort="user.username">用户</th>
        <th>本地 Session ID</th>
        <th>用户会话ID</th>
        <th>用户IP</th>
        <th>User-Agent</th>
        <th>系统IP</th>
        <th>最后登录时间</th>
        <th>最后退出时间</th>
        <th>登录次数</th>
        <th>总在线时长</th>
    </tr>
    <tbody>
    <c:forEach items="${page.content}" var="m">
        <tr>
            <td><a href="${ctx}/admin/sys/user/${m.user.id}">${m.user.username}</a></td>
            <td>${m.jsessionId}</td>
            <td>${m.uid}</td>
            <td>${m.userAgent}</td>
            <td>${m.userIp}</td>
            <td>${m.systemIp}</td>
            <td><spring:eval expression="m.lastLoginDate"/></td>
            <td><spring:eval expression="m.lastLogoutDate"/></td>
            <td><spring:eval expression="m.loginCount"/></td>
            <td><spring:eval expression="m.totalOnlineTime"/></td>
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

