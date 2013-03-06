<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jspf"%>
<es:contentHeader/>

<form:form id="maintainForm" method="post" commandName="m" cssClass="form-horizontal" enctype="multipart/form-data">
    <fieldset>
        <legend>维护树</legend>

        <es:showGlobalError commandName="m"/>

        <form:hidden path="id"/>
        <form:hidden path="path"/>

        <div class="control-group">
            <form:label path="name" cssClass="control-label">名称</form:label>
            <div class="controls">
                <form:input path="name" cssClass="validate[required,custom[name]]" placeholder="小于50个字符"/>
            </div>
        </div>

        <c:if test="${not empty m.icon}">
            <div class="control-group">
                <form:label path="icon" cssClass="control-label">当前显示的图标</form:label>
                <div class="controls">
                    <es:showAttachment filename="${m.icon}" showImage="true" width="auto" height="16" isDownload="false"/>
                    <form:hidden path="icon"/>
                </div>
            </div>
        </c:if>

        <div class="control-group">
            <label for="file" class="control-label">图标</label>
            <div class="controls">
                <input type="file" id="file" name="file" class="custom-file-input"/>
            </div>
        </div>

        <div class="control-group">
            <form:label path="show" cssClass="control-label">是否显示</form:label>
            <div class="controls inline-radio">
                <form:radiobuttons path="show" items="${booleanList}" itemLabel="info" itemValue="value" cssClass="validate[required]"/>
            </div>
        </div>

        <div class="control-group">
            <div class="controls">
                <input id="updateTree" type="submit" class="btn btn-primary" value="修改">
                <input id="deleteTree" type="submit" class="btn btn-primary" value="删除">
                <input id="appendChild" type="submit" class="btn btn-primary" value="添加子节点">
                <c:if test="${m.root == false}"><%-- 根节点不能移动 --%>
                <input id="moveTree" type="submit" class="btn btn-primary" value="移动节点">
                </c:if>
            </div>
        </div>
    </fieldset>
</form:form>
</div>
<es:contentFooter/>
<script type="text/javascript">
$(function () {
    $.validationEngineLanguage.allRules.name = {
        "regex": /^.{1,50}$/,
        "alertText": "* 小于50个字符"
    };
    var validationEngine = $("#maintainForm").validationEngine();
    <es:showFieldError commandName="m"/>

    $("#updateTree").click(function() {
        this.form.action = "${ctx}/showcase/tree/update/${m.id}";
    });
    $("#deleteTree").click(function () {
        var btn = this;
        $.app.confirm({
            message : "确认删除吗？",
            ok : function() {
                btn.form.action = "${ctx}/showcase/tree/delete/${m.id}";
                btn.form.submit();
          }
      });
      return false;
  });
  $("#appendChild").click(function () {
      window.location.href = "${ctx}/showcase/tree/appendChild/${m.id}";
      return false;
  });
  $("#moveTree").click(function () {
      window.location.href = "${ctx}/showcase/tree/move/${m.id}";
      return false;
  });

});
</script>
