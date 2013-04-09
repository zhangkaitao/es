<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jspf"%>
<es:contentHeader/>

<div class="ui-layout-west tree">
    <iframe id="treeFrame" name="treeFrame" width="100%" height="100%"  frameborder="0" scrolling="auto" src="${ctx}/admin/sys/resource/tree"></iframe>
</div>
<div class="ui-layout-center tree">
    <iframe id="maintainFrame" name="maintainFrame" width="100%" height="100%"  frameborder="0" scrolling="auto"></iframe>
</div>

<es:contentFooter/>

<script type="text/javascript">
    $(document).ready(function () {
        $('body').layout({
                west__size:                    250
           ,    west__spacing_closed:        20
           ,    west__togglerLength_closed:    100
           ,    west__togglerContent_closed:"显示树"
           ,    west__togglerTip_closed:    "显示树"
           ,    west__sliderTip:            "显示树"
           ,    togglerTip_open: "隐藏树"
           ,    togglerTip_closed: "显示树"
           ,    resizerTip:         "调整大小"
           ,    maskContents:        true // IMPORTANT - enable iframe masking
           });
        });
</script>