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
            <i class="icon-fixed-width icon-lock"></i> 列表
          </a>
        </div>
        <div class="navbar-collapse collapse">
          <ul class="nav navbar-nav">
            
<li class="dropdown bookmarks">
  <a id="drop-bookmark" title="特别关注" class="dropdown-toggle" role="button" data-toggle="dropdown" href="#">
  <i class="icon-star"></i> 特别关注<span class="caret"></span></a>
  <ul id="bookmark-menu" class="dropdown-menu" role="menu">
    <li class="with_menu_btn">
      <a href="#" title="1"><i class="icon-star"></i> 127.0.0.1:8080 </a>
        <span><a href="#" class="dropdown-menu-btn"> <i class="icon-trash"></i></a></span>
    </li>
  </ul>
</li>

<li class="dropdown">
  <a id="drop-filter" class="dropdown-toggle" role="button" data-toggle="dropdown" href="#">
  <i class="icon-filter"></i> 过滤器
  <span class="caret"></span></a>
  <ul id="filter-menu" class="dropdown-menu" role="menu" aria-labelledby="drop-filter">

	<li class="dropdown-submenu filter-char">
	  <a><i class="icon-filter text-muted"></i> 名称</a>
	  <div class="popover right">
	    <div class="arrow"></div>
	    <h3 class="popover-title">
	      搜索名称
	    </h3>
	    <div class="popover-content">
	        <form id="searchForm" class="search-form" data-change-search="true">
	        <div class="input-group">
	          <input name="_p_widget_type__contains" class="input-char form-control" type="text" value="" placeholder="输入 名称…"/>
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
    		<a href="${ctx}/admin/sys/site/create" class="btn btn-primary"><i class="icon-plus icon-white"></i> 新增 站点</a>
        </div>
     </div>
</div>