<%--
 分页格式
   首页 <<   1   2   3   4   5   6   7   8   9   10  11>  >> 尾页
   首页 <<   1   2   3   4   5   6   7   8   9   ... 11  12 >  >> 尾页
   首页 <<   1   2  ...  4   5   6   7   8   9   10 ... 12  13 >  >> 尾页
   首页 <<   1   2  ...  5   6   7   8   9   10  11  12  13 >  >> 尾页
   首页 <<   1   2  ...  5   6   7   8   9   10  11  ... 13  14 >  >> 尾页
   首页 <<   1   2  ...  5   6   7   8   9   10  11  ...   21  22 >  >> 尾页

--%>
<%@tag pageEncoding="UTF-8" description="分页" %>
<%@ attribute name="page" type="org.springframework.data.domain.Page" required="true" description="分页" %>
<%@ attribute name="url" type="java.lang.String" required="false" description="分页地址" %>
<%@ attribute name="pagePrefix" type="java.lang.String" required="false" description="分页参数的前缀" %>
<%@ attribute name="pageSize" type="java.lang.Integer" required="false" description="每页大小" %>
<%@ attribute name="async" type="java.lang.Boolean" required="false" description="异步加载，默认false" %>
<%@ attribute name="containerId" type="java.lang.String" required="false" description="异步加载到的容器的ID，内容会替换而不是追加" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="es" tagdir="/WEB-INF/tags" %>
<c:if test="${empty async}">
    <c:set var="async" value="false"/>
</c:if>

<c:if test="${empty url}">
    <c:set var="url" value="${requestScope.currentURL}"/>
</c:if>

<c:set var="displaySize" value="2"/>
<c:set var="current" value="${page.number + 1}"/>

<c:set var="begin" value="${current - displaySize}"/>
<c:if test="${begin <= displaySize}">
    <c:set var="begin" value="${1}"/>
</c:if>
<c:set var="end" value="${current + displaySize}"/>
<c:if test="${end > page.totalPages - displaySize}">
    <c:set var="end" value="${page.totalPages - displaySize}"/>
</c:if>
<c:if test="${end < 0 or page.totalPages < displaySize * 4}">
    <c:set var="end" value="${page.totalPages}"/>
</c:if>

<div class="pagination">
    <ul>
        <c:choose>
            <c:when test="${page.firstPage}">
                <li class="disabled"><a title="首页">首页</a></li>
                <li class="disabled"><a title="上一页">&lt;&lt;</a></li>
            </c:when>
            <c:otherwise>
                <li><a href="#" onclick="$.app.turnPage('${url}', '${pagePrefix}', '${pageSize}', 1);" title="首页" async="true">首页</a></li>
                <li><a href="#" onclick="$.app.turnPage('${url}', '${pagePrefix}', '${pageSize}', ${current - 1});" title="上一页" async="true">&lt;&lt;</a></li>
            </c:otherwise>
        </c:choose>

        <c:forEach begin="1" end="${begin == 1 ? 0 : 2}" var="i">
            <li <c:if test="${current == i}"> class="active"</c:if>>
                <a href="#" onclick="$.app.turnPage('${url}', '${pagePrefix}', '${pageSize}', ${i});" title="第${i}页" async="true">${i}</a>
            </li>
        </c:forEach>

        <c:if test="${begin > displaySize + 1}">
            <li><a>...</a></li>
        </c:if>

        <c:forEach begin="${begin}" end="${end}" var="i">
            <li <c:if test="${current == i}"> class="active"</c:if>>
                <a href="#" onclick="$.app.turnPage('${url}', '${pagePrefix}', '${pageSize}', ${i});" title="第${i}页" async="true">${i}</a>
            </li>
        </c:forEach>


        <c:if test="${end < page.totalPages - displaySize}">
            <li><a>...</a></li>
        </c:if>

        <c:forEach begin="${end < page.totalPages ? page.totalPages - 1 : page.totalPages + 1}" end="${page.totalPages}" var="i">
            <li <c:if test="${current == i}"> class="active"</c:if>>
                <a href="#" onclick="$.app.turnPage('${url}', '${pagePrefix}', '${pageSize}', ${i});" title="第${i}页" async="true">${i}</a>
            </li>
        </c:forEach>

        <c:choose>
            <c:when test="${page.lastPage}">
                <li class="disabled"><a title="下一页">&gt;&gt;</a></li>
                <li class="disabled"><a title="尾页">尾页</a></li>
            </c:when>
            <c:otherwise>
                <li><a href="#" onclick="$.app.turnPage('${url}', '${pagePrefix}', '${pageSize}', ${current + 1}, ${async});" title="下一页" async="true">&gt;&gt;</a></li>
                <li><a href="#" onclick="$.app.turnPage('${url}', '${pagePrefix}', '${pageSize}', ${page.totalPages}, ${async});" title="尾页" async="true">尾页</a></li>
            </c:otherwise>
        </c:choose>

        <li>
            <input id="pn" type="text" value="${current}" class="page-input"/>
            <input type="button" value="跳转" class="btn page-btn" onclick="$.app.turnPage('${url}', '${pagePrefix}', '${pageSize}', $('#pn').val());"/>
            <span class="page-info">[共${page.totalPages}页/${page.totalElements}条记录]</span >
        </li>
    </ul>
</div>