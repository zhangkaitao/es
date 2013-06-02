<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jspf"%>
<table id="table" class="sort-table table table-bordered table-hover" data-async="true">
    <thead>
    <tr>
        <th style="width: 80px">
            <a class="check-all" href="javascript:;">全选</a>
            |
            <a class="reverse-all" href="javascript:;">反选</a>
        </th>
        <th sort="id" style="width: 80px">编号</th>
        <th sort="name">分组名称</th>
        <th style="width: 80px">类型</th>
        <th style="width: 80px">是否默认分组</th>
        <th style="width: 70px">是否有效</th>
        <th style="width: 60px">操作</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${page.content}" var="m">
        <tr>
            <td class="check"><input type="checkbox" name="ids" value="${m.id}"></td>
            <td>
                <a class="btn btn-link btn-edit" href="${ctx}/admin/sys/group/${m.id}">${m.id}</a>
            </td>
            <td>${m.name}</td>
            <td>${m.type.info}</td>
            <td>${m.defaultGroup eq true ? '是' : '否'}</td>
            <td>${m.show ? '是' : '否'}</td>
            <td>
                <a class="btn btn-link" href="${ctx}/admin/sys/group/${m.id}/listRelation">查看组</a>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<es:page page="${page}"/>
