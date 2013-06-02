<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jspf"%>
<table id="table" class="sort-table table table-bordered table-hover">
    <thead>
    <tr>
        <th style="width: 20px;">&nbsp;</th>
        <th style="width: 80px">
            <a class="check-all" href="javascript:;">全选</a>
            |
            <a class="reverse-all" href="javascript:;">反选</a>
        </th>
        <th style="width: 100px" sort="id">编号</th>
        <th style="width: 150px" sort="name">角色名称</th>
        <th sort="role">角色标识</th>
        <th>详细描述</th>
        <th>状态</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${page.content}" var="m">
        <tr>
            <td>
                <a data-id="${m.id}" class="btn-link toggle-child icon-plus-sign" title="点击查看/隐藏资源和权限"></a>
            </td>
            <td class="check"><input type="checkbox" name="ids" value="${m.id}"></td>
            <td>
                <a class="btn btn-link btn-edit" href="${ctx}/admin/sys/permission/role/${m.id}">${m.id}</a>
            </td>
            <td>${m.name}</td>
            <td>${m.role}</td>
            <td>${m.description}</td>
            <td>${m.show eq true ? '可用' : '不可用'}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<es:page page="${page}"/>

