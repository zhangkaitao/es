<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@include file="/WEB-INF/jsp/common/taglibs.jspf"%>

<table class="table table-bordered table-striped table-hover">
	<thead>
		<tr>
			<th scope="col" class="sortable">名称</th>
			<th scope="col" class="sortable">说明</th>
			<th scope="col" class="sortable">操作</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${classLists}" var="m">
			<tr class="grid-item">
				<td>${m.simpleName}</td>
				<c:set var="state" value="false"></c:set>
				<c:forEach items="${xxsLists}" var="x">
					<c:if test="${m.simpleName == x.classname}">
						<c:set var="state" value="true"></c:set>
						<td>${x.name}</td>
						<td><a href="${ctx}/admin/sys/xxs/xxs/${x.id}/update888">修改属性</a></td>
					</c:if>
				</c:forEach>
				<c:if test="${!state}">
					<td>-------</td>
					<td><a href="${ctx}/admin/sys/xxs/xxs/${m.canonicalName}/create">设置属性</a></td>
				</c:if>
				<c:set var="state" value="false"></c:set>
			</tr>
		</c:forEach>
	</tbody>
</table>
