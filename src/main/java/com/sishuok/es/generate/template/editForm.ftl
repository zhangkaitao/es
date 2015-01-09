<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jspf"%>
<es:contentHeader/>
<div class="panel">


    <ul class="nav nav-tabs">
        <shiro:hasPermission name="${permissionName}:create">
        <c:if test="${r"${op eq '新增'}"} ">
            <li ${r"${op eq '新增' ? 'class=active' : ''}"}>
                <a href="${r"${ctx}"}/admin/${sysName}/${folderName}/create?BackURL=<es:BackURL/>">
                    <i class="icon-file-alt"></i>
                    新增
                </a>
            </li>
        </c:if>
        </shiro:hasPermission>
        <c:if test="${r"${not empty m.id}"}">
            <li ${r"${op eq '查看' ? 'class=active' : ''}"}>
                <a href="${r"${ctx}"}/admin/${sysName}/${folderName}/${r"${m.id}"}?BackURL=<es:BackURL/>">
                    <i class="icon-eye-open"></i>
                    查看
                </a>
            </li>
            <shiro:hasPermission name="${permissionName}:update">
            <li ${r"${op eq '修改' ? 'class=active' : ''}"}>
                <a href="${r"${ctx}"}/admin/${sysName}/${folderName}/${r"${m.id}"}/update?BackURL=<es:BackURL/>">
                    <i class="icon-edit"></i>
                    修改
                </a>
            </li>
            </shiro:hasPermission>
            <shiro:hasPermission name="${permissionName}:delete">
            <li ${r"${op eq '删除' ? 'class=active' : ''}"}>
                <a href="${r"${ctx}"}/admin/${sysName}/${folderName}/${r"${m.id}"}/delete?BackURL=<es:BackURL/>">
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


    <form:form id="editForm" method="post" commandName="m" cssClass="form-horizontal">

            <es:showGlobalError commandName="m"/>

            <form:hidden path="id"/>


            <div class="control-group">
                <form:label path="name" cssClass="control-label">名称</form:label>
                <div class="controls">
                    <form:input path="name" cssClass="input-xxlarge validate[required,minSize[2],maxSize[200]]"/>
                </div>
            </div>


            <div class="control-group">
                <form:label path="remarks" cssClass="control-label">描述</form:label>
                <div class="controls">
                    <c:choose>
                    <c:when test="${r"${op ne '查看'}"}">
                        <form:textarea path="remarks" cssClass="validate[required]" cssStyle="width: 550px;height: 300px;"/>
                    </c:when>
                    <c:otherwise>
                        ${r"${m.remarks}"}
                    </c:otherwise>
                    </c:choose>

               </div>
            </div>


            <c:if test="${r"${op eq '新增'}"}">
                <c:set var="icon" value="icon-file-alt"/>
            </c:if>
            <c:if test="${r"${op eq '修改'}"}">
                <c:set var="icon" value="icon-edit"/>
            </c:if>
            <c:if test="${r"${op eq '删除'}"}">
                <c:set var="icon" value="icon-trash"/>
            </c:if>

            <div class="control-group">
                <div class="controls">
                    <button type="submit" class="btn btn-primary">
                        <i class="${r"${icon}"}"></i>
                            ${r"${op}"}
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
<%@include file="/WEB-INF/jsp/common/import-editor-js.jspf"%>
<script type="text/javascript">
    $(function () {
        <c:choose>
            <c:when test="${r"${op eq '删除'}"}">
                //删除时不验证 并把表单readonly
                $.app.readonlyForm($("#editForm"), false);
            </c:when>
            <c:when test="${r"${op eq '查看'}"}">
                $.app.readonlyForm($("#editForm"), true);
            </c:when>
            <c:otherwise>
                var validationEngine = $("#editForm").validationEngine();
                <es:showFieldError commandName="m"/>
            </c:otherwise>
        </c:choose>

        var editor = KindEditor.create('textarea[name="content"]', {
            themeType: 'simple',
            uploadJson: '${r"${ctx}"}/kindeditor/upload',
            fileManagerJson: '${r"${ctx}"}/kindeditor/filemanager',
            allowFileManager: true,
            afterBlur: function(){this.sync();}
        });
    });
</script>
