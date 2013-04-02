<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jspf"%>
<table id="table" class="sort-table table table-bordered table-hover " data-async="true">
    <thead>
    <tr>
        <th style="width: 80px">
            <a class="check-all" href="javascript:;">全选</a>
            |
            <a class="reverse-all" href="javascript:;">反选</a>
        </th>
        <th sort="id">编号</th>
        <th sort="name">名称</th>
        <th>附件</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${page.content}" var="m">
        <tr>
            <td class="check"><input type="checkbox" name="ids" value="${m.id}"/></td>
            <td>
                <a href="${ctx}/showcase/upload/${m.id}">${m.id}</a>
            </td>
            <td>${m.name}</td>
            <td><es:showAttachment filename="${m.src}" showImage="true" width="auto" height="160"/></td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<es:page page="${page}"/>