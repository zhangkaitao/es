<%@ tag pageEncoding="UTF-8" description="列文本"%>
<%@ tag import="java.util.List"%>
<%@ attribute name="page" type="org.springframework.data.domain.Page" required="true" description="分页" %>
<%@ attribute name="bean" type="com.sishuok.es.sys.bean.entity.Bean"
	required="true" description="实体对象"%>
<%@ attribute name="m" type="java.lang.Object" required="true"
	description="实体对象"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:if test="${not empty bean}">
	<c:forEach items="${bean.beanItems}" var="bi">
		<c:if test="${bi.isShow }">
			<c:set var="tname" value="${bi.name}" scope="page" />
			<td>${m[tname]}
				<c:if test="${bi.isEdit }">
				
					<c:if test="${page.totalElements > 1}">
				        <a href="#" class="ui-icon ui-icon-extlink pop-movable"
				           rel="popover"
				           data-placement="left"
				           data-html="true"
				           data-original-title="请输入移动到的数据编号"
				           data-content="<input type='text' class='input-small id'/>&nbsp;&nbsp;<a href='#' class='btn-link btn-custom popover-up-btn'>之后</a>|<a href='#' class='btn-link popover-down-btn'>之前</a>"></a>
				    </c:if>
				
					<div class="btn-group pull-right">
						<a class="editable-handler" title="输入新 ${bi.displayName }"
							data-editable-field="name"
							data-html="true"
							data-original-title="请输入移动到的数据编号"
							data-editable-loadurl="/host/host/1/patch/?fields=name">
							<i class="icon-edit"></i></a> 
							
							<a data-res-uri="/host/idc/34/detail/"
							data-edit-uri="/host/idc/34/update/" class="details-handler"
							rel="tooltip" title="${m.name} 详情"><i class="icon-info-sign"></i></a>
					</div>
				</c:if>
			</td>
		</c:if>
	</c:forEach>
</c:if>
