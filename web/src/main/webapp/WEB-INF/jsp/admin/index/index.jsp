<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jspf"%>
<es:contentHeader title="Easy-Scaffold脚手架" index="true"/>
${user1}
<div class="index-panel">
    <div class="tabs-bar tabs-fix-top">
        <span class="icon-chevron-left" style="display: none;"></span>

        <div class="ul-wrapper">
            <ul>
                <li><a href="#tabs-0">欢迎使用</a></li>
            </ul>
        </div>
        <span class="icon-chevron-right" style="display: none;"></span>

        <div id="tabs-0">
        </div>

    </div>
   <iframe id="iframe-tabs-0" tabs="true" class="ui-layout-center"
            frameborder="0" scrolling="auto" src="${ctx}/admin/welcome"></iframe>

    <%@include file="userinfo.jsp"%>

    <div class="ui-layout-north index-header">
        <%@include file="header.jsp"%>
    </div>


    <div class="ui-layout-south">
        <%@include file="footer.jsp"%>
    </div>
    <div class="ui-layout-west menu">
        <%@include file="menu.jsp"%>
    </div>
</div>
<es:contentFooter/>
<script type="text/javascript">
    $(function() {
        $.app.initIndex();
        $.app.heartbeat();
    });
</script>