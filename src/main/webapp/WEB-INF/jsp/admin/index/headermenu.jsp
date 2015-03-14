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
		  </ul>
		</li>
		<li class="dropdown g-setlang">
		  <a class="dropdown-toggle" role="button" data-toggle="dropdown" href="#">
		  <i class="icon-globe icon-white"></i> <span class="hide-sm">
		   	 简体中文
		    <i class="caret"></i></span></a>
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
		<li class="dropdown g-theme">
			<a class="dropdown-toggle" role="button" data-toggle="dropdown" href="#"><i class=" icon-wrench icon-white"></i> <span class="hide-sm">主题 <i class="caret"></i></span></a>
			<ul id="g-theme-menu" class="dropdown-menu" role="menu">
			  
			      <li class="active"><a data-css-href="/static/xadmin/css/themes/bootstrap-xadmin.css">默认</a></li>
			  
			      <li><a data-css-href="/static/xadmin/css/themes/bootstrap-theme.css">Bootstrap2</a></li>
			  
			      <li><a data-css-href="http://bootswatch.com/cerulean/bootstrap.min.css">Cerulean</a></li>
			  
			      <li><a data-css-href="http://bootswatch.com/cosmo/bootstrap.min.css">Cosmo</a></li>
			  
			</ul>
		</li>
        
          <li class="dropdown">
            <a class="dropdown-toggle" role="button" data-toggle="dropdown" href="#">
            <strong>欢迎， <sys:showLoginUsername/></strong> <span class="caret"></span></a>
            <ul id="g-account-menu" class="dropdown-menu" role="menu">
              <li><a href="/account/password/"><i class="icon-edit"></i> 修改密码</a></li>
            </ul>
          </li>
        
        <li><a href="${ctx}/logout"><i class="show-sm icon-signout"></i><span class="hide-sm">注销</span></a></li>
      </ul>
      <!-- 顶部的nav菜单 -->
      <ul class="nav navbar-nav navbar-left">
      	  <c:forEach items="${menus}" var="m">
      	  		<!-- 第一行nav -->
				<li><a role="button" data-toggle="dropdown" href="javascript:;"><i class="${m.icon} icon-white"></i> <span class="hide-sm">${m.name}</span></a>
		  			<!-- 多层导航开始  -->
		  			<ul class="dropdown-menu" role="menu" aria-labelledby="dropdownMenu">
			        	<c:forEach items="${m.children}" var="c">
			        		<li class="dropdown-submenu"><a href="${c.url}" target="iframe"><i class="${c.icon}"></i> ${c.name}</a>
				        		<ul class="dropdown-menu">
			        				<c:forEach items="${c.children}" var="t">
				        				<es:subnav menu="${t}" />
			        				</c:forEach>
			        			</ul>
			        		</li>
			            </c:forEach>
			        </ul>
				</li>	
			</c:forEach>
		</ul>
    </div>
  </div>
  

