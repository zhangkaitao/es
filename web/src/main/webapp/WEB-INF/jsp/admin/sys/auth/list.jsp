<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jspf"%>
<es:contentHeader/>
<div data-table="table" class="panel">

    <ul class="nav nav-tabs">
        <c:forEach items="${types}" var="t">
            <li <c:if test="${t eq type}">class="active"</c:if>>
                <a href="${ctx}/admin/sys/auth?search.type_eq=${t}">
                    <i class="icon-table"></i>
                    ${t.info}授权列表
                </a>
            </li>
        </c:forEach>
    </ul>

    <es:showMessage/>

    <div class="row-fluid tool ui-toolbar">
        <div class="span4">
            <div class="btn-group">
                <a class="btn btn-custom dropdown-toggle" data-toggle="dropdown">
                    <i class="icon-file"></i>
                    批量新增
                    <i class="caret"></i>
                </a>
                <ul class="dropdown-menu">
                    <c:forEach items="${types}" var="t">
                        <li>
                            <a class="btn" href="${ctx}/admin/sys/auth/${t}/create">
                                <i class="icon-file"></i>
                                新增${t.info}授权
                            </a>
                        </li>
                    </c:forEach>
                </ul>
                <a id="update" class="btn btn-update">
                    <i class="icon-edit"></i>
                    修改
                </a>
                <a class="btn btn-batch-delete">
                    <i class="icon-trash"></i>
                    删除
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