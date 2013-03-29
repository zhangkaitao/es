<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jspf"%>
<es:contentHeader/>
<div class="panel">

    <ul class="nav nav-tabs">
        <li <c:if test="${op eq '新增'}">class="active"</c:if>>
            <a href="${ctx}/admin/sys/user/create?BackURL=<es:BackURL/>">新增</a>
        </li>
        <c:if test="${not empty m.id}">
        <li <c:if test="${op eq '查看'}">class="active"</c:if>>
            <a href="${ctx}/admin/sys/user/${m.id}?BackURL=<es:BackURL/>">查看</a>
        </li>
        <li <c:if test="${op eq '修改'}">class="active"</c:if>>
            <a href="${ctx}/admin/sys/user/update/${m.id}?BackURL=<es:BackURL/>">修改</a>
        </li>
        <li <c:if test="${op eq '删除'}">class="active"</c:if>>
            <a href="${ctx}/admin/sys/user/delete/${m.id}?BackURL=<es:BackURL/>">删除</a>
        </li>
        </c:if>
        <li>
            <a href="<es:BackURL/>">返回列表</a>
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
                    <form:input path="createDate" data-format="yyyy-MM-dd hh:mm:ss" placeholder="默认当前时间" readonly="true"/>
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

            <div class="control-group">
                <div class="controls">
                    <input type="submit" class="btn btn-primary" value="${op}">
                    <a href="<es:BackURL/>" class="btn">返回</a>
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
                $("#editForm :input").not(":submit,:button").prop("readonly", true);
            </c:when>
            <c:when test="${op eq '查看'}">
                $("#editForm :input").prop("readonly", true).remove(":button,:submit");
            </c:when>
            <c:otherwise>
                var ajaxCall = {
                    "url": "${ctx}/admin/sys/user/validate",
                    //动态提取的数据。验证时一起发送
                    extraDataDynamic : ['#id'],
                    //验证失败时的消息
                    //"alertText": "* 该名称已被其他人使用",
                    //验证成功时的消息
                    //"alertTextOk": "该名称可以使用",
                    "alertTextLoad": "* 正在验证，请稍等。。。"
                };
                //自定义ajax验证  ajax[ajaxNameCall] 放到验证规则的最后（放到中间只有当submit时才验证）
                //不能合并到一个 否则提交表单时有个黑屏阶段
                $.validationEngineLanguage.allRules.ajaxUsernameCall=ajaxCall;
                $.validationEngineLanguage.allRules.ajaxEmailCall=ajaxCall;
                $.validationEngineLanguage.allRules.ajaxMobilePhoneNumberCall=ajaxCall;
                $.validationEngineLanguage.allRules.username={
                    "regex": /^[\u4E00-\u9FA5\uf900-\ufa2d_a-zA-Z][\u4E00-\u9FA5\uf900-\ufa2d\w]{4,19}$/,
                    "alertText": "* 5到20个汉字、字母、数字或下划线组成，且必须以非数字开头"
                };
                $.validationEngineLanguage.allRules.mobilePhoneNumber={
                    "regex": /^0{0,1}(13[0-9]|15[7-9]|153|156|18[7-9])[0-9]{8}$/,
                    "alertText": "* 手机号错误"
                };
                var validationEngine = $("#editForm").validationEngine();
                <es:showFieldError commandName="m"/>
            </c:otherwise>
        </c:choose>

    });
</script>