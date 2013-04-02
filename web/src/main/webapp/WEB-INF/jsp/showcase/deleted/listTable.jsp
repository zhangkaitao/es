<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jspf"%>
<table id="table" class="sort-table table table-bordered table-hover" data-async="true">
    <thead>
    <tr>
        <th style="width: 70px;">
            <a class="check-all" href="javascript:;">全选</a>
            |
            <a class="reverse-all" href="javascript:;">反选</a>
        </th>
        <th sort="id">编号</th>
        <th sort="name">姓名</th>
        <th>年龄</th>
        <th>出生日期</th>
        <th>性别</th>
        <th>是否显示</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${page.content}" var="m">
        <tr>
            <td class="check"><input type="checkbox" name="ids" value="${m.id}"></td>
            <td>
                <a class="btn btn-link btn-edit" href="${ctx}/showcase/deleted/${m.id}">${m.id}</a>
            </td>
            <td>${m.name}</td>
            <td>${m.age}</td>
            <td><spring:eval expression="m.birthday"/></td>
            <td>${m.sex.info}</td>
            <td>${m.show?'是':'否'}</td>

        </tr>
    </c:forEach>
    </tbody>
</table>
<es:page page="${page}"/>

