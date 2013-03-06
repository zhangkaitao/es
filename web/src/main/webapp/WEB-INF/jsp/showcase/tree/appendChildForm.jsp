<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jspf"%>
<es:contentHeader/>

<form:form id="appendChildForm" method="post" commandName="child" cssClass="form-horizontal" enctype="multipart/form-data">
    <fieldset>
        <legend>添加子节点</legend>

        <es:showGlobalError commandName="child"/>

        <div class="control-group">
            <label class="control-label">父节点名称</label>
            <div class="controls">
                 <es:showAttachment filename="${parent.icon}" showImage="true" width="auto" height="16" isDownload="false"/>
                 ${parent.name}
            </div>
        </div>

        <div class="control-group">
            <form:label path="name" cssClass="control-label">子节点名称</form:label>
            <div class="controls">
                <form:input path="name" cssClass="validate[required,custom[name]]" placeholder="小于50个字符"/>
            </div>
        </div>

        <c:if test="${not empty child.icon}">
            <div class="control-group">
                <form:label path="icon" cssClass="control-label">当前显示的图标</form:label>
                <div class="controls">
                    <es:showAttachment filename="${child.icon}" showImage="true" width="auto" height="16" isDownload="false"/>
                    <form:hidden path="icon"/>
                </div>
            </div>
        </c:if>

        <div class="control-group">
            <label for="file" class="control-label">子节点图标</label>
            <div class="controls">
                <input type="file" id="file" name="file" class="custom-file-input"/>
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
                <input type="submit" class="btn btn-primary" value="添加子节点">
                <input id="toMaintain" type="button" class="btn" value="返回">
            </div>
        </div>
    </fieldset>
</form:form>
</div>
<es:contentFooter/>
<script type="text/javascript">
$(function () {
    $.validationEngineLanguage.allRules.name = {
        "regex": /^.{1,50}$/,
        "alertText": "* 小于50个字符"
    };
    var validationEngine = $("#appendChildForm").validationEngine();
    <es:showFieldError commandName="child"/>

    $("#toMaintain").click(function() {
        location.href = '${ctx}/showcase/tree/maintain/${parent.id}';
    });


});
</script>
