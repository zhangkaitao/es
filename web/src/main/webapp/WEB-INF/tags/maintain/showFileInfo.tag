<%@ tag pageEncoding="UTF-8"%>
<%@ attribute name="fileInfo" type="java.util.Map" required="false" description="当前文件" %>
<%@ attribute name="checkbox" type="java.lang.Boolean" required="false" description="是否需要checkbox，默认true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="pretty" tagdir="/WEB-INF/tags/pretty" %>
<c:if test="${empty checkbox}">
    <c:set var="checkbox" value="${true}"/>
</c:if>
<tr>
    <td class="check">
        <c:if test="${checkbox}">
            <input type="checkbox" name="paths" value="${fileInfo.path}">
            <input type="hidden" name="names" value="${fileInfo.name}">
        </c:if>
    </td>
    <td style="word-break: break-all;">
        <c:choose>
            <c:when test="${fileInfo.isDirectory}">
                <a href="${ctx}/admin/maintain/editor/list?path=${fileInfo.path}">
                    <i class="${fileInfo.iconSkin}"></i>
                    ${fileInfo.name}
                </a>
            </c:when>
            <c:when test="${fileInfo.canEdit}">
                <a href="${ctx}/admin/maintain/editor/edit?path=${fileInfo.path}">
                    <i class="${fileInfo.iconSkin}"></i>
                        ${fileInfo.name}
                </a>
            </c:when>
            <c:otherwise>
                <i class="${fileInfo.iconSkin}"></i>
                ${fileInfo.name}
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