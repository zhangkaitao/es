<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jspf"%>
<es:contentHeader title="Easy-Scaffold脚手架" index="true"/>
<div class="index-panel">
    <div id="tabs" class="tabs-fix-top">
        <span class="icon-chevron-left" style="display: none;"></span>

        <div class="ul-wrapper">
            <ul>
                <li><a href="#tabs-1">欢迎使用</a></li>
            </ul>
        </div>
        <span class="icon-chevron-right" style="display: none;"></span>

        <div id="tabs-1">
        </div>

    </div>
   <iframe id="iframe-tabs-1" tabs="true" class="ui-layout-center"
            frameborder="0" scrolling="auto" src="${ctx}/admin/welcome"></iframe>


    <div class="ui-layout-north index-header">
        <%@include file="index/header.jsp"%>
    </div>


    <div class="ui-layout-south">
        <%@include file="index/footer.jsp"%>
    </div>
    <div class="ui-layout-west menu">
        <%@include file="index/menu.jsp"%>
    </div>
</div>
<es:contentFooter/>
<script type="text/javascript">
    $(function() {
        $.app.initIndex();
        $.app.heartbeat();
    });
</script>
