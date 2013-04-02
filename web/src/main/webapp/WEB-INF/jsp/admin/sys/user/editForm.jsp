<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jspf"%>
<es:contentHeader/>

<div class="panel">
    <ul class="nav nav-tabs">
        <c:if test="${op eq '新增'}">
        <li <c:if test="${op eq '新增'}">class="active"</c:if>>
            <a href="${ctx}/admin/sys/user/create?BackURL=<es:BackURL/>">
                <i class="icon-file"></i>
                新增
            </a>
        </li>
        </c:if>

        <c:if test="${not empty m.id}">
        <li <c:if test="${op eq '查看'}">class="active"</c:if>>
            <a href="${ctx}/admin/sys/user/${m.id}?BackURL=<es:BackURL/>">
                <i class="icon-eye-open"></i>
                查看
            </a>
        </li>
        <li <c:if test="${op eq '修改'}">class="active"</c:if>>
            <a href="${ctx}/admin/sys/user/update/${m.id}?BackURL=<es:BackURL/>">
                <i class="icon-edit"></i>
                修改
            </a>
        </li>
        <li <c:if test="${op eq '删除'}">class="active"</c:if>>
            <a href="${ctx}/admin/sys/user/delete/${m.id}?BackURL=<es:BackURL/>">
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
    <form:form id="editForm" method="post" commandName="m" cssClass="form-horizontal">

            <es:showGlobalError commandName="m"/>

            <form:hidden path="id"/>
            <form:hidden path="deleted"/>


            <div class="control-group">
                <form:label path="username" cssClass="control-label">用户名</form:label>
                <div class="controls">
                    <form:input path="username" cssClass="validate[required,custom[username],ajax[ajaxUsernameCall]]" placeholder="5到20个汉字、字母、数字或下划线"/>
                </div>
            </div>

            <div class="control-group">
                <form:label path="email" cssClass="control-label">邮箱</form:label>
                <div class="controls">
                    <form:input path="email" cssClass="validate[required,custom[email],ajax[ajaxEmailCall]]" placeholder="如zhang@163.com"/>
                </div>
            </div>
            <div class="control-group">
                <form:label path="mobilePhoneNumber" cssClass="control-label">手机号</form:label>
                <div class="controls">
                    <form:input path="mobilePhoneNumber" cssClass="validate[required,custom[mobilePhoneNumber],ajax[ajaxMobilePhoneNumberCall]]" placeholder="如13512345678"/>
                </div>
            </div>


            <c:choose>
                <c:when test="${op eq '新增'}">
                    <div class="control-group">
                        <form:label path="password" cssClass="control-label">初始密码</form:label>
                        <div class="controls">
                            <form:password path="password" cssClass="validate[required,minSize[5],maxSize[100]]" placeholder="请输入至少5位的初始密码"/>
                        </div>
                    </div>
                </c:when>
                <c:otherwise>
                    <form:hidden path="password"/>
                </c:otherwise>
            </c:choose>

            <div class="control-group">
                <form:label path="createDate" cssClass="control-label">创建时间</form:label>
                <div class="controls input-append date">
                    <form:input path="createDate" data-format="yyyy-MM-dd hh:mm:ss" placeholder="默认当前时间"/>
                    <span class="add-on"><i data-time-icon="icon-time" data-date-icon="icon-calendar"></i></span>
                </div>
            </div>

            <div class="control-group">
                <form:label path="status" cssClass="control-label">状态</form:label>
                <div class="controls inline-radio">
                    <form:radiobuttons path="status" items="${statusList}" itemLabel="info" cssClass="validate[required]"/>
                </div>
            </div>

            <div class="control-group">
                <form:label path="admin" cssClass="control-label">是否管理员</form:label>
                <div class="controls inline-radio">
                    <form:radiobuttons path="admin" items="${booleanList}" itemLabel="info" itemValue="value" cssClass="validate[required]"/>
                </div>
            </div>


        <c:if test="${op eq '新增'}">
            <c:set var="icon" value="icon-file"/>
        </c:if>
        <c:if test="${op eq '修改'}">
            <c:set var="icon" value="icon-edit"/>
        </c:if>
        <c:if test="${op eq '删除'}">
            <c:set var="icon" value="icon-trash"/>
        </c:if>

        <div class="control-group">
            <div class="controls">
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
<%@include file="include/import-js.jspf"%>
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
                initValidator($("#editForm"));
                <es:showFieldError commandName="m"/>
            </c:otherwise>
        </c:choose>

    });
</script>