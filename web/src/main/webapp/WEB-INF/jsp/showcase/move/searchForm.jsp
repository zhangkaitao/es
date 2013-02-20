<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<form id="searchForm" class="form-inline accordion" action="${ctx}/showcase/move">

    <label for="search.id_in">编号</label>
    <input type="text" id="search.id_in" name="search.id_in" value="${param['search.id_in']}"  class="input-medium" placeholder="多个使用空格分隔">
    &nbsp;&nbsp;
    <label for="search.weight_gt">权重从</label>
    <input type="text" id="search.weight_gt" name="search.weight_gt" value="${param['search.weight_gt']}" class="input-small" placeholder="大于">

    <label for="search.weight_lt">到</label>
    <input type="text" id="search.weight_lt" name="search.weight_lt" value="${param['search.weight_lt']}" class="input-small" placeholder="小于">
    
    <button type="submit" class="btn">查询</button>
    <a href="javascript:location.href=$('#searchForm').prop('action')" class="btn btn-link">查询所有</a>

</form>
