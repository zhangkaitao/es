<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jspf"%>
<es:contentHeader title="Easy-Scaffold脚手架" index="true"/>
<%@include file="header.jsp"%>
<div class="main-container" id="main-container">
			<div class="main-container-inner">
				<a class="menu-toggler" id="menu-toggler" href="#">
					<span class="menu-text"></span>
				</a>
				<div class="sidebar" id="sidebar">
        			<%@include file="menu.jsp"%>
        		</div>	
        		<div class="main-content" style="height:100%;">
			   		<iframe id="iframe" name="iframe" width="100%" height="100%"  frameborder="0" src="${ctx}/admin/welcome" onLoad="iFrameHeight()"></iframe>
			   	</div><!-- /.main-content -->	
				<%@include file="setting.jsp"%>
			</div><!-- /.main-container-inner -->
			<a href="#" id="btn-scroll-up" class="btn-scroll-up btn btn-sm btn-inverse">
				<i class="icon-double-angle-up icon-only bigger-110"></i>
			</a>
		</div><!-- /.main-container -->
<es:contentFooter/>
<script type="text/javascript">
	try{ace.settings.check('navbar' , 'fixed')}catch(e){};
	try{ace.settings.check('sidebar' , 'collapsed')}catch(e){};
	try{ace.settings.check('main-container' , 'fixed')}catch(e){};
	try{ace.settings.check('breadcrumbs' , 'fixed')}catch(e){};
    $(function() {
        $.app.initIndex();
    });
    function iFrameHeight() { 
    	var ifm= document.getElementById("iframe"); 
    	var subWeb = document.frames ? document.frames
    	["iframe"].document : ifm.contentDocument; 
    	if(ifm != null && subWeb != null) { 
    		ifm.height = subWeb.body.scrollHeight; 
    		ifm.width = subWeb.body.scrollWidth; 
    	} 
    }  
</script>