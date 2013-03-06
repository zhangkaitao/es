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
                    <a href="javascript:$.app.cancelModelDialog();" class="btn">取消</a>
                </div>
            </div>
        </fieldset>
    </form:form>
</div>
<script type="text/javascript">
    $(function () {
        var validationEngine = $("#childEditForm").validationEngine();

        $.app.initDatetimePicker();

        $.parentchild.initChildForm({
            form : $("#childEditForm"),
            tableId : "childTable",
            excludeInputSelector : "[name='_show'],[name='_type']",
            trId : "${param.trId}",
            validationEngine : validationEngine,
            updateUrl : "${ctx}/showcase/parentchild/parent/child/update/{id}",
            deleteUrl : "${ctx}/showcase/parentchild/parent/child/delete/{id}",
            alwaysNew : "${param.copy}"
        });



    });

</script>