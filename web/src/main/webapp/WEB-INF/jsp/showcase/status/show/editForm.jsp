<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jspf"%>
<es:contentHeader/>
<div>
    <form:form id="editForm" method="post" commandName="m" cssClass="form-horizontal">
        <fieldset>
            <legend>显示状态管理[${op}] <a href="<es:BackURL/>" class="btn btn-link">返回</a></legend>

            <es:showGlobalError commandName="m"/>

            <form:hidden path="id"/>


            <div class="control-group">
                <form:label path="name" cssClass="control-label">名称</form:label>
                <div class="controls">
                    <form:input path="name" cssClass="validate[required]"/>
                </div>
            </div>

            <div class="control-group">
                <form:label path="name" cssClass="control-label">是否显示</form:label>
                <div class  ="controls inline-radio">
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
                var validationEngine = $("#editForm").validationEngine();
                <es:showFieldError commandName="m"/>
            </c:otherwise>
        </c:choose>
    });
</script>