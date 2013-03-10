<%@ taglib prefix="s" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<form id="searchForm" class="form-inline accordion" action="${ctx}/showcase/status/audit">

    <label for="search.id_in">编号</label>
    <input type="text" id="search.id_in" name="search.id_in" value="${param['search.id_in']}"  class="input-medium" placeholder="多个使用空格分隔"/>
    &nbsp;&nbsp;
    <label for="search.id_in">名称</label>
    <input type="text" id="search.name_like" name="search.name_like" value="${param['search.name_like']}"  class="input-medium"/>
    &nbsp;&nbsp;
    <label for="search.status_eq">状态</label>
    <select id="search.status_eq" name="search.status_eq">
        <option value="">所有</option>
        <c:forEach items="${statusList}" var="s">
        <option value="${s}" <c:if test="${param['search.status_eq'] eq s}">selected="selected"</c:if>>${s.info}</option>
        </c:forEach>
    </select>
    &nbsp;&nbsp;
    <input type="submit" class="btn" value="查询"/>
    <a href="javascript:location.href=$('#searchForm').prop('action')" class="btn btn-link">查询所有</a>

</form>
