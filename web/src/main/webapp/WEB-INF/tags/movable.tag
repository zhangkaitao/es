<%@tag pageEncoding="UTF-8" description="可移动图标" %>
<%@ attribute name="page" type="org.springframework.data.domain.Page" required="true" description="分页内容" %>
<%@ attribute name="first" type="java.lang.Boolean" required="true" description="是否是分页中的第一个" %>
<%@ attribute name="last" type="java.lang.Boolean" required="true" description="是否是分页中的最后一个" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="moveable ui-widget icon-collection">
    <c:choose>
        <c:when test="${not(page.lastPage and last)}">
            <a href="#" class="ui-icon ui-icon-carat-1-s down-btn" title="下移"></a>
        </c:when>
        <c:otherwise>
            <a class="none"></a>
        </c:otherwise>
    </c:choose>
    <c:choose>
        <c:when test="${not(page.firstPage and first)}">
            <a href="#" class="ui-icon ui-icon-carat-1-n up-btn" title="上移"></a>
        </c:when>
        <c:otherwise>
            <a class="none"></a>
        </c:otherwise>
    </c:choose>
    <c:if test="${page.totalElements > 1}">
        <a href="#" class="ui-icon ui-icon-extlink pop-movable"
           rel="popover"
           data-placement="left"
           data-html="true"
           data-original-title="请输入移动到的数据编号"
           data-content="<input type='text' class='input-small id'/>&nbsp;&nbsp;<a href='#' class='btn-link btn-custom popover-up-btn'>之后</a>|<a href='#' class='btn-link popover-down-btn'>之前</a>"></a>
    </c:if>
</div>