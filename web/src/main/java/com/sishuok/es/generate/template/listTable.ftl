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
        <th sort="name">名称</th>
        <th>内容</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${r"${page.content}"}" var="m">
        <tr>
            <td class="check"><input type="checkbox" name="ids" value="${r"${m.id}"}"></td>
            <td>
                <a class="btn btn-link btn-edit" href="${r"${ctx}"}/admin/${sysName}/${folderName}/${r"${m.id}"}">${r"${m.id}"}</a>
            </td>
            <td>${r"${m.name}"}</td>
            <td><a href="${r"${ctx}"}/admin/${sysName}/${folderName}/${r"${m.id}"}">查看</a></td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<es:page page="${r"${page}"}"/>


