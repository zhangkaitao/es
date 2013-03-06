<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jspf"%>
<es:contentHeader title="Easy-Scaffold脚手架" centerPanel="false"/>

    <div id="content" style="padding: 0px!important;">
        <div id="tabs" class="tabs-fix-top">
            <span class="icon-chevron-left"></span>
            <div class="ul-wrapper">
                <ul>
                    <li><a href="#tabs-1">欢迎使用1</a></li>
                    <li><a href="#tabs-1">欢迎使用</a></li>
                    <li><a href="#tabs-1">欢迎使用</a></li>
                    <li><a href="#tabs-1">欢迎使用</a></li>
                    <li><a href="#tabs-1">欢迎使用</a></li>
                    <li><a href="#tabs-1">欢迎使用</a></li>
                    <li><a href="#tabs-1">欢迎使用</a></li>
                    <li><a href="#tabs-1">欢迎使用</a></li>
                    <li><a href="#tabs-1">欢迎使用</a></li>
                    <li><a href="#tabs-1">欢迎使用</a></li>
                    <li><a href="#tabs-1">欢迎使用</a></li>
                    <li><a href="#tabs-1">欢迎使用10</a></li>
                    <li><a href="#tabs-1">欢迎使用10</a></li>
                    <li><a href="#tabs-1">欢迎使用10</a></li>
                    <li><a href="#tabs-1">欢迎使用10</a></li>
                    <li><a href="#tabs-1">欢迎使用12</a></li>
                    <li><a href="#tabs-1">欢迎使用12</a></li>
                    <li><a href="#tabs-1">欢迎使用12</a></li>
                    <li><a href="#tabs-1">欢迎使用14</a></li>
                </ul>
            </div>
            <span class="icon-chevron-right"></span>

            <div id="tabs-1">
            </div>
        </div>
    </div>
   <iframe id="iframe-tabs-1" name="content" tabs="true" class="ui-layout-center"
           frameborder="0" scrolling="auto" src="${ctx}/welcome"></iframe>


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
<script type="text/javascript">
    $(function() {
        $.app.initIndex();
    });
</script>
