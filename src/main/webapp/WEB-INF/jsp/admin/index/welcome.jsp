<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jspf"%>
<div id="content-block" class="col-sm-11 col-md-10">
      
      <ul class="breadcrumb">
      
      
      <li>
        <a href="/">首页</a>
      </li>
      
      <li>
        用户
        
      </li>
      
      
      </ul>
      

      
      <div class="navbar content-navbar navbar-default navbar-xs" data-toggle="breakpoint"
        data-class-xs="navbar content-navbar navbar-inverse navbar-xs"
        data-class-sm="navbar content-navbar navbar-default navbar-xs">
        <div class="navbar-header">
          
          
<button class="navbar-toggle pull-left" data-toggle="class" data-target="#body-content" data-class-name="show_menu">
  <i class="icon-list"></i>
</button>

  <a href="/auth/user/add/" class="navbar-toggle pull-right"><i class="icon-plus icon-white"></i></a>

<button class="navbar-toggle pull-right" data-toggle="collapse" data-target=".content-navbar .navbar-collapse">
  <i class="icon-filter icon-white"></i>
</button>


          <a class="navbar-brand" data-toggle="collapse" data-target="#top-nav .navbar-collapse">
            <i class="icon-user"></i> 用户
          </a>
        </div>
        <div class="navbar-collapse collapse">
          <ul class="nav navbar-nav">
            
<li class="dropdown bookmarks">
  <a id="drop-bookmark" title="书签" class="dropdown-toggle" role="button" data-toggle="dropdown" href="#">
  <i class="icon-book"></i> 书签<span class="caret"></span></a>
  <ul id="bookmark-menu" class="dropdown-menu" role="menu">
    
    
    <li class="with_menu_btn">
      <a href="/auth/user/?_cols=username.email.first_name.last_name.is_staff.is_active" title="ttt"><i class="icon-bookmark"></i> ttt</a>
      
        <a href="/xadmin/bookmark/18/update/" class="dropdown-menu-btn"><i class="icon-pencil"></i></a>
      
    </li>
    
    <li class="with_menu_btn">
      <a href="/auth/user/?_cols=email.first_name.is_staff.last_name.is_active" title="qwrfwet"><i class="icon-bookmark"></i> qwrfwet</a>
      
        <a href="/xadmin/bookmark/22/update/" class="dropdown-menu-btn"><i class="icon-pencil"></i></a>
      
    </li>
    
    
    <li class="divider add-bookmark"></li>
    <li class="dropdown-submenu add-bookmark"><a href="#"><i class="icon-plus"></i> 新建书签</a>
      <div class="popover right dropdown-menu">
        <div class="arrow"></div>
        <h3 class="popover-title">
          将当前页面保存为书签
        </h3>
        <div class="popover-content">
          <form id="bookmark_form" method="post" action="/auth/user/bookmark/">
            <input type='hidden' name='csrfmiddlewaretoken' value='ZdNRNZa4eG2Tfa57jlSjZcpdoTit14g1' />
            <input type="hidden" name="query" value="_p_is_active__exact=1&amp;_p_is_superuser__exact=1"/>
            <input name="title" type="text" class="form-control" placeholder="输入书签标题…"/>
            <button class="btn btn-success" type="submit" data-loading-text="请稍侯...">保存书签</button>
          </form>
        </div>
      </div>
    </li>
    
  </ul>
</li>

<li class="dropdown">
  <a id="drop-filter" class="dropdown-toggle" role="button" data-toggle="dropdown" href="#">
  <i class="icon-filter"></i> 过滤器 <span class="badge badge-success">2</span>
  <span class="caret"></span></a>
  <ul id="filter-menu" class="dropdown-menu" role="menu" aria-labelledby="drop-filter">
    
    <li><a href="?"><i class="icon-trash"></i> 清除过滤器</a></li>
    <li class="divider"></li>
    
  	
<li class="dropdown-submenu">
  <a><i class="icon-filter text-muted"></i> 职员状态</a>
  <ul class="dropdown-menu">
    
        <li class="active">
        <a href="?_p_is_staff__exact=&amp;_p_is_superuser__exact=1&amp;_p_is_active__exact=1">全部</a></li>
    
        <li>
        <a href="?_p_is_staff__exact=1&amp;_p_is_superuser__exact=1&amp;_p_is_active__exact=1">是</a></li>
    
        <li>
        <a href="?_p_is_staff__exact=0&amp;_p_is_superuser__exact=1&amp;_p_is_active__exact=1">否</a></li>
    
  </ul>
</li>
<li class="dropdown-submenu">
  <a><i class="icon-filter text-success"></i> 超级用户状态</a>
  <ul class="dropdown-menu">
    
        <li>
        <a href="?_p_is_superuser__exact=&amp;_p_is_active__exact=1">全部</a></li>
    
        <li class="active">
        <a href="?_p_is_superuser__exact=1&amp;_p_is_active__exact=1">是</a></li>
    
        <li>
        <a href="?_p_is_superuser__exact=0&amp;_p_is_active__exact=1">否</a></li>
    
  </ul>
</li>
<li class="dropdown-submenu">
  <a><i class="icon-filter text-success"></i> 有效</a>
  <ul class="dropdown-menu">
    
        <li>
        <a href="?_p_is_superuser__exact=1&amp;_p_is_active__exact=">全部</a></li>
    
        <li class="active">
        <a href="?_p_is_superuser__exact=1&amp;_p_is_active__exact=1">是</a></li>
    
        <li>
        <a href="?_p_is_superuser__exact=1&amp;_p_is_active__exact=0">否</a></li>
    
  </ul>
</li>
  </ul>
</li>
          </ul>
          

<form class="navbar-form navbar-left" action="" method="get">
    <div class="input-group search-group">
    
    <input id="searchbar" class="form-control" type="text" name="_q_" value="" placeholder="Search 用户">
    <span class="input-group-btn"><button class="btn btn-primary" type="submit"><i class="icon-search icon-white"></i></button></span>
    </div>
    <input type="hidden" name="_p_is_superuser__exact" value="1"/><input type="hidden" name="_p_is_active__exact" value="1"/>
</form>

          
          <div class="navbar-btn navbar-right hide-xs">
            
            
  
    <a href="/auth/user/add/" class="btn btn-primary"><i class="icon-plus icon-white"></i> 
    增加 用户</a>
  

          </div>
        </div>
      </div>
      

      
      
      

      
  <div class="content-toolbar btn-toolbar pull-right clearfix">
    
<div class="btn-group export">
  <a class="dropdown-toggle btn btn-default btn-sm" data-toggle="dropdown" href="#">
    <i class="icon-share"></i> 导出 <span class="caret"></span>
  </a>
  <ul class="dropdown-menu" role="menu" aria-labelledby="dLabel">
    
      <li><a data-toggle="modal" data-target="#export-modal-csv"><i class="icon-circle-arrow-down"></i> 导出 CSV</a></li>
    
      <li><a data-toggle="modal" data-target="#export-modal-xml"><i class="icon-circle-arrow-down"></i> 导出 XML</a></li>
    
      <li><a data-toggle="modal" data-target="#export-modal-json"><i class="icon-circle-arrow-down"></i> 导出 JSON</a></li>
    
  </ul>

  
    <div id="export-modal-csv" class="modal fade">
      <div class="modal-dialog">
        <div class="modal-content">
          <form method="get" action="">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
            <h4 class="modal-title">导出 CSV</h4>
          </div>
          <div class="modal-body">
            <input type="hidden" name="_p_is_superuser__exact" value="1"/><input type="hidden" name="_do_" value="export"/><input type="hidden" name="_p_is_active__exact" value="1"/>
            <input type="hidden" name="export_type" value="csv">
              <label class="checkbox">
                
                
                <input type="checkbox" name="export_csv_header" checked="checked" value="on"> 导出表头
                
                
                
              </label>
              <label class="checkbox">
                <input type="checkbox" name="all" value="on"> 导出全部数据
              </label>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
            <button class="btn btn-success" type="submit"><i class="icon-share"></i> 导出</button>
          </div>
          </form>
        </div><!-- /.modal-content -->
      </div><!-- /.modal-dalog -->
    </div><!-- /.modal -->
  
    <div id="export-modal-xml" class="modal fade">
      <div class="modal-dialog">
        <div class="modal-content">
          <form method="get" action="">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
            <h4 class="modal-title">导出 XML</h4>
          </div>
          <div class="modal-body">
            <input type="hidden" name="_p_is_superuser__exact" value="1"/><input type="hidden" name="_do_" value="export"/><input type="hidden" name="_p_is_active__exact" value="1"/>
            <input type="hidden" name="export_type" value="xml">
              <label class="checkbox">
                
                
                
                <input type="checkbox" name="export_xml_format" checked="checked" value="on"> 导出格式
                
                
              </label>
              <label class="checkbox">
                <input type="checkbox" name="all" value="on"> 导出全部数据
              </label>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
            <button class="btn btn-success" type="submit"><i class="icon-share"></i> 导出</button>
          </div>
          </form>
        </div><!-- /.modal-content -->
      </div><!-- /.modal-dalog -->
    </div><!-- /.modal -->
  
    <div id="export-modal-json" class="modal fade">
      <div class="modal-dialog">
        <div class="modal-content">
          <form method="get" action="">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
            <h4 class="modal-title">导出 JSON</h4>
          </div>
          <div class="modal-body">
            <input type="hidden" name="_p_is_superuser__exact" value="1"/><input type="hidden" name="_do_" value="export"/><input type="hidden" name="_p_is_active__exact" value="1"/>
            <input type="hidden" name="export_type" value="json">
              <label class="checkbox">
                
                
                
                
                <input type="checkbox" name="export_json_format" checked="checked" value="on"> 导出格式
                
              </label>
              <label class="checkbox">
                <input type="checkbox" name="all" value="on"> 导出全部数据
              </label>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
            <button class="btn btn-success" type="submit"><i class="icon-share"></i> 导出</button>
          </div>
          </form>
        </div><!-- /.modal-content -->
      </div><!-- /.modal-dalog -->
    </div><!-- /.modal -->
  

</div>
    
    <div class="btn-group">
      <a class="dropdown-toggle btn btn-default btn-sm" data-toggle="dropdown" href="#">
        <i class="icon-list-alt"></i> 列 <span class="caret"></span>
      </a>
      <ul class="dropdown-menu model_fields pull-right" role="menu" aria-labelledby="dLabel">
        <li><a href="?_p_is_superuser__exact=1&amp;_p_is_active__exact=1"><i class="icon-refresh"></i> 恢复显示列</a></li>
        <li class="divider"></li>
        
        <li><a href="?_p_is_superuser__exact=1&amp;_cols=id.username.email.first_name.last_name.is_staff&amp;_p_is_active__exact=1">
          <i class="icon-blank"></i>
          ID</a></li>
        
        <li><a href="?_p_is_superuser__exact=1&amp;_cols=username.email.first_name.last_name.is_staff.password&amp;_p_is_active__exact=1">
          <i class="icon-blank"></i>
          密码</a></li>
        
        <li><a href="?_p_is_superuser__exact=1&amp;_cols=username.email.first_name.last_name.is_staff.last_login&amp;_p_is_active__exact=1">
          <i class="icon-blank"></i>
          上次登录</a></li>
        
        <li><a href="?_p_is_superuser__exact=1&amp;_cols=username.email.first_name.last_name.is_staff.is_superuser&amp;_p_is_active__exact=1">
          <i class="icon-blank"></i>
          超级用户状态</a></li>
        
        <li><a href="?_p_is_superuser__exact=1&amp;_cols=email.first_name.last_name.is_staff&amp;_p_is_active__exact=1">
          <i class="icon-check"></i>
          用户名</a></li>
        
        <li><a href="?_p_is_superuser__exact=1&amp;_cols=username.email.last_name.is_staff&amp;_p_is_active__exact=1">
          <i class="icon-check"></i>
          名字</a></li>
        
        <li><a href="?_p_is_superuser__exact=1&amp;_cols=username.email.first_name.is_staff&amp;_p_is_active__exact=1">
          <i class="icon-check"></i>
          姓氏</a></li>
        
        <li><a href="?_p_is_superuser__exact=1&amp;_cols=username.first_name.last_name.is_staff&amp;_p_is_active__exact=1">
          <i class="icon-check"></i>
          电子邮件地址</a></li>
        
        <li><a href="?_p_is_superuser__exact=1&amp;_cols=username.email.first_name.last_name&amp;_p_is_active__exact=1">
          <i class="icon-check"></i>
          职员状态</a></li>
        
        <li><a href="?_p_is_superuser__exact=1&amp;_cols=username.email.first_name.last_name.is_staff.is_active&amp;_p_is_active__exact=1">
          <i class="icon-blank"></i>
          有效</a></li>
        
        <li><a href="?_p_is_superuser__exact=1&amp;_cols=username.email.first_name.last_name.is_staff.date_joined&amp;_p_is_active__exact=1">
          <i class="icon-blank"></i>
          加入日期</a></li>
        
      </ul>
    </div>
    
    
    <div class="btn-group layout-btns" data-toggle="buttons">
      <label class="btn btn-default btn-sm layout-normal active">
        <input type="radio"> <i class="icon-th-large"></i>
      </label>
      <label class="btn btn-default btn-sm layout-condensed">
        <input type="radio"> <i class="icon-th"></i>
      </label>
      
    </div>
    
    
    <div class="btn-group layout-btns" data-toggle="buttons-checkbox">
      <button type="button" class="btn btn-default btn-sm layout-full"><i class="icon-fullscreen"></i></button>
      
    </div>
    
    
  </div>

  <ul class="pagination pagination-sm pagination-left pagination-inline">
    
  <li><span><span class="text-success">1</span> 用户</span></li>
  
  

  </ul>
  
  <form id="changelist-form" action="" method="post"><input type='hidden' name='csrfmiddlewaretoken' value='ZdNRNZa4eG2Tfa57jlSjZcpdoTit14g1' />
  
  <div class="results table-responsive">
  
  
  <table class="table table-bordered table-striped table-hover">
    
    <thead>
      <tr>
        <th scope="col" class="sortable sorted ascending">
          
            <div class="pull-right">
              
                <a class="toggle" href="?_p_is_superuser__exact=1&o=-username&_p_is_active__exact=1"><i class="icon-sort-up"></i></a>
              
            </div>
          
          
            <div class="dropdown pull-left">
              <a class="dropdown-toggle" data-toggle="dropdown" href="#">
                用户名
              </a>
              <ul class="dropdown-menu" role="menu">
                
                  <li class="active"><a href="?_p_is_superuser__exact=1&o=username&_p_is_active__exact=1" class="active"><i class="icon-caret-up"></i> 正序</a></li>
                
                  <li><a href="?_p_is_superuser__exact=1&o=-username&_p_is_active__exact=1" class="active"><i class="icon-caret-down"></i> 倒序</a></li>
                
                  <li><a href="?_p_is_superuser__exact=1&o=&_p_is_active__exact=1" class="active"><i class="icon-remove"></i> 取消排序</a></li>
                
              </ul>
            </div>
          
        </th>
        <th scope="col" class="sortable">
          
          
            <div class="dropdown pull-left">
              <a class="dropdown-toggle" data-toggle="dropdown" href="#">
                电子邮件地址
              </a>
              <ul class="dropdown-menu" role="menu">
                
                  <li><a href="?_p_is_superuser__exact=1&o=email.username&_p_is_active__exact=1" class="active"><i class="icon-caret-up"></i> 正序</a></li>
                
                  <li><a href="?_p_is_superuser__exact=1&o=-email.username&_p_is_active__exact=1" class="active"><i class="icon-caret-down"></i> 倒序</a></li>
                
              </ul>
            </div>
          
        </th>
        <th scope="col" class="sortable">
          
          
            <div class="dropdown pull-left">
              <a class="dropdown-toggle" data-toggle="dropdown" href="#">
                名字
              </a>
              <ul class="dropdown-menu" role="menu">
                
                  <li><a href="?_p_is_superuser__exact=1&o=first_name.username&_p_is_active__exact=1" class="active"><i class="icon-caret-up"></i> 正序</a></li>
                
                  <li><a href="?_p_is_superuser__exact=1&o=-first_name.username&_p_is_active__exact=1" class="active"><i class="icon-caret-down"></i> 倒序</a></li>
                
              </ul>
            </div>
          
        </th>
        <th scope="col" class="sortable">
          
          
            <div class="dropdown pull-left">
              <a class="dropdown-toggle" data-toggle="dropdown" href="#">
                姓氏
              </a>
              <ul class="dropdown-menu" role="menu">
                
                  <li><a href="?_p_is_superuser__exact=1&o=last_name.username&_p_is_active__exact=1" class="active"><i class="icon-caret-up"></i> 正序</a></li>
                
                  <li><a href="?_p_is_superuser__exact=1&o=-last_name.username&_p_is_active__exact=1" class="active"><i class="icon-caret-down"></i> 倒序</a></li>
                
              </ul>
            </div>
          
        </th>
        <th scope="col" class="sortable">
          
          
            <div class="dropdown pull-left">
              <a class="dropdown-toggle" data-toggle="dropdown" href="#">
                职员状态
              </a>
              <ul class="dropdown-menu" role="menu">
                
                  <li><a href="?_p_is_superuser__exact=1&o=is_staff.username&_p_is_active__exact=1" class="active"><i class="icon-caret-up"></i> 正序</a></li>
                
                  <li><a href="?_p_is_superuser__exact=1&o=-is_staff.username&_p_is_active__exact=1" class="active"><i class="icon-caret-down"></i> 倒序</a></li>
                
              </ul>
            </div>
          
        </th>
        <th scope="col">
          
          
            &nbsp;
          
        </th>
      </tr>
      
    </thead>
    
    
    <tbody>
    
      <tr class="grid-item">
        <td >
          
          
            <a href="/auth/user/1/update/">admin</a>
          
        </td>
      
        <td >
          
          
            &nbsp;
          
        </td>
      
        <td >
          
          
            &nbsp;
          
        </td>
      
        <td >
          
          
            &nbsp;
          
        </td>
      
        <td >
          
          
            <i class="icon-ok-sign text-success" alt="True"></i>
          
        </td>
      
        <td >
          
          
            <div class="dropdown related_menu pull-right"><a title="关联数据" class="relate_menu dropdown-toggle" data-toggle="dropdown"><i class="icon icon-list"></i></a><ul class="dropdown-menu" role="menu"><li class="with_menu_btn"><a href="/reversion/revision/?_rel_user__id__exact=1" title="revision"><i class="icon icon-th-list"></i> revision</a><a class="add_link dropdown-menu-btn" href="/reversion/revision/add/?_rel_user__id__exact=1"><i class="icon icon-plus pull-right"></i></a></li><li class="with_menu_btn"><a href="/comments/comment/?_rel_user__id__exact=1" title="评论"><i class="icon icon-th-list"></i> 评论</a><a class="add_link dropdown-menu-btn" href="/comments/comment/add/?_rel_user__id__exact=1"><i class="icon icon-plus pull-right"></i></a></li><li class="with_menu_btn"><a href="/xadmin/bookmark/?_rel_user__id__exact=1" title="书签"><i class="icon icon-th-list"></i> 书签</a><a class="add_link dropdown-menu-btn" href="/xadmin/bookmark/add/?_rel_user__id__exact=1"><i class="icon icon-plus pull-right"></i></a></li><li class="with_menu_btn"><a href="/xadmin/usersettings/?_rel_user__id__exact=1" title="用户设置"><i class="icon icon-th-list"></i> 用户设置</a><a class="add_link dropdown-menu-btn" href="/xadmin/usersettings/add/?_rel_user__id__exact=1"><i class="icon icon-plus pull-right"></i></a></li><li class="with_menu_btn"><a href="/xadmin/userwidget/?_rel_user__id__exact=1" title="用户小组件"><i class="icon icon-th-list"></i> 用户小组件</a><a class="add_link dropdown-menu-btn" href="/xadmin/userwidget/add/?_rel_user__id__exact=1"><i class="icon icon-plus pull-right"></i></a></li></ul></div>
          
        </td>
      </tr>
      
    
    </tbody>
    
  </table>
  
  
  </div>
  
  </form>

  <ul class="pagination">
    
  <li><span><span class="text-success">1</span> 用户</span></li>
  
  

  </ul>

    </div>