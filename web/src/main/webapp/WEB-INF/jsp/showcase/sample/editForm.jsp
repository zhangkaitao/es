<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jspf"%>
<es:contentHeader/>
<div>
    <form:form id="editForm" method="post" commandName="sample" cssClass="form-horizontal">
        <fieldset>
            <legend>示例管理[${op}] <a href="${ctx}/showcase/sample" class="btn btn-link">返回</a></legend>

            <es:showGlobalError commandName="sample"/>

            <form:hidden path="id"/>

            <div class="control-group">
                <form:label path="name" cssClass="control-label">名称</form:label>
                <div class="controls">
                    <form:input path="name" cssClass="validate[required,custom[username],ajax[ajaxNameCall]]" placeholder="5到10个字母、数字、下划线"/>
                </div>
            </div>

            <div class="control-group">
                <form:label path="age" cssClass="control-label">年龄</form:label>
                <div class="controls">
                    <form:input path="age" cssClass="validate[required,custom[integer]]" placeholder="请输入年龄"/>
                </div>
            </div>

            <div class="control-group">
                <form:label path="birthday" cssClass="control-label">出生日期</form:label>
                <div class="controls input-append date">
                    <form:input path="birthday" data-format="yyyy-MM-dd hh:mm:ss" placeholder="例如2013-02-07-11:58"/>
                    <span class="add-on">
                        <i data-time-icon="icon-time" data-date-icon="icon-calendar"></i>
                    </span>
                </div>
            </div>

            <div class="control-group">
                <form:label path="sex" cssClass="control-label">性别</form:label>
                <div class="controls">
                    <form:select path="sex" cssClass="validate[required]">
                        <form:option label="请选择" value=""/>
                        <form:options items="${sexList}" itemLabel="info"></form:options>
                    </form:select>
                </div>
            </div>
            <div class="control-group">
                <form:label path="show" cssClass="control-label">是否显示</form:label>
                <div class="controls inline-radio">
                    <form:radiobuttons path="show" items="${booleanList}" itemLabel="info" itemValue="value" cssClass="validate[required]"/>
                </div>
            </div>

            <div class="control-group">
                <div class="controls">
                    <input type="submit" class="btn btn-primary" value="${op}">
                    <a href="${ctx}/showcase/sample" class="btn">返回</a>
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
                $("#editForm :input").prop("readonly", true);
            </c:when>
            <c:otherwise>
                //自定义ajax验证  ajax[ajaxNameCall] 放到验证规则的最后（放到中间只有当submit时才验证）
                $.validationEngineLanguage.allRules.ajaxNameCall= {
                    "url": "${ctx}/showcase/sample/validate",
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
                <es:showFieldError commandName="sample"/>
            </c:otherwise>
        </c:choose>
    });
</script>