<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jspf"%>
<es:contentHeader/>
<%@include file="/WEB-INF/jsp/admin/index/header.jsp"%>
<%@include file="/WEB-INF/jsp/admin/index/menu.jsp"%>
	<div id="content-block" class="col-sm-11 col-md-10">
		<ul class="breadcrumb">
	      	<li>
	        	<a href="/">首页</a>
	      	</li>
	      	<li>
	       	 	实体列表
	      	</li>
      	</ul>
    	<es:showMessage/>
		<%@include file="functionForm.jsp" %>
  		<div class="results table-responsive">
  			<%@include file="listTable.jsp"%>
  		</div>
  	</div>
<%@include file="/WEB-INF/jsp/admin/index/footer.jsp"%>
<es:contentFooter/>
