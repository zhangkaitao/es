<%@ tag pageEncoding="UTF-8"%>
<%@ attribute name="fileInfo" type="java.util.Map" required="false" description="当前文件" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="pretty" tagdir="/WEB-INF/tags/pretty" %>
<tr>
    <td>

        <c:choose>
            <c:when test="${fileInfo.isDirectory}">
                <a href="${ctx}/admin/maintain/editor/list?path=${fileInfo.path}">
                    <span class="${fileInfo.iconSkin}"></span>
                    ${fileInfo.name}
                </a>
            </c:when>
            <c:otherwise>
                <a href="${ctx}/admin/maintain/editor/edit?path=${fileInfo.path}">
                    <span class="${fileInfo.iconSkin}"></span>
                    ${fileInfo.name}
                </a>
            </c:otherwise>
        </c:choose>
    </td>
    <td>${fileInfo.lastModified}</td>
    <td>
        <c:if test="${fileInfo.size > 0}">
            <pretty:prettyMemory byteSize="${fileInfo.size}"/>
        </c:if>
    </td>
</tr>