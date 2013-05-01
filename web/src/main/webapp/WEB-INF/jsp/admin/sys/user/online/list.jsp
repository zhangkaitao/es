<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jspf"%>
<es:contentHeader/>

<div data-table="table" class="panel">


    <ul class="nav nav-tabs">
        <li <c:if test="${empty param['search.userId_eq'] and empty param['search.userId_gt']}">class="active"</c:if>>
            <a href="${ctx}/admin/sys/user/online">
                <i class="icon-table"></i>
                所有用户列表
            </a>
        </li>

        <li <c:if test="${not empty param['search.userId_gt']}">class="active"</c:if>>
            <a href="${ctx}/admin/sys/user/online?search.userId_gt=0">
                <i class="icon-table"></i>
                登录用户列表
            </a>
        </li>
        <li <c:if test="${not empty param['search.userId_eq']}">class="active"</c:if>>
            <a href="${ctx}/admin/sys/user/online?search.userId_eq=0">
                <i class="icon-table"></i>
                匿名游客列表
            </a>
        </li>
    </ul>


    <div class="row-fluid tool ui-toolbar">
        <div class="span4">
            <div class="btn-group">
                <a class="btn btn-force-logout">
                    <span class="icon-lightbulb"></span>
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
<%@include file="/WEB-INF/jsp/common/admin/import-sys-js.jspf"%>
<script type="text/javascript">
    $(function() {
        $.sys.user.initOnlineListButton();
    });

</script>