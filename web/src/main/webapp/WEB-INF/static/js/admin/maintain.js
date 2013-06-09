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
   ,
   staticResource : {
       initBtn : function() {
           $(".btn-version").click(function() {
               $.app.waiting("正在执行...");
               var tr = $(this).closest("tr");
               var fileName = tr.find("[name='fileName']").val();
               var content = tr.find("[name='content']").val();
               var version = tr.find("[name='version']").text();
               if(!version) {
                   version = "1";
               } else {
                   version = parseInt(version) + 1;
               }

               var url = ctx + "/admin/maintain/staticResource/incVersion";
               $.post(
                   url,
                   {
                       fileName: fileName,
                       content : content,
                       newVersion : version
                   }
                   ,
                   function(newContent) {
                       $.app.waitingOver();
                       tr.find("[name='version']").text(version);
                       tr.find("[name='content']").val(newContent);
                       $.app.alert({message : '操作成功'});
                   }
               );
               return false;
           });

           $(".btn-batch-version").click(function() {
               var checkboxes = $.table.getAllSelectedCheckbox($("#table"));
               if(!checkboxes.length) {
                   return;
               }
               $.app.confirm({
                   message : "确认选中的js/css版本+1吗？",
                   ok : function() {
                       var fileNames = [];
                       var contents = [];
                       var versions = [];
                       for(var i = 0, l = checkboxes.length; i < l; i++) {
                           var tr = $(checkboxes[i]).closest("tr");

                           var fileName = tr.find("[name='fileName']").val();
                           var content = tr.find("[name='content']").val();
                           var version = tr.find("[name='version']").text();
                           if(!version) {
                               version = "1";
                           } else {
                               version = parseInt(version) + 1;
                           }
                           fileNames[fileNames.length] = fileName;
                           contents[contents.length] = content;
                           versions[versions.length] = version;
                       }

                       var url = ctx + "/admin/maintain/staticResource/batchIncVersion";
                       $.post(
                           url,
                           {
                               "fileNames[]" : fileNames,
                               "contents[]" : contents,
                               "newVersions[]" : versions
                           },
                           function(data) {
                               $.app.alert({
                                   message : data,
                                   ok : function() {
                                       window.location.reload();
                                   }
                               });

                           }
                       );
                   }
               });
           });


           $(".btn-clear-version").click(function() {
               var checkboxes = $.table.getAllSelectedCheckbox($("#table"));
               if(!checkboxes.length) {
                   return;
               }
               $.app.confirm({
                   message : "确认清空选中的js/css版本吗？",
                   ok : function() {
                       var fileNames = [];
                       var contents = [];
                       for(var i = 0, l = checkboxes.length; i < l; i++) {
                           var tr = $(checkboxes[i]).closest("tr");
                           var fileName = tr.find("[name='fileName']").val();
                           var content = tr.find("[name='content']").val();

                           fileNames[fileNames.length] = fileName;
                           contents[contents.length] = content;
                       }

                       var url = ctx + "/admin/maintain/staticResource/clearVersion";
                       $.post(
                           url,
                           {
                               "fileNames[]" : fileNames,
                               "contents[]" : contents
                           },
                           function(data) {
                               $.app.alert({
                                   message : data,
                                   ok : function() {
                                       window.location.reload();
                                   }
                               });

                           }
                       );
                   }
               });
           });



           $(".btn-compress").click(function() {
               $.app.waiting("正在执行...");
               var tr = $(this).closest("tr");
               var fileName = tr.find("[name='fileName']").val();
               var content = tr.find("[name='content']").val();

               var url = ctx + "/admin/maintain/staticResource/compress";
               $.post(
                   url,
                   {
                       fileName: fileName,
                       content : content
                   }
                   ,
                   function(data) {
                       $.app.waitingOver();
                       $.app.alert({message : data});
                   }
               );
               return false;
           });


           $(".btn-batch-compress").click(function() {
               var checkboxes = $.table.getAllSelectedCheckbox($("#table"));
               if(!checkboxes.length) {
                   return;
               }
               $.app.confirm({
                   message : "确认压缩选中的js/css吗？",
                   ok : function() {
                       var fileNames = [];
                       var contents = [];
                       for(var i = 0, l = checkboxes.length; i < l; i++) {
                           var tr = $(checkboxes[i]).closest("tr");

                           var fileName = tr.find("[name='fileName']").val();
                           var content = tr.find("[name='content']").val();
                           fileNames[fileNames.length] = fileName;
                           contents[contents.length] = content;
                       }

                       var url = ctx + "/admin/maintain/staticResource/batchCompress";
                       $.post(
                           url,
                           {
                               "fileNames[]" : fileNames,
                               "contents[]" : contents
                           },
                           function(data) {
                               $.app.alert({
                                   message : data,
                                   ok : function() {
                                       window.location.reload();
                                   }
                               });

                           }
                       );
                   }
               });

           });





           $(".btn-switch").click(function() {
               $.app.waiting("正在执行...");
               var btn = $(this);
               var tr = btn.closest("tr");
               var fileName = tr.find("[name='fileName']").val();
               var content = tr.find("[name='content']").val();

               var url = ctx + "/admin/maintain/staticResource/switch";
               $.post(
                   url,
                   {
                       fileName: fileName,
                       content : content,
                       min : btn.hasClass("min")
                   }
                   ,
                   function(data) {
                       $.app.waitingOver();
                       if(data.success) {
                           tr.find("[name='content']").val(data.content);
                           tr.find("[name='url']").text(data.url);
                       }
                       $.app.alert({message : data.msg});
                   }
               );
               return false;
           });


           $(".btn-batch-switch").click(function() {
               var btn = $(this);
               var checkboxes = $.table.getAllSelectedCheckbox($("#table"));
               if(!checkboxes.length) {
                   return;
               }
               $.app.confirm({
                   message : "确认切换选中的js/css吗？",
                   ok : function() {
                       var fileNames = [];
                       var contents = [];
                       for(var i = 0, l = checkboxes.length; i < l; i++) {
                           var tr = $(checkboxes[i]).closest("tr");

                           var fileName = tr.find("[name='fileName']").val();
                           var content = tr.find("[name='content']").val();
                           fileNames[fileNames.length] = fileName;
                           contents[contents.length] = content;
                       }

                       var url = ctx + "/admin/maintain/staticResource/batchSwitch";
                       $.post(
                           url,
                           {
                               "fileNames[]" : fileNames,
                               "contents[]" : contents,
                               min : btn.hasClass("min")
                           },
                           function(data) {
                               $.app.alert({
                                   message : data,
                                   ok : function() {
                                       window.location.reload();
                                   }
                               });

                           }
                       );
                   }
               });
           });
       }
   }
};