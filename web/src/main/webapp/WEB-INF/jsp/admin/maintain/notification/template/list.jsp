<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jspf"%>
<es:contentHeader/>
<div data-table="table" class="panel">

    <ul class="nav nav-tabs">
        <li ${param['search.deleted_eq'] ne 'false' ? 'class="active"' : ''}>
            <a href="${ctx}/admin/maintain/notification/template">
                <i class="icon-table"></i>
                通知模板列表
            </a>
        </li>
        <li ${param['search.deleted_eq'] eq 'false' ? 'class="active"' : ''}>
            <a href="${ctx}/admin/maintain/notification/template?search.deleted_eq=false">
                <i class="icon-table"></i>
                已删除的通知模板列表
            </a>
        </li>
    </ul>

    <es:showMessage/>

    <div class="row-fluid tool ui-toolbar">
        <div class="span4">
            <div class="btn-group">
                <shiro:hasPermission name="maintain:notificationTemplate:create">
                <a class="btn btn-create">
                    <i class="icon-file-alt"></i>
                    新增
                </a>
                </shiro:hasPermission>
                <shiro:hasPermission name="maintain:notificationTemplate:update">
                <a id="update" class="btn btn-update">
                    <i class="icon-edit"></i>
                    修改
                </a>
                </shiro:hasPermission>
                <shiro:hasPermission name="maintain:notificationTemplate:delete">
                <a class="btn btn-delete">
                    <i class="icon-trash"></i>
                    删除
                </a>
                </shiro:hasPermission>
            </div>
        </div>
        <div class="span8">
            <%@include file="searchForm.jsp"%>
        </div>
    </div>
    <%@include file="listTable.jsp"%>

</div>

<es:contentFooter/>