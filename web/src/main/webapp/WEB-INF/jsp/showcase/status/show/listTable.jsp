<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<table id="table" class="sort-table table table-bordered table-hover table-striped">
    <thead>
    <tr>
        <th style="width: 80px;">
            <a class="check-all" href="javascript:;">全选</a>
            |
            <a class="reverse-all" href="javascript:;">反选</a>
        </th>
        <th sort="id">编号</th>
        <th sort="name">名称</th>
        <th>是否显示</th>
        <th style="width: 50px">操作</th>
    </tr>
    <tbody>
    <c:forEach items="${page.content}" var="m">
        <tr>
            <td class="check"><input type="checkbox" name="ids" value="${m.id}"></td>
            <td>
                <a class="btn btn-link" href="${ctx}/showcase/status/show/${m.id}">${m.id}</a>
            </td>
            <td>${m.name}</td>
            <td>${m.status.info}</td>
            <td>
                <a class="btn btn-link btn-edit" title="修改" href="${ctx}/showcase/status/show/update/${m.id}">
                    <span class=" icon-edit"></span>
                </a>
                <a class="btn btn-link btn-edit" title="删除" href="${ctx}/showcase/status/show/delete/${m.id}">
                    <span class=" icon-trash"></span>
                </a>
                <br/>
                <c:if test="${m.status eq 'hide'}">
                    <a href="${ctx}/showcase/status/show/${m.id}/show" class="btn btn-link btn-mini margin-1">显示</a>
                </c:if>
                <c:if test="${m.status eq 'show'}">
                    <a href="${ctx}/showcase/status/show/${m.id}/hide" class="btn btn-link btn-mini margin-1">隐藏</a>
                </c:if>
            </td>
        </tr>
    </c:forEach>
    </tbody>
    <tfoot>
    <tr>
        <td colspan="1000">
            <es:page page="${page}" />
        </td>
    </tr>
    </tfoot>
</table>
