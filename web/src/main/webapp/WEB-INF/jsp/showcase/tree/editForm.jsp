<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jspf"%>
<es:contentHeader/>
<div class="panel">

    <%@include file="nav.jspf"%>

    <form:form id="editForm" method="post" commandName="m" cssClass="form-horizontal" enctype="multipart/form-data">
        <es:showGlobalError commandName="m"/>

        <form:hidden path="id"/>
        <form:hidden path="parentId"/>
        <form:hidden path="parentIds"/>
        <form:hidden path="weight"/>

        <div class="control-group">
            <form:label path="name" cssClass="control-label">名称</form:label>
            <div class="controls">
                <form:input path="name" cssClass="validate[required,custom[name]]" placeholder="小于50个字符"/>
            </div>
        </div>

        <c:if test="${not empty m.icon}">
            <div class="control-group">
                <form:label path="icon" cssClass="control-label">当前显示的图标</form:label>
                <div class="controls">
                    <es:showAttachment filename="${m.icon}" showImage="true" width="auto" height="16" isDownload="false"/>
                    <form:hidden path="icon"/>
                </div>
            </div>
        </c:if>

        <div class="control-group">
            <label for="file" class="control-label">图标</label>
            <div class="controls">
                <input type="file" id="file" name="file" class="custom-file-input"/>
            </div>
        </div>

        <div class="control-group">
            <form:label path="show" cssClass="control-label">是否显示</form:label>
            <div class="controls inline-radio">
                <form:radiobuttons
                        path="show" items="${booleanList}" itemLabel="info" itemValue="value" cssClass="validate[required]"/>
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
<script type="text/javascript">
    $(function () {
        <c:choose>
            <c:when test="${op eq '删除'}">
                //删除时不验证 并把表单readonly
                $.app.readonlyForm($("#editForm"), ${m.root});
            </c:when>
            <c:when test="${op eq '查看'}">
                $.app.readonlyForm($("#editForm"), true);
            </c:when>
            <c:otherwise>
                $.validationEngineLanguage.allRules.name = {
                    "regex": /^.{1,50}$/,
                    "alertText": "* 小于50个字符"
                };
                var validationEngine = $("#editForm").validationEngine();
                <es:showFieldError commandName="m"/>
            </c:otherwise>
        </c:choose>
    });
</script>