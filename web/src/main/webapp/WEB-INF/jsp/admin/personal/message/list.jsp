<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jspf"%>
<es:contentHeader/>
<div data-table="table" class="panel"  data-state="${state}" data-state-info="${state.info}">

    <ul class="nav nav-tabs">
        <c:forEach items="${states}" var="s">
            <c:if test="${s ne 'delete_box'}">
            <li ${s eq state ? 'class="active"' : ''}>
                <a href="${ctx}/admin/personal/message/${s}/list">
                    <i class="icon-table"></i>
                    ${s.info}
                </a>
            </li>
            </c:if>
        </c:forEach>
    </ul>

    <es:showMessage/>

    <div class="row-fluid tool ui-toolbar">
        <div class="span4">
            <div class="btn-group">
                <a class="btn no-disabled" href="${ctx}/admin/personal/message/send">
                    <i class="icon-file-alt"></i>
                    发送新消息
                </a>

                <c:if test="${state eq 'in_box'}">
                    <a class="btn btn-mark-read">
                        <i class="icon-bookmark"></i>
                        标记为已读
                    </a>
                </c:if>

                <c:if test="${state ne 'draft_box' and state ne 'store_box' and state ne 'trash_box'}">
                    <a class="btn btn-store">
                        <i class="icon-briefcase"></i>
                        收藏
                    </a>
                </c:if>

                <c:if test="${state eq 'trash_box'}">
                    <a class="btn btn-store">
                        <i class="icon-briefcase"></i>
                        恢复到收藏箱
                    </a>
                </c:if>

                <a class="btn btn-recycle-or-delete">
                    <i class="icon-trash"></i>
                    删除
                </a>

                <a class="btn no-disabled btn-clear">
                    <i class="icon-eraser"></i>
                    清空${state.info}
                </a>
            </div>
        </div>
        <div class="span8 muted" style="text-align: right;line-height: 31px;padding-right: 10px;">
            <c:if test="${state eq 'in_box' or state eq 'out_box'}">
                提示:${state.info}超过365天的消息将被移到垃圾箱, 请将要保存的消息移到收藏箱
            </c:if>
            <c:if test="${state eq 'trash_box'}">
                提示: 系统会自动清除垃圾箱里超过30天的短信
            </c:if>
        </div>
    </div>
    <%@include file="listTable.jsp"%>

</div>
<es:contentFooter/>
<%@include file="/WEB-INF/jsp/common/admin/import-personal-js.jspf"%>
<script type="text/javascript">
    $(function() {
        $.personal.message.initBtn();
    });
</script>
