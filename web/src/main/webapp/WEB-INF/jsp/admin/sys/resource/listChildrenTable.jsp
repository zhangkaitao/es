<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jspf"%>
<table id="table" class="sort-table table table-bordered table-hover" data-async="true">
    <thead>
    <tr>
        <th style="width: 80px">
            <a class="check-all" href="javascript:;">全选</a>
            |
            <a class="reverse-all" href="javascript:;">反选</a>
        </th>
        <th style="width: 60px" sort="id">编号</th>
        <th style="width: 150px" sort="name">名称</th>
        <th sort="identity">资源标识</th>
        <th sort="url">URL地址</th>
        <th style="width: 65px">是否显示</th>
    </tr>
    <tbody>
    <c:forEach items="${page.content}" var="m">
        <tr>
            <td class="check"><input type="checkbox" name="ids" value="${m.id}" root="${m.root}"></td>
            <td>
                <a class="btn btn-link no-padding" href="${ctx}/admin/sys/resource/${m.id}">${m.id}</a>
            </td>
            <td>
                <sys:showResourceName id="${m.id}"/>
            </td>
            <td>
                <div style="width: 80px;overflow: hidden" data-toggle="tooltip" title="${m.identity}">${m.identity}</div>
            </td>
            <td>
                <div style="width: 80px;overflow: hidden" data-toggle="tooltip" title="${m.url}">${m.url}</div>
            </td>
            <td>${m.show ? '是' : '否'}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<es:page page="${page}"/>

