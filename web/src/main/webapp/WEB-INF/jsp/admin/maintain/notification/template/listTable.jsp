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
        <th style="width: 100px" sort="id">编号</th>
        <th style="width: 60px">所属系统</th>
        <th style="width: 150px" sort="name">模板名称</th>
        <th>模板内容</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${page.content}" var="m">
        <tr>
            <td class="check"><input type="checkbox" name="ids" value="${m.id}"></td>
            <td>
                <a class="btn btn-link btn-edit" href="${ctx}/admin/maintain/notification/template/${m.id}">${m.id}</a>
            </td>
            <td>${m.system.info}</td>
            <td>${m.name}</td>
            <td>${m.template}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<es:page page="${page}"/>
