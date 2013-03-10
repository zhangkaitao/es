<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jspf"%>
<es:contentHeader/>
<%@include file="/WEB-INF/jsp/common/import-zTree-css.jspf"%>

<form id="moveForm" method="post" class="form-horizontal">
    <input type="hidden" id="moveType" name="moveType" value="">
    <fieldset>
        <legend>移动子节点</legend>

        <div class="control-group">
            <label class="control-label">源节点</label>
            <div class="controls">
                <span class="help-inline" style="padding: 4px;">
                    <es:showAttachment filename="${source.icon}" showImage="true" width="auto" height="16" isDownload="false"/>
                    ${source.name}
                </span>
            </div>
        </div>

        <div class="control-group">
            <label class="control-label">目标节点</label>
            <div class="controls input-append" title="选择目标节点">
                <input type="hidden" id="targetId" name="targetId" value="${target.id}">
                <input type="text" id="targetname" name="targetname" value="${target.name}" class="validate[required]" readonly="readonly">
                <a id="selectTree"  href="javascript:;">
                    <span class="add-on"><i class="icon-chevron-down"></i></span>
                </a>
            </div>
        </div>


        <div class="control-group">
            <label class="control-label">移动到目标节点</label>
            <div class="controls">
                <input id="moveAsPrev" type="submit" class="btn btn-primary" value="之前">
                <input id="moveAsNext" type="submit" class="btn btn-primary" value="之后">
                <input id="moveAsInner" type="submit" class="btn btn-primary" value="孩子节点">
                <input id="toMaintain" type="button" class="btn" value="返回">
            </div>
        </div>
    </fieldset>
</form>
</div>

<es:contentFooter/>
<%@include file="/WEB-INF/jsp/common/import-zTree-js.jspf"%>
<script type="text/javascript">
$(function () {

    function initSelectTree($btn, id, name) {

        var treeContentStr =
                '<div id="treeContent{id}" class="treeContent" style="display:none; position: absolute;">' +
                    '<ul id="treeSelect{id}" class="ztree"></ul>' +
                '</div>';
        $("body").append(treeContentStr.replace(/\{id\}/g, id));

        var $id = $("#" + id);
        var $name = $("#" + name);
        var treeContent = "treeContent" + id;
        var $treeContent = $("#" + treeContent);
        var treeSelect = "treeSelect" + id;

        var setting = {
            view: {
                dblClickExpand: false
            },
            data: {
                simpleData: {
                    enable: true
                }
            },
            callback: {
                onClick: onClick
            }
        };

        var zNodes =[
            <c:forEach items="${trees}" var="m">
                { id:${m.id}, pId:${m.parentId}, name:"${m.name}", icon:"${ctx}/${m.icon}", open: true},
            </c:forEach>
        ];

        function onClick(e, treeId, treeNode) {
            var zTree = $.fn.zTree.getZTreeObj(treeSelect);
            var nodes = zTree.getSelectedNodes();
            var lastNode = nodes[nodes.length - 1];
            $name.prop("value", lastNode.name);
            $id.prop("value", lastNode.id);
        }

        function showMenu() {
            var nameOffset = $name.offset();
            $treeContent.css({left: nameOffset.left + "px", top: nameOffset.top + $name.outerHeight() + "px"}).slideDown("fast");
            $("body").bind("mousedown", onBodyDown);
        }

        function hideMenu() {
            $treeContent.fadeOut("fast");
            $("body").unbind("mousedown", onBodyDown);
        }

        function onBodyDown(event) {
            if (!(event.target.id == treeContent || $(event.target).parents($treeContent).length > 0)) {
                hideMenu();
            }
            $btn.each(function() {
                if(!(event.target.id == this.id)) {
                    hideMenu();
                    return;
                }
            });
        }

        $.fn.zTree.init($("#" + treeSelect), setting, zNodes);

        $btn.click(function () {
            showMenu();
        });
    }

    initSelectTree($("#selectTree,#targetname"), "targetId", "targetname");

    var validationEngine = $("#moveForm").validationEngine({
        validationEventTrigger : "submit"
    });

    $("#toMaintain").click(function() {
        location.href = '${ctx}/showcase/tree/maintain/${source.id}';
    });

    $("#moveAsPrev").click(function() {
        $("#moveType").val("prev");
    });
    $("#moveAsNext").click(function() {
        $("#moveType").val("next");
    });
    $("#moveAsInner").click(function() {
        $("#moveType").val("inner");
    });


});
</script>
