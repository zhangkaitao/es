<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<form id="searchForm" class="form-inline search-form">

    <label for="search.id_in">编号</label>
    <input type="text" id="search.id_in" name="search.id_in" value="${param['search.id_in']}"  class="input-medium" placeholder="多个使用空格分隔">
    &nbsp;&nbsp;
    <label for="searchCategoryId">类别</label>
    <input type="text" id="searchCategoryId" name="search.category.id_in" value="${param['search.category.id_in']}"  class="input-medium" placeholder="多个使用空格分隔">

    <button type="submit" class="btn ">查询</button>
    <a class="btn btn-link btn-search-all">查询所有</a>

</form>
