<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jspf"%>
<es:contentHeader/>
<div data-table="table" class="panel"  data-state="${state}" data-state-info="${state.info}">

    <ul class="nav nav-tabs">
        <li class="active">
            <a href="${ctx}/admin/personal/notification">
                <i class="icon-table"></i>
                我的通知
            </a>
        </li>
    </ul>


    <div class="row-fluid tool ui-toolbar">

        <table id="table" class="sort-table table table-hover">
            <thead>
            <tr>
                <th sort="system" style="width: 100px;">所属系统</th>
                <th sort="date" style="width: 120px;">时间</th>
                <th>内容</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${page.content}" var="m">
                <tr>
                    <td>${m.system.info}</td>
                    <td><pretty:prettyTime date="${m.date}"/></td>
                    <td>
                        ${fn:replace(m.content, "{ctx}", ctx)}
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <es:page page="${page}"/>


    </div>
</div>
<es:contentFooter/>
