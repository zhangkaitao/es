<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jspf" %>
<es:contentHeader/>
<style>
    #calendar div.clearfix {
        margin-top: 5px;
        padding-bottom: 5px;
        margin-left: 10px;
        clear: both;
        float: left;
    }
</style>
<div id='calendar'>
    <div class="clearfix">
        <label style="width: 60px;text-align: right;float:left;">标题：</label>
        <div style="float: left;" title="${calendar.title}">
            ${calendar.title}
        </div>
    </div>
    <div class="clearfix">
        <label style="width: 60px;text-align: right;float:left;">开始时间：</label>
        <div style="float: left;">
            <fmt:formatDate value="${calendar.startDate}" pattern="yyyy-MM-dd"/>
            <c:if test="${not empty calendar.startTime}">
                <fmt:formatDate value="${calendar.startTime}" pattern="HH:mm:ss"/>
            </c:if>
        </div>
    </div>
    <div class="clearfix">
        <label style="width: 60px;text-align: right;float:left;">结束时间：</label>
        <div style="float: left;">
            <fmt:formatDate value="${calendar.endDate}" pattern="yyyy-MM-dd"/>
            <c:if test="${not empty calendar.endTime}">
                <fmt:formatDate value="${calendar.endTime}" pattern="HH:mm:ss"/>
            </c:if>
            <c:if test="${empty calenadr.startTime and empty calendar.endTime}">
                （全天提醒事项）
            </c:if>
        </div>
    </div>
    <div class="clearfix">
        <label style="width: 60px;text-align: right;float:left;">备注：</label>
        <div style="float: left;">
            <textarea>${calendar.details}</textarea>
        </div>
    </div>



    <div class="clearfix" style="margin-left: 70px;">
        <button type="submit" class="btn-delete-calendar btn btn-primary" data-id="${calendar.id}">
            <i class="icon-delete"></i>
            删除提醒事项
        </button>
        <a href="javascript:$.app.closeModalDialog();" class="btn">
            关闭窗口
        </a>
    </div>

</div>

<es:contentFooter/>