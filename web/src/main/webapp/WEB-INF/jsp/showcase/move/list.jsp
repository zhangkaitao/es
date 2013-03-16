<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jspf"%>
<es:contentHeader/>
<es:showMessage/>

<div data-table="table">
    <div class="row-fluid tool ui-toolbar">
        <div class="span4">
            <div class="btn-group">
                <a id="create" class="btn " href="${ctx}/showcase/move/create">
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
                <a id="reweight" class="btn">
                    <span class="icon-cog"></span>
                    优化权重
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

<script type="text/javascript">
    $(function() {
        $.movable.initMovableReweight($("#reweight"), "${ctx}/showcase/move/reweight");
    });
</script>
