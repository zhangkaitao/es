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
	<form class="exform" enctype="multipart/form-data" action="" method="post" id="user_form"> 
	   <div class="form-container row clearfix">
	    <div id="column-0" class="formColumn column form-column full col col-sm-12 form-horizontal" horizontal="True" span="12">
	     <div class="panel panel-default fieldset unsort no_title" id="box-0">
	      <div class="panel-heading">
	       <i class="icon icon-chevron-up chevron"></i>
	       <h3 class="panel-title"></h3>
	      </div>
	      <div class="panel-body ">
	       <div id="div_id_username" class="form-group">
	        <label for="id_username" class="control-label  requiredField"> 用户名<span class="asteriskField">*</span></label>
	        <div class="controls ">
	         <input class="textinput textInput form-control form-control" id="id_username" maxlength="30" name="username" type="text" /> 
	         <p id="hint_id_username" class="help-block">必填。不多于30个字符。只能用字母、数字和字符 @/./+/-/_ 。</p>
	        </div>
	       </div>
	       <div id="div_id_password1" class="form-group">
	        <label for="id_password1" class="control-label  requiredField"> 密码<span class="asteriskField">*</span></label>
	        <div class="controls ">
	         <input class="textinput textInput form-control form-control" id="id_password1" name="password1" type="password" /> 
	        </div>
	       </div>
	       <div id="div_id_password2" class="form-group">
	        <label for="id_password2" class="control-label  requiredField"> 密码确认<span class="asteriskField">*</span></label>
	        <div class="controls ">
	         <input class="textinput textInput form-control form-control" id="id_password2" name="password2" type="password" /> 
	         <p id="hint_id_password2" class="help-block">为了校验，输入与上面相同的密码。</p>
	        </div>
	       </div>
	      </div>
	     </div>
	    </div>
	   </div> 
	   <div class="form-actions well well-sm clearfix"> 
	    <div class="btn-group clearfix show-xs save-group col-xs-12"> 
	     <button type="submit" class="default btn btn-primary col-xs-10" name="_save" data-loading-text="保存中.."><i class="icon-save"></i> 保存</button> 
	     <button type="button" class="more btn btn-primary col-xs-2" data-toggle="collapse" data-target=".nav-collapse.more-btns"><i class="icon-ellipsis-vertical"></i></button> 
	    </div> 
	    <button type="submit" class="default btn btn-primary hide-xs" name="_save" data-loading-text="保存中.."><i class="icon-save"></i> 保存</button> 
	    <div class="nav-collapse collapse more-btns"> 
	     <input type="submit" class="btn btn-default" value="保存并增加另一个" name="_addanother" /> 
	     <input type="submit" class="btn btn-default" value="保存并继续编辑" name="_continue" /> 
	    </div> 
	   </div> 
	  </form>

	
</div>
    
