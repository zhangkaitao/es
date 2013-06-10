<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jspf"%>
<es:contentHeader/>
<div data-table="table" class="panel">

    <c:forEach items="${files}" var="f">
        ${f.name}<br/>
    </c:forEach>


</div>

<es:contentFooter/>
