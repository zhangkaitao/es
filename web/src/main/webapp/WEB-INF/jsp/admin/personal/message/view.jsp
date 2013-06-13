<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jspf"%>
<es:contentHeader/>
<div class="panel">

    <ul class="nav nav-tabs">
            <li class="active">
                <a href="${ctx}/admin/personal/message/${m.id}?BackURL=<es:BackURL/>">
                    <i class="icon-file-alt"></i>
                    查看消息
                </a>
            </li>
        <li>
            <a href="<es:BackURL/>" class="btn btn-link">
                <i class="icon-reply"></i>
                返回
            </a>
        </li>
    </ul>

    <es:showMessage/>

    <div class="control-group">
        <span class="bold span2"><pretty:prettyTime date="${m.sendDate}"/></span>
        <c:choose>
            <c:when test="${user.id eq m.senderId}">
                我
            </c:when>
            <c:otherwise>
                <sys:showUsername id="${m.senderId}" needLink="false"/>
            </c:otherwise>
        </c:choose>
        发给
        <c:choose>
            <c:when test="${user.id eq m.receiverId}">
                我
            </c:when>
            <c:otherwise>
                <sys:showUsername id="${m.receiverId}" needLink="false"/>
            </c:otherwise>
        </c:choose>
        的消息<br/>
        <span class="bold span2">标题：</span>
        ${m.title}
    </div>
    <br/>

    <div class="accordion message">
        <c:forEach items="${messages}" var="message">
            <c:if test="${message.sendDate.time < m.sendDate.time}">
            <div class="accordion-group">
                <div class="accordion-heading">
                    <a class="accordion-toggle bold no-underline" data-toggle="collapse" href="#collapse${message.id}">
                        ${message.title}
                        <span class="muted" style="float: right;padding-right: 20px;">
                            <sys:showUsername id="${message.senderId}" needLink="false"/>
                            &nbsp;&nbsp;&nbsp;
                            <spring:eval expression="message.sendDate"/>
                        </span>
                    </a>

                </div>
                <div id="collapse${message.id}" class="accordion-body collapse">
                    <div class="accordion-inner"></div>
                </div>
            </div>
            </c:if>
        </c:forEach>

        <div class="accordion-group">
            <div class="accordion-heading">
                <a class="accordion-toggle bold no-underline" data-toggle="collapse" href="#collapse${m.id}">
                    ${m.title}
                    <span class="muted" style="float: right;padding-right: 20px;">
                        <sys:showUsername id="${m.senderId}" needLink="false"/>
                        &nbsp;&nbsp;&nbsp;
                        <spring:eval expression="m.sendDate"/>
                    </span>
                </a>

            </div>
            <div id="collapse${m.id}" class="accordion-body collapse in" data-loaded="true">
                <div class="accordion-inner">
                    <%@include file="viewContent.jsp"%>
                </div>
            </div>
        </div>

        <c:forEach items="${messages}" var="message">
            <c:if test="${message.sendDate.time > m.sendDate.time}">
                <div class="accordion-group">
                    <div class="accordion-heading">
                        <a class="accordion-toggle bold no-underline" data-toggle="collapse" href="#collapse${message.id}">
                                ${message.title}
                                <span class="muted" style="float: right;padding-right: 20px;">
                                    <sys:showUsername id="${message.senderId}" needLink="false"/>
                                    &nbsp;&nbsp;&nbsp;
                                    <spring:eval expression="message.sendDate"/>
                                </span>
                        </a>

                    </div>
                    <div id="collapse${message.id}" class="accordion-body collapse">
                        <div class="accordion-inner"></div>
                    </div>
                </div>
            </c:if>
        </c:forEach>
    </div>

</div>
<es:contentFooter/>
<%@include file="/WEB-INF/jsp/common/admin/import-personal-js.jspf"%>
<script type="text/javascript">
    $(function() {
        $.personal.message.initBtn();
    });
</script>
