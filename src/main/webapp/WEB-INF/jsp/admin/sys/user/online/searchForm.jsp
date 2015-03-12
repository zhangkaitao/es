<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="navbar content-navbar navbar-default navbar-xs" data-toggle="breakpoint"
        data-class-xs="navbar content-navbar navbar-inverse navbar-xs"
        data-class-sm="navbar content-navbar navbar-default navbar-xs">
        <div class="navbar-header">
          
          
<button class="navbar-toggle pull-left" data-toggle="class" data-target="#body-content" data-class-name="show_menu">
  <i class="icon-list"></i>
</button>

  <a href="/xadmin/userwidget/add/" class="navbar-toggle pull-right"><i class="icon-plus icon-white"></i></a>

<button class="navbar-toggle pull-right" data-toggle="collapse" data-target=".content-navbar .navbar-collapse">
  <i class="icon-filter icon-white"></i>
</button>


          <a class="navbar-brand" data-toggle="collapse" data-target="#top-nav .navbar-collapse">
            <i class="icon-dashboard"></i> 用户小组件
          </a>
        </div>
        <div class="navbar-collapse collapse">
          <ul class="nav navbar-nav">
            
<li class="dropdown bookmarks">
  <a id="drop-bookmark" title="书签" class="dropdown-toggle" role="button" data-toggle="dropdown" href="#">
  <i class="icon-book"></i> <i class="fa fa-book fa-fw"></i>书签<span class="caret"></span></a>
  <ul id="bookmark-menu" class="dropdown-menu" role="menu">
    
    
    <li class="with_menu_btn">
      <a href="/xadmin/userwidget/?_cols=id.widget_type.page_id.user&amp;_p_user__id__exact=1" title="1"><i class="icon-bookmark"></i> 1</a>
      
        <a href="/xadmin/bookmark/28/update/" class="dropdown-menu-btn"><i class="icon-pencil"></i></a>
      
    </li>
    
    
  </ul>
</li>

<li class="dropdown">
  <a id="drop-filter" class="dropdown-toggle" role="button" data-toggle="dropdown" href="#">
  <i class="icon-filter"></i> 过滤器
  <span class="caret"></span></a>
  <ul id="filter-menu" class="dropdown-menu" role="menu" aria-labelledby="drop-filter">
    
  	
<li class="dropdown-submenu filter-fk-search">
  <a><i class="icon-filter text-muted"></i> 用户</a>
  <div class="popover right">
    <div class="arrow"></div>
    <h3 class="popover-title">
      搜索 用户
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

<li class="dropdown-submenu filter-char">
  <a><i class="icon-filter text-muted"></i> Widget类型</a>
  <div class="popover right">
    <div class="arrow"></div>
    <h3 class="popover-title">
      搜索 Widget类型
    </h3>
    <div class="popover-content">
      <form method="get" action="">
        
        <div class="input-group">
          <input name="_p_widget_type__contains" class="input-char form-control" type="text" value="" placeholder="输入 Widget类型…"/>
          <span class="input-group-btn">
          
            <button class="btn btn-success" type="submit"><i class="icon-search icon-white"></i></button>
          </span>
        </div>
      </form>
    </div>
  </div>
</li>
<li class="dropdown-submenu filter-char">
  <a><i class="icon-filter text-muted"></i> 页面</a>
  <div class="popover right">
    <div class="arrow"></div>
    <h3 class="popover-title">
      搜索 页面
    </h3>
    <div class="popover-content">
      <form method="get" action="">
        
        <div class="input-group">
          <input name="_p_page_id__contains" class="input-char form-control" type="text" value="" placeholder="输入 页面…"/>
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
