<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<form id="searchForm" class="form-inline search-form" data-change-search="true">

    <esform:label path="search.name_like">名称</esform:label>
    <esform:input path="search.name_like" cssClass="input-medium"/>
    &nbsp;&nbsp;
    <esform:label path="search.status_eq">默认</esform:label>
    <esform:select path="search.defaultGroup_eq" cssClass="input-small">
        <esform:option label="所有" value=""/>
        <esform:options items="${booleanList}" itemLabel="info"/>
    </esform:select>
    &nbsp;&nbsp;
    <esform:label path="search.show_eq">有效</esform:label>
    <esform:select path="search.show_eq" cssClass="input-small">
        <esform:option label="所有" value=""/>
        <esform:options items="${booleanList}" itemLabel="info"/>
    </esform:select>

    &nbsp;&nbsp;
    <input type="submit" class="btn " value="查询"/>
    <a class="btn btn-link btn-clear-search">清空</a>
</form>
