<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<form id="searchForm" class="form-inline accordion" action="${ctx}/showcase/editor">

    <label for="search.id_in">编号</label>
    <input type="text" id="search.id_in" name="search.id_in" value="${param['search.id_in']}"  class="input-medium" placeholder="多个使用空格分隔"/>
    &nbsp;&nbsp;
    <label for="search.title_like">标题</label>
    <input type="text" id="search.title_like" name="search.title_like" value="${param['search.title_like']}"  class="input-medium" placeholder="多个使用空格分隔"/>
    &nbsp;&nbsp;    
    <input type="submit" class="btn" value="查询"/>
    <a href="javascript:location.href=$('#searchForm').prop('action')" class="btn btn-link">查询所有</a>

</form>
