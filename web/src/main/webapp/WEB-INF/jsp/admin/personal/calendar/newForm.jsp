<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jspf" %>
<style>
    #editForm div.clearfix {
        margin-top: 10px;
        padding-bottom: 5px;
        margin-left: 10px;
        clear: both;
        float: left;
    }
</style>
<div class="clearfix">
    <form:form id="editForm" method="post" commandName="model" cssClass="row-fluid form-horizontal form-inline">

        <es:showGlobalError commandName="model"/>


        <div class="clearfix">
            <form:label path="title" cssStyle="width: 60px;text-align: right;">标题：</form:label>
            <form:input path="title" cssClass="validate[required,maxSize[100]]"/>
        </div>

        <div class="clearfix">
            <form:label path="startDate"  cssStyle="width: 60px;text-align: right;">开始日期：</form:label>
            <span class="input-append date">
                <form:input path="startDate" cssClass="validate[required]" cssStyle="width: 180px;"  data-position="bottom-left" data-format="yyyy-MM-dd"/>
                <span class="add-on"><i data-time-icon="icon-time" data-date-icon="icon-calendar"></i></span>
            </span>

        </div>

        <div class="clearfix">
            <form:label path="length"  cssStyle="width: 60px;text-align: right;">持续天数：</form:label>
            <form:input path="length" cssClass="validate[required] input-small"/>
            <label><input class="all-day" type="checkbox">全天提醒事项</label>

        </div>

        <div class="clearfix">
            <form:label path="startTime" cssStyle="width: 60px;text-align: right;">开始时间：</form:label>
            <span class="input-append date">
                <form:input path="startTime" cssClass="validate[required]"  cssStyle="width: 180px;" data-position="bottom-left" data-format="hh:mm:ss"/>
                <span class="add-on"><i data-time-icon="icon-time" data-date-icon="icon-calendar"></i></span>
            </span>


        </div>

        <div class="clearfix">
            <form:label path="endTime"  cssStyle="width: 60px;text-align: right;">结束时间：</form:label>
            <span class="input-append date">
                <form:input path="endTime" cssClass="validate[required]" cssStyle="width: 180px;" data-position="bottom-left" data-format="hh:mm:ss"/>
                <span class="add-on"><i data-time-icon="icon-time" data-date-icon="icon-calendar"></i></span>
            </span>
        </div>


        <div class="clearfix">
            <form:label path="details"  cssStyle="width: 60px;text-align: right;">备注：</form:label>
            <form:textarea path="details" cssClass="validate[maxSize[500]]"/>
        </div>


        <div class="clearfix">
            <form:label path="backgroundColor" cssStyle="width: 60px;text-align: right;">背景颜色：</form:label>
            <select id="backgroundColor" name="backgroundColor" style="background: ${backgroundColorList[0]}">
                <c:forEach items="${backgroundColorList}" var="c">
                    <option style="background: ${c}" value="${c}">&nbsp;</option>
                </c:forEach>
            </select>
            <%--<form:label path="textColor" cssStyle="width: 60px;text-align: right;">前景：</form:label>--%>
            <%--<form:input path="textColor" cssClass="input-small"/>--%>
        </div>

    </form:form>
</div>
<script>
    $(function() {

        var validationEngine = $("form").validationEngine({
            promptPosition : "topRight",
            autoPositionUpdate:true,
            scroll:false
        });

        $.app.initDatetimePicker();

        $("#backgroundColor").change(function() {
            $(this).attr("style", $(this).find("option:selected").attr("style"));
        })

        $(".all-day").change(function() {
            if($(this).is(":checked")) {
                $("[name=startTime],[name=endTime]").val("").attr("disabled", true).removeClass("validate[required]");
                $("[name=startTime]").validationEngine("hide");
                $("[name=endTime]").validationEngine("hide");
            } else {
                $("[name=startTime],[name=endTime]").removeAttr("disabled").addClass("validate[required]");
            }
        });
    })
</script>
