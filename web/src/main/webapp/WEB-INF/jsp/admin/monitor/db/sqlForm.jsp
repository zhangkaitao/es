<%@ page import="org.springframework.data.domain.Page" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jspf" %>
<es:contentHeader/>
<div data-table="table" class="panel">
    <c:set var="type" value="sql"/>
    <%@include file="nav.jspf" %>

    <form method="post" class="form-inline">
        <esform:hidden path="page.pn"/>
        <esform:label path="sql">请输入SQL(不支持DDL/DCL执行)：</esform:label><br/>
        <esform:textarea path="sql" style="width: 500px;height: 160px"/><br/>
        <input type="submit" class="btn" value="执行">
    </form>

    <div id="result">
        <c:if test="${not empty error}">
            出错了：<br/>
            ${error}<br/>
        </c:if>
        <c:if test="${not empty updateCount}">更新了${updateCount}行</c:if>
        <c:if test="${resultPage.totalElements eq 0}">没有结果</c:if>
        <c:if test="${resultPage.totalElements gt 0}">
            当前第${resultPage.number+1}页，总共${resultPage.totalPages}页/${resultPage.totalElements}条记录
            <%
                Page resultPage = (Page)pageContext.findAttribute("resultPage");
            %>
            <% if(resultPage.hasPreviousPage()) { %>
                <a class="btn btn-link btn-pre-page">上一页</a>
            <% } %>
            <% if(resultPage.hasNextPage()) { %>
            <a class="btn btn-link btn-next-page">下一页</a>
            <% } %>
            <br/>
            <%
                List<Object> result = resultPage.getContent();
            %>

                <table class="table table-bordered table-hover">
                    <thead>
                        <tr>
                            <c:forEach items="${columnNames}" var="columnName">
                                <th>${columnName}</th>
                            </c:forEach>
                        </tr>
                    </thead>
                    <tbody>
                        <%
                            for(Object o : result) {
                                out.write("<tr>");
                                if(!o.getClass().isArray()) {
                                    out.write("<td>" + o + "</td>");
                                 } else {
                                    for(Object c  : (Object[])o) {
                                       out.write("<td>" + c + "</td>");
                                    }
                                }
                                out.write("</tr>");
                            }
                        %>
                    </tbody>
                </table>
            </c:if>
    </div>

</div>
<es:contentFooter/>
<script type="text/javascript">
    $(".btn-pre-page").click(function() {
        var $pn = $("[name='page.pn']");
        $pn.val(parseInt($pn.val()) - 1);
        $pn.closest("form").submit();
    });
    $(".btn-next-page").click(function() {
        var $pn = $("[name='page.pn']");
        if(!$pn.val()) {
            $pn.val(1);
        } else {
            $pn.val(parseInt($pn.val()) + 1);
        }
        $pn.closest("form").submit();
    });
</script>