<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jspf"%>
<table id="table" class="sort-table table table-bordered table-hover" data-async="true">
    <thead>
    <tr>
        <th style="width: 80px">
            <a class="check-all" href="javascript:;">全选</a>
            |
            <a class="reverse-all" href="javascript:;">反选</a>
        </th>
        <th sort="title">标题</th>

        <c:if test="${state ne 'out_box'}">
        <th style="width: 100px" sort="senderId">发件人</th>
        </c:if>

        <c:if test="${state ne 'in_box'}">
        <th style="width: 100px" sort="receiverId">收件人</th>
        </c:if>

        <th style="width: 150px">发送时间</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${page.content}" var="m">
        <tr>
            <td class="check"><input type="checkbox" name="ids" value="${m.id}"></td>
            <%--<td>--%>
                <%--<a class="btn btn-link btn-edit" href="${ctx}/admin/personal/message/${m.id}">${m.id}</a>--%>
            <%--</td>--%>
            <td>
                <c:choose>
                    <c:when test="${state eq 'draft_box'}">
                        <a href="${ctx}/admin/personal/message/draft/${m.id}/send" class="btn btn-link no-padding">${m.title}</a>
                    </c:when>
                    <c:otherwise>
                        <a href="${ctx}/admin/personal/message/${m.id}" class="btn btn-link no-padding">${m.title}</a>
                        <c:if test="${m.read eq false}">
                            <span class="label label-important">未读</span>
                        </c:if>
                    </c:otherwise>

                </c:choose>
            </td>

            <c:if test="${state ne 'out_box'}">
            <td><sys:showUsername id="${m.senderId}"/></td>
            </c:if>

            <c:if test="${state ne 'in_box'}">
            <td><sys:showUsername id="${m.receiverId}"/></td>
            </c:if>

            <td><spring:eval expression="m.sendDate"/></td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<es:page page="${page}"/>
