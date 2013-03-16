<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jspf"%>
<es:contentHeader/>
<div>
    <form:form id="editForm" method="post" commandName="m" cssClass="form-horizontal">

        <fieldset>
            <legend>编辑器管理[${op}] <a href="<es:BackURL/>" class="btn btn-link">返回</a></legend>

            <es:showGlobalError commandName="m"/>

            <form:hidden path="id"/>


            <div class="control-group">
                <form:label path="title" cssClass="control-label">标题</form:label>
                <div class="controls">
                    <form:input path="title" cssClass="input-xxlarge validate[required,minSize[2],maxSize[200]]"/>
                </div>
            </div>


            <div class="control-group">
                <form:label path="content" cssClass="control-label">内容</form:label>
                <div class="controls">
                    <c:choose>
                    <c:when test="${op ne '查看'}">
                        <form:textarea path="content" cssClass="validate[required]" cssStyle="width: 800px;height: 300px;"/>
                    </c:when>
                    <c:otherwise>
                        ${m.content}
                    </c:otherwise>
                    </c:choose>

               </div>
            </div>

            <div class="control-group">
                <div class="controls">
                    <input type="submit" class="btn btn-primary" value="${op}">
                    <a href="<es:BackURL/>" class="btn">返回</a> (提交快捷键: Ctrl + Enter)
                </div>
            </div>
        </fieldset>
    </form:form>
</div>
<es:contentFooter/>
<%@include file="/WEB-INF/jsp/common/import-editor-js.jspf"%>
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

        var editor = KindEditor.create('textarea[name="content"]', {
            themeType: 'simple',
            uploadJson: '${ctx}/kindeditor/upload',
            fileManagerJson: '${ctx}/kindeditor/filemanager',
            allowFileManager: true,
            afterCreate: function () {
                var self = this;
                KindEditor.ctrl(document, 13, function () {
                    self.sync();
                    $("#editForm").submit();
                });
                KindEditor.ctrl(self.edit.doc, 13, function () {
                    self.sync();
                    $("#editForm").submit();
                });
            }
        });
    });
</script>