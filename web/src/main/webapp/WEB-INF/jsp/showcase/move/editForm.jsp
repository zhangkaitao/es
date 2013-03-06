<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jspf"%>
<es:contentHeader/>
<div>
    <form:form id="editForm" method="post" commandName="m" cssClass="form-horizontal">
        <!--上一个地址-->
        <es:BackURL hiddenInput="true"/>
        <fieldset>
            <legend>可移动管理[${op}] <a href="<es:BackURL/>" class="btn btn-link">返回</a></legend>

            <es:showGlobalError commandName="m"/>

            <form:hidden path="id"/>

            <div class="control-group">
                <form:label path="name" cssClass="control-label">名称</form:label>
                <div class="controls">
                    <form:input path="name" cssClass="validate[required]" placeholder="5到10个字母、数字、下划线"/>
                </div>
            </div>

            <c:if test="${op ne '新增'}">
            <div class="control-group">
                <form:label path="weight" cssClass="control-label">权重</form:label>
                <div class="controls">
                    <form:input path="weight" placeholder="请输入权重（数字越大，权重越高）"/>
                    <span class="help-inline">如果不填，默认最高优先级</span>
                </div>
            </div>
            </c:if>

            <div class="control-group">
                <form:label path="show" cssClass="control-label">是否显示</form:label>
                <div class="controls inline-radio">
                    <form:radiobuttons path="show" items="${booleanList}" itemLabel="info" itemValue="value" cssClass="validate[required]"/>
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
                $("#editForm :input").not(":button,:submit").prop("readonly", true);
            </c:when>
            <c:otherwise>
                var validationEngine = $("#editForm").validationEngine();
                <es:showFieldError commandName="m"/>
            </c:otherwise>
        </c:choose>
    });
</script>