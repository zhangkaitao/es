<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<form id="searchForm" class="form-inline search-form">


    <esform:label path="search.id_in">编号</esform:label>
    <esform:input path="search.id_in" cssClass="input-small" placeholder="多个使用空格分隔"/>
    &nbsp;&nbsp;

    <esform:label path="searchCategoryId">类别</esform:label>
    <esform:input id="searchCategoryId" path="search.category.id_in" cssClass="input-medium" placeholder="多个使用空格分隔"/>

    <button type="submit" class="btn ">查询</button>
    <a class="btn btn-link btn-clear-search">清空</a>

</form>
