<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jspf"%>
<es:contentHeader/>

<div data-table="table" class="panel">


    <ul class="nav nav-tabs">
        <li ${empty param['search.status_eq'] ? 'class="active"' : ''}>
            <a href="${ctx}/showcase/status/audit">
                <i class="icon-table"></i>
                所有数据列表
            </a>
        </li>
        <li ${param['search.status_eq'] eq 'waiting' ? 'class="active"' : ''}>
            <a href="${ctx}/showcase/status/audit?search.status_eq=waiting">
                <i class="icon-table"></i>
                等待审核列表
            </a>
        </li>
        <li ${param['search.status_eq'] eq 'fail' ? 'class="active"' : ''}>
            <a href="${ctx}/showcase/status/audit?search.status_eq=fail">
                <i class="icon-table"></i>
                审核失败列表
            </a>
        </li>
        <li ${param['search.status_eq'] eq 'success' ? 'class="active"' : ''}>
            <a href="${ctx}/showcase/status/audit?search.status_eq=success">
                <i class="icon-table"></i>
                审核通过列表
            </a>
        </li>
    </ul>

    <es:showMessage/>

    <div class="row-fluid tool ui-toolbar">
        <div class="span4">
            <div class="btn-group">
                <shiro:hasPermission name="showcase:statusAudit:create">
                <a class="btn btn-create">
                    <span class="icon-file-alt"></span>
                    新增
                </a>
                </shiro:hasPermission>
                <shiro:hasPermission name="showcase:statusAudit:update">
                <a class="btn btn-update">
                    <span class="icon-edit"></span>
                    修改
                </a>
                </shiro:hasPermission>
                <shiro:hasPermission name="showcase:statusAudit:delete">
                <a class="btn btn-delete">
                    <span class="icon-trash"></span>
                    删除
                </a>
                </shiro:hasPermission>
                <shiro:hasPermission name="showcase:statusAudit:audit">
                <div class="btn-group">
                    <a class="btn dropdown-toggle" data-toggle="dropdown" href="#">
                        <i class="icon-pencil"></i>
                        审核
                        <span class="caret"></span>
                    </a>
                    <ul class="dropdown-menu">
                        <li>
                            <a class="btn btn-link status-success">
                                <i class="icon-pencil"></i>
                                审核成功
                            </a>
                        </li>
                        <li>
                            <a class="btn btn-link status-fail">
                                <i class="icon-pencil"></i>
                                审核失败
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
<script type="text/javascript">
    $(function() {
        $(".status-success,.status-fail").click(function() {

            var checkbox = $.table.getAllSelectedCheckbox($(".table"));
            if(checkbox.size() == 0) {
                return;
            }
            var isAuditSuccess = $(this).is(".status-success");
            var title = isAuditSuccess ? "审核成功" : "审核失败";
            var url = isAuditSuccess ?
                      "${ctx}/showcase/status/audit/status/success?" + checkbox.serialize()
                      :
                      "${ctx}/showcase/status/audit/status/fail?" + checkbox.serialize();
            $.app.confirm({
                title : title,
                message : "请输入审核结果：<br/><textarea id='comment' style='width: 300px;height: 50px;'></textarea>",
                ok : function() {
                    var comment = $("#comment").val();
                    var table = $("#table");
                    $.table.reloadTable(table, url + "&comment=" + comment, $.table.tableURL(table));
                }
            });
        });
    });
</script>