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
        <th sort="id">编号</th>
        <th sort="category">所属类别</th>
        <th sort="name">名称</th>
        <th sort="price">价格</th>
        <th>开始时间</th>
        <th>结束时间</th>
        <th>是否显示</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${page.content}" var="m" varStatus="status">
        <tr id="${m.id}">
            <td class="check"><input type="checkbox" name="ids" value="${m.id}"></td>
            <td>
                <a href="${ctx}/showcase/product/product/${m.id}">${m.id}</a>
            </td>
            <td>
                <a class="btn btn-link btn-edit"
                   href="${ctx}/showcase/product/product?search.category.id_in=${m.category.id}">
                    ${m.category.name}
                </a>
            </td>
            <td>${m.name}</td>
            <td>${m.price}</td>
            <td><spring:eval expression="m.beginDate"/></td>
            <td><spring:eval expression="m.endDate"/></td>
            <td>${m.show?'是':'否'}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<es:page page="${page}"/>
