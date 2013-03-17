<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jspf"%>
<es:contentHeader/>
<ul class="nav nav-pills">
    <li <c:if test="${param['search.deleted_eq'] ne 'true' and param['search.status_eq'] ne 'blocked'}">class="active"</c:if>>
        <a href="${ctx}/admin/sys/user">用户列表</a>
    </li>
    <li <c:if test="${param['search.deleted_eq'] eq 'true'}">class="active"</c:if>>
        <a href="${ctx}/admin/sys/user?search.deleted_eq=true">已删除用户列表</a>
    </li>
    <li <c:if test="${param['search.status_eq'] eq 'blocked'}">class="active"</c:if>>
        <a href="${ctx}/admin/sys/user?search.status_eq=blocked">已封禁用户列表</a>
    </li>
</ul>

<es:showMessage/>

<div data-table="table">
    <div class="row-fluid tool ui-toolbar">
        <div class="span4">
            <div class="btn-group">
                <a class="btn btn-create">
                    <span class="icon-file"></span>
                    新增
                </a>
                <a id="update" class="btn btn-update">
                    <span class="icon-edit"></span>
                    修改
                </a>
                <a class="btn btn-batch-delete">
                    <span class="icon-trash"></span>
                    批量删除
                </a>
            </div>
        </div>
        <div class="span8">
            <%@include file="searchForm.jsp" %>
        </div>
    </div>
    <table id="table" class="sort-table table table-bordered table-hover table-striped">
          <thead>
            <tr>
                <th style="width: 80px;">
                    <a class="check-all" href="javascript:;">全选</a>
                    |
                    <a class="reverse-all" href="javascript:;">反选</a>
                </th>
                <th sort="id">编号</th>
                <th sort="username">用户名</th>
                <th sort="email">邮箱</th>
                <th sort="mobilePhoneNumber">手机号</th>
                <th>创建时间</th>
                <th>帐户状态</th>
                <th style="width: 190px">操作</th>
            </tr>
          <tbody>
          <c:forEach items="${page.content}" var="m">
            <tr>
                <td class="check"><input type="checkbox" name="ids" value="${m.id}"></td>
                <td>
                    <a href="${ctx}/admin/sys/user/${m.id}">${m.id}</a>
                </td>
                <td>${m.username}</td>
                <td>${m.email}</td>
                <td>${m.mobilePhoneNumber}</td>
                <td><spring:eval expression="m.createDate"/></td>
                <td>${m.status.info}</td>
                <td>
                    <a class="btn btn-link no-padding" href="${ctx}/admin/sys/user/update/${m.id}">修改</a>
                    |
                    <c:choose>
                        <c:when test="${m.deleted == false}">
                            <a class="btn btn-link no-padding" title="删除" href="${ctx}/admin/sys/user/delete/${m.id}">删除</a>
                        </c:when>
                        <c:otherwise>
                            <a class="btn btn-link no-padding recycle" data-url="${ctx}/admin/sys/user/recycle/${m.id}">还原</a>
                        </c:otherwise>
                    </c:choose>
                    |
                    <a class="btn btn-link no-padding change-password" data-url="${ctx}/admin/sys/user/changePassword/${m.id}">改密</a>
                    |
                    <c:choose>
                        <c:when test="${m.status eq 'normal'}">
                            <a class="btn btn-link no-padding block-user" data-url="${ctx}/admin/sys/user/changeStatus/${m.id}/blocked">封禁</a>
                        </c:when>
                        <c:otherwise>
                            <a class="btn btn-link no-padding unblock-user" data-url="${ctx}/admin/sys/user/changeStatus/${m.id}/normal">解封</a>
                        </c:otherwise>
                    </c:choose>
                    <br/>
                    <a class="btn btn-link no-padding status-history" data-url="${ctx}/admin/sys/user/statusHistory?search.user.username_eq=${m.username}">状态变更历史</a>
                    |
                    <a class="btn btn-link no-padding last-online-info" data-url="${ctx}/admin/sys/user/lastOnlineInfo?search.user.username_eq=${m.username}">最后在线历史</a>
                </td>
            </tr>
          </c:forEach>
          </tbody>
    </table>
    <es:page page="${page}" />
</div>
<es:contentFooter/>
<script type="text/javascript">
    $(function() {
        $(".change-password").click(function() {
            var url = $(this).attr("data-url");
            $.app.confirm({
                message : "请输入新密码：<br/><input type='password' id='password' class='input-medium'/>",
                ok : function() {
                    var password = $("#password").val();
                    if(password) {
                        window.location.href = url + "?newPassword=" + password;
                    }
                }
            });
        });

        $(".block-user,.unblock-user").click(function() {
            var url = $(this).attr("data-url");
            var tip = $(this).is(".block-user") ? "请输入封禁原因:" : "请输入解封原因：";
            $.app.confirm({
                message : tip + "<br/><textarea id='reason' style='width: 300px;height: 50px;'></textarea>",
                ok : function() {
                    var reason = $("#reason").val();
                    if(reason) {
                        window.location.href = url + "?reason=" + reason;
                    }
                }
            });
        });
        $(".recycle").click(function() {
            var url = $(this).attr("data-url");
            $.app.confirm({
                message : "确认还原吗？",
                ok : function() {
                    window.location.href = url;
                }
            });
        });

        $(".status-history").click(function() {
            $.app.modalDialog("状态改变历史", $(this).attr("data-url"));
        });
        $(".last-online-info").click(function() {
            $.app.modalDialog("最后在线历史", $(this).attr("data-url"), {width:1000});
        });
    });
</script>
