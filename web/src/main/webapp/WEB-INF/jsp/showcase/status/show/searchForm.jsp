<%@ taglib prefix="s" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<form id="searchForm" class="form-inline search-form" data-change-search="true">

    <esform:label path="search.id_in">编号</esform:label>
    <esform:input path="search.id_in" cssClass="input-small" placeholder="多个使用空格分隔"/>
    &nbsp;&nbsp;

    <esform:label path="search.name_like">名称</esform:label>
    <esform:input path="search.name_like" cssClass="input-small" placeholder="模糊查询"/>

    &nbsp;&nbsp;


    <esform:label path="search.status_eq">状态</esform:label>
    <esform:select path="search.status_eq" cssClass="input-small">
        <esform:option label="所有" value=""/>
        <esform:options items="${statusList}" itemLabel="info"/>
    </esform:select>

    &nbsp;&nbsp;
    <input type="submit" class="btn" value="查询"/>
    <a class="btn btn-link btn-clear-search">清空</a>

</form>


