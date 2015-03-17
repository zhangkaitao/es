<%@ page contentType="text/html;charset=UTF-8" language="java" %>
	<div id="main" class="clearfix row">
    	<div id="left-side" class="col-sm-1 col-md-2">
      		<!-- 左侧导航开始 -->
			<div class="panel-group hide-sm nav-sitemenu col-md-2" id="nav-accordion">
				<div class="panel panel-default">
					<div class="panel-heading">
					<h6 class="panel-title">
						<span class="badge badge-info">3</span>
							 <a class="accordion-toggle" data-toggle="collapse" data-parent="#nav-accordion" href="#nav-panel-1">
							     <i class="icon-fixed-width icon-lock"></i>
							          	用户管理
							  </a>
						</h6>
					</div>
					<div id="nav-panel-1" class="list-group panel-collapse collapse in">
						<a href="/admin/sys/user/online" class="list-group-item active">
							 <i class="icon-fixed-width icon-globe"></i> 用户在线列表</span>
						</a>
					</div>
					<div id="nav-panel-1" class="list-group panel-collapse collapse in">
						<a href="/admin/sys/site" class="list-group-item">
							 <i class="icon-fixed-width icon-globe"></i> 站点管理</span>
						</a>
					</div>
				</div>
			</div>
		</div>	
		<!-- 左侧导航结束 -->
