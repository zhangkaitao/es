<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@include file="/WEB-INF/jsp/common/taglibs.jspf"%>
<form id="changelist-form" action="" method="post">
	<input type='hidden' name='csrfmiddlewaretoken'
		value='depy0qAotmsVOdoNJbNKvzmuBljvyVks' />
	<div class="results table-responsive">
		<table class="table table-bordered table-striped table-hover">
			<thead>
				<tr>
					<th scope="col" class="action-checkbox-column"><input
						type="checkbox" id="action-toggle" /></th>
					<th scope="col" class="sortable">
						<div class="dropdown pull-left">
							<a class="dropdown-toggle" data-toggle="dropdown" href="#">
								用户名 </a>
							<ul class="dropdown-menu" role="menu">
								<li><a href="?o=widget_type" class="active"><i
										class="icon-caret-up"></i> 正序</a></li>
								<li><a href="?o=-widget_type" class="active"><i
										class="icon-caret-down"></i> 倒序</a></li>
							</ul>
						</div>
					</th>
					<th scope="col" class="sortable">
						<div class="dropdown pull-left">
							<a class="dropdown-toggle" data-toggle="dropdown" href="#">
								用户主机IP </a>
							<ul class="dropdown-menu" role="menu">
								<li><a href="?o=page_id" class="active"><i
										class="icon-caret-up"></i> 正序</a></li>
								<li><a href="?o=-page_id" class="active"><i
										class="icon-caret-down"></i> 倒序</a></li>
							</ul>
						</div>
					</th>
					<th scope="col" class="sortable">
						<div class="dropdown pull-left">
							<a class="dropdown-toggle" data-toggle="dropdown" href="#">
								登录时间 </a>
							<ul class="dropdown-menu" role="menu">

								<li><a href="?o=user" class="active"><i
										class="icon-caret-up"></i> 正序</a></li>

								<li><a href="?o=-user" class="active"><i
										class="icon-caret-down"></i> 倒序</a></li>

							</ul>
						</div>

					</th>
					<th scope="col" class="sortable">
						<div class="dropdown pull-left">
							<a class="dropdown-toggle" data-toggle="dropdown" href="#">
								登录时间 </a>
							<ul class="dropdown-menu" role="menu">

								<li><a href="?o=user" class="active"><i
										class="icon-caret-up"></i> 正序</a></li>

								<li><a href="?o=-user" class="active"><i
										class="icon-caret-down"></i> 倒序</a></li>

							</ul>
						</div>

					</th>
					<th scope="col" class="sortable">
						<div class="dropdown pull-left">
							<a class="dropdown-toggle" data-toggle="dropdown" href="#">
								登录时间 </a>
							<ul class="dropdown-menu" role="menu">

								<li><a href="?o=user" class="active"><i
										class="icon-caret-up"></i> 正序</a></li>

								<li><a href="?o=-user" class="active"><i
										class="icon-caret-down"></i> 倒序</a></li>

							</ul>
						</div>

					</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${page.content}" var="m">
					<tr class="grid-item">
						<td class="action-checkbox"><input class="action-select"
							name="_selected_action" type="checkbox" value="278" /></td>
						<td>${m.host}</td>
						<td>${m.systemHost}</td>
						<td><pretty:prettyTime date="${m.startTimestamp}" /></td>
						<td><pretty:prettyTime date="${m.lastAccessTime}" /></td>
						<td>${m.id}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<es:pageAndAction page="${page}"/>
	</div>
</form>