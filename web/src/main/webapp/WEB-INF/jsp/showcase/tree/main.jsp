<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jspf"%>
<es:contentHeader/>

<iframe id="maintainFrame" name="maintainFrame" class="ui-layout-center"
         frameborder="0" scrolling="auto" style="top: 0px!important;"></iframe>

<iframe id="treeFrame" name="treeFrame" class="ui-layout-west" frameborder="0" scrolling="auto"
         src="${ctx}/showcase/tree/tree"></iframe>

<es:contentFooter/>
<script type="text/javascript">
    var layout; // a var is required because this page utilizes: myLayout.allowOverflow() method
    $(document).ready(function () {
        layout = $('body').layout({
                west__size:                    250
           ,    west__spacing_closed:        20
           ,    west__togglerLength_closed:    100
           ,    west__togglerContent_closed:"显示树"
           ,    west__togglerTip_closed:    "显示树"
           ,    west__sliderTip:            "显示树"
           ,    togglerTip_open: "隐藏树"
           ,    togglerTip_closed: "显示树"
           ,    center__maskContents:        true // IMPORTANT - enable iframe masking
           });
        });
</script>