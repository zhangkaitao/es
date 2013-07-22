<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jspf"%>
<es:contentHeader/>
<%@include file="/WEB-INF/jsp/common/import-zTree-css.jspf"%>
<style>
    #resourceInfo, #appendResourcePermissionBtn {
        padding-top: 20px;
    }

</style>
<div class="panel">

    <ul class="nav nav-tabs">
        <shiro:hasPermission name="sys:role:create">
        <c:if test="${op eq '新增'}">
            <li ${op eq '新增' ? 'class="active"' : ''}>
                <a href="${ctx}/admin/sys/permission/role/create?BackURL=<es:BackURL/>">
                    <i class="icon-file-alt"></i>
                    新增
                </a>
            </li>
        </c:if>
        </shiro:hasPermission>
        <c:if test="${not empty m.id}">
            <li ${op eq '查看' ? 'class="active"' : ''}>
                <a href="${ctx}/admin/sys/permission/role/${m.id}?BackURL=<es:BackURL/>">
                    <i class="icon-eye-open"></i>
                    查看
                </a>
            </li>
            <shiro:hasPermission name="sys:role:update">
            <li ${op eq '修改' ? 'class="active"' : ''}>
                <a href="${ctx}/admin/sys/permission/role/${m.id}/update?BackURL=<es:BackURL/>">
                    <i class="icon-edit"></i>
                    修改
                </a>
            </li>
            </shiro:hasPermission>
            <shiro:hasPermission name="sys:role:delete">
            <li ${op eq '删除' ? 'class="active"' : ''}>
                <a href="${ctx}/admin/sys/permission/role/${m.id}/delete?BackURL=<es:BackURL/>">
                    <i class="icon-trash"></i>
                    删除
                </a>
            </li>
            </shiro:hasPermission>
        </c:if>
        <li>
            <a href="<es:BackURL/>" class="btn btn-link">
                <i class="icon-reply"></i>
                返回
            </a>
        </li>
    </ul>

    <form:form id="editForm" method="post" commandName="m" cssClass="form-inline form-horizontal form-small">

        <es:showGlobalError commandName="m"/>

        <form:hidden path="id"/>

        <div id="roleInfo">
            <h4 class="hr">基本信息</h4>

            <div class="control-group span4">
                <form:label path="name" cssClass="control-label">角色名称</form:label>
                <div class="controls">
                    <form:input path="name" cssClass="validate[required]" placeholder="角色描述名"/>
                </div>
            </div>

            <div class="control-group span4">
                <form:label path="role" cssClass="control-label">角色标识</form:label>
                <div class="controls">
                    <form:input path="role" cssClass="validate[required]" placeholder="程序中使用的名称"/>
                </div>
            </div>

            <div class="control-group span4">
                <form:label path="description" cssClass="control-label">详细描述</form:label>
                <div class="controls">
                    <form:input path="description"/>
                </div>
            </div>


            <div class="control-group span4">
                <form:label path="show" cssClass="control-label">状态</form:label>
                <div class="controls inline-radio">
                    <form:radiobuttons
                            path="show" items="${availableList}" itemLabel="info" itemValue="value" cssClass="validate[required]"/>
                </div>
            </div>


        </div>
        <div id="resourcePermissionInfo" class="span10">
            <h4 class="hr">授权信息</h4>
            <div id="selectResourcePermission">
                <div class="muted font-12" style="margin: 10px auto;">
                    请选择授权的角色
                </div>
                <div id="resourceInfo" class="span3">
                    <label>资源</label>&nbsp;
                    <div class="input-append" title="选择资源">
                        <input type="hidden" id="resourceId" value="${target.id}">
                        <input type="text" id="resourceName" class="input-medium" value="${target.name}"
                               readonly="readonly">
                        <a id="selectResourceTree" href="javascript:;">
                            <span class="add-on"><i class="icon-chevron-down"></i></span>
                        </a>
                    </div>
                </div>

                <div id="permissionInfo" class="span4">
                    <label>权限</label>&nbsp;
                    <select id="permissions" multiple="multiple">
                        <c:forEach items="${permissions}" var="p">
                            <option value="${p.id}" title="${p.description}">${p.name}&nbsp;&nbsp;(${p.permission})</option>
                        </c:forEach>
                    </select>
                </div>

                <div id="appendResourcePermissionBtn" class="control-group span2">
                    &nbsp;
                    <a class="btn btn-warning btn-add-resource-permission">
                        <i class="icon-file-alt"></i>
                        添加
                    </a>
                </div>
                <div class="muted font-12" style="margin: 10px auto;float: left;width: 100%;">
                    注意：添加的数据是临时的，还需要点击页面下方的[新增/修改]保存该数据
                </div>
            </div>

            <div id="selectedResourcePermssion" class="span9">
                <div class="muted font-12" style="margin: 10px auto;">
                    已选择的资源和权限
                    &nbsp;&nbsp;&nbsp;&nbsp;
                    <a class="btn btn-link btn-delete-all-resource-permission">删除选中</a>
                </div>
                <table class="table table-bordered table-hover">
                    <thead>
                    <tr>
                        <th style="width: 80px">
                            <a class="check-all" href="javascript:;">全选</a>
                            |
                            <a class="reverse-all" href="javascript:;">反选</a>
                        </th>
                        <th>资源</th>
                        <th>权限</th>
                        <th style="width: 20px;">&nbsp;</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${m.resourcePermissions}" var="o">
                    <c:if test="${esfn:existsResource(o.resourceId, onlyDisplayShow)}">
                    <tr>
                        <td class="check"><input type="checkbox"></td>
                        <td>
                            <input type='hidden' id='resourceId_${o.resourceId}' name='resourceId' value='${o.resourceId}'>
                            <sys:showResourceName id="${o.resourceId}"/>
                        </td>
                        <td>
                            <span style="line-height: 30px">
                            <c:set var="permissionIds" value=""/>
                            <c:set var="count" value="0"/>
                            <c:forEach items="${o.permissionIds}" var="permissionId">
                            <c:if test="${esfn:existsPermission(permissionId, onlyDisplayShow)}">
                                <c:set var="count" value="${count+1}"/>
                                <c:set var="permissionIds" value="${permissionIds}${count == 1 ? '' : ','}${permissionId}"/>
                                <c:if test="${status.count > 1}">|</c:if>
                                <sys:showPermissionName id="${permissionId}"/>
                            </c:if>
                            </c:forEach>
                            </span>
                            <input type='hidden' name='permissionIds' value='${permissionIds}'>
                        </td>
                        <td>
                            <a class='btn btn-link btn-edit btn-delete-resource-permission' href='javascript:;'
                               onclick='removeResourcePermission(this);'>
                                <i class='icon-trash'></i>
                            </a>
                        </td>
                    </tr>
                    </c:if>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>

            <div class="clearfix"></div>

            <c:if test="${op eq '新增'}">
                <c:set var="icon" value="icon-file-alt"/>
            </c:if>
            <c:if test="${op eq '修改'}">
                <c:set var="icon" value="icon-edit"/>
            </c:if>
            <c:if test="${op eq '删除'}">
                <c:set var="icon" value="icon-trash"/>
            </c:if>

            <div class="control-group left-group">
                <div>
                    <button type="submit" class="btn btn-primary">
                        <i class="${icon}"></i>
                            ${op}
                    </button>
                    <a href="<es:BackURL/>" class="btn">
                        <i class="icon-reply"></i>
                        返回
                    </a>
                </div>
            </div>


    </form:form>
</div>
<es:contentFooter/>
<%@include file="/WEB-INF/jsp/common/import-zTree-js.jspf"%>

<script type="text/javascript">
    $(function () {
        <c:choose>
            <c:when test="${op eq '删除'}">
                //删除时不验证 并把表单readonly
                $.app.readonlyForm($("#editForm"), false);
                $("#selectResourcePermission").remove();
                $(".btn-delete-resource-permission").remove();
                $(".btn-delete-all-resource-permission").remove();
            </c:when>
            <c:when test="${op eq '查看'}">
                $.app.readonlyForm($("#editForm"), true);
                $("#selectResourcePermission").remove();
                $(".btn-delete-resource-permission").remove();
                $(".btn-delete-all-resource-permission").remove();
            </c:when>
            <c:otherwise>
                //自定义ajax验证  ajax[ajaxNameCall] 放到验证规则的最后（放到中间只有当submit时才验证）
                $.validationEngineLanguage.allRules.ajaxNameCall= {
                    "url": "${ctx}/admin/sys/permission/role/validate",
                    //动态提取的数据。验证时一起发送
                    extraDataDynamic : ['#id'],
                    //验证失败时的消息
                    //"alertText": "* 该名称已被其他人使用",
                    //验证成功时的消息
                    //"alertTextOk": "该名称可以使用",
                    "alertTextLoad": "* 正在验证，请稍等。。。"
                };
                $.validationEngineLanguage.allRules.username={
                    "regex": /^\w{5,10}$/,
                    "alertText": "* 5到10个字母、数字、下划线"
                };
                var validationEngine = $("#editForm").validationEngine();
                <es:showFieldError commandName="m"/>
            </c:otherwise>
        </c:choose>



        var resourceTreeId = $.zTree.initSelectTree({
            zNodes : [],
            nodeType : "default",
            fullName:true,
            urlPrefix : "${ctx}/admin/sys/resource",
            async : true,
            asyncLoadAll : true,
            onlyDisplayShow: true,
            lazy : true,
            select : {
                btn : $("#selectResourceTree,#resourceName"),
                id : "resourceId",
                name : "resourceName",
                includeRoot: false
            },
            autocomplete : {
                enable : true
            },
            setting :{
                check : {
                    enable:true,
                    chkStyle:"checkbox",
                    onlyCheckLeaf : false,
                    onlySelectLeaf : true
                }
            }
        });


        $(".btn-add-resource-permission").click(function() {

            var resourceIds = $("#resourceId").val().split(",");
            var resourceNames = $("#resourceName").val().split(",");
            var $selectedPermissions = $("#permissions option:selected");
            var permissionIds = $selectedPermissions.map(function() {return this.value;}).get().join(",");
            var permissionNames = $selectedPermissions.map(function() {return this.innerText;}).get().join(",");

            if(!resourceIds || !resourceIds.length || !resourceIds[0]) {
                $.app.alert({
                    message : "请选择资源"
                });
                return;
            }
            for(var index in resourceIds) {
                var resourceId = resourceIds[index];
                if(!resourceId) {
                    continue;
                }
                if($("#resourceId_" + resourceId).size() > 0) {
                    $.app.alert({
                        message : "您已经选择了此资源，不能重复选择！"
                    });
                    return;
                }
            }

            if(!permissionIds) {
                $.app.alert({
                    message : "请选择权限"
                });
                return;
            }

            var template =
                    "<tr>" +
                            "<td class='check'><input type='checkbox'></td>" +
                            "<td>" +
                            "<input type='hidden' id='resourceId_{resourceId}' name='resourceId' value='{resourceId}'>" +
                            "{resourceName}" +
                            "</td>" +
                            "<td>" +
                            "<input type='hidden' name='permissionIds' value='{permissionIds}'>" +
                            "{permissionNames}" +
                            "</td>" +
                            "<td>" +
                            "<a class='btn btn-link btn-edit btn-delete-resource-permission' href='javascript:;' onclick='removeResourcePermission(this);'><i class='icon-trash'></i></a>" +
                            "</td>" +
                            "</tr>";

            for(var index in resourceIds) {
                var resourceId = resourceIds[index];
                var resourceName = resourceNames[index];
                if(!resourceId) {
                    continue;
                }

                $("#selectedResourcePermssion .table tbody").append(
                        template.replace(/{resourceId}/g, resourceId)
                                .replace("{resourceName}", resourceName.replace(",", "<br/>").replace(">", "&gt;"))
                                .replace("{permissionIds}", permissionIds)
                                .replace("{permissionNames}", permissionNames)
                );
                //更新表格的全选反选
                $.table.initCheckbox($("#selectedResourcePermssion table"));
            };
            $.fn.zTree.getZTreeObj(resourceTreeId).checkAllNodes(false);
            $("#resourceId,#resourceName").val("");
            $selectedPermissions.attr("selected", false);
        });

        $.table.initCheckbox($("#selectedResourcePermssion table"));

        $(".btn-delete-all-resource-permission").click(function() {
            var checkbox = $.table.getAllSelectedCheckbox($("#selectedResourcePermssion table"));
            if(!checkbox.length) {
                return;
            }
            $.app.confirm({
                title : "确认删除授权资源和权限",
                message : "确认删除授权的资源和权限吗？",
                ok : function() {
                    checkbox.each(function() {
                        $(this).closest("tr").remove();
                    });
                }
            });

        });

    });

    function removeResourcePermission(a) {
        $.app.confirm({
            message : "确认删除吗？",
            ok : function() {
                $(a).closest('tr').remove();
            }
        });

    }


</script>