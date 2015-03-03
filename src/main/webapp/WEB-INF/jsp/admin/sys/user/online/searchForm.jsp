<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<form id="searchForm" class="form-inline search-form" data-change-search="true">

	<div class="form-group">
		<label class="col-sm-3 control-label no-padding-right" for="form-field-1"> Text Field </label>
			<div class="col-sm-9">
				<input type="text" id="form-field-1" placeholder="Username" class="col-xs-10 col-sm-5" />
			</div>
	</div>

    <esform:label path="search.username_like">用户名</esform:label>
    <esform:input path="search.username_like" cssClass="input-medium" placeholder="模糊匹配"/>
    &nbsp;&nbsp;
	    <input type="submit" class="btn btn-sm" value="查询"/>
	    <a class="btn btn-sm btn-clear-search">清空</a>

</form>
