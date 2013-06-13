<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jspf"%>
<es:contentHeader/>
<div>

    <ul class="nav nav-tabs">
        <shiro:hasPermission name="showcase:upload:create">
        <c:if test="${op eq '新增'}">
            <li ${op eq '新增' ? 'class="active"' : ''}>
                <a href="${ctx}/showcase/upload/create?BackURL=<es:BackURL/>">
                    <i class="icon-file-alt"></i>
                    新增
                </a>
            </li>
        </c:if>
        </shiro:hasPermission>

        <c:if test="${not empty m.id}">
            <li ${op eq '查看' ? 'class="active"' : ''}>
                <a href="${ctx}/showcase/upload/${m.id}?BackURL=<es:BackURL/>">
                    <i class="icon-eye-open"></i>
                    查看
                </a>
            </li>
            <shiro:hasPermission name="showcase:upload:update">
            <li ${op eq '修改' ? 'class="active"' : ''}>
                <a href="${ctx}/showcase/upload/${m.id}/update?BackURL=<es:BackURL/>">
                    <i class="icon-edit"></i>
                    修改
                </a>
            </li>
            </shiro:hasPermission>
            <shiro:hasPermission name="showcase:upload:delete">
            <li ${op eq '删除' ? 'class="active"' : ''}>
                <a href="${ctx}/showcase/upload/${m.id}/delete?BackURL=<es:BackURL/>">
                    <i class="icon-trash"></i>
                    删除
                </a>
            </li>
            </shiro:hasPermission>
        </c:if>
        <li>
            <a href="<es:BackURL/>" class="btn btn-link">
                <i class="icon-reply"></i>
                返回
            </a>
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

            <c:if test="${op eq '新增'}">
                <c:set var="icon" value="icon-file-alt"/>
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
                var validationEngine = $("#editForm").validationEngine();
                <es:showFieldError commandName="m"/>
            </c:otherwise>
        </c:choose>


    });
</script>