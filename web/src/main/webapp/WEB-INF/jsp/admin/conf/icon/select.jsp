<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jspf"%>
<div class="sys-icon-list">
    <ul class="nav nav-tabs">
        <c:forEach items="${types}" var="t" varStatus="status">
            <li ${status.first ? 'class="active"' : ''}><a href="#${t}" data-toggle="tab">${t.info}</a></li>
        </c:forEach>
    </ul>
    <div class="tab-content">
        <c:forEach items="${types}" var="t" varStatus="status">
            <div class="tab-pane <c:if test="${status.first}">active</c:if>" id="${t}">
                <c:forEach items="${icons}" var="icon">
                    <c:if test="${icon.type eq t}">
                        <a class="btn sys-icon-btn">
                            <i class="${icon.identity}"></i>
                        </a>
                    </c:if>
                </c:forEach>
            </div>
        </c:forEach>
    </div>
</div>
