<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jspf"%>
<es:contentHeader/>
<div class="panel">

    <ul class="nav nav-tabs">
        <shiro:hasPermission name="sys:auth:create">
        <c:if test="${op eq '新增'}">
            <c:forEach items="${types}" var="t">
                <li ${t eq type ? 'class="active"' : ''}>
                    <a href="${ctx}/admin/sys/auth/${t}/create?BackURL=<es:BackURL/>">
                        <i class="icon-file-alt"></i>
                        新增${t.info}授权
                    </a>
                </li>
            </c:forEach>
        </c:if>
        </shiro:hasPermission>

        <c:if test="${not empty m.id}">
            <li ${op eq '查看' ? 'class="active"' : ''}>
                <a href="${ctx}/admin/sys/auth/${m.id}?BackURL=<es:BackURL/>">
                    <i class="icon-eye-open"></i>
                    查看
                </a>
            </li>
            <shiro:hasPermission name="sys:auth:update">
            <li ${op eq '修改' ? 'class="active"' : ''}>
                <a href="${ctx}/admin/sys/auth/${m.id}/update?BackURL=<es:BackURL/>">
                    <i class="icon-edit"></i>
                    修改
                </a>
            </li>
            </shiro:hasPermission>
            <shiro:hasPermission name="sys:auth:delete">
            <li ${op eq '删除' ? 'class="active"' : ''}>
                <a href="${ctx}/admin/sys/auth/${m.id}/delete?BackURL=<es:BackURL/>">
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

    <form:form id="editForm" method="post" commandName="m" cssClass="form-inline form-horizontal form-medium">

        <es:showGlobalError commandName="m"/>
        <form:hidden path="id"/>
        <form:hidden path="type"/>

        <c:set var="type" value="${not empty type ? type : m.type}"/>

        <h4 class="hr">${type.info}信息</h4>

        <c:if test="${op ne '新增'}">
            <c:if test="${type eq 'user'}">
                <div class="control-group">
                    <label class="control-label">
                        用户
                    </label>
                    <div class="controls" style="padding-top: 3px;">
                        <sys:showUsername id="${m.userId}"/>
                        <form:hidden path="userId"/>
                    </div>
                </div>
            </c:if>
            <c:if test="${type eq 'user_group'}">
                <div class="control-group">
                    <label class="control-label">
                        用户分组
                    </label>
                    <div class="controls" style="padding-top: 3px;">
                        <sys:showGroupName id="${m.groupId}"/>
                        <form:hidden path="groupId"/>
                    </div>
                </div>
            </c:if>
            <c:if test="${type eq 'organization_group'}">
                <div class="control-group">
                    <label class="control-label">
                        组织机构分组
                    </label>
                    <div class="controls" style="padding-top: 3px;">
                        <sys:showGroupName id="${m.groupId}"/>
                    </div>
                </div>
            </c:if>
            <c:if test="${type eq 'organization_job'}">
                <div class="control-group">
                    <label class="control-label">
                        组织机构
                    </label>
                    <div class="controls" style="padding-top: 3px;">
                        <sys:showOrganizationName id="${m.organizationId}"/>
                        <form:hidden path="organizationId"/>
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label">
                        工作职务
                    </label>
                    <div class="controls" style="padding-top: 3px;">
                        <sys:showJobName id="${m.jobId}"/>
                        <form:hidden path="jobId"/>
                    </div>
                </div>
            </c:if>
        </c:if>


        <c:if test="${op eq '新增'}">
            <c:if test="${type eq 'user'}">
                <div class="control-group">
                    <label for="userIds" class="control-label">
                        用户编号(批量)<br/>
                        <span class="muted">多个逗号分隔</span>
                    </label>
                    <div class="controls">
                        <div style="float: left;">
                            <textarea id="userIds" name="userIds" rows="3" cols="20" class="validate[required]" placeholder="请输入用户编号，多个逗号分隔"></textarea>
                        </div>
                        <div id="search-username" class="accordion-body collapse" style="float: left;margin-left: 20px">
                            <input type="text" id="username" class="input-medium" placeholder="输入用户名 检索用户编码">
                        </div>

                        <span style="margin-left: 20px;display: inline-block"
                              data-toggle="tooltip" data-placement="bottom" data-html="true"
                              title="根据用户名检索用户编号！<br/>检索到的用户编号会自动添加到【用户编号(批量)】中">
                            <a class="btn btn-link" data-toggle="collapse" href="#search-username">
                                <i class="icon-search"></i>
                            </a>
                        </span>
                    </div>
                </div>
            </c:if>

            <c:if test="${type eq 'user_group' or type eq 'organization_group'}">
                <div class="control-group">
                    <label for="groupIds" class="control-label">分组编号(批量)</label>
                    <div class="controls">
                        <div style="float: left;">
                            <textarea id="groupIds" name="groupIds" rows="3" cols="20" class="validate[required]" placeholder="请输入分组编号，多个逗号分隔"></textarea>
                        </div>
                        <div id="search-groupName" class="accordion-body collapse" style="float: left;margin-left: 20px">
                            <input type="text" id="groupName" class="input-medium" placeholder="输入分组名称 检索分组编码">
                        </div>

                        <span style="margin-left: 20px;display: inline-block"
                              data-toggle="tooltip" data-placement="bottom" data-html="true"
                              title="根据分组名检索分组编号！<br/>检索到的分组编号会自动添加到【分组编号(批量)】中">
                            <a class="btn btn-link" data-toggle="collapse" href="#search-groupName">
                                <i class="icon-search"></i>
                            </a>
                        </span>
                    </div>
                </div>
            </c:if>

            <c:if test="${type eq 'organization_job'}">
                <c:set var="noOrganizationAndJobTitle" value="true"/>
                <%@include file="/WEB-INF/jsp/admin/sys/organization/selectOrganizationAndJob.jspf"%>
            </c:if>
        </c:if>

        <br/>

        <h4 class="hr">角色信息</h4>
        <div class="control-group">
            <label class="control-label">角色列表</label>
            <div class="controls">
                <div class="auth">
                    <div class="left">
                        <div class="title muted">未选择的角色列表</div>
                        <div class="list">
                            <ul>
                                <c:forEach items="${roles}" var="r">
                                    <c:if test="${not esfn:in(m.roleIds, r.id)}">
                                        <li class="ui-state-default" data-value="${r.id}" title="${r.description}">
                                            ${r.name}[${r.role}]
                                        </li>
                                    </c:if>
                                </c:forEach>
                            </ul>
                        </div>
                    </div>
                    <div class="btns">
                        <a class="btn btn-link btn-move-all-right" data-toggle="tooltip" data-placement="bottom" title="全部右移">
                            <i class="icon-double-angle-right"></i>
                        </a>
                        <a class="btn btn-link btn-move-all-left" data-toggle="tooltip" data-placement="bottom" title="全部左移">
                            <i class="icon-double-angle-left"></i>
                        </a>
                    </div>
                    <div class="right" id="roleIds_msg" data-prompt-position="topLeft">
                        <div class="title muted">已选择的角色列表</div>
                        <div class="list">
                            <ul data-input-id="roleIds">
                                <c:forEach items="${roles}" var="r">
                                    <c:if test="${esfn:in(m.roleIds, r.id)}">
                                        <li class="ui-state-default" data-value="${r.id}" title="${r.description}">
                                            ${r.name}[${r.role}]
                                        </li>
                                    </c:if>
                                </c:forEach>
                            </ul>
                        </div>
                        <form:hidden path="roleIds" cssClass="validate[required]"/>
                    </div>

                </div>
            </div>
        </div>

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
<%@include file="/WEB-INF/jsp/common/import-zTree-css.jspf"%>
<%@include file="/WEB-INF/jsp/common/import-zTree-js.jspf"%>
<%@include file="/WEB-INF/jsp/common/admin/import-sys-js.jspf"%>
<script type="text/javascript">
    $(function () {
        <c:choose>
            <c:when test="${op eq '删除'}">
                //删除时不验证 并把表单readonly
                $.app.readonlyForm($("#editForm"), false);
                $(".auth > .left,.auth > .btns").remove();
            </c:when>
            <c:when test="${op eq '查看'}">
                $.app.readonlyForm($("#editForm"), true);
                $(".auth > .left,.auth > .btns").remove();
            </c:when>
            <c:otherwise>
                var validationEngine = $("#editForm").validationEngine({prettySelect:true, useSuffix : "_msg"});
                <es:showFieldError commandName="m"/>

                $.sys.auth.initSelectRoleForm();

            </c:otherwise>
        </c:choose>

        <c:if test="${op eq '新增'}">


            <c:if test="${type eq 'user'}">
                var $username = $("#username");
                $.app.initAutocomplete({
                    input : $username,
                    source : "${ctx}/admin/sys/user/ajax/autocomplete",
                    select : function(event, ui) {
                        var $ids = $("#userIds");
                        var ids = $ids.val();
                        ids = (ids ? ids : "") + (ids ? "," : "") + ui.item.value;
                        $ids.val(ids);
                        $username.val("");
                        return false;
                    }
                });
            </c:if>


            <c:if test="${type eq 'user_group' or type eq 'organization_group'}">
                var $groupName = $("#groupName");
                var groupType = '${type eq 'user_group' ? 'user' : 'organization'}';
                $.app.initAutocomplete({
                    input : groupName,
                    source : "${ctx}/admin/sys/group/ajax/autocomplete?search.type_eq=" + groupType,
                    select : function(event, ui) {
                        var $ids = $("#groupIds");
                        var ids = $ids.val();
                        ids = (ids ? ids : "") + (ids ? "," : "") + ui.item.value;
                        $ids.val(ids);
                        $groupName.val("");
                        return false;
                    }
                });
            </c:if>

            <c:if test="${type eq 'organization_job'}">
                $.sys.organization.initSelectForm("organizationIds", "jobIds", true);

                $("#editForm").submit(function() {
                    var selectedOrganizationLength = $("#selectedOrganization > table > tbody > tr").length;
                    if(!selectedOrganizationLength) {
                        $("#organizationName").validationEngine('showPrompt', '必须选择一个组织机构', null, null, true);
                        return false;
                    } else {
                        $("#organizationName").validationEngine("hide");
                        return true;
                    }
                });
            </c:if>
        </c:if>

    });
</script>