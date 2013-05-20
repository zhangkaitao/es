<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jspf"%>
<es:contentHeader/>

<div class="panel">
    <%@include file="nav.jspf"%>

    <form:form id="editForm" method="post" commandName="user" cssClass="form-horizontal">
        <form:hidden path="id"/>
        <c:if test="${op eq '查看个人资料'}">
            <h4 class="hr">个人资料</h4>
        </c:if>

        <div class="control-group">
            <form:label path="username" cssClass="control-label">用户名</form:label>
            <div class="controls">
                <form:input path="username" cssClass="validate[required,custom[username],ajax[ajaxCall]]" placeholder="5到20个汉字、字母、数字或下划线"/>
            </div>
        </div>

        <div class="control-group">
            <form:label path="email" cssClass="control-label">邮箱</form:label>
            <div class="controls">
                <form:input path="email" cssClass="validate[required,custom[email],ajax[ajaxCall]]" placeholder="如zhang@163.com"/>
            </div>
        </div>
        <div class="control-group">
            <form:label path="mobilePhoneNumber" cssClass="control-label">手机号</form:label>
            <div class="controls">
                <form:input path="mobilePhoneNumber" cssClass="validate[required,custom[mobilePhoneNumber],ajax[ajaxCall]]" placeholder="如13512345678"/>
            </div>
        </div>

        <c:if test="${op eq '查看个人资料'}">
            <div class="control-group">
                <form:label path="createDate" cssClass="control-label">创建时间</form:label>
                <div class="controls input-append">
                    <form:input path="createDate"/>
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
        </c:if>

        <c:if test="${op eq '查看个人资料'}">
            <br/>
            <h4 class="hr">组织机构和工作职务</h4>
            <div class="span8">
                <c:set var="onlyDisplayShow" value="true"/>
                <%@include file="../organizations.jspf"%>
            </div>
        </c:if>
        <div class="clearfix"></div>
        <c:if test="${op eq '查看个人资料' and not empty lastOnline}">
            <br/>
            <h4 class="hr">上次登录历史</h4>
            <div class="control-group">
                <label class="control-label">上次登录IP</label>
                <div class="controls">
                    <input type="text" value="${lastOnline.host}">
                </div>
            </div>
            <div class="control-group">
                <label class="control-label">上次登录时间</label>
                <div class="controls">
                    <input type="text" value="<spring:eval expression='lastOnline.lastLoginTimestamp'/>">
                </div>
            </div>
            <div class="control-group">
                <label class="control-label">上次退出时间</label>
                <div class="controls">
                    <input type="text" value="<spring:eval expression='lastOnline.lastStopTimestamp'/>">
                </div>
            </div>
            <div class="control-group">
                <label class="control-label">登录次数</label>
                <div class="controls">
                    <input type="text" value="${lastOnline.loginCount}">
                </div>
            </div>
            <div class="control-group">
                <label class="control-label">总在线时长</label>
                <div class="controls">
                    <input type="text" value="<time:prettySecond seconds="${lastOnline.totalOnlineTime}"/>"/>
                </div>
            </div>
        </c:if>

        <c:if test="${op eq '修改个人资料'}">
            <c:set var="icon" value="icon-edit"/>
        </c:if>

        <div class="control-group">
            <div class="controls">
                <button type="submit" class="btn btn-primary">
                    <i class="${icon}"></i>
                        ${op}
                </button>
            </div>
        </div>
    </form:form>
</div>
<es:contentFooter/>
<%@include file="/WEB-INF/jsp/common/admin/import-sys-js.jspf"%>
<script type="text/javascript">
    $(function () {
        $("#editForm").find(":checkbox,:radio").filter(":not(:checked)").next("label").andSelf().remove();
        $("#username,#createDate,#status,#admin").attr("disabled", true);
        <c:choose>
        <c:when test="${op eq '查看个人资料'}">
        $.app.readonlyForm($("#editForm"), true);
        </c:when>
        <c:when test="${op eq '修改个人资料'}">
        $.sys.user.initValidator($("#editForm"));
        <es:showFieldError commandName="m"/>
        </c:when>
        </c:choose>

    });
</script>
