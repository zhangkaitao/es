<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jspf"%>
<es:contentHeader/>
<style type="text/css">
    .ui-layout-north {
        display:	none;
        overflow:	hidden;
    }

    .ui-layout-toggler{
        background-color: #0099ff;
        opacity: 1;

    }
    .ui-layout-resizer {
        opacity: 1;
        filter:none;
    }
    .ui-layout-toggler .content {
        color: #fff;
        line-height: 18px;
    }
</style>
<div class="ui-layout-north tree" style="overflow: hidden">
    <iframe id="treeFrame" name="treeFrame" width="100%" height="100%"
            style="overflow: hidden"
            frameborder="0" scrolling="no"
            src="about:blank" longdesc="${ctx}/admin/sys/user/tree"></iframe>
</div>
<div class="ui-layout-center tree">
    <iframe id="listFrame" name="listFrame" width="100%" height="100%"  frameborder="0" scrolling="auto"
             src="${ctx}/admin/sys/user/0/0"></iframe>
</div>

<es:contentFooter/>

<script type="text/javascript">

    function loadIframePage (pane, $Pane) {
        if (!$Pane) $Pane = $('.ui-layout-'+ pane);
        var $Iframe = $Pane.prop('tagName')=='IFRAME' ? $Pane : $Pane.find('IFRAME:first');
        var src  = $Iframe.attr('src')
           ,page = $Iframe.attr('longdesc')
                ;
        if (page && src != page) $Iframe.attr('src',page);
    }

    function unloadIframePage (pane, $Pane) {
        if (!$Pane) $Pane = $('.ui-layout-'+ pane);
        var $Iframe = $Pane.prop('tagName')=='IFRAME' ? $Pane : $Pane.find('IFRAME:first');
        $Iframe.attr('src',"about:blank");
    }


    $(document).ready(function () {
        $('body').layout({
                north__size:                    300
           ,    north__spacing_closed:        20
           ,    north__togglerLength_closed:    200
           ,    initClosed : true
           ,    north__togglerContent_closed:"显示组织机构和工作职务查询"
           ,    north__togglerTip_closed:    "显示组织机构和工作职务查询"
           ,    north__sliderTip:            "显示组织机构和工作职务查询"
           ,    resizerTip:         "调整大小"
           ,    togglerTip_open: "隐藏组织机构和工作职务查询"
           ,    togglerTip_closed: "显示组织机构和工作职务查询"
           ,    maskContents:        true // IMPORTANT - enable iframe masking
           ,    north__onopen : loadIframePage
           ,    north__onclose_start:	$.layout.callbacks.pseudoClose
           ,	north__pseudoClose:		{ skipIE: true } // simple iframe - OK in IE

        });
        });
</script>