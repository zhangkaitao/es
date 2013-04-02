<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jspf"%>
<es:contentHeader/>

<div data-table="historyTable" class="panel">

    <ul class="nav nav-tabs">
        <li class="active">
            <a href="${ctx}/admin/sys/user/statusHistory">
                <i class="icon-table"></i>
                状态变更历史列表
            </a>
        </li>
    </ul>

    <div class="row-fluid tool ui-toolbar">
        <%@include file="searchForm.jsp" %>
    </div>
    <%@include file="listTable.jsp"%>
</div>
<es:contentFooter/>