<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<table id="table" class="sort-table table table-bordered table-hover">
    <thead>
        <tr>
            <th style="width: 80px;">
                <a class="check-all" href="javascript:;">全选</a>
                |
                <a class="reverse-all" href="javascript:;">反选</a>
            </th>
            <th sort="id">编号</th>
            <th sort="name">名称</th>
            <th>是否显示</th>
        </tr>
    </thead>
    <tbody>
    <c:forEach items="${page.content}" var="m">
        <tr>
            <td class="check"><input type="checkbox" name="ids" value="${m.id}"></td>
            <td>
                <a class="btn btn-link btn-edit" href="${ctx}/showcase/status/show/${m.id}">${m.id}</a>
            </td>
            <td>${m.name}</td>
            <td>${m.status.info}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<es:page page="${page}"/>
