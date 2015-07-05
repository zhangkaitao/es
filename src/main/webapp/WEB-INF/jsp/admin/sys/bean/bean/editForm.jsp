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
            ${op} 实体管理
	     </li>
	</ul>
	<!-- 
	<form class="exform" enctype="multipart/form-data" action="" method="post" id="user_form"> 
	 -->
	<form:form id="editForm" method="post" action="/admin/sys/bean/bean/save" commandName="m" enctype="multipart/form-data" cssClass="exform">
	
			<es:showGlobalError commandName="m"/>
            <form:hidden path="id"/>
            
	   <div class="form-container row clearfix">
	    <div id="column-0" class="formColumn column form-column full col col-sm-12 form-horizontal" horizontal="True" span="12">
	     <div class="panel panel-default fieldset unsort no_title" id="box-0">
	      <div class="panel-heading">
	       <i class="icon icon-chevron-up chevron"></i>
	       <h3 class="panel-title"></h3>
	      </div>
	      <div class="panel-body ">
	      
	       <div id="div_id_password2" class="form-group">
	        <label for="id_password2" class="control-label  requiredField">选择实体类</label>
	        <div class="controls ">
	        	<form:input path="classname" cssClass="textinput textInput form-control validate[required,minSize[2],maxSize[200]]" value="${m.classname}"/>
	        	<form:hidden path="allclassname" value="${m.allclassname}"/>
	        </div>
	       </div>
	       <div id="div_id_username" class="form-group">
	        <label for="id_username" class="control-label  requiredField"> 实体名</label>
	        <div class="controls ">
	        	<form:input path="name" cssClass="textinput textInput form-control validate[required,minSize[2],maxSize[200]]"/>
	        </div>
	       </div>
	       <div id="div_id_password1" class="form-group">
	        <label for="id_password1" class="control-label  requiredField"> 说明</label>
	        <div class="controls ">
	        	<form:input path="comments" cssClass="textinput textInput form-control validate[required,minSize[2],maxSize[200]]"/>
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
			                	显示名称
			        </th>
			        <th scope="col">
			                	表单类型
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
			        <th scope="col">
			                	操作
			        </th>
			      </tr>
			    </thead>
			    <tbody>
			   		<c:forEach items="${xxsAttributes}" var="t">
			   				<input name="ids" type="hidden" value="${t.id}"/>
				      		<tr class="grid-item">
				      			<td>${t.name}<input name="xxsname" type="hidden" value="${t.name}"/></td>
				      			<td>${t.javaType}<input name="xxssimpleName" type="hidden" value="${t.javaType}"/></td>
				      			<td>
	         						<input class="textinput textInput form-control" id="id_username" maxlength="30" name="displayName" type="text" value="${t.displayName}" /> 
	        					</td>
	        					<td>
				      				<select name="" class="form-control">
												<option value="1">文本输入框</option>
										        <option value="2">日期输入框</option>
										        <option value="3">复选框</option>
										        <option value="4">单选框</option>
										        <option value="5">下拉框</option>
										        <option value="6">文本区</option>
										        <option value="50">文本编辑器</option>
										        <option value="7">图片上传</option>
										        <option value="8">视频上传</option>
										        <option value="9">附件上传</option>
										</select>
				      				
								</td>
				      			<td><input <c:if test="${t.list}">checked="checked"</c:if> class="checkboxinput" id="list" name="isList" type="checkbox" /></td>
				      			<td><input <c:if test="${t.query}">checked="checked"</c:if> class="checkboxinput" id="query" name="isQuery" type="checkbox" /></td>
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
	        					<td>更多设置</td>
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
	   </form:form>
</div>
   <%@include file="/WEB-INF/jsp/admin/index/footer.jsp"%>
<es:contentFooter/>
    
