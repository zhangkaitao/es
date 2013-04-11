<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jspf"%>
<es:contentHeader/>
<div data-table="table" class="panel">

    <ul class="nav nav-tabs">
        <li <c:if test="${empty param['search.show_eq']}">class="active"</c:if>>
            <a href="${ctx}/showcase/sample">
                <i class="icon-table"></i>
                所有示例列表
            </a>
        </li>
        <li <c:if test="${param['search.show_eq'] eq 'true'}">class="active"</c:if>>
            <a href="${ctx}/showcase/sample?search.show_eq=true">
                <i class="icon-table"></i>
                可显示的示例列表
            </a>
        </li>
        <li <c:if test="${param['search.show_eq'] eq 'false'}">class="active"</c:if>>
            <a href="${ctx}/showcase/sample?search.show_eq=false">
                <i class="icon-table"></i>
                隐藏的示例列表
            </a>
        </li>
    </ul>

    <es:showMessage/>

    <div class="row-fluid tool ui-toolbar">
        <div class="span4">
            <div class="btn-group">
                <a class="btn btn-create">
                    <i class="icon-file"></i>
                    新增
                </a>
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