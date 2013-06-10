<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jspf"%>
<es:contentHeader/>
<div data-table="table" class="panel">

    <es:showMessage/>

    <table class="table sort-table">
        <thead>
        <tr>
            <th sort="name">名称</th>
            <th sort="lastModified" style="width: 30%">修改时间</th>
            <th sort="size" style="width: 20%">大小</th>
        </tr>
        </thead>

        <tbody>
            <c:if test="${not empty parent}">
                <maintain:showFileInfo fileInfo="${parent}"/>
            </c:if>
            <maintain:showFileInfo fileInfo="${current}"/>
            <tr>
                <td colspan="3" style="padding: 0px;line-height: 1px;border-color: #0099ff;"></td>
            </tr>
            <c:forEach items="${files}" var="f">
                <maintain:showFileInfo fileInfo="${f}"/>
            </c:forEach>
        </tbody>

    </table>

</div>

<es:contentFooter/>
