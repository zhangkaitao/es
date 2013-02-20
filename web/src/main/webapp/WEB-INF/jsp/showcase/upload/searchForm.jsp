<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<form id="searchForm" class="form-inline accordion" action="${ctx}/showcase/upload">

    <label for="search.id_in">编号</label>
    <input type="text" id="search.id_in" name="search.id_in" value="${param['search.id_in']}"  class="input-medium" placeholder="多个使用空格分隔">
    &nbsp;&nbsp;
    <label for="search.name_like">名称</label>
    <input type="text" id="search.name_like" name="search.name_like" value="${param['search.name_like']}" class="input-medium" placeholder="请输入要匹配的名称">
    &nbsp;&nbsp;
    <button type="submit" class="btn">查询</button>
    <a href="javascript:location.href=$('#searchForm').prop('action')" class="btn btn-link">查询所有</a>

</form>
