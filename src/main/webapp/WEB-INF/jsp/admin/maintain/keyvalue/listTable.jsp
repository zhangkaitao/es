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
        <c:forEach items="${commonXxs}" var="x">
        	<th sort="${x.name }">${x.displayName }</th>
        </c:forEach>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${page.content}" var="m">
        <tr>
            <td class="check"><input type="checkbox" name="ids" value="${m.id}"></td>
            <td>
                <a class="btn btn-link btn-edit" href="${ctx}/admin/maintain/keyvalue/${m.id}">${m.id}</a>
            </td>
            
            <c:forEach items="${commonXxs}" var="x">
	        	<td>${m.${x.name}}</td>
	        </c:forEach>
        </tr>
    </c:forEach>
    </tbody>
</table>
<es:page page="${page}"/>
