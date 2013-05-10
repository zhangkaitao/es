<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<form id="searchForm" class="form-inline search-form" data-change-search="true">


    <esform:label path="search.id_in">编号</esform:label>
    <esform:input path="search.id_in" cssClass="input-medium" placeholder="多个使用空格分隔"/>

    &nbsp;&nbsp;

    <esform:label path="search.weight_gt">权重从</esform:label>
    <esform:input path="search.weight_gt" cssClass="input-small" placeholder="大于"/>

    <esform:label path="search.weight_lt">到</esform:label>
    <esform:input path="search.weight_lt" cssClass="input-small" placeholder="小于"/>


    <button type="submit" class="btn">查询</button>
    <a class="btn btn-link btn-clear-search">清空</a>

</form>
