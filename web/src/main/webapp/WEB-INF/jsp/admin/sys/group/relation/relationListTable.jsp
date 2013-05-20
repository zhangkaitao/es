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
        <c:if test="${group.type eq 'user'}">
            <th sort="userId" style="width: 80px">用户编号</th>
            <th>用户名称</th>
        </c:if>
        <c:if test="${group.type eq 'organization'}">
            <th sort="organization.id" style="width: 80px">用户编号</th>
            <th sort="organization.name">用户名称</th>
        </c:if>
    </tr>
    <tbody>
    <c:forEach items="${page.content}" var="m">
        <%--如果组织机构删除了 就没必要显示了--%>
        <c:if test="${group.type eq 'organization' and esfn:existsOrganization(m.organizationId, onlyDisplayShow)}">
        <tr>
            <td class="check"><input type="checkbox" name="ids" value="${m.id}"></td>
            <c:if test="${group.type eq 'user'}">
                <td>
                    <c:if test="${not empty m.userId}">
                        <a class="btn btn-link btn-edit btn-view-user" data-id="${m.userId}">
                            <sys:showUsername id="${m.userId}"/>
                        </a>
                    </c:if>
                    <c:if test="${empty m.userId}">
                        <a class="btn btn-link btn-range-search-user"
                           data-start-user-id="${m.startUserId}" data-end-user-id="${m.endUserId}">
                            [${m.startUserId}, ${m.endUserId}]
                        </a>
                    </c:if>
                </td>
                <td>
                    <c:if test="${not empty m.userId}">
                        <sys:showUsername id="${m.userId}"/>
                    </c:if>
                    <c:if test="${empty m.userId}">
                        <a class="btn btn-link btn-range-search-user" data-start-user-id="${m.startUserId}" data-end-user-id="${m.endUserId}">
                            点击查询[${m.startUserId}, ${m.endUserId}]范围内用户信息
                        </a>
                    </c:if>
                </td>
            </c:if>

            <c:if test="${group.type eq 'organization'}">
                <td>
                    <a class="btn btn-link btn-edit" href="${ctx}/admin/sys/group/${m.organizationId}">
                        ${m.organizationId}
                    </a>
                </td>
                <td><sys:showOrganizationName id="${m.organizationId}"/></td>
            </c:if>
        </tr>
    </c:if>
    </c:forEach>
    </tbody>
</table>
<es:page page="${page}"/>
