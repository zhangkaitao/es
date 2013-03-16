<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jspf"%>
<es:contentHeader/>
<es:showMessage/>
<div data-table="table">
    <div class="row-fluid tool ui-toolbar">
        <div class="span5">
            <div class="btn-group">


                <a class="btn btn-create">
                    <span class="icon-file"></span>
                    新增
                </a>
                <a id="ajaxCreate" class="btn" href="${ctx}/showcase/upload/ajax/create">
                    <span class="icon-file"></span>
                    ajax新增
                </a>
                <a id="ajaxBatchCreate" class="btn" href="${ctx}/ajaxUpload">
                    <span class="icon-file"></span>
                    ajax批量上传
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
        <div class="span7">
            <div class="search">
                <%@include file="searchForm.jsp"%>
            </div>
        </div>
    </div>

    <%@include file="listTable.jsp"%>
</div>
<es:contentFooter/>
