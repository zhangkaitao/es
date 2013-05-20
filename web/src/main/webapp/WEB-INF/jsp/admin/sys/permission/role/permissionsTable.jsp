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
      <c:set var="count" value="0"/>
      <c:forEach items="${role.resourcePermissions}" var="o">
      <c:if test="${esfn:existsResource(o.resourceId, onlyDisplayShow)}">
          <c:set var="count" value="${count+1}"/>
          <tr>
              <td>
                  <sys:showResourceName id="${o.resourceId}"/>
              </td>
              <td>
                  <span style="line-height: 30px;">
                  <c:set var="permissionCount" value="0"/>
                  <c:forEach items="${o.permissionIds}" var="permissionId" varStatus="status">
                  <c:if test="${esfn:existsPermission(permissionId, onlyDisplayShow)}">
                      <c:set var="permissionCount" value="${permissionCount + 1}"/>
                      <c:if test="${permissionCount > 1}">|</c:if>
                      <sys:showPermissionName id="${permissionId}"/>
                  </c:if>
                  </c:forEach>
                  </span>
              </td>
          </tr>
      </c:if>
      </c:forEach>
      <c:if test="${count eq 0}">
          <tr>
              <td colspan="2">无</td>
          </tr>
      </c:if>
      </tbody>
  </table>
