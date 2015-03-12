<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jspf"%>
<c:if test="${empty header['container']}">
<es:contentHeader/>
</c:if>
   <%@include file="/WEB-INF/jsp/admin/index/headermenu.jsp"%>
   <!-- 右侧开始 -->	
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
            <input type="hidden" name="_do_" value="export"/>
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
            <input type="hidden" name="_do_" value="export"/>
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
            <input type="hidden" name="_do_" value="export"/>
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
        <li><a href="?"><i class="icon-refresh"></i> 恢复显示列</a></li>
        <li class="divider"></li>
        
        <li><a href="?_cols=id.widget_type.page_id.user">
          <i class="icon-blank"></i>
          ID</a></li>
        
        <li><a href="?_cols=widget_type.page_id">
          <i class="icon-check"></i>
          用户</a></li>
        
        <li><a href="?_cols=widget_type.user">
          <i class="icon-check"></i>
          页面</a></li>
        
        <li><a href="?_cols=page_id.user">
          <i class="icon-check"></i>
          Widget类型</a></li>
        
        <li><a href="?_cols=widget_type.page_id.user.value">
          <i class="icon-blank"></i>
          Widget参数</a></li>
        
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

<c:if test="${empty header['container']}">
	<%@include file="/WEB-INF/jsp/admin/index/footer.jsp"%>
	<es:contentFooter/>
	<%@include file="/WEB-INF/jsp/common/admin/import-sys-js.jspf"%>
</c:if>
