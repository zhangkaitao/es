<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jspf"%>
<table class="sort-table table table-bordered table-hover">
      <thead>
        <tr>
            <th>资源</th>
            <th>权限</th>
        </tr>
      </thead>
      <tbody>
      <c:if test="${empty role.resourcePermissions}">
          <tr>
              <td colspan="2">无</td>
          </tr>
      </c:if>
      <c:forEach items="${role.resourcePermissions}" var="o">
          <tr>
              <td>
                  <sys:showResourceName id="${o.resourceId}"/>
              </td>
              <td>
                  <c:forEach items="${o.permissionIds}" var="permissionId" varStatus="status">
                      <c:if test="${status.count > 1}">,</c:if>
                      <sys:showPermissionName id="${permissionId}"/>
                  </c:forEach>
              </td>
          </tr>
      </c:forEach>
      </tbody>
  </table>
