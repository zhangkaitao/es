<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jspf"%>
<table id="table" class="sort-table table table-bordered table-hover" data-async="true">
    <thead>
    <tr>
        <th style="width: 20px;">&nbsp;</th>
        <th style="width: 80px;">
            <a class="check-all" href="javascript:;">全选</a>
            |
            <a class="reverse-all" href="javascript:;">反选</a>
        </th>
        <th sort="id">编号</th>
        <th sort="name">名称</th>
        <th>类型</th>
        <th>开始时间</th>
        <th>结束时间</th>
        <th>是否显示</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${page.content}" var="m">
        <tr>
            <td>
                <a data-id="${m.id}" class="btn-link toggle-child icon-plus-sign"></a>
            </td>
            <td class="check">
                <input type="checkbox" name="ids" value="${m.id}">
            </td>
            <td>
                <a href="${ctx}/showcase/parentchild/parent/${m.id}">${m.id}</a>
            </td>
            <td>${m.name}</td>
            <td>${m.type.info}</td>
            <td><spring:eval expression="m.beginDate"/></td>
            <td><spring:eval expression="m.endDate"/></td>
            <td>${m.show ? '是' : '否'}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<es:page page="${parents}"/>
<script type="text/javascript">
    $(function() {
        $.app.toggleLoadTable($("#table"), "${ctx}/showcase/parentchild/parent/{parentId}/child")
    });
</script>

