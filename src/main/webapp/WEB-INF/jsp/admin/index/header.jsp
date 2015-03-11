<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jspf"%>
<!-- Header -->
  <div id="top-nav" class="navbar navbar-xs navbar-inverse navbar-fixed-top">
    <div class="navbar-header">
      <a class="navbar-brand" href="#">ES-SHOP后台管理系统</a>
    </div>
    <div class="navbar-collapse collapse">
      <ul class="nav navbar-nav navbar-right">
		<li class="dropdown g-add">
		  <a class="dropdown-toggle" role="button" data-toggle="dropdown" href="#">
		  <i class="icon-plus icon-white"></i> <span class="hide-sm">增加 <i class="caret"></i></span></a>
		  <ul id="g-add-menu" class="dropdown-menu" role="menu">
		    
		        <li><a href="/xadmin/bookmark/add/"><i class="icon-plus"></i> 增加 书签</a></li>
		    
		        <li><a href="/xadmin/usersettings/add/"><i class="icon-plus"></i> 增加 用户设置</a></li>
		    
		        <li><a href="/comments/comment/add/"><i class="icon-plus"></i> 增加 评论</a></li>
		    
		        <li><a href="/app/continent/add/"><i class="icon-plus"></i> 增加 Continent</a></li>
		    
		        <li><a href="/host/idc/add/"><i class="icon-plus"></i> 增加 IDC</a></li>
		    
		        <li><a href="/host/host/add/"><i class="icon-plus"></i> 增加 Host</a></li>
		    
		        <li><a href="/reversion/version/add/"><i class="icon-plus"></i> 增加 Version</a></li>
		    
		        <li><a href="/auth/user/add/"><i class="icon-plus"></i> 增加 用户</a></li>
		    
		        <li><a href="/reversion/revision/add/"><i class="icon-plus"></i> 增加 Revision</a></li>
		    
		        <li><a href="/auth/permission/add/"><i class="icon-plus"></i> 增加 权限</a></li>
		    
		        <li><a href="/host/maintainlog/add/"><i class="icon-plus"></i> 增加 Maintain Log</a></li>
		    
		        <li><a href="/xadmin/userwidget/add/"><i class="icon-plus"></i> 增加 用户小组件</a></li>
		    
		        <li><a href="/app/country/add/"><i class="icon-plus"></i> 增加 Country</a></li>
		    
		        <li><a href="/host/accessrecord/add/"><i class="icon-plus"></i> 增加 Access Record</a></li>
		    
		        <li><a href="/app/kitchensink/add/"><i class="icon-plus"></i> 增加 Kitchen sink</a></li>
		    
		        <li><a href="/host/hostgroup/add/"><i class="icon-plus"></i> 增加 Host Group</a></li>
		    
		        <li><a href="/auth/group/add/"><i class="icon-plus"></i> 增加 组</a></li>
		    
		  </ul>
		</li>
		<li class="dropdown g-setlang">
		  <a class="dropdown-toggle" role="button" data-toggle="dropdown" href="#">
		  <i class="icon-globe icon-white"></i> <span class="hide-sm">
		    简体中文
		    <i class="caret"></i></span></a>
		  <form id="setlang-form" action="/i18n/setlang/" method="post" style="display:none;">
		    <input type='hidden' name='csrfmiddlewaretoken' value='depy0qAotmsVOdoNJbNKvzmuBljvyVks' />
		    <input name="next" type="hidden" value="/xadmin/userwidget/" />
		    <input id="setlang-id_language" name="language" type="hidden" value="zh-cn" />
		  </form>
		  <ul id="g-setlang-menu" class="dropdown-menu" role="menu">
		    
		    
		      <li><a data-lang="de"><i class="icon-flag"></i> Deutsch (de)</a></li>
		    
		      <li><a data-lang="en"><i class="icon-flag"></i> English (en)</a></li>
		    
		      <li><a data-lang="ja"><i class="icon-flag"></i> 日本語 (ja)</a></li>
		    
		      <li><a data-lang="lt"><i class="icon-flag"></i> Lietuvi?kai (lt)</a></li>
		    
		      <li><a data-lang="nl"><i class="icon-flag"></i> Nederlands (nl)</a></li>
		    
		      <li><a data-lang="pl"><i class="icon-flag"></i> polski (pl)</a></li>
		    
		      <li><a data-lang="pt"><i class="icon-flag"></i> Português (pt)</a></li>
		    
		      <li class="active"><a data-lang="zh-cn"><i class="icon-flag"></i> 简体中文 (zh-cn)</a></li>
		    
		  </ul>
		</li>
		<script type="text/javascript">
		  $(function(){
		    $('#g-setlang-menu a').click(function(e){
			  alert("vvvv");
		      var lang = $(this).data('lang');
		      $('#setlang-form #setlang-id_language').attr('value', lang);
		      $('#setlang-form').submit();
		      
		      var topmenu = $('#top-nav .navbar-collapse');
		      if(topmenu.data('bs.collapse')) topmenu.collapse('hide');
		    })
		  })
		</script>
<li class="dropdown g-theme">
<a class="dropdown-toggle" role="button" data-toggle="dropdown" href="#">
<i class="icon-magic icon-white"></i> <span class="hide-sm">主题 <i class="caret"></i></span></a>
<ul id="g-theme-menu" class="dropdown-menu" role="menu">
  
      <li class="active"><a data-css-href="/static/xadmin/css/themes/bootstrap-xadmin.css">默认</a></li>
  
      <li><a data-css-href="/static/xadmin/css/themes/bootstrap-theme.css">Bootstrap2</a></li>
  
      <li><a data-css-href="http://bootswatch.com/cerulean/bootstrap.min.css">Cerulean</a></li>
  
      <li><a data-css-href="http://bootswatch.com/cosmo/bootstrap.min.css">Cosmo</a></li>
  
      <li><a data-css-href="http://bootswatch.com/cyborg/bootstrap.min.css">Cyborg</a></li>
  
      <li><a data-css-href="http://bootswatch.com/darkly/bootstrap.min.css">Darkly</a></li>
  
      <li><a data-css-href="http://bootswatch.com/flatly/bootstrap.min.css">Flatly</a></li>
  
      <li><a data-css-href="http://bootswatch.com/journal/bootstrap.min.css">Journal</a></li>
  
      <li><a data-css-href="http://bootswatch.com/lumen/bootstrap.min.css">Lumen</a></li>
  
      <li><a data-css-href="http://bootswatch.com/paper/bootstrap.min.css">Paper</a></li>
  
      <li><a data-css-href="http://bootswatch.com/readable/bootstrap.min.css">Readable</a></li>
  
      <li><a data-css-href="http://bootswatch.com/sandstone/bootstrap.min.css">Sandstone</a></li>
  
      <li><a data-css-href="http://bootswatch.com/simplex/bootstrap.min.css">Simplex</a></li>
  
      <li><a data-css-href="http://bootswatch.com/slate/bootstrap.min.css">Slate</a></li>
  
      <li><a data-css-href="http://bootswatch.com/spacelab/bootstrap.min.css">Spacelab</a></li>
  
      <li><a data-css-href="http://bootswatch.com/superhero/bootstrap.min.css">Superhero</a></li>
  
      <li><a data-css-href="http://bootswatch.com/united/bootstrap.min.css">United</a></li>
  
      <li><a data-css-href="http://bootswatch.com/yeti/bootstrap.min.css">Yeti</a></li>
  
</ul>
</li>
        
          <li class="dropdown">
            <a class="dropdown-toggle" role="button" data-toggle="dropdown" href="#">
            <strong>欢迎， <sys:showLoginUsername/></strong> <span class="caret"></span></a>
            <ul id="g-account-menu" class="dropdown-menu" role="menu">
              <li><a href="/account/password/"><i class="icon-key"></i> Change Password</a></li>
            </ul>
          </li>
        
        <li><a href="${ctx}/logout"><i class="show-sm icon-signout"></i><span class="hide-sm">注销</span></a></li>
      </ul>
      <ul class="nav navbar-nav navbar-left">
			<li class="dropdown g-add">
		  		<a class="dropdown-toggle" role="button" data-toggle="dropdown" href="#">
		  			<span class="hide-sm">cms 管理 <i class="caret"></i></span></a>
		  			<ul id="g-add-menu" class="dropdown-menu" role="menu">
		    
		        		<li><a href="/xadmin/bookmark/add/"><i class="icon-plus"></i> 增加 书签</a></li>
		    
		        		<li><a href="/xadmin/usersettings/add/"><i class="icon-plus"></i> 增加 用户设置</a></li>
		  			</ul>
			</li>
		</ul>
		<ul class="nav navbar-nav navbar-left">
			<li class="dropdown g-add">
		  		<a class="dropdown-toggle" role="button" data-toggle="dropdown" href="#">
		  			<span class="hide-sm">个人中心 <i class="caret"></i></span></a>
		  			<ul id="g-add-menu" class="dropdown-menu" role="menu">
		    
		        		<li><a href="/xadmin/bookmark/add/"><i class="icon-plus"></i> 增加 书签</a></li>
		    
		        		<li><a href="/xadmin/usersettings/add/"><i class="icon-plus"></i> 增加 用户设置</a></li>
		  			</ul>
			</li>
		</ul>
		<ul class="nav navbar-nav navbar-left">
			<li class="dropdown g-add">
		  		<a class="dropdown-toggle" role="button" data-toggle="dropdown" href="#">
		  			<span class="hide-sm">系统设置 <i class="caret"></i></span></a>
		  			<ul id="g-add-menu" class="dropdown-menu" role="menu">
		    
		        		<li><a href="/xadmin/bookmark/add/"><i class="icon-plus"></i> 增加 书签</a></li>
		    
		        		<li><a href="/xadmin/usersettings/add/"><i class="icon-plus"></i> 增加 用户设置</a></li>
		  			</ul>
			</li>
		</ul>

<form method="get" class="top-search-form navbar-form navbar-left" id="g-search">
  <div class="input-group">
    <input name="_q_" class="form-control" type="text">
    <span class="input-group-btn">
      <button class="btn btn-default dropdown-toggle" data-toggle="dropdown">
        <i class="icon-search"></i> 搜索 <span class="caret"></span>
      </button>
      <ul class="dropdown-menu">
      
          <li><a data-action="/comments/comment/"><i class="icon-search"></i> 搜索评论</a></li>
      
          <li><a data-action="/app/continent/"><i class="icon-search"></i> 搜索Continents</a></li>
      
          <li><a data-action="/host/idc/"><i class="icon-search"></i> 搜索IDC</a></li>
      
          <li><a data-action="/host/host/"><i class="icon-search"></i> 搜索Host</a></li>
      
          <li><a data-action="/auth/user/"><i class="icon-search"></i> 搜索用户</a></li>
      
          <li><a data-action="/host/maintainlog/"><i class="icon-search"></i> 搜索Maintain Log</a></li>
      
          <li><a data-action="/app/country/"><i class="icon-search"></i> 搜索Countries</a></li>
      
          <li><a data-action="/app/kitchensink/"><i class="icon-search"></i> 搜索Kitchen sinks</a></li>
      
          <li><a data-action="/host/hostgroup/"><i class="icon-search"></i> 搜索Host Group</a></li>
      
          <li><a data-action="/auth/group/"><i class="icon-search"></i> 搜索组</a></li>
      
      </ul>
    </span>
  </div>
</form>


    </div>
  </div>
