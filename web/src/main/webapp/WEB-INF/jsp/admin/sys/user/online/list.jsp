<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jspf"%>
<es:contentHeader/>

<div data-table="table" class="panel">


    <ul class="nav nav-pills tool ui-toolbar ">
        <li <c:if test="${empty param['search.userId_eq'] and empty param['search.userId_gt']}">class="active"</c:if>>
            <a href="${ctx}/admin/sys/user/online">所有</a>
        </li>

        <li <c:if test="${not empty param['search.userId_gt']}">class="active"</c:if>>
            <a href="${ctx}/admin/sys/user/online?search.userId_gt=0">在线用户</a>
        </li>
        <li <c:if test="${not empty param['search.userId_eq']}">class="active"</c:if>>
            <a href="${ctx}/admin/sys/user/online?search.userId_eq=0">游客</a>
        </li>
    </ul>


    <div class="row-fluid tool ui-toolbar">
        <div class="span4">
            <div class="btn-group">
                <a class="btn btn-force-logout">
                    <span class="icon-file"></span>
                    强制退出
                </a>
            </div>
        </div>
        <div class="span8">
            <%@include file="searchForm.jsp" %>
        </div>
    </div>

    <%@include file="listTable.jsp"%>
</div>
<es:contentFooter/>
<script type="text/javascript">
    $(function() {
        $(".btn-force-logout").click(function() {
            var checkbox = $.table.getAllSelectedCheckbox($(".table"));
            if(checkbox.size() == 0) {
                return;
            }
            $.app.confirm({
                message : "确认强制退出吗？",
                ok : function() {
                    var url = "${ctx}/admin/sys/user/online/forceLogout?" + checkbox.serialize();
                    window.location.href = url;
                }
            });
        });


    });

</script>