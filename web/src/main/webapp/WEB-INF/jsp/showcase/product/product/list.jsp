<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jspf"%>
<es:contentHeader/>
<c:set var="categoryId" value="${empty category ? 0 : category.id}"></c:set>
<div data-table="table" class="panel">


    <ul class="nav nav-tabs">
        <li ${empty param['search.show_eq'] ? 'class="active"' : ''}>
            <a href="${ctx}/showcase/product/product">
                <i class="icon-table"></i>
                所有产品列表
            </a>
        </li>
        <li ${param['search.show_eq'] eq 'true' ? 'class="active"' : ''}>
            <a href="${ctx}/showcase/product/product?search.show_eq=true">
                <i class="icon-table"></i>
                可显示的产品列表
            </a>
        </li>
        <li ${param['search.show_eq'] eq 'false' ? 'class="active"' : ''}>
            <a href="${ctx}/showcase/product/product?search.show_eq=false">
                <i class="icon-table"></i>
                隐藏的产品列表
            </a>
        </li>
        <c:if test="${not empty param.fromCategory}">
            <li>
                <a href="<es:BackURL/>">
                    <i class="icon-reply"></i>
                    返回类别列表
                </a>
            </li>
        </c:if>
    </ul>

    <es:showMessage/>
    <div class="row-fluid tool ui-toolbar">
        <div class="span4">
            <div class="btn-group">
                <shiro:hasPermission name="showcase:product:create">
                <a class="btn btn-create">
                    <span class="icon-file-alt"></span>
                    新增
                </a>
                </shiro:hasPermission>
                <shiro:hasPermission name="showcase:product:update">
                <a class="btn btn-update">
                    <span class="icon-edit"></span>
                    修改
                </a>
                </shiro:hasPermission>
                <shiro:hasPermission name="showcase:product:delete">
                <a class="btn btn-delete">
                    <span class="icon-trash"></span>
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

<script type="text/javascript">
    $(function() {
        $("#searchCategoryId").click(function() {
            $.app.modalDialog(
                    "参照",
                    "${ctx}/showcase/product/category/select/multiple;domId=searchCategoryId",
                    {
                        height : 400
                    }
            );
        });
    });
</script>

