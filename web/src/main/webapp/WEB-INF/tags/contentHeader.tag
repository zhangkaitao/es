<%@tag pageEncoding="UTF-8"%>
<%@attribute name="title" type="java.lang.String" required="false" %>
<%@attribute name="centerPanel" type="java.lang.Boolean" required="false" description="是否是center面板" %>
<%
    if(centerPanel == null) {
        centerPanel = true;
    }
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>${title}</title>
    <%@include file="/WEB-INF/jsp/common/import-css.jspf"%>
</head>
<body <%= centerPanel ? "class='center-panel'" : ""%>>