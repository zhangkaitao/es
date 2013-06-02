<%@ page import="org.apache.commons.lang3.ArrayUtils" %>
<%@ page import="org.hibernate.SessionFactory" %>
<%@ page import="org.hibernate.metadata.ClassMetadata" %>
<%@ page import="org.springframework.beans.BeanWrapperImpl" %>
<%@ page import="org.springframework.data.domain.Page" %>
<%@ page import="java.beans.PropertyDescriptor" %>
<%@ page import="java.util.Arrays" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jspf" %>
<es:contentHeader/>
<div data-table="table" class="panel">
    <c:set var="type" value="ql"/>
    <%@include file="nav.jspf" %>

    <form method="post" class="form-inline">
        <esform:hidden path="page.pn"/>
        <esform:label path="ql">请输入QL：</esform:label><br/>
        <esform:textarea path="ql" style="width: 500px;height: 160px"/><br/>
        <input type="submit" class="btn" value="执行">
    </form>

    <div id="result">
        <c:if test="${not empty error}">
            出错了：<br/>
            ${error}
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
                SessionFactory sessionFactory = (SessionFactory)pageContext.findAttribute("sessionFactory");
                List result = resultPage.getContent();
                Object obj = result.get(0);
                ClassMetadata metadata = sessionFactory.getClassMetadata(obj.getClass());
                if(metadata != null) {
                    BeanWrapperImpl beanWrapper = new BeanWrapperImpl(obj);
            %>
                <table class="table table-bordered table-hover">
                    <thead>
                        <tr>
                            <%
                                for(PropertyDescriptor descriptor : beanWrapper.getPropertyDescriptors()) {
                                    if(ArrayUtils.contains(metadata.getPropertyNames(), descriptor.getName())) {
                                        out.write("<th>" + descriptor.getName() + "</th>");
                                    }
                                }
                            %>
                        </tr>
                    </thead>
                    <tbody>

                            <%
                                for(Object o : result) {
                                    out.write("<tr>");
                                    for(PropertyDescriptor descriptor : beanWrapper.getPropertyDescriptors()) {
                                        if(ArrayUtils.contains(metadata.getPropertyNames(), descriptor.getName())) {
                                            out.write("<td>" + descriptor.getReadMethod().invoke(o) + "</td>");
                                        }
                                    }
                                    out.write("</tr>");
                                }
                            %>
                    </tbody>
                </table>
            <%
            } else {
                out.write("未知实体类型，直接循环输出<br/>");
                for(Object r : result) {
                    String str = r.toString();
                    if(r.getClass().isArray()) {
                        str = Arrays.toString((Object[])r);
                    }
                    out.write(str + "<br/>");
                }
            }
            %>
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