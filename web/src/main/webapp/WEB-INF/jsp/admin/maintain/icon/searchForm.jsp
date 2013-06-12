<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<form id="searchForm" class="form-inline search-form" data-change-search="true">

    <esform:label path="search.identity_like">标识符</esform:label>
    <esform:input path="search.identity_like" cssClass="input-medium" placeholder="模糊查询"/>
    &nbsp;
    <input type="submit" class="btn" value="查询"/>
    <a class="btn btn-link btn-clear-search">清空</a>
</form>
