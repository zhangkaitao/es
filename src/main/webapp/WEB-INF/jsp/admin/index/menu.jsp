<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="sidebar-shortcuts" id="sidebar-shortcuts">
	<div class="sidebar-shortcuts-large" id="sidebar-shortcuts-large">
		<button class="btn btn-success">
			<i class="icon-signal"></i>
		</button>

		<button class="btn btn-info">
			<i class="icon-pencil"></i>
		</button>

		<button class="btn btn-warning">
			<i class="icon-group"></i>
		</button>

		<button class="btn btn-danger">
			<i class="icon-cogs"></i>
		</button>
	</div>

	<div class="sidebar-shortcuts-mini" id="sidebar-shortcuts-mini">
		<span class="btn btn-success"></span>

		<span class="btn btn-info"></span>

		<span class="btn btn-warning"></span>

		<span class="btn btn-danger"></span>
	</div>
</div><!-- #sidebar-shortcuts -->
<ul class="nav nav-list">
	<c:forEach items="${menus}" var="m">
		<li>
			<a href="#" class="dropdown-toggle">
				<i class="${m.icon}"></i>
				<span class="menu-text"> ${m.name} </span>
				<b class="arrow icon-angle-down"></b>
			</a>
			<ul class="submenu">
				<c:forEach items="${m.children}" var="c">
			        <es:submenu menu="${c}"/>
			   	</c:forEach>
			</ul>
		</li>
	</c:forEach>
</ul><!-- /.nav-list -->
<div class="sidebar-collapse" id="sidebar-collapse">
	<i class="icon-double-angle-left" data-icon1="icon-double-angle-left" data-icon2="icon-double-angle-right"></i>
</div>