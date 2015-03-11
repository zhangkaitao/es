<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="panel-group hide-sm nav-sitemenu col-md-2" id="nav-accordion">
  <div class="panel panel-default">
	  <c:forEach items="${menus}" var="m">
		   <div class="panel-heading">
		      <h6 class="panel-title">
		        	<span class="badge badge-info">3</span>
					<a class="accordion-toggle" data-toggle="collapse" data-parent="#nav-accordion" href="#nav-panel-${m.id}"><i class="icon-fixed-width ${m.icon}"></i>${m.name}</a>
				</h6>
			</div>
			<div id="nav-panel-${m.id}" class="list-group panel-collapse collapse">
	      		<c:forEach items="${m.children}" var="c">
			        <es:submenu menu="${c}"/>
			   	</c:forEach>
			</div>
		</c:forEach>
	</div>
</div>
