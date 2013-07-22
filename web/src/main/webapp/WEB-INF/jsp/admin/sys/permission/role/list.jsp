<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jspf"%>
<es:contentHeader/>
<div data-table="table" class="panel">

    <ul class="nav nav-tabs">
        <li ${empty param['search.show_eq'] ? 'class="active"' : ''}>
            <a href="${ctx}/admin/sys/permission/role">
                <i class="icon-table"></i>
                所有角色列表
            </a>
        </li>
        <li ${param['search.show_eq'] eq true ? 'class="active"' : ''}>
            <a href="${ctx}/admin/sys/permission/role?search.show_eq=true">
                <i class="icon-table"></i>
                可用角色列表
            </a>
        </li>
        <li ${param['search.show_eq'] eq false ? 'class="active"' : ''}>
            <a href="${ctx}/admin/sys/permission/role?search.show_eq=false">
                <i class="icon-table"></i>
               不可用角色列表
            </a>
        </li>
    </ul>

    <es:showMessage/>

    <div class="row-fluid tool ui-toolbar">
        <div class="span4">
            <div class="btn-group">
                <shiro:hasPermission name="sys:role:create">
                <a class="btn btn-create">
                    <i class="icon-file-alt"></i>
                    新增
                </a>
                </shiro:hasPermission>
                <shiro:hasPermission name="sys:role:update">
                <a id="update" class="btn btn-update">
                    <i class="icon-edit"></i>
                    修改
                </a>
                </shiro:hasPermission>
                <shiro:hasPermission name="sys:role:delete">
                <a class="btn btn-delete">
                    <i class="icon-trash"></i>
                    删除
                </a>
                </shiro:hasPermission>
                <shiro:hasPermission name="sys:role:delete">
                <div class="btn-group last">
                    <a class="btn dropdown-toggle" data-toggle="dropdown" href="#">
                        <i class="icon-pencil"></i>
                        状态
                        <span class="caret"></span>
                    </a>
                    <ul class="dropdown-menu">
                        <li>
                            <a class="btn btn-link status-show">
                                <i class="icon-pencil"></i>
                                可用
                            </a>
                        </li>
                        <li>
                            <a class="btn btn-link status-hide">
                                <i class="icon-pencil"></i>
                                不可用
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
            var title = isShow ? "可用数据" : "不可用数据";
            var message = isShow ? "确认可用数据吗？" : "确认不可用数据吗？";
            var url = isShow ?
                    "${ctx}/admin/sys/permission/role/changeStatus/true?" + checkbox.serialize()
                    :
                    "${ctx}/admin/sys/permission/role/changeStatus/false?" + checkbox.serialize();
            $.app.confirm({
                title : title,
                message : message,
                ok : function() {
                    var table = $("#table");
                    $.table.reloadTable(table, url, $.table.tableURL(table));
                }
            });
        });

        $.app.toggleLoadTable($("#table"), "${ctx}/admin/sys/permission/role/{parentId}/permissions")
    });
</script>

