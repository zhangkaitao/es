<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jspf"%>
<div class="scroll-pane">
    <table id="table" class="sort-table table table-bordered table-hover"
            data-async="true" data-async-callback="callback" data-async-container="panel" style="width:1000px;max-width: 1000px;">
        <thead>
        <tr>
            <th style="width: 80px;">
                <a class="check-all" href="javascript:;">全选</a>
                |
                <a class="reverse-all" href="javascript:;">反选</a>
            </th>
            <th sort="username" style="width: 60px;">用户</th>
            <th style="width: 100px">用户主机IP</th>
            <th style="width: 100px">系统主机IP</th>
            <th style="width: 90px">登录时间</th>
            <th style="width: 90px">最后访问时间</th>
            <th style="width: 50px">状态</th>
            <th>User-Agent</th>
            <th>用户会话ID</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${page.content}" var="m">
            <tr>
                <td class="check">
                    <input type="checkbox" name="ids" value="${m.id}"/>
                </td>
                <td>
                    <c:if test="${m.userId eq 0}">游客</c:if>
                    <a href="${ctx}/admin/sys/user/${m.userId}">${m.username}</a>
                </td>
                <td>${m.host}</td>
                <td>${m.systemHost}</td>
                <td><pretty:prettyTime date="${m.startTimestamp}"/></td>
                <td><pretty:prettyTime date="${m.lastAccessTime}"/></td>
                <td>${m.status.info}</td>
                <td>${m.userAgent}</td>
                <td>${m.id}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
<es:page page="${page}"/>
