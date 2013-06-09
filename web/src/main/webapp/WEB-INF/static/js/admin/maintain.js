$.maintain = {
   icon : {
       initIconList : function(input, callback) {

            if(!callback) {
                callback = function(cssClass) {
                    input.next("i").remove();
                    input.val(cssClass);
                    input.after("<i class='" + cssClass + "' style='margin-left:10px;'></i>");
                }
            }

            input.off("click").on("click", function() {
                var iconList = $(".sys-icon-list");
                if(iconList.is(":visible")) {
                    onBodyDown();
                    return;
                }
                if(!iconList.length) {
                    $.ajax({
                        type : "GET",
                        async : false,
                        url : ctx + "/admin/maintain/icon/select",
                        success : function (html) {
                            $("body").append(html);
                        }
                    });
                    iconList = $(".sys-icon-list");

                }

                var inputOffset = input.offset();
                iconList.css({left: inputOffset.left + "px", top: inputOffset.top + input.outerHeight() + "px"}).slideDown("fast");
                iconList.find(".tab-pane").niceScroll({domfocus:true, hidecursordelay: 2000});
                iconList.find(".tab-pane").getNiceScroll().show();


                $("body").bind("mousedown", onBodyDown);

                function onBodyDown(event) {
                    if(event) {
                        var target = $(event.target);
                        var btn = target.closest("a.sys-icon-btn");
                        if(btn.length) {
                            callback(btn.find("i").attr("class"));
                        }

                        if(!btn.length && target.closest(".sys-icon-list").length) {
                            return;
                        }
                    }

                    iconList.find(".tab-pane").getNiceScroll().hide();
                    iconList.fadeOut("fast");
                    $("body").unbind("mousedown", onBodyDown);
                }
            });

        }
   }
};