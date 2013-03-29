<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jspf"%>
<es:contentHeader/>
<div>

    <ul class="nav nav-tabs">
        <li class="active">
            <a>文件管理[${op}]</a>
        </li>
        <li>
            <a href="<es:BackURL/>" class="btn btn-link">返回列表</a>
        </li>
    </ul>

    <form:form id="editForm" method="post" commandName="m" cssClass="form-horizontal" enctype="multipart/form-data">

            <es:showGlobalError commandName="m"/>

            <form:hidden path="id"/>

            <div class="control-group">
                <form:label path="name" cssClass="control-label">名称</form:label>
                <div class="controls">
                    <form:input path="name" cssClass="validate[required,maxSize[10]]" placeholder="最多10个字符"/>
                </div>
            </div>

            <c:if test="${not empty m.src}">
            <div class="control-group">
                <form:label path="name" cssClass="control-label"></form:label>
                <div class="controls">
                    <es:showAttachment filename="${m.src}" showImage="true" width="auto" height="160"/>
                    <form:hidden path="src"/>
                </div>
            </div>
            </c:if>

            <div class="control-group">
                <label for="file" class="control-label">文件</label>
                <div class="controls">
                    <input id="file" type="file" name="file" class="custom-file-input"/>
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
                $("#editForm :input").prop("readonly", true);
            </c:when>
            <c:otherwise>
                var validationEngine = $("#editForm").validationEngine();
                <es:showFieldError commandName="m"/>
            </c:otherwise>
        </c:choose>


    });
</script>