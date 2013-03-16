<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jspf"%>
<es:contentHeader/>

<ul class="nav nav-pills">
    <li <c:if test="${empty param['search.status_eq']}">class="active"</c:if>>
        <a href="${ctx}/showcase/status/audit">所有数据列表</a>
    </li>
    <li <c:if test="${param['search.status_eq'] eq 'waiting'}">class="active"</c:if>>
        <a href="${ctx}/showcase/status/audit?search.status_eq=waiting">等待审核列表</a>
    </li>
    <li <c:if test="${param['search.status_eq'] eq 'fail'}">class="active"</c:if>>
        <a href="${ctx}/showcase/status/audit?search.status_eq=fail">审核拒绝列表</a>
    </li>
    <li <c:if test="${param['search.status_eq'] eq 'success'}">class="active"</c:if>>
        <a href="${ctx}/showcase/status/audit?search.status_eq=success">审核通过列表</a>
    </li>
</ul>
<es:showMessage/>

<div data-table="table">
    <div class="row-fluid tool ui-toolbar">
        <div class="span4">
            <div class="btn-group">
                <a class="btn btn-create">
                    <span class="icon-file"></span>
                    新增
                </a>
                <a class="btn btn-update">
                    <span class="icon-edit"></span>
                    修改
                </a>
                <a class="btn btn-batch-delete">
                    <span class="icon-trash"></span>
                    批量删除
                </a>
            </div>
        </div>
        <div class="span8">
            <%@include file="searchForm.jsp"%>
        </div>
    </div>
    <%@include file="listTable.jsp"%>
</div>
<es:contentFooter/>