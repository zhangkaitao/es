<%@tag pageEncoding="UTF-8" description="操作" %>
<%@ attribute name="bean" type="com.sishuok.es.sys.bean.entity.Bean" required="true" description="实体对象" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="es" tagdir="/WEB-INF/tags" %>

<div class="content-toolbar btn-toolbar pull-right clearfix">
	<!-- 列控制 -->
   	<div class="btn-group">
	   	<a class="dropdown-toggle btn btn-default btn-sm" data-toggle="dropdown" href="#">
    	   	<i class="icon-list-alt"></i> 显示列 <span class="caret"></span>
      	</a>
      	<ul class="dropdown-menu model_fields pull-right" role="menu" aria-labelledby="dLabel">
        	<li><a href=""><i class="icon-refresh"></i> 恢复显示列</a></li>
        	<li class="divider"></li>
        	<c:if test="${not empty bean}">
			    <c:forEach items="${bean.beanItems}" var="bi">
			    	<li>
			        	<a href="">
			          		<i class="icon-blank"></i>
			          		${bi.displayName}</a>
			        </li>
			    </c:forEach>
			</c:if>
      	 </ul>
    </div>
	<!-- 行列密度 -->
    <div class="btn-group layout-btns" data-toggle="buttons">
      <label class="btn btn-default btn-sm layout-normal active">
        <input type="radio"> <i class="icon-th-large"></i>
      </label>
      <label class="btn btn-default btn-sm layout-condensed">
        <input type="radio"> <i class="icon-th"></i>
      </label>
    </div>
    <!-- 全屏 -->
    <div class="btn-group layout-btns" data-toggle="buttons-checkbox">
      <button type="button" class="btn btn-default btn-sm layout-full"><i class="icon-fullscreen"></i></button>
    </div>
</div>