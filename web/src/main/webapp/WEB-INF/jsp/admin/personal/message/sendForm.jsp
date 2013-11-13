<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jspf"%>
<es:contentHeader/>
<div class="panel">

    <ul class="nav nav-tabs">
            <c:if test="${op eq '发送草稿'}">
                <li class="active">
                    <a href="${ctx}/admin/personal/message/draft/${m.id}/send?BackURL=<es:BackURL/>">
                        <i class="icon-file-alt"></i>
                            ${op}
                    </a>
                </li>
            </c:if>
            <c:if test="${op eq '发送新消息'}">
                <li class="active">
                    <a href="${ctx}/admin/personal/message/send?BackURL=<es:BackURL/>">
                        <i class="icon-file-alt"></i>
                        ${op}
                    </a>
                </li>
            </c:if>
            <c:if test="${op eq '回复消息'}">
                <li class="active">
                    <a href="${ctx}/admin/personal/message/${parent.id}/reply?BackURL=<es:BackURL/>">
                        <i class="icon-file-alt"></i>
                        ${op}
                    </a>
                </li>
            </c:if>
            <c:if test="${op eq '转发消息'}">
                <li class="active">
                    <a href="${ctx}/admin/personal/message/${parent.id}/forward?BackURL=<es:BackURL/>">
                        <i class="icon-file-alt"></i>
                            ${op}
                    </a>
                </li>
            </c:if>
        <li>
            <a href="<es:BackURL/>" class="btn btn-link">
                <i class="icon-reply"></i>
                返回
            </a>
        </li>
    </ul>

    <c:if test="${op eq '回复消息'}">
        <div class="control-group">
            <span class="bold span2"><pretty:prettyTime date="${parent.sendDate}"/></span>
            <c:choose>
                <c:when test="${user.id eq parent.senderId}">
                    我
                </c:when>
                <c:otherwise>
                    <sys:showUsername id="${parent.senderId}" needLink="false"/>
                </c:otherwise>
            </c:choose>
            发给
            <c:choose>
                <c:when test="${user.id eq parent.receiverId}">
                    我
                </c:when>
                <c:otherwise>
                    <sys:showUsername id="${parent.receiverId}" needLink="false"/>
                </c:otherwise>
            </c:choose>
            的消息<br/>
        <br/>
        <div class="accordion message">
            <div class="accordion-group">
                <div class="accordion-heading">
                    <a class="accordion-toggle bold no-underline" data-toggle="collapse" href="#collapse${parent.id}">
                            ${parent.title}
                    <span class="muted" style="float: right;padding-right: 20px;">
                        <sys:showUsername id="${parent.senderId}" needLink="false"/>
                        &nbsp;&nbsp;&nbsp;
                        <spring:eval expression="parent.sendDate"/>
                    </span>
                    </a>

                </div>
                <div id="collapse${parent.id}" class="accordion-body collapse in">
                    <div class="accordion-inner">
                            ${parent.content.content}
                    </div>
                </div>
        </div>
        <br/>
    </c:if>
    <form:form id="editForm" method="post" commandName="m" cssClass="form-horizontal form-small">

            <es:BackURL hiddenInput="true"/>

            <es:showGlobalError commandName="m"/>

            <form:hidden path="id"/>
            <form:hidden path="content.id"/>
            <form:hidden path="parentId"/>
            <form:hidden path="parentIds"/>
            <form:hidden path="receiverId"/>

            <c:if test="${op eq '发送新消息' or op eq '转发消息' or op eq '发送草稿'}">
            <div class="control-group">
                <form:label path="receiverId" cssClass="control-label">收件人</form:label>
                <div id="search-receiver" class="controls">
                    <c:set var="receiver" value="${not empty receiver ? receiver : param.receiver}"/>
                    <input type="text" id="receiverId_msg" name="receiver" value="${receiver}" placeholder="输入收件人用户名">
                    <form:hidden path="receiverId"/>
                </div>
            </div>
            </c:if>

            <div class="control-group">
                <form:label path="title" cssClass="control-label">
                    标题
                </form:label>
                <div class="controls">
                    <form:input path="title" cssClass="validate[required,minSize[5],maxSize[200]]" placeholder="长度在5-200个字符之间"/>
                </div>
            </div>

            <div class="control-group">
                <form:label path="content.content" cssClass="control-label">内容</form:label>
                <div class="controls">
                    <form:textarea id="content" path="content.content" data-promptPosition="topRight:-200" cssStyle="width: 680px;height: 300px;"/>
                </div>
            </div>

            <div class="control-group">
                <div class="controls">
                    <button type="submit" class="btn btn-primary">
                        <i class="${icon}"></i>
                            ${op}
                    </button>
                    <a class="btn btn-save-draft">
                        <i class="icon-save"></i>
                        存草稿
                    </a>
                </div>
            </div>


    </form:form>
</div>
<es:contentFooter/>
<%@include file="/WEB-INF/jsp/common/import-editor-js.jspf"%>
<script type="text/javascript">
    $(function () {
        var form = $("#editForm");
        var validationEngine = form.validationEngine({prettySelect:true, useSuffix : "_msg"});
        <es:showFieldError commandName="m"/>

        var editor = KindEditor.create("textarea[name='content.content']", {
            themeType: 'simple',
            uploadJson: '${ctx}/kindeditor/upload',
            fileManagerJson: '${ctx}/kindeditor/filemanager',
            allowFileManager: true,
            afterBlur: function(){this.sync();}
        });

        var $username = $("#receiverId_msg");
        if($username[0]){
            $.app.initAutocomplete({
                input : $username,
                source : "${ctx}/admin/sys/user/ajax/autocomplete",
                select : function(event, ui) {
                    $username.val(ui.item.label);
                    return false;
                }
            });
        }

        $(window).on('beforeunload',function() {
            if($username.val() || $("#title").val() || editor.html()) {
                return "确定离开当前编辑页面吗？";
            }
        });

        form.submit(function() {
            $(window).unbind("beforeunload");
        });

        $(".btn-save-draft").click(function() {
            //必须调用 尽管 form.submit绑定了释放，但是 form.validationEngine("detach"); 会off掉
            $(window).unbind("beforeunload");
            form.validationEngine("detach");
            form.attr("action", "${ctx}/admin/personal/message/draft/save").submit();

        });

    });
</script>
