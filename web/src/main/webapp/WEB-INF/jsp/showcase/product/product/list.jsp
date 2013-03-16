<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jspf"%>
<es:contentHeader/>
<es:showMessage/>
<c:set var="categoryId" value="${empty category ? 0 : category.id}"></c:set>
<div data-table="table">
    <div class="row-fluid tool ui-toolbar">
        <div class="span6">
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
        <div class="span6">
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
                        "height" : 450
                    }
            );
        });
    });
</script>

