<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jspf"%>
<es:contentHeader/>
<div>
    <form:form id="editForm" method="post" commandName="m" cssClass="form-horizontal">
        <fieldset>
            <legend>系统用户管理[${op}] <a href="<es:BackURL/>" class="btn btn-link">返回</a></legend>

            <es:showGlobalError commandName="m"/>

            <form:hidden path="id"/>
            <form:hidden path="deleted"/>


            <div class="control-group">
                <form:label path="username" cssClass="control-label">用户名</form:label>
                <div class="controls">
                    <form:input path="username" cssClass="validate[required,custom[username],ajax[ajaxNameCall]]" placeholder="5到20个汉字、字母、数字或下划线"/>
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
                <div class="controls">
                    <input type="submit" class="btn btn-primary" value="${op}">
                    <a href="<es:BackURL/>" class="btn">返回</a>
                </div>
            </div>
        </fieldset>
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
                //自定义ajax验证  ajax[ajaxNameCall] 放到验证规则的最后（放到中间只有当submit时才验证）
                $.validationEngineLanguage.allRules.ajaxNameCall= {
                    "url": "${ctx}/admin/sys/sysUser/validate",
                    //动态提取的数据。验证时一起发送
                    extraDataDynamic : ['#id'],
                    //验证失败时的消息
                    //"alertText": "* 该名称已被其他人使用",
                    //验证成功时的消息
                    //"alertTextOk": "该名称可以使用",
                    "alertTextLoad": "* 正在验证，请稍等。。。"
                };
                $.validationEngineLanguage.allRules.username={
                    "regex": /^[\u4E00-\u9FA5\uf900-\ufa2d\w]{5,20}$/,
                    "alertText": "* 5到20个汉字、字母、数字或下划线组成"
                };
                var validationEngine = $("#editForm").validationEngine();
                <es:showFieldError commandName="m"/>
            </c:otherwise>
        </c:choose>

    });
</script>