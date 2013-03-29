<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jspf"%>
<es:contentHeader/>

<div data-table="table" class="panel">

    <es:showMessage/>


    <ul class="nav nav-pills tool ui-toolbar">
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
                    删除
                </a>
                <div class="btn-group">
                    <!-- 第一个忽略掉 否则有圆角 -->
                    <a></a>
                    <a class="btn dropdown-toggle" data-toggle="dropdown" href="#">
                        更多操作
                        <span class="caret"></span>
                    </a>
                    <ul class="dropdown-menu">
                        <li>
                            <a class="btn btn-link change-password">改密</a>
                        </li>
                        <li>
                            <a class="btn btn-link block-user">封禁用户</a>
                        </li>
                        <li>
                            <a class="btn btn-link unblocked-user">解封用户</a>
                        </li>
                        <li>
                            <a class="btn btn-link recycle">还原删除的用户</a>
                        </li>
                        <li class="divider"></li>
                        <li>
                            <a class="btn btn-link status-history">状态变更历史</a>
                        </li>
                        <li>
                            <a class="btn btn-link last-online-info">最后在线历史</a>
                        </li>

                    </ul>
                </div>
            </div>
        </div>
        <div class="span8">
            <%@include file="searchForm.jsp" %>
        </div>
    </div>

    <table id="table" class="sort-table table table-bordered table-hover">
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
                <th>管理员</th>
            </tr>
          <tbody>
          <c:forEach items="${page.content}" var="m">
            <tr>
                <td class="check">
                    <input type="checkbox" name="ids" value="${m.id}" data-status="${m.status}" data-deleted="${m.deleted}"/>
                </td>
                <td>
                    <a href="${ctx}/admin/sys/user/${m.id}">${m.id}</a>
                </td>
                <td>${m.username}</td>
                <td>${m.email}</td>
                <td>${m.mobilePhoneNumber}</td>
                <td><spring:eval expression="m.createDate"/></td>
                <td>${m.status.info}</td>
                <td>${m.admin?'是' : '否'}</td>
            </tr>
          </c:forEach>
          </tbody>
    </table>
    <es:page page="${page}"/>
</div>
<es:contentFooter/>
<script type="text/javascript">
    $(function() {
        $(".change-password").click(function() {
            var checkbox = $.table.getAllSelectedCheckbox($(".table"));
            if(checkbox.size() == 0) return;
            var id = checkbox.val();
            var url = "${ctx}/admin/sys/user/changePassword?" + checkbox.serialize();

            $.app.confirm({
                title: "修改密码",
                message : "请输入新密码：<br/><input type='password' id='password' class='input-medium'/>",
                ok : function() {
                    var password = $("#password").val();
                    if(password) {
                        window.location.href = url + "&newPassword=" + password;
                    }
                }
            });
        });

        $(".block-user,.unblocked-user").click(function() {
            var checkbox = $.table.getAllSelectedCheckbox($(".table"));
            if(checkbox.size() == 0) return;
            var id = checkbox.val();
            var status = $(this).is(".unblocked-user") ? "normal" : "blocked";
            var url = "${ctx}/admin/sys/user/changeStatus/" + status + "?" + checkbox.serialize();

            var title = status == 'blocked' ? "封禁用户" : "解封用户";
            var tip = status == 'blocked' ? "请输入封禁原因:" : "请输入解封原因：";

            $.app.confirm({
                title: title,
                message : tip + "<br/><textarea id='reason' style='width: 300px;height: 50px;'></textarea>",
                ok : function() {
                    var reason = $("#reason").val();
                    if(reason) {
                        window.location.href = url + "&reason=" + reason;
                    }
                }
            });
        });
        $(".status-history").click(function() {
            var checkbox = $.table.getAllSelectedCheckbox($(".table"));
            if(checkbox.size() == 0) return;
            var ids = $.app.joinVar(checkbox.val());
            var url = "${ctx}/admin/sys/user/statusHistory?search.user.id_eq=" + ids;

            $.app.modalDialog("状态改变历史", url, {width : 800});
        });
        $(".last-online-info").click(function() {

            var checkbox = $.table.getAllSelectedCheckbox($(".table"));
            if(checkbox.size() == 0) return;
            var ids = $.app.joinVar(checkbox.val());
            var url = "${ctx}/admin/sys/user/lastOnline?search.userId_eq=" + ids;

            $.app.modalDialog("最后在线历史", url, {width : 800});
        });


        $(".recycle").click(function() {

            var checkbox = $.table.getAllSelectedCheckbox($(".table"));
            if(checkbox.size() == 0) return;


            var url = "${ctx}/admin/sys/user/recycle?" + checkbox.serialize();
            $.app.confirm({
                title : "欢迎删除的用户",
                message : "确认还原吗？",
                ok : function() {
                    window.location.href = url;
                }
            });
        });

    });
</script>
