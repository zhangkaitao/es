<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jspf"%>
<es:contentHeader/>
<div data-table="table" class="panel">

    <ul class="nav nav-tabs">
        <li ${empty param['search.start_eq'] ? 'class="active"' : ''}>
            <a href="${ctx}/admin/maintain/dynamicTask">
                <i class="icon-table"></i>
                所有任务列表
            </a>
        </li>
        <li ${param['search.start_eq'] eq 'true' ? 'class="active"' : ''}>
            <a href="${ctx}/admin/maintain/dynamicTask?search.start_eq=true">
                <i class="icon-table"></i>
                已启动任务列表
            </a>
        </li>
        <li ${param['search.start_eq'] eq 'false' ? 'class="active"' : ''}>
            <a href="${ctx}/admin/maintain/dynamicTask?search.start_eq=false">
                <i class="icon-table"></i>
                未启动任务列表
            </a>
        </li>
    </ul>

    <es:showMessage/>

    <div class="row-fluid tool ui-toolbar">
        <div class="span4">
            <div class="btn-group">
                <shiro:hasPermission name="maintain:dynamicTask:create">
                <a class="btn btn-create">
                    <i class="icon-file-alt"></i>
                    新增
                </a>
                </shiro:hasPermission>
                <shiro:hasPermission name="maintain:dynamicTask:update">
                <a id="update" class="btn btn-update">
                    <i class="icon-edit"></i>
                    修改
                </a>
                </shiro:hasPermission>
                <shiro:hasPermission name="maintain:dynamicTask:delete">
                <a class="btn btn-custom btn-delete">
                    <i class="icon-trash"></i>
                    删除
                </a>
                </shiro:hasPermission>

                <shiro:hasPermission name="maintain:dynamicTask:update">
                    <a class="btn btn-custom btn-start">
                        <i class="icon-trash"></i>
                        启动任务
                    </a>
                </shiro:hasPermission>

                <shiro:hasPermission name="maintain:dynamicTask:update">
                    <a class="btn btn-custom btn-stop">
                        <i class="icon-trash"></i>
                        停止任务
                    </a>
                </shiro:hasPermission>
            </div>
        </div>
        <div class="span8">
        </div>
    </div>
    <%@include file="listTable.jsp"%>

</div>

<es:contentFooter/>
<script type="text/javascript">
    $(function() {
        $(".btn-delete").click(function() {
            var checkbox = $.table.getAllSelectedCheckbox($("#table"));
            if(!checkbox.length) {
                return;
            }

            $.app.confirm({
                title : "确认删除",
                message: "<div class='form-inline'>是否强制终止正在运行的任务：<label class='checkbox inline'><input type='radio' name='forceTermination' checked='true' value='true'>是</label>&nbsp;&nbsp;<label class='checkbox inline'><input type='radio' name='forceTermination' value='false'>否</label></div>",
                ok : function() {
                    var forceTermination = $("[name=forceTermination]:checked").val();
                    location.href = ctx + '/admin/maintain/dynamicTask/batch/delete?' + checkbox.serialize() + "&forceTermination=" + forceTermination;
                }
            });
        });

        $(".btn-start").click(function() {
            var checkbox = $.table.getAllSelectedCheckbox($("#table"));
            if(!checkbox.length) {
                return;
            }

            $.app.confirm({
                title : "启动任务",
                message: "确认启动选中任务吗？",
                ok : function() {
                    var forceTermination = $("[name=forceTermination]:checked").val();
                    location.href = ctx + '/admin/maintain/dynamicTask/start?' + checkbox.serialize();
                }
            });
        });

        $(".btn-stop").click(function() {
            var checkbox = $.table.getAllSelectedCheckbox($("#table"));
            if(!checkbox.length) {
                return;
            }

            $.app.confirm({
                title : "停止任务",
                message: "<div class='form-inline'>是否强制终止正在运行的任务：<label class='checkbox inline'><input type='radio' name='forceTermination' checked='true' value='true'>是</label>&nbsp;&nbsp;<label class='checkbox inline'><input type='radio' name='forceTermination' value='false'>否</label></div>",
                ok : function() {
                    var forceTermination = $("[name=forceTermination]:checked").val();
                    location.href = ctx + '/admin/maintain/dynamicTask/stop?' + checkbox.serialize() + "&forceTermination=" + forceTermination;
                }
            });
        });

    });
</script>