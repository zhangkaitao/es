<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jspf"%>
<table class="sort-table table table-bordered table-hover">
      <thead>
        <tr>
            <th sort="id">编号</th>
            <th sort="name">名称</th>
            <th>类型</th>
            <th>开始时间</th>
            <th>结束时间</th>
            <th>是否显示</th>
        </tr>
      </thead>
      <tbody>
      <c:if test="${empty page.content}">
          <tr>
              <td colspan="6">无</td>
          </tr>
      </c:if>
      <c:forEach items="${page.content}" var="child">
        <tr>
            <td>${child.id}</td>
            <td>${child.name}</td>
            <td>${child.type.info}</td>
            <td><spring:eval expression="child.beginTime"/></td>
            <td><spring:eval expression="child.endTime"/></td>
            <td>${child.show ? '是' : '否'}</td>
        </tr>
      </c:forEach>
      </tbody>
  </table>
