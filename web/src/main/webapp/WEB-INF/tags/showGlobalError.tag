<%@ tag pageEncoding="UTF-8" description="显示全局错误消息" %>
<%@ attribute name="commandName" type="java.lang.String" required="true" description="命令对象名称" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<spring:hasBindErrors name="${commandName}">
  <c:if test="${errors.globalErrorCount > 0}">
  <div class="alert alert-error">
  <button type="button" class="close" data-dismiss="alert">&times;</button>
  <c:forEach items="${errors.globalErrors}" var="error">
      <spring:message var="message" code="${error.code}" arguments="${error.arguments}" text="${error.defaultMessage}"/>
      <c:if test="${not empty message}">
          ${message}<br/>
      </c:if>
  </c:forEach>
  </div>
  </c:if>
</spring:hasBindErrors>
