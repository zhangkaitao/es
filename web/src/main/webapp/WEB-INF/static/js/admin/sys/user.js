function initValidator(form) {
    var ajaxCall = {
        "url": ctx + "/admin/sys/user/validate",
        //动态提取的数据。验证时一起发送
        extraDataDynamic: ['#id'],
        //验证失败时的消息
        //"alertText": "* 该名称已被其他人使用",
        //验证成功时的消息
        //"alertTextOk": "该名称可以使用",
        "alertTextLoad": "* 正在验证，请稍等。。。"
    };
    //自定义ajax验证  ajax[ajaxNameCall] 放到验证规则的最后（放到中间只有当submit时才验证）
    //不能合并到一个 否则提交表单时有个黑屏阶段
    $.validationEngineLanguage.allRules.ajaxUsernameCall = ajaxCall;
    $.validationEngineLanguage.allRules.ajaxEmailCall = ajaxCall;
    $.validationEngineLanguage.allRules.ajaxMobilePhoneNumberCall = ajaxCall;
    $.validationEngineLanguage.allRules.username = {
        "regex": /^[\u4E00-\u9FA5\uf900-\ufa2d_a-zA-Z][\u4E00-\u9FA5\uf900-\ufa2d\w]{4,19}$/,
        "alertText": "* 5到20个汉字、字母、数字或下划线组成，且必须以非数字开头"
    };
    $.validationEngineLanguage.allRules.mobilePhoneNumber = {
        "regex": /^0{0,1}(13[0-9]|15[7-9]|153|156|18[7-9])[0-9]{8}$/,
        "alertText": "* 手机号错误"
    };
    var validationEngine = form.validationEngine();
    return validationEngine;
}

function initUserListButton() {
    $(".change-password").click(function() {
        var checkbox = $.table.getAllSelectedCheckbox($(".table"));
        if(checkbox.size() == 0) return;
        var id = checkbox.val();
        var url = ctx + "/admin/sys/user/changePassword?" + checkbox.serialize();

        $.app.confirm({
            title: "修改密码",
            message : "请输入新密码：<br/><input type='password' id='password' class='input-medium'/>",
            ok : function() {
                var password = $("#password").val();
                if(password) {
                    window.location.href = url + "&newPassword=" + password;
                }
            }
        });
    });

    $(".block-user,.unblocked-user").click(function() {
        var checkbox = $.table.getAllSelectedCheckbox($(".table"));
        if(checkbox.size() == 0) return;
        var id = checkbox.val();
        var status = $(this).is(".unblocked-user") ? "normal" : "blocked";
        var url = ctx + "/admin/sys/user/changeStatus/" + status + "?" + checkbox.serialize();

        var title = status == 'blocked' ? "封禁用户" : "解封用户";
        var tip = status == 'blocked' ? "请输入封禁原因:" : "请输入解封原因：";

        $.app.confirm({
            title: title,
            message : tip + "<br/><textarea id='reason' style='width: 300px;height: 50px;'></textarea>",
            ok : function() {
                var reason = $("#reason").val();
                if(reason) {
                    window.location.href = url + "&reason=" + reason;
                }
            }
        });
    });
    $(".status-history").click(function() {
        var checkbox = $.table.getAllSelectedCheckbox($(".table"));
        if(checkbox.size() == 0) return;
        var ids = $.app.joinVar(checkbox.val());
        var url = ctx + "/admin/sys/user/statusHistory?search.user.id_eq=" + ids;

        $.app.modalDialog("状态改变历史", url, {width : 800});
    });
    $(".last-online-info").click(function() {

        var checkbox = $.table.getAllSelectedCheckbox($(".table"));
        if(checkbox.size() == 0) return;
        var ids = $.app.joinVar(checkbox.val());
        var url = ctx + "/admin/sys/user/lastOnline?search.userId_eq=" + ids;

        $.app.modalDialog("最后在线历史", url, {width : 800});
    });


    $(".recycle").click(function() {

        var checkbox = $.table.getAllSelectedCheckbox($(".table"));
        if(checkbox.size() == 0) return;


        var url = ctx + "/admin/sys/user/recycle?" + checkbox.serialize();
        $.app.confirm({
            title : "欢迎删除的用户",
            message : "确认还原吗？",
            ok : function() {
                window.location.href = url;
            }
        });
    });

}

function initOnlineListButton() {
    $(".btn-force-logout").click(function() {
        var checkbox = $.table.getAllSelectedCheckbox($(".table"));
        if(checkbox.size() == 0) {
            return;
        }
        $.app.confirm({
            message : "确认强制退出吗？",
            ok : function() {
                var url = ctx + "/admin/sys/user/online/forceLogout?" + checkbox.serialize();
                window.location.href = url;
            }
        });
    });
}