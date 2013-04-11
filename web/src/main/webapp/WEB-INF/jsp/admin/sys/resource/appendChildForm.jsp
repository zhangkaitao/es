<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jspf"%>
<es:contentHeader/>

<%@include file="nav.jspf"%>
<form:form id="appendChildForm" method="post" commandName="child" cssClass="form-horizontal" enctype="multipart/form-data">

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
                <button type="submit" class="btn btn-primary">
                    <i class="icon-file"></i>
                    添加子节点
                </button>
                <a class="btn" href="<es:BackURL/>">
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
    $.validationEngineLanguage.allRules.name = {
        "regex": /^.{1,50}$/,
        "alertText": "* 小于50个字符"
    };
    var validationEngine = $("#appendChildForm").validationEngine();
    <es:showFieldError commandName="child"/>


});
</script>
