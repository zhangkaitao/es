<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jspf"%>
<table id="table" class="sort-table table table-bordered table-hover">
    <thead>
    <tr>
        <th style="width: 80px;">
            <a class="check-all" href="javascript:;">全选</a>
            |
            <a class="reverse-all" href="javascript:;">反选</a>
        </th>
        <th sort="id">编号</th>
        <th sort="name">名称</th>
        <th>状态</th>
        <th>备注</th>
        <th style="width: 50px">操作</th>
    </tr>
    <tbody>
    <c:forEach items="${page.content}" var="m">
        <tr>
            <td class="check"><input type="checkbox" name="ids" value="${m.id}"></td>
            <td>
                <a class="btn btn-link btn-edit" href="${ctx}/showcase/status/audit/${m.id}">${m.id}</a>
            </td>
            <td>${m.name}</td>
            <td>${m.status.info}</td>
            <td>${m.comment}</td>
            <td>
                <a class="btn btn-link btn-edit" title="修改" href="${ctx}/showcase/status/audit/update/${m.id}">
                    <span class=" icon-edit"></span>
                </a>
                <a class="btn btn-link btn-edit" title="删除" href="${ctx}/showcase/status/audit/delete/${m.id}">
                    <span class=" icon-trash"></span>
                </a>
                <br/>
                <c:if test="${m.status eq 'waiting'}">
                    <div class="btn-group">
                        <a class="btn btn-link btn-custom btn-mini dropdown-toggle margin-1" data-toggle="dropdown" href="#">
                            审核
                            <span class="caret"></span>
                        </a>
                        <ul class="dropdown-menu left">
                            <li>
                                <a id="auditSuccess" href="javascript:;" data-url="${ctx}/showcase/status/audit/${m.id}/success">通过</a>
                            </li>
                            <li>
                                <a id="auditFail" href="javascript:;" data-url="${ctx}/showcase/status/audit/${m.id}/fail">拒绝</a>
                            </li>
                        </ul>
                    </div>
                </c:if>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<es:page page="${page}"/>
<script type="text/javascript">
    $(function() {
        $("#auditSuccess,#auditFail").click(function() {
            var url = $(this).attr("data-url");
            $.app.confirm({
                message : "请输入审核结果：<br/><textarea id='comment' style='width: 300px;height: 50px;'></textarea>",
                ok : function() {
                    var comment = $("#comment").val();
                    var table = $("#table");
                    $.table.reloadTable(table, url + "?comment=" + comment, $.table.tableURL(table));
                }
            });
        });
    });
</script>

