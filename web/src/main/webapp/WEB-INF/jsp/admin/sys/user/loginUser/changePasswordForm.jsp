<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jspf"%>
<es:contentHeader/>

<div class="panel">

    <%@include file="nav.jspf"%>

    <es:showMessage/>

    <form id="editForm" method="post" class="form-horizontal">

        <div class="control-group">
            <label for="oldPassword" class="control-label">旧密码</label>
            <div class="controls">
                <input type="password" id="oldPassword" name="oldPassword"
                        class="validate[required]"/>
            </div>
        </div>

        <div class="control-group">
            <label for="newPassword1" class="control-label">新密码</label>
            <div class="controls">
                <input type="password" id="newPassword1" name="newPassword1"
                        class="validate[required,minSize[5],maxSize[100]]" placeholder="请输入至少5位的新密码"/>
            </div>
        </div>
        <div class="control-group">
            <label for="newPassword2" class="control-label">确认新密码</label>
            <div class="controls">
                <input type="password" id="newPassword2" name="newPassword2"
                        class="validate[required,equals[newPassword1]]"/>
            </div>
        </div>

        <div class="control-group">
            <div class="controls">
                <button type="submit" class="btn btn-primary">
                    <i class="icon-key"></i>
                        ${op}
                </button>
            </div>
        </div>

    </form>

</div>
<es:contentFooter/>
<%@include file="/WEB-INF/jsp/common/admin/import-sys-js.jspf"%>
<script type="text/javascript">
    $(function () {
        $.sys.user.initValidator($("#editForm"));
        <es:showFieldError commandName="m"/>

    });
</script>
