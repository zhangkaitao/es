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

      
      
  <div class="content-toolbar btn-toolbar pull-right clearfix">
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

  
  <form id="changelist-form" action="" method="post"><input type='hidden' name='csrfmiddlewaretoken' value='depy0qAotmsVOdoNJbNKvzmuBljvyVks' />
  
  <div class="results table-responsive">
  
  
  	<%@include file="listTable.jsp"%>
  
  
  </div>
  
	<div class="form-actions well well-sm">
	  <input type="hidden" id="action" name="action" value=""/>
	  <input type="hidden" id="select-across" name="select_across" value=""/>
	  <div class="btn-group clearfix dropup">
	    <a class="dropdown-toggle btn btn-success" data-toggle="dropdown" href="#">
	    <i class="icon-wrench icon-white"></i> 
	    <span class="action-counter">50 个中 0 个被选</span>
	    <span class="all" style="display: none;">选中了 93 个</span>
	    <span class="caret"></span></a>
	    <ul class="dropdown-menu" role="menu" aria-labelledby="dLabel">
	      
	        <li><a onclick="$.do_action('delete_selected');"><i class="icon-remove"></i> Delete selected 用户小组件</a></li>
	      
	    </ul>
	  </div>
	  
	      
	      <a class="question btn btn-default" href="javascript:;" style="display: none;" title="点击此处选择所有页面中包含的对象。">选中所有的 93 个 用户小组件</a>
	      <a class="clear btn btn-default" href="javascript:;" style="display: none;">清除选中</a>
	      
	      <script type="text/javascript">var _actions_icnt="50";</script>
	  
	</div>

  </form>
  </div>
<%@include file="/WEB-INF/jsp/admin/index/footer.jsp"%>
<es:contentFooter/>
