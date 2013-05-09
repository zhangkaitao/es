<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jspf"%>
<es:contentHeader/>

<div data-table="table" class="panel">

    <c:set var="id" value="0"/>
    <c:if test="${not empty current}">
        <c:set var="id" value="${current.id}"/>
    </c:if>

    <ul class="nav nav-tabs">
        <li ${empty param['search.show_eq'] ? 'class="active"' : ''}>
            <a href="${ctx}/showcase/tree/list/${id}">
                <i class="icon-table"></i>
                所有
            </a>
        </li>
        <li ${param['search.show_eq'] eq 'true' ? 'class="active"' : ''}>
            <a href="${ctx}/showcase/tree/list/${id}?search.show_eq=true">
                <i class="icon-table"></i>
                显示的
            </a>
        </li>
        <li ${param['search.show_eq'] eq 'false' ? 'class="active"' : ''}>
            <a href="${ctx}/showcase/tree/list/${id}?search.show_eq=false">
                <i class="icon-table"></i>
                隐藏的
            </a>
        </li>
    </ul>

    <es:showMessage/>
    <%--<c:if test="${not empty message}">--%>
        <%--<script type="text/javascript">--%>
            <%--parent.window.frames['treeFrame'].window.location.reload();--%>
        <%--</script>--%>
    <%--</c:if>--%>

    <div class="row-fluid tool ui-toolbar">
        <div class="span4">
            <div class="btn-group">
                <shiro:hasPermission name="showcase:tree:create">
                <a id="appendChild" class="btn btn-custom">
                    <i class="icon-file"></i>
                    添加子节点
                </a>
                </shiro:hasPermission>
                <shiro:hasPermission name="showcase:tree:update">
                <a id="updateTree" class="btn btn-custom">
                    <i class="icon-edit"></i>
                    修改
                </a>
                </shiro:hasPermission>
                <shiro:hasPermission name="showcase:tree:delete">
                <a id="deleteTree" class="btn btn-custom">
                    <i class="icon-trash"></i>
                    删除
                </a>
                </shiro:hasPermission>
                <shiro:hasPermission name="showcase:tree:update">
                <div class="btn-group">
                    <a class="btn dropdown-toggle" data-toggle="dropdown" href="#">
                        <i class="icon-pencil"></i>
                        更多操作
                        <span class="caret"></span>
                    </a>
                    <ul class="dropdown-menu">
                        <li>
                            <a id="moveTree" class="btn btn-custom">
                                <i class="icon-move"></i>
                                移动节点
                            </a>
                        </li>
                        <li class="divider"></li>
                        <li>
                            <a class="btn btn-link status-show">
                                <i class="icon-pencil"></i>
                                选中显示
                            </a>
                        </li>
                        <li>
                            <a class="btn btn-link status-hide">
                                <i class="icon-pencil"></i>
                                选中隐藏
                            </a>
                        </li>
                    </ul>
                </div>
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
<%@include file="/WEB-INF/jsp/common/import-zTree-js.jspf"%>
<script type="text/javascript">
    $(function() {
        var tableId = "table";
        var urlPrefix = "${ctx}/showcase/tree";
        $.btn.initChangeShowStatus(urlPrefix + "/changeStatus", tableId);
        $.zTree.initMaintainBtn(urlPrefix, tableId, ${not empty param.async and param.async});
    });

</script>
