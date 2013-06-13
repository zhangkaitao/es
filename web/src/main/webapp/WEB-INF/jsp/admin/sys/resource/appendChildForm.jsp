<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jspf"%>
<es:contentHeader/>
<div class="panel">
<%@include file="nav.jspf"%>
<form:form id="appendChildForm" method="post" commandName="child" cssClass="form-horizontal" enctype="multipart/form-data">

        <es:showGlobalError commandName="child"/>

        <div class="control-group">
            <label class="control-label">父节点名称</label>
            <div class="controls">
                 <maintain:showIcon iconIdentity="${parent.icon}"/>
                 ${parent.name}
            </div>
        </div>

        <div class="control-group">
            <form:label path="name" cssClass="control-label">子节点名称</form:label>
            <div class="controls">
                <form:input path="name" cssClass="validate[required,custom[name]]" placeholder="小于50个字符"/>
            </div>
        </div>


        <div class="control-group">
            <form:label path="identity" cssClass="control-label">资源标识</form:label>
            <div class="controls">
                <form:input path="identity" placeholder="用于权限验证"/>
            </div>
        </div>

        <div class="control-group">
            <form:label path="url" cssClass="control-label">URL地址</form:label>
            <div class="controls">
                <form:input path="url" placeholder="菜单跳转地址"/>
            </div>
        </div>

        <div class="control-group">
            <form:label path="icon" cssClass="control-label">子节点图标</form:label>
            <div class="controls">
                <form:input path="icon" cssClass="input-medium"/>
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
                <button type="submit" class="btn btn-primary">
                    <i class="icon-file-alt"></i>
                    添加子节点
                </button>
            </div>
        </div>
</form:form>
</div>
<es:contentFooter/>
<%@include file="/WEB-INF/jsp/common/admin/import-maintain-js.jspf"%>
<script type="text/javascript">
$(function () {
    $.validationEngineLanguage.allRules.name = {
        "regex": /^.{1,50}$/,
        "alertText": "* 小于50个字符"
    };
    var validationEngine = $("#appendChildForm").validationEngine();
    <es:showFieldError commandName="child"/>

    $.maintain.icon.initIconList($("#icon"));

});
</script>
