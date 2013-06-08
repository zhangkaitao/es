<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jspf"%>
<es:contentHeader/>
<%@include file="/WEB-INF/jsp/common/import-zTree-css.jspf"%>

<div class="panel">
<%@include file="nav.jspf"%>

<es:showMessage/>

<form id="moveForm" method="post" class="form-horizontal">
    <es:BackURL hiddenInput="true"/>
    <input type="hidden" id="moveType" name="moveType" value="">

        <div class="control-group">
            <label class="control-label">源节点</label>
            <div class="controls">
                <span class="help-inline" style="padding: 4px;">
                    <maintain:showIcon iconIdentity="${source.icon}"/>
                    ${source.name}
                </span>
            </div>
        </div>

        <div class="control-group">
            <label class="control-label">目标节点</label>
            <div class="controls input-append" title="选择目标节点">
                <input type="hidden" id="targetId" name="targetId" value="${target.id}">
                <input type="text" id="targetName" name="targetName" value="${target.name}" class="validate[required]" readonly="readonly">
                <a id="selectTree"  href="javascript:;">
                    <span class="add-on"><i class="icon-chevron-down"></i></span>
                </a>
            </div>
        </div>


        <div class="control-group">
            <label class="control-label">移动到目标节点</label>
            <div class="controls">
                <button id="moveAsPrev" type="submit" class="btn btn-primary">
                    <i class="icon-chevron-left"></i>
                    之前
                </button>
                <button id="moveAsNext" type="submit" class="btn btn-primary">
                    <i class="icon-chevron-right"></i>
                    之后
                </button>
                <button id="moveAsInner" type="submit" class="btn btn-primary">
                    <i class="icon-chevron-down"></i>
                    孩子节点
                </button>
            </div>
        </div>
</form>
</div>

<es:contentFooter/>
<%@include file="/WEB-INF/jsp/common/import-zTree-js.jspf"%>
<script type="text/javascript">
$(function () {
    $.zTree.initSelectTree({
        zNodes : null,
        urlPrefix : "${ctx}/admin/sys/organization/job",
        excludeId: "${source.id}",
        async : true,
        select : {
            btn : $("#selectTree,#targetName"),
            id : "targetId",
            name : "targetName"
        },
        autocomplete : {
            enable : true
        }
    });

    $.zTree.initMoveBtn();


    var validationEngine = $("#moveForm").validationEngine({
        validationEventTrigger : "submit"
    });

});
</script>
