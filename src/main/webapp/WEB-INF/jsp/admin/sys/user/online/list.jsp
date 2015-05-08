<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jspf"%>
<c:if test="${empty header['container']}">
<es:contentHeader/>
</c:if>
<%@include file="/WEB-INF/jsp/admin/index/header.jsp"%>
<%@include file="/WEB-INF/jsp/admin/index/menu.jsp"%>
	<div id="content-block" class="col-sm-11 col-md-10">
      	<ul class="breadcrumb">
	      	<li>
	        	<a href="/">首页</a>
	      	</li>
	      	<li>
	       	 	<a href="#">用户管理</a>
	      	</li>
	      	<li>
	       	 	用户管理在线列表
	      	</li>
      	 </ul>
      <%@include file="searchForm.jsp" %>
      <%@include file="toolbar.jsp" %>
  	  <%@include file="listTable.jsp"%>
  </div>
<%@include file="/WEB-INF/jsp/admin/index/footer.jsp"%>
<es:contentFooter/>
