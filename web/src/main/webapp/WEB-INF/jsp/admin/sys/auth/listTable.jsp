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
        <th style="width: 100px" sort="id">编号</th>
        <c:if test="${type eq 'user'}">
            <th style="width: 150px" sort="userId">用户名</th>
        </c:if>
        <c:if test="${type eq 'user_group'}">
            <th style="width: 150px" sort="groupId">用户组名称</th>
        </c:if>
        <c:if test="${type eq 'organization_job'}">
            <th style="width: 150px" sort="organizationId">组织机构</th>
            <th style="width: 150px" sort="jobId">工作职务</th>
        </c:if>
        <c:if test="${type eq 'organization_group'}">
            <th style="width: 150px" sort="groupId">组织机构组</th>
        </c:if>

        <th>角色列表</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${page.content}" var="m">
        <tr>
            <td class="check"><input type="checkbox" name="ids" value="${m.id}"></td>
            <td>
                <a class="btn btn-link btn-edit" href="${ctx}/admin/sys/auth/${m.id}">${m.id}</a>
            </td>
            <c:if test="${type eq 'user'}">
                <td><sys:showUsername id="${m.userId}"/></td>
            </c:if>
            <c:if test="${type eq 'user_group'}">
                <td><sys:showGroupName id="${m.groupId}"/></td>
            </c:if>
            <c:if test="${type eq 'organization_job'}">

                <td><sys:showOrganizationName id="${m.organizationId}"/></td>
                <td><sys:showJobName id="${m.jobId}"/></td>
            </c:if>
            <c:if test="${type eq 'organization_group'}">
                <td><sys:showGroupName id="${m.groupId}"/></td>
            </c:if>

            <td>
                <span style="line-height: 30px">
                <c:set var="count" value="0"/>
                <c:forEach items="${m.roleIds}" var="roleId">
                <c:if test="${esfn:existsRole(roleId, onlyDisplayShow)}">
                    <c:set var="count" value="${count + 1}"/>
                    <c:if test="${count > 1}">|</c:if>
                    <sys:showRoleName id="${roleId}"/>
                </c:if>
                </c:forEach>
                </span>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<es:page page="${page}"/>
