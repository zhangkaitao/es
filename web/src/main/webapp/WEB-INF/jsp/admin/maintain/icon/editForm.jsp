<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jspf"%>
<es:contentHeader/>
<div class="panel">

    <c:set var="type" value="${not empty type ? type : m.type}"/>

    <ul class="nav nav-tabs">
        <shiro:hasPermission name="maintain:icon:create">
        <c:if test="${op eq '新增'}">
            <c:forEach items="${types}" var="t">
                <li ${type eq t ? 'class="active"' : ''}>
                    <a href="${ctx}/admin/maintain/icon/${t}/create?BackURL=<es:BackURL/>">
                        <i class="icon-file-alt"></i>
                        新增${t.info}
                    </a>
                </li>
            </c:forEach>
        </c:if>
        </shiro:hasPermission>

        <c:if test="${not empty m.id}">
            <li ${op eq '查看' ? 'class="active"' : ''}>
                <a href="${ctx}/admin/maintain/icon/${m.id}?BackURL=<es:BackURL/>">
                    <i class="icon-eye-open"></i>
                    查看
                </a>
            </li>
            <shiro:hasPermission name="maintain:icon:update">
            <li ${op eq '修改' ? 'class="active"' : ''}>
                <a href="${ctx}/admin/maintain/icon/${m.id}/update?BackURL=<es:BackURL/>">
                    <i class="icon-edit"></i>
                    修改
                </a>
            </li>
            </shiro:hasPermission>
            <shiro:hasPermission name="maintain:icon:delete">
            <li ${op eq '删除' ? 'class="active"' : ''}>
                <a href="${ctx}/admin/maintain/icon/${m.id}/delete?BackURL=<es:BackURL/>">
                    <i class="icon-trash"></i>
                    删除
                </a>
            </li>
            </shiro:hasPermission>
        </c:if>
        <li>
            <a href="<es:BackURL/>" class="btn btn-link">
                <i class="icon-reply"></i>
                返回
            </a>
        </li>
    </ul>

    <form:form id="editForm" method="post" commandName="m" cssClass="form-horizontal" enctype="multipart/form-data">

            <es:showGlobalError commandName="m"/>

            <form:hidden path="id"/>
            <form:hidden path="type"/>


            <div class="control-group">
                <form:label path="identity" cssClass="control-label">标识符</form:label>
                <div class="controls">
                    <form:input path="identity" cssClass="validate[required,custom[identity],ajax[ajaxIdentityCall]]" placeholder="用于表示图标的唯一标识"/>
                </div>
            </div>
            <c:if test="${type eq 'css_class'}">
                <div class="control-group">
                    <form:label path="cssClass" cssClass="control-label">css类</form:label>
                    <div class="controls">
                        <form:input path="cssClass" cssClass="validate[required]" placeholder="图标css类"/>
                    </div>
                </div>
            </c:if>

            <c:if test="${type eq 'upload_file'}">
                <c:if test="${not empty m.imgSrc}">
                <div class="control-group">
                    <label class="control-label">之前上传的图标</label>
                    <div class="controls">
                        <maintain:showIcon icon="${m}"/>
                        <form:hidden path="imgSrc"/>
                    </div>
                </div>
                </c:if>
                <div class="control-group">
                    <label for="file" class="control-label">图标文件</label>
                    <div class="controls">
                        <input id="file" type="file" name="file" class="custom-file-input"/>

                    </div>
                </div>
            </c:if>

            <c:if test="${type eq 'css_sprite'}">
                <c:if test="${not empty m.spriteSrc}">
                    <div class="control-group">
                        <label class="control-label">之前上传的图标</label>
                        <div class="controls">
                            <maintain:showIcon icon="${m}"/>
                        </div>
                    </div>
                </c:if>
                <div class="control-group">
                    <form:label path="spriteSrc" class="control-label">图标文件</form:label>
                    <div class="controls">
                        <form:input path="spriteSrc"/> <span class="muted font-12">绝对路径：如http://www.sishuok.com，相对路径不要加上下文</span>
                    </div>
                </div>
                <div class="control-group">
                    <form:label path="left" cssClass="control-label">位置</form:label>
                    <div class="controls">
                        <form:input path="left" cssClass="validate[required, custom[integer]] input-small"
                                    placeholder="距离左边" data-toggle="tooltip" data-placement="bottom" title="距离左边"/>
                        <form:input path="top" cssClass="validate[required, custom[integer]] input-small"
                                    placeholder="距离上边"  data-toggle="tooltip" data-placement="bottom" title="距离上边"/>
                    </div>
                </div>

            </c:if>

            <c:if test="${type eq 'upload_file' or type eq 'css_sprite'}">
                <div class="control-group">
                    <form:label path="width" cssClass="control-label">大小</form:label>
                    <div class="controls">
                        <form:input path="width" cssClass="validate[required, custom[integer]] input-small"
                                    placeholder="宽度" data-toggle="tooltip" data-placement="bottom" title="宽度"/>
                        <form:input path="height" cssClass="validate[required, custom[integer]] input-small"
                                    placeholder="高度" data-toggle="tooltip" data-placement="bottom" title="高度"/>
                    </div>
                </div>
                <div class="control-group">
                    <form:label path="style" cssClass="control-label">其他css属性</form:label>
                    <div class="controls">
                        <form:input path="style"/>
                    </div>
                </div>
            </c:if>

            <div class="control-group">
                <form:label path="description" cssClass="control-label">描述</form:label>
                <div class="controls">
                    <form:input path="description" placeholder="此图标的简介"/>
                </div>
            </div>

            <c:if test="${op eq '新增'}">
                <c:set var="icon" value="icon-file-alt"/>
            </c:if>
            <c:if test="${op eq '修改'}">
                <c:set var="icon" value="icon-edit"/>
            </c:if>
            <c:if test="${op eq '删除'}">
                <c:set var="icon" value="icon-trash"/>
            </c:if>

            <div class="control-group">
                <div class="controls">
                    <button type="submit" class="btn btn-primary">
                        <i class="${icon}"></i>
                            ${op}
                    </button>
                    <a href="<es:BackURL/>" class="btn">
                        <i class="icon-reply"></i>
                        返回
                    </a>
                </div>
            </div>


    </form:form>
</div>
<es:contentFooter/>
<script type="text/javascript">
    $(function () {
        <c:choose>
            <c:when test="${op eq '删除'}">
                //删除时不验证 并把表单readonly
                $.app.readonlyForm($("#editForm"), false);
            </c:when>
            <c:when test="${op eq '查看'}">
                $.app.readonlyForm($("#editForm"), true);
            </c:when>
            <c:otherwise>
                //自定义ajax验证  ajax[ajaxNameCall] 放到验证规则的最后（放到中间只有当submit时才验证）
                $.validationEngineLanguage.allRules.ajaxIdentityCall= {
                    "url": "${ctx}/admin/maintain/icon/validate",
                    //动态提取的数据。验证时一起发送
                    extraDataDynamic : ['#id'],
                    //验证失败时的消息
                    //"alertText": "* 该名称已被其他人使用",
                    //验证成功时的消息
                    //"alertTextOk": "该名称可以使用",
                    "alertTextLoad": "* 正在验证，请稍等。。。"
                };

                $.validationEngineLanguage.allRules.identity = {
                    "regex": /^[a-zA-Z][a-zA-Z-_0-9]*$/,
                    "alertText": "* 必须以字母开头"
                };
                var validationEngine = $("#editForm").validationEngine();
                <es:showFieldError commandName="m"/>
            </c:otherwise>
        </c:choose>
    });
</script>