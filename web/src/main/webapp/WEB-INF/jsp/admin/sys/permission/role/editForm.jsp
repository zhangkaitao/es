<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jspf"%>
<es:contentHeader/>
<div class="panel">

    <ul class="nav nav-tabs">
        <c:if test="${op eq '新增'}">
            <li <c:if test="${op eq '新增'}">class="active"</c:if>>
                <a href="${ctx}/admin/sys/permission/role/create?BackURL=<es:BackURL/>">
                    <i class="icon-file"></i>
                    新增
                </a>
            </li>
        </c:if>

        <c:if test="${not empty m.id}">
            <li <c:if test="${op eq '查看'}">class="active"</c:if>>
                <a href="${ctx}/admin/sys/permission/role/${m.id}?BackURL=<es:BackURL/>">
                    <i class="icon-eye-open"></i>
                    查看
                </a>
            </li>
            <li <c:if test="${op eq '修改'}">class="active"</c:if>>
                <a href="${ctx}/admin/sys/permission/role/update/${m.id}?BackURL=<es:BackURL/>">
                    <i class="icon-edit"></i>
                    修改
                </a>
            </li>
            <li <c:if test="${op eq '删除'}">class="active"</c:if>>
                <a href="${ctx}/admin/sys/permission/role/delete/${m.id}?BackURL=<es:BackURL/>">
                    <i class="icon-trash"></i>
                    删除
                </a>
            </li>
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

            <div class="control-group span3">
                <form:label path="name" cssClass="control-label">角色名称</form:label>
                <div class="controls">
                    <form:input path="name" cssClass="validate[required] input-medium" placeholder="角色描述名"/>
                </div>
            </div>

            <div class="control-group span3">
                <form:label path="role" cssClass="control-label">角色标识</form:label>
                <div class="controls">
                    <form:input path="role" cssClass="validate[required] input-medium" placeholder="程序中使用的名称"/>
                </div>
            </div>

            <div class="control-group span3">
                <form:label path="description" cssClass="control-label">详细描述</form:label>
                <div class="controls">
                    <form:input path="description"/>
                </div>
            </div>
        </div>
        <div id="permissionInfo">
            <h4 class="hr">授权信息</h4>

            <div id="resource">

            </div>

            <div id="permission">

            </div>

        </div>

            <div class="clearfix"></div>

            <c:if test="${op eq '新增'}">
                <c:set var="icon" value="icon-file"/>
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
<script type="text/javascript">
    $(function () {
        <c:choose>
            <c:when test="${op eq '删除'}">
                //删除时不验证 并把表单readonly
                $.app.readonlyForm($("#editForm"), false);
            </c:when>
            <c:when test="${op eq '查看'}">
                $.app.readonlyForm($("#editForm"), true);
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
    });
</script>