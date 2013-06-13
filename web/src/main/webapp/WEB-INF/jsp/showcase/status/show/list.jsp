<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jspf"%>
<es:contentHeader/>

<div data-table="table" class="panel">

    <ul class="nav nav-tabs">
        <li ${empty param['search.status_eq'] ? 'class="active"' : ''}>
            <a href="${ctx}/showcase/status/show">
                <i class="icon-table"></i>
                所有数据列表
            </a>
        </li>
        <li ${param['search.status_eq'] eq 'show' ? 'class="active"' : ''}>
            <a href="${ctx}/showcase/status/show?search.status_eq=show">
                <i class="icon-table"></i>
                显示的数据列表
            </a>
        </li>
        <li ${param['search.status_eq'] eq 'hide' ? 'class="active"' : ''}>
            <a href="${ctx}/showcase/status/show?search.status_eq=hide">
                <i class="icon-table"></i>
                隐藏的数据列表
            </a>
        </li>
    </ul>


    <es:showMessage/>

    <div class="row-fluid tool ui-toolbar">
        <div class="span4">
            <div class="btn-group">
                <shiro:hasPermission name="showcase:statusShow:create">
                <a class="btn btn-create">
                    <span class="icon-file-alt"></span>
                    新增
                </a>
                </shiro:hasPermission>
                <shiro:hasPermission name="showcase:statusShow:update">
                <a class="btn btn-update">
                    <span class="icon-edit"></span>
                    修改
                </a>
                </shiro:hasPermission>
                <shiro:hasPermission name="showcase:statusShow:delete">
                <a class="btn btn-delete">
                    <span class="icon-trash"></span>
                    删除
                </a>
                </shiro:hasPermission>
                <shiro:hasPermission name="showcase:statusShow:audit">
                <div class="btn-group">
                    <a class="btn dropdown-toggle" data-toggle="dropdown" href="#">
                        <i class="icon-pencil"></i>
                        审核
                        <span class="caret"></span>
                    </a>
                    <ul class="dropdown-menu">
                        <li>
                            <a class="btn btn-link status-show">
                                <i class="icon-pencil"></i>
                                显示
                            </a>
                        </li>
                        <li>
                            <a class="btn btn-link status-hide">
                                <i class="icon-pencil"></i>
                                隐藏
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
        $(".status-show,.status-hide").click(function() {

            var checkbox = $.table.getAllSelectedCheckbox($(".table"));
            if(checkbox.size() == 0) {
                return;
            }
            var isShow = $(this).is(".status-show");
            var title = isShow ? "显示数据" : "隐藏数据";
            var message = isShow ? "确认显示数据吗？" : "确认隐藏数据吗？";
            var url = isShow ?
                    "${ctx}/showcase/status/show/status/show?" + checkbox.serialize()
                    :
                    "${ctx}/showcase/status/show/status/hide?" + checkbox.serialize();
            $.app.confirm({
                title : title,
                message : message,
                ok : function() {
                    var table = $("#table");
                    $.table.reloadTable(table, url, $.table.tableURL(table));
                }
            });
        });
    });
</script>