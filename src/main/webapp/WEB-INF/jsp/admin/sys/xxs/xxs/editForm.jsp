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
            ${op} xxs
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
	        <label for="id_username" class="control-label  requiredField"> 实体名<span class="asteriskField">*</span></label>
	        <div class="controls ">
	         	<input class="textinput textInput form-control"  type="text" value="${entityName }" />  
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
	         	<select id="id_continent" class="form-control" name="continent">
		         	<option value="" selected="selected">---------</option>
		        	<c:forEach items="${entityLists}" var="m">
		         		<option value="${simpleName}">${m.simpleName}</option>
		        	</c:forEach>
	         	</select> 
	         <p id="hint_id_password2" class="help-block">为了校验，输入与上面相同的密码。</p>	
	        </div>
	       </div>
	       
	      </div>
	      
	      
	      
	      
	      
	     </div>
	     
	     <div class="results table-responsive">
  			<table class="table table-bordered table-striped table-hover">
			    <thead>
			      <tr>
			        <th scope="col">
			                	名称
			        </th>
			        <th scope="col">
			                	类型
			        </th>
			        <th scope="col">
			                	说明
			        </th>
			        <th scope="col">
			                	列表
			        </th>
			        <th scope="col">
			                	查询
			        </th>
			        <th scope="col">
			                	查询匹配方式
			        </th>
			        <th scope="col">
			                	排序
			        </th>
			      </tr>
			    </thead>
			    <tbody>
			   		<c:forEach items="${fields}" var="m">
				      		<tr class="grid-item">
				      			<td>${m.name}</td>
				      			<td>${m.type.simpleName}</td>
				      			<td>
	         						<input class="textinput textInput form-control" id="id_username" maxlength="30" name="username" type="text" /> 
	        					</td>
				      			<td><input checked="checked" class="checkboxinput" id="id_is_public" name="is_public" type="checkbox" /></td>
				      			<td><input checked="checked" class="checkboxinput" id="id_is_public" name="is_public" type="checkbox" /></td>
				      			<td>
				      				<select name="" class="form-control">
											
												<option value="=" selected title="">=</option>
											
												<option value="!="  title="">!=</option>
											
												<option value="&gt;"  title="">&gt;</option>
											
												<option value="&gt;="  title="">&gt;=</option>
											
												<option value="&lt;"  title="">&lt;</option>
											
												<option value="&lt;="  title="">&lt;=</option>
											
												<option value="between"  title="">Between</option>
											
												<option value="like"  title="">Like</option>
											
												<option value="left_like"  title="">Left Like</option>
											
												<option value="right_like"  title="">Right Like</option>
											
										</select>
				      				
								</td>
								<td>
	         						<input class="textinput textInput form-control" id="id_username" maxlength="30" name="username" type="text" /> 
	        					</td>
				      		</tr>
			    	</c:forEach>
			    </tbody>
			</table>
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
    
