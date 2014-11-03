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
        <th style="width: 100px" sort="id">任务名称</th>
        <th style="width: 100px" sort="name">cron表达式</th>
        <th>任务Bean名称/任务全限定类名</th>
        <th>任务方法名</th>
        <th>是否已启动</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${page.content}" var="m">
        <tr>
            <td class="check"><input type="checkbox" name="ids" value="${m.id}"></td>
            <td>
                <a class="btn btn-link btn-edit" href="${ctx}/admin/maintain/dynamicTask/${m.id}/update">${m.name}</a>
            </td>
            <td>${m.cron}</td>
            <td>${m.beanName}/${m.beanClass}</td>
            <td>${m.methodName}</td>
            <td>
                ${m.start ? '已启动' : '未启动'}
                <c:if test="${not empty description}">(${description})</c:if>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<es:page page="${page}"/>
