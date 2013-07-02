<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jspf"%>
<es:contentHeader/>
<div data-table="table" class="panel">

    <ul class="nav nav-tabs">
        <li class="active">
            <a href="${ctx}/showcase/excel">
                <i class="icon-table"></i>
                所有数据列表
            </a>
        </li>
    </ul>

    <es:showMessage/>

    <div class="row-fluid tool ui-toolbar">
        <div class="span6">
            <div class="btn-group">
                <shiro:hasPermission name="showcase:excel:create">
                <a class="btn btn-create">
                    <i class="icon-file-alt"></i>
                    新增
                </a>
                </shiro:hasPermission>
                <shiro:hasPermission name="showcase:excel:update">
                <a id="update" class="btn btn-update">
                    <i class="icon-edit"></i>
                    修改
                </a>
                </shiro:hasPermission>
                <shiro:hasPermission name="showcase:excel:delete">
                <a class="btn btn-delete">
                    <i class="icon-trash"></i>
                    删除
                </a>
                </shiro:hasPermission>
                <shiro:hasPermission name="showcase:excel:create">
                    <a class="btn btn-custom no-disabled" href="${ctx}/showcase/excel/init" data-toggle="tooltip" data-placement="bottom" data-title="先删除老的数据再生成100w数据">
                        <i class="icon-file-alt"></i>
                        初始化100w数据
                    </a>
                </shiro:hasPermission>
                <shiro:hasPermission name="showcase:excel:import or showcase:excel:export">
                    <div class="btn-group last">
                        <a class="btn btn-custom no-disabled dropdown-toggle" data-toggle="dropdown">
                            <i class="icon-laptop"></i>
                            Excel导入/导出
                            <i class="caret"></i>
                        </a>
                        <ul class="dropdown-menu">
                            <shiro:hasPermission name="showcase:excel:import">
                            <li>
                                <a class="btn no-disabled">
                                    <i class="icon-level-down"></i>
                                    Excel导入
                                </a>
                            </li>
                            </shiro:hasPermission>
                            <shiro:hasPermission name="showcase:excel:export">
                            <li>
                                <a class="btn no-disabled">
                                    <i class="icon-level-up"></i>
                                    Excel导出
                                </a>
                            </li>
                            </shiro:hasPermission>
                        </ul>
                    </div>
                </shiro:hasPermission>
            </div>
        </div>
        <div class="span6">
            <%@include file="searchForm.jsp"%>
        </div>
    </div>
    <%@include file="listTable.jsp"%>

</div>

<es:contentFooter/>