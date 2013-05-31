<%@ page import="com.sishuok.es.common.utils.LogUtils" %>
<%@ page import="java.io.PrintWriter" %>
<%@ page import="java.io.StringWriter" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jspf"%>
<es:contentHeader/>
<div class="panel">
    <%
        LogUtils.logPageError(request);

        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        pageContext.setAttribute("statusCode", statusCode);

        String uri = (String) request.getAttribute("javax.servlet.error.request_uri");
        String queryString = request.getQueryString();
        String url = uri + (queryString == null || queryString.length() == 0 ? "" : "?" + queryString);
        pageContext.setAttribute("url", url);

        Throwable exception = (Throwable) request.getAttribute("javax.servlet.error.exception");
        request.setAttribute("exception", exception);

    %>

    <c:if test="${statusCode eq 404}">
        <es:showMessage errorMessage="<h3 style='display:inline;'>页面没有找到！</h3><br/>对不起，暂时没有找到您所访问的页面地址,请联系管理员解决此问题！&nbsp;&nbsp;&nbsp;&nbsp;<refresh><a href='${url}' class='btn btn-danger'>刷新,看看是否能访问了</a></refresh>"/>
    </c:if>

    <c:if test="${statusCode ne 404}">
        <es:showMessage errorMessage="<h3 style='display:inline;'>服务器程序出问题了！</h3><br/>对不起,您访问的页面出了一点内部小问题，刷新重新访问或先去别的页面转转,过会再来吧~！&nbsp;&nbsp;&nbsp;&nbsp;<refresh><a href='${url}' class='btn btn-danger'>刷新,看看是否能访问了</a></refresh>"/>
        <c:if test="${not empty exception}">
            <%
                StringWriter stringWriter = new StringWriter();
                PrintWriter printWriter = new PrintWriter(stringWriter);
                exception.printStackTrace(printWriter);
                pageContext.setAttribute("stackTrace", stringWriter.toString());
            %>
            <%@include file="exceptionDetails.jsp"%>
        </c:if>
    </c:if>



</div>
<es:contentFooter/>
