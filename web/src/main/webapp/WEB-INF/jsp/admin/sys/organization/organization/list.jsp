<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jspf"%>
<es:contentHeader/>

<div data-table="table" class="panel">

    <c:set var="id" value="0"/>
    <c:if test="${not empty current}">
        <c:set var="id" value="${current.id}"/>
    </c:if>

    <ul class="nav nav-tabs">
        <li <c:if test="${empty param['search.show_eq']}">class="active"</c:if>>
            <a href="${ctx}/admin/sys/organization/organization/list/${id}">
                <i class="icon-table"></i>
                所有
            </a>
        </li>
        <li <c:if test="${param['search.show_eq'] eq 'true'}">class="active"</c:if>>
            <a href="${ctx}/admin/sys/organization/organization/list/${id}?search.show_eq=true">
                <i class="icon-table"></i>
                可显示的
            </a>
        </li>
        <li <c:if test="${param['search.show_eq'] eq 'false'}">class="active"</c:if>>
            <a href="${ctx}/admin/sys/organization/organization/list/${id}?search.show_eq=false">
                <i class="icon-table"></i>
                不显示的
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
                <a id="appendChild" class="btn btn-custom">
                    <i class="icon-file"></i>
                    添加子节点
                </a>
                <a id="moveTree" class="btn btn-custom">
                    <i class="icon-move"></i>
                    移动节点
                </a>
                <a id="updateTree" class="btn btn-custom">
                    <i class="icon-edit"></i>
                    修改
                </a>
                <a id="deleteTree" class="btn btn-custom">
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
