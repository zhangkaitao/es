<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jspf"%>
<es:contentHeader/>
<div data-table="table" class="panel">

    <ul class="nav nav-tabs">
        <li <c:if test="${empty type}">class="active"</c:if>>
            <a href="${ctx}/admin/sys/group/${group.id}/listRelation">
                <i class="icon-table"></i>
                查看组 &gt; ${group.type.info} &gt; ${group.name}
            </a>
        </li>
        <li>
            <a href="${ctx}/admin/sys/group">
                <i class="icon-reply"></i>
                返回分组列表
            </a>
        </li>
    </ul>

    <es:showMessage/>

    <div class="row-fluid tool ui-toolbar">
        <div class="span4">
            <div class="btn-group">
                <a class="btn btn-custom" href="${ctx}/admin/sys/group/${group.id}/batch/append">
                    <span class="icon-file"></span>
                    批量新增
                </a>
                <a class="btn btn-custom btn-batch-delete">
                    <i class="icon-trash"></i>
                    删除
                </a>
            </div>
        </div>
        <div class="span8">
            <%@include file="relationSearchForm.jsp"%>
        </div>
    </div>
    <%@include file="relationListTable.jsp"%>

</div>

<es:contentFooter/>
<script type="text/javascript">
    $(function() {
        $(".btn-batch-delete").off("click").on("click", function() {
            var table = $("#table");
            var checkbox = $.table.getAllSelectedCheckbox(table);
            if(!checkbox.length) {
                return;
            }

            $.app.confirm({
                title: "确认删除吗？",
                message : "确认删除吗",
                ok : function() {
                    var url = "${ctx}/admin/sys/group/${group.id}/batch/delete?" + checkbox.serialize();
                    window.location.href = url + "&BackURL=" + $.table.encodeTableURL(table);
                }
            });
        });

        $(".btn-view-user").off("click").on("click", function() {
            var userId = $(this).data("id");
            window.location.href="${ctx}/admin/sys/user/" + userId;
        });



        $(".btn-range-search-user").off("click").on("click", function() {
            var startUserId = $(this).data("start-user-id");
            var endUserId = $(this).data("end-user-id");

            var title = "[" + startUserId + ", " + endUserId + "]范围内用户信息";
            var url = "${ctx}/admin/sys/user/0/0?search.id_gte=" + startUserId + "&search.id_lte=" + endUserId;

            $.tabs.activeTab($.tabs.nextCustomTabIndex(), title, url, true);

        });

    });
</script>
