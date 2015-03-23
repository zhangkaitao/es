<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jspf"%>

<table class="table table-bordered table-striped table-hover">
    <thead>
      <tr>
        <th scope="col" class="sortable">
                	名称
        </th>
        <th scope="col" class="sortable">
                	说明
        </th>
        <th scope="col" class="sortable">
                	操作
        </th>
      </tr>
    </thead>
    <tbody>
   		<c:forEach items="${page.content}" var="m">
	      		<tr class="grid-item">
	      			<td>${m.name}</td>
	      			<td>${m.comments}</td>
					<td><a href="${ctx}/admin/sys/xxs/xxs/${m.id}/update">设置属性</a></td>
	      		</tr>
    	</c:forEach>
    </tbody>
</table>
