<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jspf"%>
<es:contentHeader/>
<es:showMessage/>
<div class="panel" data-table="table">
    <div class="row-fluid tool ui-toolbar">
        <div class="span4">
            <div class="btn-group">
                <div class="btn-group">
                    <a class="btn btn-create">
                        新&nbsp;增
                    </a>
                    <a class="btn dropdown-toggle" data-toggle="dropdown" href="#">
                        <span class="caret"></span>
                    </a>
                    <ul class="dropdown-menu">
                        <li>
                            <a id="ajaxCreate" class="btn" href="${ctx}/showcase/upload/ajax/create">
                                <span class="icon-file"></span>
                                ajax新增
                            </a>
                        </li>
                        <li>
                            <a id="ajaxBatchCreate"  class="btn" href="${ctx}/ajaxUpload">
                                <span class="icon-file"></span>
                                ajax批量上传
                            </a>
                        </li>
                    </ul>
                </div>
                <a class="btn btn-update">
                    <span class="icon-edit"></span>
                    修&nbsp;改
                </a>
                <a class="btn btn-batch-delete">
                    <span class="icon-trash"></span>
                    批量删除
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
