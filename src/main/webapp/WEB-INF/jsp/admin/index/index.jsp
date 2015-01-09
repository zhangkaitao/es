<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jspf"%>
<es:contentHeader title="Easy-Scaffold脚手架" index="true"/>

<%@include file="header.jsp"%>
<div class="index-panel">

	<div class="main-container" id="main-container">
			<script type="text/javascript">
				try{ace.settings.check('main-container' , 'fixed')}catch(e){}
			</script>

			<div class="main-container-inner">
        		<%@include file="menu.jsp"%>
			   	<iframe id="iframe" name="iframe" width="1000" overflow="auto"  frameborder="0" src="${ctx}/admin/welcome"></iframe>
				<%@include file="setting.jsp"%>
			</div><!-- /.main-container-inner -->
			<a href="#" id="btn-scroll-up" class="btn-scroll-up btn btn-sm btn-inverse">
				<i class="icon-double-angle-up icon-only bigger-110"></i>
			</a>
		</div><!-- /.main-container -->
<es:contentFooter/>
<script type="text/javascript">
    $(function() {
        $.app.initIndex();
    });
</script>