<%@ tag pageEncoding="UTF-8" description="显示字段错误消息" %>
<%@ attribute name="commandName" type="java.lang.String" required="true" description="命令对象名称" %>
<%@ attribute name="errorPosition" type="java.lang.String" required="false" description="错误消息位置，可以是 topLeft, topRight, bottomLeft, centerRight, bottomRight" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<c:if test="${empty errorPosition}">
    <c:set var="errorPosition" value="topRight"/>
</c:if>
<spring:hasBindErrors name="${commandName}">
    <c:if test="${errors.fieldErrorCount > 0}">
    <c:forEach items="${errors.fieldErrors}" var="error">
        <spring:message var="message" code="${error.code}" arguments="${error.arguments}" text="${error.defaultMessage}"/>
        <c:if test="${not empty message}">
            $("[name='${error.field}']")
                    .validationEngine("showPrompt", "${message}", "error", "${errorPosition}", true)
                    .validationEngine("updatePromptsPosition");
        </c:if>
    </c:forEach>
    </c:if>
</spring:hasBindErrors>
