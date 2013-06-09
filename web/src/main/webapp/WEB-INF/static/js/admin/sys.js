$.sys = {
    organization : {
        initSelectForm : function(organizationFormName, jobFormName, isAuthSelect) {
            var organizationTreeId = $.zTree.initSelectTree({
                zNodes : [],
                nodeType : "default",
                fullName:true,
                urlPrefix : ctx + "/admin/sys/organization/organization",
                async : true,
                asyncLoadAll : true,
                onlyDisplayShow: false,
                lazy : true,
                select : {
                    btn : $("#selectOrganizationTree,#organizationName"),
                    id : "organizationId",
                    name : "organizationName",
                    includeRoot: true
                },
                autocomplete : {
                    enable : true
                }
            });

            var jobTreeId = $.zTree.initSelectTree({
                zNodes : [],
                urlPrefix : ctx + "/admin/sys/organization/job",
                async : true,
                asyncLoadAll : true,
                onlyDisplayShow: false,
                lazy : true,
                select : {
                    btn : $("#selectJobTree,#jobName"),
                    id : "jobId",
                    name : "jobName",
                    includeRoot: true
                },
                autocomplete : {
                    enable : true
                },
                setting :{
                    check : {
                        enable:true,
                        chkStyle:"checkbox",
                        chkboxType: { "Y": "", "N": "s" }
                    }
                }
            });

            $(".btn-add-organization").click(function() {
                var organizationId = $("#organizationId").val();
                var jobId = $("#jobId").val();

                if(isAuthSelect) {
                    if(!organizationId && !jobId) {
                        $.app.alert({
                            message : "请至少选择一个组织机构或工作职务"
                        });
                        return;
                    }
                } else {
                    if(!organizationId) {
                        $.app.alert({
                            message : "请选择组织机构"
                        });
                        return;
                    }
                    if($("#organizationId_" + organizationId).length > 0) {
                        $.app.alert({
                            message : "您已经选择了此组织机构，不能重复选择！"
                        });
                        return;
                    }
                }




                var organizationName = $("#organizationName").val();
                var jobName = $("#jobName").val();

                var template =
                    "<tr>" +
                        "<td class='check'><input type='checkbox'></td>" +
                        "<td>" +
                        "<input type='hidden' id='organizationId_{organizationId}' name='{organizationFormName}' value='{organizationId}'>" +
                        "{organizationName}" +
                        "</td>" +
                        "<td>" +
                        "<input type='hidden' name='{jobFormName}' value='{jobId}'>" +
                        "{jobName}" +
                        "</td>" +
                        "<td>" +
                        "<a class='btn btn-link btn-edit btn-delete-organization' href='javascript:;' onclick='$.sys.organization.removeOrganization(this);'><i class='icon-trash'></i></a>" +
                        "</td>" +
                        "</tr>";
                $("#selectedOrganization .table tbody").append(
                    template.replace(/{organizationId}/g, organizationId)
                        .replace("{organizationName}", organizationName.replace(/,/g, "<br/>").replace(">", "&gt;"))
                        .replace("{jobId}", jobId)
                        .replace("{jobName}", jobName.replace(/,/g, "<br/>").replace(">", "&gt;"))
                        .replace("{organizationFormName}", organizationFormName)
                        .replace("{jobFormName}", jobFormName)
                );
                //更新表格的全选反选
                $.table.initCheckbox($("#selectedOrganization table"));
                var jobTree = $.fn.zTree.getZTreeObj(jobTreeId);
                if(jobTree) jobTree.checkAllNodes(false);
                var organizationTree = $.fn.zTree.getZTreeObj(organizationTreeId)
                if(organizationTree) organizationTree.checkAllNodes(false);
                $("#organizationId,#organizationName,#jobId,#jobName").val("");
            });

            $.table.initCheckbox($("#selectedOrganization table"));

            $(".btn-delete-all-organization").click(function() {
                var checkbox = $.table.getAllSelectedCheckbox($("#selectedOrganization table"));
                if(!checkbox.length) {
                    return;
                }
                $.app.confirm({
                    title : "确认删除组织机构和工作职务",
                    message : "确认删除选中的组织机构和工作职务吗？",
                    ok : function() {
                        checkbox.each(function() {
                            $(this).closest("tr").remove();
                        });
                    }
                });

            });
        },
        removeOrganizationBtn : function() {
            $("#selectOrganization").remove();
            $(".btn-delete-organization").remove();
            $(".btn-delete-all-organization").remove();
        },
        removeOrganization : function(a) {
            $.app.confirm({
                message : "确认删除吗？",
                ok : function() {
                    $(a).closest('tr').remove();
                }
            });
        }
    },
    user : {
        initValidator : function(form) {
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
            $.validationEngineLanguage.allRules.ajaxCall = ajaxCall;
            $.validationEngineLanguage.allRules.username = {
                "regex": /^[\u4E00-\u9FA5\uf900-\ufa2d_a-zA-Z][\u4E00-\u9FA5\uf900-\ufa2d\w]{1,19}$/,
                "alertText": "* 2到20个汉字、字母、数字或下划线组成，且必须以非数字开头"
            };
            $.validationEngineLanguage.allRules.mobilePhoneNumber = {
                "regex": /^0{0,1}(13[0-9]|15[7-9]|153|156|18[7-9])[0-9]{8}$/,
                "alertText": "* 手机号错误"
            };
            var validationEngine = form.validationEngine();
            return validationEngine;
        },
        initUserListButton : function() {
            $(".change-password").click(function() {
                var checkbox = $.table.getAllSelectedCheckbox($(".table"));
                if(!checkbox.length) return;
                var id = checkbox.val();
                var url = ctx + "/admin/sys/user/changePassword?" + checkbox.serialize();

                var model = $.app.confirm({
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
                if(!checkbox.length) return;
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
                if(!checkbox.length) return;
                var ids = $.app.joinVar(checkbox.val());
                var url = ctx + "/admin/sys/user/statusHistory?search.user.id_eq=" + ids;

                $.app.modalDialog("状态改变历史", url, {width : 800});
            });
            $(".last-online-info").click(function() {

                var checkbox = $.table.getAllSelectedCheckbox($(".table"));
                if(!checkbox.length) return;
                var ids = $.app.joinVar(checkbox.val());
                var url = ctx + "/admin/sys/user/lastOnline?search.userId_eq=" + ids;

                $.app.modalDialog("最后在线历史", url, {width : 800});
            });


            $(".recycle").click(function() {

                var checkbox = $.table.getAllSelectedCheckbox($(".table"));
                if(!checkbox.length) return;


                var url = ctx + "/admin/sys/user/recycle?" + checkbox.serialize();
                $.app.confirm({
                    title : "欢迎删除的用户",
                    message : "确认还原吗？",
                    ok : function() {
                        window.location.href = url;
                    }
                });
            });

        },

        initOnlineListButton : function() {
            $(".btn-force-logout").click(function() {
                var checkbox = $.table.getAllSelectedCheckbox($(".table"));
                if(!checkbox.length) {
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
    },
    auth : {
        initSelectRoleForm : function() {
            var $leftUL = $(".auth > .left > .list > ul");
            var $rightUL = $(".auth > .right > .list > ul");
            $([$leftUL, $rightUL])
                .sortable({
                    connectWith: "ul",
                    placeholder: "ui-state-highlight",
                    stop : storeToRoleIds
                })
                .disableSelection();

            $(".btn-move-all-right").click(function() {
                $leftUL.find("li").appendTo($rightUL);
                storeToRoleIds();
            });
            $(".btn-move-all-left").click(function() {
                $rightUL.find("li").appendTo($leftUL);
                storeToRoleIds();
            });

            $(".auth > .left > .list").niceScroll({domfocus:true, hidecursordelay: 2000});
            $(".auth > .right > .list").niceScroll({domfocus:true, hidecursordelay:2000});

            function storeToRoleIds() {
                //重新计算滚动条
                resizeNiceScroll();

                var selectedRoleIdInput = $("[name=" + $rightUL.data("input-id") + "]");
                var roleIds = [];
                $($rightUL).find("li").each(function() {
                    var value = $(this).data("value");
                    roleIds[roleIds.length] = value;
                });
                selectedRoleIdInput.val(roleIds.join(","));
            }

            function resizeNiceScroll() {
                $(".auth > .left > .list").getNiceScroll().resize();
                $(".auth > .right > .list").getNiceScroll().resize();
            }
        }
    }

};