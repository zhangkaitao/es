<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jspf"%>
${m.content.content}
    <div style="padding: 20px 0">
    <c:if test="${m.senderId ne user.id}">
        <a class="btn" href="${ctx}/admin/personal/message/${m.id}/reply">回复</a>
        <a class="btn" href="${ctx}/admin/personal/message/${m.id}/forward">转发</a>
    </c:if>

        <a class="btn btn-view-store" data-href="${ctx}/admin/personal/message/batch/store?ids=${m.id}">收藏</a>

    <c:if test="${(m.senderId eq user.id and m.senderState ne 'trash_box') or (m.receiverId eq user.id and m.receiverState ne 'trash_box') }">
        <a class="btn btn-view-recycle" data-href="${ctx}/admin/personal/message/batch/recycle?ids=${m.id}">删除</a>
    </c:if>

    <c:if test="${(m.senderId eq user.id and m.senderState eq 'trash_box') or (m.receiverId eq user.id and m.receiverState eq  'trash_box') }">
            <a class="btn btn-view-delete" data-href="${ctx}/admin/personal/message/batch/delete?ids=${m.id}">删除</a>
    </c:if>

    </div>

