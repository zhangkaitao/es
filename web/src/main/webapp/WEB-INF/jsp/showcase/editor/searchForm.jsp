<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<form id="searchForm" class="form-inline search-form">

    <esform:label path="search.id_in">编号</esform:label>
    <esform:input path="search.id_in" cssClass="input-medium" placeholder="多个使用空格分隔"/>

    &nbsp;&nbsp;
    <esform:label path="search.title_like">标题</esform:label>
    <esform:input path="search.title_like" cssClass="input-medium" placeholder="模糊匹配"/>

    &nbsp;&nbsp;    
    <input type="submit" class="btn " value="查询"/>
    <a class="btn btn-link btn-clear-search">清空</a>

</form>
