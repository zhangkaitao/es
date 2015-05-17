<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="navbar content-navbar navbar-default navbar-xs" data-toggle="breakpoint"
        data-class-xs="navbar content-navbar navbar-inverse navbar-xs"
        data-class-sm="navbar content-navbar navbar-default navbar-xs">
        <div class="navbar-header">
          
          
<button class="navbar-toggle pull-left" data-toggle="class" data-target="#main" data-class-name="show_menu">
  <i class="icon-list"></i>
</button>

  <a href="/xadmin/userwidget/add/" class="navbar-toggle pull-right"><i class="icon-plus icon-white"></i></a>

<button class="navbar-toggle pull-right" data-toggle="collapse" data-target=".content-navbar .navbar-collapse">
  <i class="icon-filter icon-white"></i>
</button>


          <a class="navbar-brand" data-toggle="collapse" data-target="#top-nav .navbar-collapse">
            <i class="icon-dashboard"></i> 用户管理
          </a>
        </div>
        <div class="navbar-collapse collapse">
          <ul class="nav navbar-nav">
			<li class="dropdown">
			  <a id="drop-filter" class="dropdown-toggle" role="button" data-toggle="dropdown" href="#">
			  <i class="icon-filter"></i> 过滤器
			  
			  <span class="badge badge-success"></span>
			  
			  <span class="caret"></span></a>
			  <ul id="filter-menu" class="dropdown-menu" role="menu" aria-labelledby="drop-filter">
			    
			    <li><a href="?" class="btn-clear-search"><i class="icon-trash"></i> 清除过滤器</a></li>
    			<li class="divider"></li>
			    
				 <!-- 
				<li class="dropdown-submenu filter-fk-search">
				  <a><i class="icon-filter text-muted"></i> 用户名</a>
				  <div class="popover right">
				    <div class="arrow"></div>
				    <h3 class="popover-title">
				      搜索 用户名
				    </h3>
				    <div class="popover-content">
				      <form class="exform" method="get" action="">
				        
				          <input name="_p_user__id__exact" class="select-search" type="hidden" value="" 
				          data-search-url="/auth/user/" data-label=" " data-choices="?"
				          data-placeholder="搜索 用户…"/>
				
				          <button type="submit" class="btn btn-success">应用</button>
				        
				      </form>
				    </div>
				  </div>
				</li>
				  -->	
				<li class="dropdown-submenu filter-char">
				  <a><i class="icon-filter text-muted"></i> 用户主机IP</a>
				  <div class="popover right">
				    <div class="arrow"></div>
				    <h3 class="popover-title">
				      搜索 用户主机IP
				    </h3>
				    <div class="popover-content">
				      <form id="searchForm" class="form-inline search-form">
				        
				        <div class="input-group">
				          
				          <esform:input path="search.systemHost_like" cssClass="input-char form-control" type="text" placeholder="用户主机IP 模糊匹配"/>
				          <span class="input-group-btn">
				          
				            <button class="btn btn-success" type="submit"><i class="icon-search icon-white"></i></button>
				          </span>
				        </div>
				      </form>
				    </div>
				  </div>
				</li>
			
			  </ul>
			</li>
          </ul>
          
          
          <div class="navbar-btn navbar-right hide-xs">
    		<a href="/xadmin/userwidget/add/" class="btn btn-primary"><i class="icon-plus icon-white"></i> 增加 用户小组件</a>
          </div>
        </div>
      </div>
