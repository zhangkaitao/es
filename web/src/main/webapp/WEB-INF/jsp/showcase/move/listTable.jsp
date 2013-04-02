<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jspf"%>
<table  id="table" class="sort-table move-table table table-bordered table-hover"
         data-move-url-prefix="${ctx}/showcase/move">
    <thead>
    <tr>
        <th style="width: 70px">
            <a class="check-all" href="javascript:;">全选</a>
            |
            <a class="reverse-all" href="javascript:;">反选</a>
        </th>
        <th sort="id">编号</th>
        <th sort="name">姓名</th>
        <th sort="weight">权重</th>
        <th>是否显示</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${page.content}" var="m" varStatus="status">
        <tr id="${m.id}">
            <td class="check"><input type="checkbox" name="ids" value="${m.id}"></td>
            <td>
                <a href="${ctx}/showcase/move/${m.id}">${m.id}</a>
            </td>
            <td>${m.name}</td>
            <td>
                 ${m.weight}
                <es:movable page="${page}" first="${status.first}" last="${status.last}"/>
            </td>
            <td>${m.show?'是':'否'}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<es:page page="${page}"/>
