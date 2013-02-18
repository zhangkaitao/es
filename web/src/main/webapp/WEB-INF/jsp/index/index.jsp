<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jspf"%>
<es:contentHeader title="Easy-Scaffold脚手架" centerPanel="false"/>

    <div id="content" style="padding: 0px!important;">
        <div id="tabs">
            <ul class="tabs-fix-top">
                <li><a href="#tabs-1">欢迎使用</a></li>
            </ul>
            <div id="tabs-1">
            </div>
        </div>
    </div>
   <iframe id="iframe-tabs-1" tabs="true" class="ui-layout-center" frameborder="0" scrolling="auto"
            src="${ctx}/welcome"></iframe>


    <div class="ui-layout-north">
        <%@include file="header.jsp"%>
    </div>

    <div class="ui-layout-south">
        <%@include file="footer.jsp"%>
    </div>
    <div class="ui-layout-west">
        <%@include file="menu.jsp"%>
    </div>
<es:contentFooter/>
<script>
    $(document).ready(function () {
        $.app.initIndex();
    });
</script>
