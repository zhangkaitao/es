<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jspf"%>
<es:contentHeader/>
<%@include file="/WEB-INF/jsp/common/import-zTree-css.jspf"%>
<div class="panel">

    <ul class="nav nav-tabs">
        <li class="active">
            <a href="${ctx}/admin/sys/group/${group.id}/batch/append?BackURL=<es:BackURL/>">
                <i class="icon-file-alt"></i>
                ${group.type.info}-批量新增
            </a>
        </li>

        <li>
            <a href="<es:BackURL/>" class="btn btn-link">
                <i class="icon-reply"></i>
                返回
            </a>
        </li>
    </ul>

    <form id="editForm" method="post" class="form-horizontal">

        <es:BackURL hiddenInput="true"/>

        <div class="control-group">
            <label for="organizationName" class="control-label">组织机构</label>
            <div class="controls input-append" title="选择组织机构">
                <input type="hidden" id="organizationId" name="ids">
                <input type="text" id="organizationName" class="input-medium" readonly="readonly">
                <a id="selectOrganizationTree">
                    <span class="add-on"><i class="icon-chevron-down"></i></span>
                </a>
            </div>
        </div>
        <div class="control-group" style="color: #777;">
            <label for="selectedOrganizationName" class="control-label">当前选择的组织机构</label>
            <div class="controls">
                <textarea id="selectedOrganizationName" class="input-xxlarge" rows="3" readonly="readonly"></textarea>
            </div>
        </div>



        <div class="control-group">
            <label class="control-label"></label>
            <div class="controls">
                <button type="submit" class="btn btn-primary">
                    <i class="icon-file-alt"></i>
                    批量新增
                </button>
                <a href="<es:BackURL/>" class="btn">
                    <i class="icon-reply"></i>
                    返回
                </a>
            </div>
        </div>


    </form>
</div>
<es:contentFooter/>
<%@include file="/WEB-INF/jsp/common/import-zTree-js.jspf"%>

<script type="text/javascript">
$(function() {
    var organizationTreeId = $.zTree.initSelectTree({
        zNodes : [],
        nodeType : "default",
        fullName:true,
        urlPrefix : "${ctx}/admin/sys/organization/organization",
        async : true,
        asyncLoadAll : true,
        onlyDisplayShow: true,
        lazy : true,
        select : {
            btn : $("#selectOrganizationTree,#organizationName"),
            id : "organizationId",
            name : "organizationName",
            includeRoot: true
        },
        autocomplete : {
            enable : true
        },
        setting :{
            check : {
                enable:true,
                chkStyle:"checkbox",
                chkboxType: { "Y": "", "N": "s" },
                onlyCheckLeaf : false,
                onlySelectLeaf : false
            }
        }
    });
    $("#organizationName").change(function() {
        $("#selectedOrganizationName").val($(this).val().replace(/,/g, "\n"));
    });
});


</script>
