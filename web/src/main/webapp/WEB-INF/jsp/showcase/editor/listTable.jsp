<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jspf"%>
<table id="table" class="sort-table table table-bordered table-hover">
    <thead>
    <tr>
        <th style="width: 70px;">
            <a class="check-all" href="javascript:;">全选</a>
            |
            <a class="reverse-all" href="javascript:;">反选</a>
        </th>
        <th sort="id">编号</th>
        <th sort="title">标题</th>
        <th>内容</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${page.content}" var="m">
        <tr>
            <td class="check"><input type="checkbox" name="ids" value="${m.id}"></td>
            <td>
                <a class="btn btn-link btn-edit" href="${ctx}/showcase/editor/${m.id}">${m.id}</a>
            </td>
            <td>${m.title}</td>
            <td><a href="${ctx}/showcase/editor/${m.id}">查看</a></td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<es:page page="${page}"/>

