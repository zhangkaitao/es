<%@ tag pageEncoding="UTF-8" description="显示操作成功的消息，内容为:message" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:if test="${not empty message}">
    <div class="alert alert-success">
        <button type="button" class="close" data-dismiss="alert">&times;</button>
        <span class="icon-ok"></span>${message}
    </div>
</c:if>