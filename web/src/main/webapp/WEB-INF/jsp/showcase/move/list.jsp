<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jspf"%>
<es:contentHeader/>

<div data-table="table" class="panel">
    <ul class="nav nav-tabs">
        <li ${empty param['search.show_eq'] ? 'class="active"' : ''}>
            <a href="${ctx}/showcase/move">
                <i class="icon-table"></i>
                所有数据列表
            </a>
        </li>
        <li ${param['search.show_eq'] eq 'true' ? 'class="active"' : ''}>
            <a href="${ctx}/showcase/move?search.show_eq=true">
                <i class="icon-table"></i>
                可显示的数据列表
            </a>
        </li>
        <li ${param['search.show_eq'] eq 'false' ? 'class="active"' : ''}>
            <a href="${ctx}/showcase/move?search.show_eq=false">
                <i class="icon-table"></i>
                隐藏的数据列表
            </a>
        </li>
    </ul>

    <es:showMessage/>

    <div class="row-fluid tool ui-toolbar">
        <div class="span4">
            <div class="btn-group">
                <shiro:hasPermission name="showcase:move:create">
                <a id="create" class="btn btn-create" href="${ctx}/showcase/move/create">
                    <i class="icon-file-alt"></i>
                    新增
                </a>
                </shiro:hasPermission>
                <shiro:hasPermission name="showcase:move:update">
                <a class="btn btn-update">
                    <i class="icon-edit"></i>
                    修改
                </a>
                </shiro:hasPermission>
                <shiro:hasPermission name="showcase:move:deleted">
                <a class="btn btn-delete">
                    <i class="icon-trash"></i>
                    删除
                </a>
                </shiro:hasPermission>
                <shiro:hasPermission name="showcase:move:create or showcase:move:update or showcase:move:delete">
                <a id="reweight" class="btn btn-create">
                    <i class="icon-cog"></i>
                    优化权重
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

<script type="text/javascript">
    $(function() {
        $.movable.initMovableReweight($("#reweight"), "${ctx}/showcase/move/reweight");
    });
</script>
