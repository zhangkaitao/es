<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@include file="/WEB-INF/jsp/common/taglibs.jspf"%>
<div class="results table-responsive">
	<table class="table table-bordered table-striped table-hover">
		<thead>
			<tr>
				<es:showListTitle bean="${bean }"/>
				<th scope="col" class="sortable">操作</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${classLists}" var="m">
				<tr class="grid-item">
					<c:set var="state" value="false"></c:set>
					<c:forEach items="${beanLists}" var="x">
						<c:if test="${m.simpleName == x.cname}">
							<c:set var="state" value="true"></c:set>
							<es:showListValue bean="${bean}" page="${page }" m="${x}"/>
							<td><a href="${ctx}/admin/sys/bean/bean/${x.id}/modify">修改属性</a></td>
						</c:if>
					</c:forEach>
					<c:if test="${!state}">
						<td>${m.simpleName }</td>
						<td>-------</td>
						<td><a href="${ctx}/admin/sys/bean/bean/${m.canonicalName}/create">设置属性</a></td>
					</c:if>
					<c:set var="state" value="false"></c:set>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>