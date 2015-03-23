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
	       	<a href="${ctx}/admin/sys/site">站点管理</a>
	     </li>
	     <li>
        	<c:if test="${op eq '新增'}">
                <c:set var="icon" value="icon-file-alt"/>
            </c:if>
            <c:if test="${op eq '修改'}">
                <c:set var="icon" value="icon-edit"/>
            </c:if>
            <c:if test="${op eq '删除'}">
                <c:set var="icon" value="icon-trash"/>
            </c:if>
            ${op} 站点
	     </li>
	</ul>
    <div class="navbar content-navbar navbar-default navbar-xs" data-toggle="breakpoint"
        				data-class-xs="navbar content-navbar navbar-inverse navbar-xs"
        				data-class-sm="navbar content-navbar navbar-default navbar-xs">
		<div class="navbar-header">
          	<button type="button" class="navbar-toggle pull-left" onclick="javascript: history.back();"><i class="icon-arrow-left"></i></button>
          	<a class="navbar-brand" data-toggle="collapse" data-target="#top-nav .navbar-collapse">
				<c:if test="${op eq '新增'}">
	                <c:set var="icon" value="icon-file-alt"/>
	            </c:if>
	            <c:if test="${op eq '修改'}">
	                <c:set var="icon" value="icon-edit"/>
	            </c:if>
	            <c:if test="${op eq '删除'}">
	                <c:set var="icon" value="icon-trash"/>
	            </c:if>
	            ${op} 站点
          	</a>
        </div>
        <div class="navbar-collapse collapse">
          <ul class="nav navbar-nav">
            
          </ul>
          
          
          <div class="navbar-btn navbar-right hide-xs">
            
            
          </div>
        </div>
      </div>
      
  
  

			<input type='hidden' name='csrfmiddlewaretoken' value='OuptumW56Q6QgVIlFe8QwcAsw7m4LYqh' /> 
				<div class="form-container row clearfix" >
					<div id="column-0" class="formColumn column form-column full col col-sm-12 form-horizontal"  horizontal="True" span="12">
	    				<div class="panel panel-default fieldset unsort no_title" id="box-0" >
	    					<div class="panel-heading">
	    						<i class='icon icon-chevron-up chevron'></i>
	    							<h3 class="panel-title"></h3>
	    					</div>
	    					<div class="panel-body " >
	    						<div id="div_id_name" class="form-group">
	    							<label for="id_name" class="control-label  requiredField">
	                					Name<span class="asteriskField">*</span>
	                				</label>
	                			<div class="controls ">
	                				<input class="text-field admintextinputwidget form-control" id="id_name" maxlength="32" name="name" type="text" /> 
	                			</div>
	                		</div>
	                		<div id="div_id_description" class="form-group">
	                			<label for="id_description" class="control-label  requiredField">
	                				Description<span class="asteriskField">*</span>
	                			</label>
		                		<div class="controls ">
		                			<textarea class="textarea-field admintextareawidget form-control" cols="40" id="id_description" name="description" rows="10"></textarea>
		                		</div>
	                		</div>
	                		<div id="div_id_hosts" class="form-group">
	                			<label for="id_hosts_0" class="control-label ">
	                			Hosts
	            				</label>
	            				<div class="controls ">
	            					<a href="/host/host/add/" title="创建新的 Host" class="btn btn-primary btn-sm btn-ajax pull-right" data-for-id="id_hosts" data-refresh-url="/host/hostgroup/add/?_field=hosts&hosts=">
	            						<i class="icon-plus"></i>
	            					</a>
	            					<div class="control-wrap" id="id_hosts_wrap_container">
	            						<label for="id_hosts_0" class="checkbox-inline">
	            							<input id="id_hosts_0" inline="True" name="hosts" type="checkbox" value="1" /> 
	            								Some Company
	            						</label>
	            							<label for="id_hosts_1" class="checkbox-inline"><input id="id_hosts_1" inline="True" name="hosts" type="checkbox" value="2" /> bbb</label><label for="id_hosts_2" class="checkbox-inline"><input id="id_hosts_2" inline="True" name="hosts" type="checkbox" value="3" /> asdfdsff</label></div></div></div></div></div></div></div>


  

  

				<div class="form-actions well well-sm clearfix">
				
				
				
				  <div class="btn-group clearfix show-xs save-group col-xs-12">
				  <button type="submit" class="default btn btn-primary col-xs-10" name="_save" data-loading-text="保存中.." /><i class="icon-save"></i> 保存</button>
				  <button type="button" class="more btn btn-primary col-xs-2" data-toggle="collapse" data-target=".nav-collapse.more-btns"><i class="icon-ellipsis-vertical"></i></button>
				  </div>
				  <button type="submit" class="default btn btn-primary hide-xs" name="_save" data-loading-text="保存中.." /><i class="icon-save"></i> 保存</button>
				
				
				<div class="nav-collapse collapse more-btns">
				
				<input type="submit" class="btn btn-default" value="保存并增加另一个" name="_addanother"  />
				<input type="submit" class="btn btn-default" value="保存并继续编辑" name="_continue" />
				
				</div>
				
				
				
				
				</div>

    </div>
    
