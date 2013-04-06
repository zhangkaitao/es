<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jspf"%>

<es:contentHeader/>

<ul class="nav nav-tabs">
    <li class="active">
        <a>
            <i class="icon-edit"></i>
            维护组织机构树
        </a>
    </li>
</ul>
<form:form id="maintainForm" method="post" commandName="m" cssClass="form-horizontal" enctype="multipart/form-data">

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


    <div class="control-group">
        <form:label path="name" cssClass="control-label">类型</form:label>
        <div class="controls">
            <form:select path="type" items="${types}" itemLabel="info" cssClass="validate[required]"/>
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

        <div class="control-group">
            <div class="controls">
                <button id="updateTree" type="submit" class="btn btn-primary">
                    <i class="icon-edit"></i>
                    修改
                </button>
                <c:if test="${m.root == false}">
                <button id="deleteTree" type="submit" class="btn btn-primary">
                    <i class="icon-remove"></i>
                    删除
                </button>
                </c:if>
                <button id="appendChild" type="submit" class="btn btn-primary">
                    <i class="icon-file"></i>
                    添加子节点
                </button>
                <c:if test="${m.root == false}"><%-- 根节点不能移动 --%>
                <button id="moveTree" type="submit" class="btn btn-primary">
                    <i class="icon-move"></i>
                    移动节点
                </button>
                </c:if>
            </div>
        </div>
</form:form>
</div>
<es:contentFooter/>
<%@include file="/WEB-INF/jsp/common/import-zTree-js.jspf"%>
<script type="text/javascript">
$(function () {
    $.validationEngineLanguage.allRules.name = {
        "regex": /^.{1,50}$/,
        "alertText": "* 小于50个字符"
    };
    var validationEngine = $("#maintainForm").validationEngine();
    <es:showFieldError commandName="m"/>

    $.zTree.initMaintainBtn("${ctx}/admin/sys/organization/organization", "${m.id}", ${not empty param.async and param.async});



});
</script>
