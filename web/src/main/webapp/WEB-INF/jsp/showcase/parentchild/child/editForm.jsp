<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jspf"%>
<div>
    <form:form id="childEditForm" method="post" commandName="child" cssClass="form-horizontal">
        <fieldset>
            <legend>子管理[${op}]</legend>
            <es:showGlobalError commandName="child"/>
            <form:hidden path="id"/>

            <div class="control-group">
                <form:label path="name" cssClass="control-label">名称</form:label>
                <div class="controls">
                    <form:input path="name" cssClass="validate[required]" placeholder="请输入名称"/>
                </div>
            </div>

            <div class="control-group">
                <form:label path="type" cssClass="control-label">类型</form:label>
                <div class="controls">
                    <form:select path="type" items="${typeList}" itemLabel="info"/>
                </div>
            </div>

            <div class="control-group ">
                <form:label path="beginTime" cssClass="control-label">开始时间</form:label>
                <div class="controls input-append date">
                    <form:input path="beginTime" data-format="hh:mm:ss" placeholder="例如11:11:11" readonly="true"/>
                    <span class="add-on"><i data-time-icon="icon-time" data-date-icon="icon-calendar"></i></span>
                </div>
            </div>

            <div class="control-group">
                <form:label path="endTime" cssClass="control-label">结束时间</form:label>
                <div class="controls input-append date">
                    <form:input path="endTime" data-format="hh:mm:ss" placeholder="例如11:11:11" readonly="true"/>
                    <span class="add-on"><i data-time-icon="icon-time" data-date-icon="icon-calendar"></i></span>
                </div>
            </div>

            <div class="control-group">
                <form:label path="show" cssClass="control-label">是否显示</form:label>
                <div class="controls inline-radio">
                    <form:radiobuttons path="show" items="${booleanList}" itemLabel="info" itemValue="value" cssClass="validate[required]"/>
                </div>
            </div>

            <br/><br/>
            <div class="control-group">
                <div class="controls">
                    <input type="submit" class="btn btn-primary" value="${op}">
                    <a href="javascript:$.app.cancelEdit();" class="btn">取消</a>
                </div>
            </div>
        </fieldset>
    </form:form>
</div>
<script type="text/javascript">
    $(function () {
        <c:choose>
            <c:when test="${op eq '删除'}">
                //删除时不验证 并把表单readonly
                $("#editForm :input").prop("readonly", true);
            </c:when>
            <c:otherwise>
                var validationEngine = $("#childEditForm").validationEngine();
                <es:showFieldError commandName="child"/>
            </c:otherwise>
        </c:choose>
        $.app.initDatetimePicker();
    });

    $("#childEditForm").submit(function() {
        if(!validationEngine.validate()) {
            return;
        }
        save($(this));
        return false;
    });

    function save($form) {
        //currentDocument 是执行$.app.modalEdit时保存的当前document 请参考application.js中的modalEdit
        var $childTbody = $("#childTable tbody", currentDocument);
        var $tr = $("<tr></tr>");
        $childTbody.append($tr);
        var $td = $("<td></td>");

        var $id = $form.find("input[name=id]");
        $tr.append($td.clone(true).append($id.val()).append($id.hide()));

        var $name =$form.find("input[name=name]");
        $tr.append($td.clone(true).append($name.val()).append($name.hide()));

        var $type =$form.find("input[name=type]");
        $tr.append($td.clone(true).append($type.val()).append($type.hide()));

        var $beginTime = $form.find("input[name=beginTime]");
        $tr.append($td.clone(true).append($beginTime.val()).append($beginTime.hide()));

        var $endTime = $form.find("input[name=endTime]");
        $tr.append($td.clone(true).append($endTime.val()).append($endTime.hide()));

        var $show = $form.find("input[name=show]");
        $tr.append($td.clone(true).append($show.val()).append($show.hide()));

        $tr.append($td.clone(true).append("修改|删除"));
        $.app.cancelEdit();
    }

</script>