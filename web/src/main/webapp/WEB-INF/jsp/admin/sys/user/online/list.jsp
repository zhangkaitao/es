<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jspf"%>
<es:contentHeader/>


<ul class="nav nav-pills">
    <li <c:if test="${empty param['search.userId_eq'] and empty param['search.userId_gt']}">class="active"</c:if>>
        <a href="${ctx}/admin/sys/user/online">所有</a>
    </li>

    <li <c:if test="${not empty param['search.userId_gt']}">class="active"</c:if>>
        <a href="${ctx}/admin/sys/user/online?search.userId_gt=0">在线用户</a>
    </li>
    <li <c:if test="${not empty param['search.userId_eq']}">class="active"</c:if>>
        <a href="${ctx}/admin/sys/user/online?search.userId_eq=0">游客</a>
    </li>
</ul>

<div data-table="table">
    <div class="row-fluid tool ui-toolbar">
        <%@include file="searchForm.jsp" %>
    </div>
    <%@include file="listTable.jsp"%>
</div>
<es:contentFooter/>