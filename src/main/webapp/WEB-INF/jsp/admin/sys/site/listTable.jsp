<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jspf"%>

<table class="table table-bordered table-striped table-hover">
    <thead>
      <tr>
        <th scope="col" class="action-checkbox-column">
            <input type="checkbox" id="action-toggle" />
        </th>
        <es:showListTitle bean="${bean}" />
        <th scope="col" class="sortable">
            <div class="dropdown pull-left">
              <a class="dropdown-toggle" data-toggle="dropdown" href="#">
                	名称
              </a>
              <ul class="dropdown-menu" role="menu">
                
                  <li><a href="?o=page_id" class="active"><i class="icon-caret-up"></i> 正序</a></li>
                
                  <li><a href="?o=-page_id" class="active"><i class="icon-caret-down"></i> 倒序</a></li>
                
              </ul>
            </div>
          
        </th>
        <th scope="col" class="sortable">
                操作
        </th>
      </tr>
    </thead>
    <tbody>
   		<c:forEach items="${page.content}" var="m">
      		<tr class="grid-item">
        		<td  class="action-checkbox">
            		<input class="action-select" type="checkbox" name="ids" value="${m.id}" />
        		</td>
        		<es:showListValue page="${page }" bean="${bean}" m="${m}"/>
      			<td class="nowrap">
      				<div class="btn-group pull-right">
		                <a class="editable-handler" title="输入name" data-editable-field="name" data-editable-loadurl="/host/host/1/patch/?fields=name"><i class="icon-edit"></i></a>
		                <a data-res-uri="/host/idc/34/detail/" data-edit-uri="/host/idc/34/update/" class="details-handler" rel="tooltip" title="${m.name} 详情"><i class="icon-info-sign"></i></a>
		            </div>
      				<a href="${ctx}/admin/sys/site/${m.id}">${m.name}</a>
      			</td>
				<td><a href="${ctx}/admin/sys/site/${m.id}">查看</a></td>
      		</tr>
    	</c:forEach>
    </tbody>
</table>
<es:pageAndAction page="${page}"/>

