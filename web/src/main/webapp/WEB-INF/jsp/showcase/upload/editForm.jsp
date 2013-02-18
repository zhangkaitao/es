<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jspf"%>
<es:contentHeader/>
<div>
    <form:form id="editForm" method="post" commandName="upload" cssClass="form-horizontal" enctype="multipart/form-data">
        <fieldset>
            <legend>文件管理[${op}] <a href="${ctx}/showcase/upload" class="btn btn-link">返回</a></legend>

            <es:showGlobalError commandName="upload"/>

            <form:hidden path="id"/>

            <div class="control-group">
                <form:label path="name" cssClass="control-label">名称</form:label>
                <div class="controls">
                    <form:input path="name" cssClass="validate[required,maxSize[10]]" placeholder="最多10个字符"/>
                </div>
            </div>

            <c:if test="${not empty upload.src}">
            <div class="control-group">
                <form:label path="name" cssClass="control-label"></form:label>
                <div class="controls">
                    <es:showAttachment filename="${upload.src}" showImage="true" width="auto" height="160"/>
                    <form:hidden path="src"/>
                </div>
            </div>
            </c:if>

            <div class="control-group">
                <form:label path="name" cssClass="control-label">文件</form:label>
                <div class="controls">
                    <input type="file" name="file" class="custom-file-input"/>
                </div>
            </div>

            <div class="control-group">
                <div class="controls">
                    <input type="submit" class="btn btn-primary" value="${op}">
                    <a href="${ctx}/showcase/upload" class="btn">返回</a>
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
                var validationEngine = $("#editForm").validationEngine();
                <es:showFieldError commandName="upload"/>
            </c:otherwise>
        </c:choose>

    });
</script>