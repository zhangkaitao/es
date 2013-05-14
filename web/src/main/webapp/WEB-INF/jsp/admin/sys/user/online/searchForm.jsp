<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<form id="searchForm" class="form-inline search-form" data-change-search="true">

    <esform:label path="search.username_like">用户名</esform:label>
    <esform:input path="search.username_like" cssClass="input-medium" placeholder="模糊匹配"/>
    &nbsp;&nbsp;
    <input type="submit" class="btn" value="查询"/>
    <a class="btn btn-link btn-clear-search">清空</a>


</form>
