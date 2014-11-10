$.personal = {
    message : {
        initBtn: function () {
            //view.jsp
            $('.message .accordion-toggle').on('click', function () {
                var toggleBtn = $(this);
                var target = $(toggleBtn.attr("href"));
                var id = target.attr("id").replace("collapse", "");
                if(!target.data("loaded")) {
                    target.data("loaded", true);
                    toggleBtn.append("<img class='loading' src=ctx + '/static/images/loading.gif' style='height:20px'>");
                    target.find(".accordion-inner").load(ctx + "/admin/personal/message/" + id + "/content", function() {
                        toggleBtn.find(".loading").remove();
                    });

                    initViewBtn(target);
                }
            });

            function initViewBtn(target) {

                target.find(".btn-view-store").click(function() {
                    var href = $(this).data("href");
                    $.app.confirm({
                        title: "收藏消息",
                        message: "确认收藏该消息吗？",
                        ok: function () {
                            location.href = href;
                        }
                    });
                    return false;
                });

                target.find(".btn-view-recycle").click(function() {
                    var href = $(this).data("href");
                    $.app.confirm({
                        title: "移动消息到垃圾箱",
                        message: "确认把该消息移动到垃圾箱吗？",
                        ok: function () {
                            location.href = href;
                        }
                    });
                    return false;
                });

                target.find(".btn-view-delete").click(function() {
                    var href = $(this).data("href");
                    $.app.confirm({
                        title: "删除垃圾箱消息",
                        message: "确认从垃圾箱删除该消息吗？",
                        ok: function () {
                            location.href = href;
                        }
                    });
                    return false;
                });
            }

            initViewBtn($(document));

            //view/list
            $(".btn-store").click(function () {
                var checkbox = $.table.getAllSelectedCheckbox($(".table"));
                if (checkbox.size() == 0) {
                    return;
                }

                $.app.confirm({
                    title: "收藏消息",
                    message: "确认收藏选中的消息吗？",
                    ok: function () {
                        location.href = ctx + "/admin/personal/message/batch/store?" + checkbox.serialize();
                    }
                });
            });

            $(".btn-recycle-or-delete").click(function () {
                var checkbox = $.table.getAllSelectedCheckbox($(".table"));
                if (checkbox.size() == 0) {
                    return;
                }


                var panel = $(this).closest(".panel");
                var state = panel.data("state");
                var isRecycle = state != 'trash_box';
                var stateInfo = panel.data("state-info");

                $.app.confirm({
                    title: isRecycle ? "移动消息到垃圾箱" : "删除垃圾箱消息",
                    message: isRecycle ? "确认将" + stateInfo + "中的消息移动到垃圾箱吗？" : "确认删除垃圾箱选中的消息吗？",
                    ok: function () {
                        location.href = ctx + "/admin/personal/message/batch/" + (isRecycle ? 'recycle' : 'delete') + "?" + checkbox.serialize();
                    }
                });
            });

            $(".btn-clear").click(function () {

                var panel = $(this).closest(".panel");
                var state = panel.data("state");
                var stateInfo = panel.data("state-info");

                $.app.confirm({
                    title: "清空" + stateInfo,
                    message: "确认清空" + stateInfo + "吗？",
                    ok: function () {
                        location.href = ctx + "/admin/personal/message/clear/" + state;
                    }
                });
            });

            $(".btn-mark-read").click(function() {
                var table = $(".table");
                var checkbox = $.table.getAllSelectedCheckbox(table);
                if (checkbox.size() == 0) {
                    return;
                }

                location.href = ctx + "/admin/personal/message/markRead?" + checkbox.serialize() + "&BackURL=" + $.table.encodeTableURL(table);
            });
        }

    }

};