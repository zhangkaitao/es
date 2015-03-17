<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jspf"%>
<table class="table table-bordered table-striped table-hover">
    <thead>
      <tr>
        <th scope="col" class="action-checkbox-column">
            <input type="checkbox" id="action-toggle" />
        </th>
        <th scope="col" class="sortable">
            <div class="dropdown pull-left">
              <a class="dropdown-toggle" data-toggle="dropdown" href="#">
                	id
              </a>
              <ul class="dropdown-menu" role="menu">
                
                  <li><a href="?o=widget_type" class="active"><i class="icon-caret-up"></i> 正序</a></li>
                
                  <li><a href="?o=-widget_type" class="active"><i class="icon-caret-down"></i> 倒序</a></li>
                
              </ul>
            </div>
        </th>
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
            		<input class="action-select" name="_selected_action" type="checkbox" value="278" />
        		</td>
      			<td><a class="btn btn-link btn-edit" href="${ctx}/admin/sys/site/${m.id}">${m.id}</a></td>
      			<td>${m.name}</td>
				<td><a href="${ctx}/admin/sys/site/${m.id}">查看</a></td>
      		</tr>
    	</c:forEach>
    </tbody>
  </table>
<es:page page="${page}"/>
