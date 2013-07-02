<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jspf"%>
<es:contentHeader/>

<div class="panel" data-table="table">
    <ul class="nav nav-tabs">
        <li class="active">
            <a href="${ctx}/showcase/upload">
                <i class="icon-table"></i>
                所有文件列表
            </a>
        </li>
    </ul>

    <es:showMessage/>

    <div class="row-fluid tool ui-toolbar">
        <div class="span4">
            <div class="btn-group">
                <shiro:hasPermission name="showcase:upload:create">
                <div class="btn-group first">
                    <a class="btn no-disabled dropdown-toggle" data-toggle="dropdown">
                        <i class="icon-file-alt"></i>
                        新&nbsp;增
                        <i class="caret"></i>
                    </a>
                    <ul class="dropdown-menu">
                        <li>
                            <a class="btn no-disabled btn-create">
                                <i class="icon-file-alt"></i>
                                普通新增
                            </a>
                        </li>
                        <li>
                            <a id="ajaxCreate" class="btn no-disabled" href="${ctx}/showcase/upload/ajax/create">
                                <i class="icon-file-alt"></i>
                                ajax新增
                            </a>
                        </li>
                        <li>
                            <a id="ajaxBatchCreate"  class="btn no-disabled" href="${ctx}/ajaxUpload">
                                <i class="icon-file-alt"></i>
                                ajax批量上传
                            </a>
                        </li>
                    </ul>
                </div>
                </shiro:hasPermission>
                <shiro:hasPermission name="showcase:upload:update">
                <a class="btn btn-update">
                    <i class="icon-edit"></i>
                    修&nbsp;改
                </a>
                </shiro:hasPermission>
                <shiro:hasPermission name="showcase:upload:delete">
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
