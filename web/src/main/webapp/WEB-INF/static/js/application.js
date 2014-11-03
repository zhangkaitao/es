//自己扩展的jquery函数
//压缩时请把编码改成ANSI
$.app = {

    /**初始化主页 layout，菜单，tab*/
    initIndex: function () {
        $.menus.initMenu();
        $.layouts.initLayout();
        $.tabs.initTab();

        $.app.initCommonBtn();

        var message = $.app.initMessage();
        var notification = $.app.initNotification();
        var fiveMinute = 5 * 60 * 1000;
        var pollingUrl = ctx + "/admin/polling";
        var longPolling = function(url, callback) {
            $.ajax({
                url: url,
                async: true,
                cache: false,
                global: false,
                timeout: 30 * 1000,
                dataType : "json",
                success: function (data, status, request) {
                    callback(data);
                    data = null;
                    status = null;
                    request = null;
                    setTimeout(
                        function () {
                            longPolling(url, callback);
                        },
                        10
                    );
                },
                error: function (xmlHR, textStatus, errorThrown) {
                    xmlHR = null;
                    textStatus = null;
                    errorThrown = null;

                    setTimeout(
                        function () {
                            longPolling(url, callback);
                        },
                        30 * 1000
                    );
                }
            });
        };
        longPolling(pollingUrl, function(data) {
            if(data) {
                if(data.unreadMessageCount) {
                    message.update(data.unreadMessageCount);
                }
                if(data.notifications) {
                    notification.update(data.notifications);
                }
            }
        });

    },
    initCommonBtn : function() {
        $(".btn-view-info,.btn-change-password").click(function() {
            var a = $(this);
            var url = "";
            if(a.is(".btn-view-info")) {
                url = ctx +"/admin/sys/user/loginUser/viewInfo";
            } else if(a.is(".btn-change-password")) {
                url = ctx + "/admin/sys/user/loginUser/changePassword";
            }
            setTimeout(function() {
                $.tabs.activeTab($.tabs.nextCustomTabIndex(), "个人资料", url, true)
            }, 0);
        });
        $(".btn-view-message,.btn-message").click(function() {
            var url = ctx + "/admin/personal/message";
            setTimeout(function() {
                $.tabs.activeTab($.tabs.nextCustomTabIndex(), "我的消息", url, true)
            }, 0);
        });
        $(".btn-view-notice").click(function() {
            var url = ctx + "/office/personal/notice/list?read=false";
            setTimeout(function() {
                $.tabs.activeTab($.tabs.nextCustomTabIndex(), "我的通知", url, true)
            }, 0);
        });
        $(".btn-view-worklist,.btn-view-work").click(function() {
            var $that = $(this);
            var url = ctx + "/office/personal/worklist";
            setTimeout(function() {
                if($that.is(".btn-view-work")) {
                    url = $that.data("url");
                }
                $.tabs.activeTab($.tabs.nextCustomTabIndex(), "我的待办工作", url, true)
            }, 0);

            return false;
        });


    },
    initMessage : function() {
        var messageBtn = $(".btn-message");
        var icon = messageBtn.find(".icon-message");
        var messageBtnInterval = null;

        var activeUnreadIcon = function(count) {
            clearInterval(messageBtnInterval);
            if(count > 0) {
                var label = messageBtn.find(".icon-count");
                if(!label.length) {
                    label = $("<i class='label label-important icon-count'></i>");
                    messageBtn.append(label);
                }
                label.text(count);
                messageBtn.addClass("unread");
                messageBtnInterval = setInterval(function() {icon.toggleClass("icon-envelope-alt").toggleClass("icon-envelope");}, 650);
            }
        };

        messageBtn.click(function() {
            clearInterval(messageBtnInterval);
            $($.find("#menu a:contains(我的消息)")).dblclick();
            messageBtn.removeClass("unread");
            messageBtn.find(".icon-count").remove();
            icon.removeClass("icon-envelope").addClass("icon-envelope-alt");

        });

        activeUnreadIcon(messageBtn.data("unread"));

        return {
            update : function(unReadMessageCount) {
                activeUnreadIcon(unReadMessageCount);
            }
        };
    },
    initNotification : function() {
        var notificationBtn = $(".btn-notification");
        var notificationList = $(".notification-list");
        var menu = $(".notification-list .menu");
        var menuList = menu.find(".list");
        var detail = $(".notification-list .detail");
        var detailList = detail.find(".list");
        var loading = $(".notification-list .loading");
        var noComment = $(".notification-list .no-comment");
        var markReadUrl = ctx + "/admin/personal/notification/markRead?id=";

        var contentTemplate = '<li class="view-content {unread}"><span>{title}</span><span class="pull-right">{date}</span></li>';
        var detailContentTemplate = '<div id="notificaiton-{id}" class="notification-detail" style="display: none"><div class="title"><span>{title}</span><span class="pull-right">{date}</span></div><div class="content">{content}</div></div>';
        var moreContent = '<li class="view-all-notification"><span>&gt;&gt;查看所有通知</span></li>';

        var viewAllNotification = function() {
            $($.find("#menu a:contains(我的通知)")).dblclick();
            hideNotification();
            return false;
        };
        var hideNotification = function() {
            notificationList.find(".content > div").hide();
            notificationList.removeClass("in");
            $("body")
                .off("click")
                .find("iframe").contents().find("body").each(function() {
                    $(this).off("click");
                });
        };

        var activeDetailBtn = function() {
            var notificationDetails = detail.find(".notification-detail");
            var current = notificationDetails.not(":hidden");
            var currentIndex = notificationDetails.index(current);

            var pre = detail.find(".pre");
            var next = detail.find(".next");
            pre.removeClass("none");
            next.removeClass("none");


            if(currentIndex == 0) {
                pre.addClass("none");
            }

            var currentMenu = $(menu.find(".view-content").get(currentIndex));
            if(currentMenu.hasClass("unread")) {
                currentMenu.removeClass("unread");
                var id = current.attr("id").replace("notificaiton-", "");
                $.ajax({
                    url: markReadUrl + id,
                    global: false,
                    error: function (xmlHR, textStatus, errorThrown) {
                        //ignore
                    }
                });
            }

            if(currentIndex == notificationDetails.length - 1) {
                next.addClass("none");
            }

        };

        var showNoComment = function() {
            notificationList.find(".content > div").hide();
            noComment.show();
        };
        var showMenu = function() {
            notificationList.find(".content > div").hide();
            menu.show();
        };


        var initDetail = function(dataList) {
            var content = "";
            $(dataList).each(function(index, data) {
                content = content + detailContentTemplate.replace("{id}", data.id).replace("{title}", data.title).replace("{date}", data.date).replace("{content}", data.content);
            });
            detailList.html(content);
            detail.find(".notification-detail:first").show();
            detail.find(".back-notification-list").click(function() {
                slide(detail, menu, "left");
            });
            detail.find(".pre").click(function() {
                var current = detail.find(".notification-detail").not(":hidden");
                var pre = current.prev(".notification-detail");
                if (pre.length) {
                    slide(current, pre, "left");
                }
            });
            detail.find(".next").click(function() {
                var current = detail.find(".notification-detail").not(":hidden");
                var next = current.next(".notification-detail");
                if (next.length) {
                    slide(current, next, "right");
                }
            });
            slide(menu, detail, "right");

            return false;
        };



        var initMenu = function(dataList, hasUnread) {

            var content = "";
            $(dataList).each(function (index, data) {
                content = content + contentTemplate.replace("{unread}", data.read ? "" : "unread").replace("{title}", data.title).replace("{date}", data.date);
            });
            content = content + moreContent;
            menuList.html(content);

            menu.find(".view-content").click(function() {
                initDetail(dataList);
            });
            menu.find(".view-all-notification").click(function() {
                viewAllNotification();
            });

            if(hasUnread) {
                showNotification();
            }

            return false;
        };
        var slide = function(from, to, direction) {
            from.css({
                position: 'relative',
                width:"100%"
            });
            from.stop(true).hide("slide", {direction : direction == "left" ? "rigth" : "left"}, function() {
                from.css({
                    position : "",
                    width : "",
                    left : ""
                });
            });
            to.css({
                position: 'absolute',
                top: to.is(".notification-detail") ? to.closest(".detail").find(".title").outerHeight() + "px" : "0px",
                left: "0px",
                width: "100%",
                display : "none"
            });
            to.stop(true).show("slide", {direction : direction}, function() {
                to.css({
                    position : "",
                    left : "",
                    top : "",
                    width : ""
                });
                if(to.is(".notification-detail") || to.is(".detail")) {
                    activeDetailBtn();
                }
            });
        };

        var showNotification = function() {
            notificationList.addClass("in");

            var windowClickHideNotification = function (event) {
                var target = $(event.target);
                if (!target.closest(".btn-notification").length && !target.closest(".notification-list").length) {
                    hideNotification();
                }
            };

            $("body")
                .on("click", windowClickHideNotification)
                .find("iframe").contents().find("body").each(function() {
                    $(this).on("click", windowClickHideNotification);
                });
            if(menu.find(".view-content").length) {
                showMenu();
            } else {
                showNoComment();
            }
        };

        notificationBtn.click(function() {
            if(notificationList.hasClass("in")) {
                hideNotification();
            } else {
                showNotification();
            }
        });
        notificationList.find(".close-notification-list").click(function() {
            hideNotification();
        });

        hideNotification();

        return {
            update : function(dataList) {

                if(!dataList.length) {
                    showNoComment();
                    return;
                }

                var hasUnread = false;
                for(var i = 0, l = dataList.length; i < l; i++) {
                    var data = dataList[i];
                    if(!data.read) {
                        hasUnread = true;
                        data.title = data.title.replace("{ctx}", ctx);
                        data.content = data.content.replace("{ctx}", ctx);
                    }
                }

               initMenu(dataList, hasUnread);

            }
        };
    },
    /**
     * 异步加载url内容到tab
     */
    loadingToCenterIframe: function (panel, url, loadingMessage, forceRefresh) {
        panel.data("url", url);

        var panelId = panel.prop("id");
        var iframeId = "iframe-" + panelId;
        var iframe = $("#" + iframeId);

        if (!iframe.length || forceRefresh) {
            if(!iframe.length) {
                iframe = $("iframe[tabs=true]:last").clone(true);
                iframe.prop("id", iframeId);
                $("iframe[tabs=true]:last").after(iframe);
            }

            $.app.waiting(loadingMessage);
            iframe.prop("src", url).one("load", function () {
                $.app.activeIframe(panelId, iframe);
                $.app.waitingOver();
            });

        } else {
            $.app.activeIframe(panelId, iframe);
        }

    },
    activeIframe: function (panelId, iframe) {
        if (!iframe) {
            iframe = $("#iframe-" + panelId);
        }
        var layout = $.layouts.layout;
        if (layout.panes.center.prop("id") == iframe.prop("id")) {
            return;
        }
        layout.panes.center.hide();
        layout.panes.center = iframe;
        layout.panes.center.show();
        layout.resizeAll();
        $.tabs.initTabScrollHideOrShowMoveBtn(panelId);
    },

    waiting : function(message, isSmall) {
        if(!message) {
            message = "装载中...";
        }

        message = '<img src="' + ctx + '/static/images/loading.gif" '+ (isSmall ? "width='20px'" : "") +'/> ' + message;
        if(!isSmall) {
            message = "<h4>"+message+"</h4>";
        }
        $.blockUI({
            fadeIn: 700,
            fadeOut: 700,
            showOverlay: false,
            css: {
                border: 'none',
                padding: '15px',
                backgroundColor: '#eee',
                '-webkit-border-radius': '10px',
                '-moz-border-radius': '10px',
                opacity:1,
                color: '#000',
                width: isSmall ? "40%" : "30%"

            },
            message: message
        });
    }
    ,
    waitingOver: function () {
        $.unblockUI();
    }
    ,
    /**
     * 当前显示的模态窗口队列
     */
    _modalDialogQueue:null,
    /**
     * 模态窗口
     * @title 标题
     * @param url
     * @param settings
     */
    modalDialog : function(title, url, settings) {

        $.app.waiting();
        var defaultSettings = {
            title : title,
            closeText : "关闭",
            closeOnEscape:false,
            height:300,
            width:600,
            modal:true,
            noTitle : false,
            close: function() {
                $(this).closest(".ui-dialog").remove();
            },
            _close : function(modal) {
                $(modal).dialog("close");
                if($.app._modalDialogQueue.length > 0) {
                    $.app._modalDialogQueue.pop();
                }
            },
            buttons:{
                '确定': function() {
                    if(settings.ok) {
                        if(settings.ok($(this))) {
                            settings._close(this);
                        }
                    } else {
                        settings._close(this);
                    }
                    if(settings.callback) {
                        settings.callback();
                    }
                },
                '关闭': function () {
                    settings._close(this);
                    if(settings.callback) {
                        settings.callback();
                    }
                }
            }
        };
        if(!settings) {
            settings = {};
        }
        settings = $.extend(true, {}, defaultSettings, settings);

        if(!settings.ok) {
            delete settings.buttons['确定'];
        }

        $.ajax({
            url: url,
            headers: { table:true }
        }).done(function (data) {
                $.app.waitingOver();
                var div = $("<div></div>").append(data);
                var dialog = div.dialog(settings)
                    .closest(".ui-dialog").data("url", url).removeClass("ui-widget-content")
                    .find(".ui-dialog-content ").removeClass("ui-widget-content").focus();

                if(settings.noTitle) {
                    dialog.closest(".ui-dialog").find(".ui-dialog-titlebar").addClass("no-title");
                }
                dialog.closest(".ui-dialog").focus();
                if(!$.app._modalDialogQueue) {
                    $.app._modalDialogQueue = new Array();
                }
                $.app._modalDialogQueue.push(dialog);
                $.table.initTable(div.find(".table"));

//            $.blockUI({
//                url : url,
//                theme:true,
//                showOverlay : true,
//                title : title,
//                message : data,
//                css : css,
//                themedCSS: css
//            });


            });
    }
    ,
    /**
     * 取消编辑
     */
    cancelModelDialog : function() {
        if($.app._modalDialogQueue && $.app._modalDialogQueue.length > 0) {
            $.app._modalDialogQueue.pop().dialog("close");
        }
    }
    ,
    alert : function(options) {
        if(!options) {
            options = {};
        }
        var defaults = {
            title : "警告",
            message : "非法的操作",
            okTitle : "关闭",
            ok : $.noop
        };
        options.alert = true;
        options = $.extend({}, defaults, options);
        this.confirm(options);
    }
    ,
    /**
     * 格式
     * @param options
     */
    confirm : function(options) {
        var defaults = {
            title : "确认执行操作",
            message : "确认执行操作吗？",
            cancelTitle : '取消',
            okTitle : '确定',
            cancel : $.noop,
            ok : $.noop,
            alert : false
        };

        if(!options) {
            options = {};
        }
        options = $.extend({}, defaults, options);

        var template =
            '<div class="modal hide fade confirm">' +
                '<div class="modal-header">' +
                '<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>' +
                '<h3 class="title">{title}</h3>' +
                '</div>' +
                '<div class="modal-body">' +
                '<div>{message}</div>' +
                '</div>' +
                '<div class="modal-footer">' +
                '<a href="#" class="btn btn-ok btn-danger" data-dismiss="modal">{okTitle}</a>' +
                '<a href="#" class="btn btn-cancel" data-dismiss="modal">{cancelTitle}</a>'+
                '</div>' +
                '</div>';

        var modalDom =
            $(template
                .replace("{title}", options.title)
                .replace("{message}", options.message)
                .replace("{cancelTitle}", options.cancelTitle)
                .replace("{okTitle}", options.okTitle));


        var hasBtnClick = false;
        if(options.alert) {
            modalDom.find(".modal-footer > .btn-cancel").remove();
        } else {
            modalDom.find(".modal-footer > .btn-cancel").click(function() {
                hasBtnClick = true;
                options.cancel();
            });
        }
        modalDom.find(".modal-footer > .btn-ok").click(function() {
            hasBtnClick = true;
            options.ok();
        });

        var modal = modalDom.modal();

        modal.on("hidden", function() {
            modal.remove();//移除掉 要不然 只是hidden
            if(hasBtnClick) {
                return true;
            }
            if(options.alert) {
                options.ok();
            } else {
                options.cancel();
            }
        });

        return modal;
    }
    ,
    isImage : function(filename) {
        return /gif|jpe?g|png|bmp$/i.test(filename);
    }
    ,
    initDatetimePicker : function() {
        //初始化 datetime picker
        $('.date:not(.custom)').each(function() {
            var $date = $(this);

            if($date.attr("initialized") == "true") {
                return;
            }

            var pickDate = $(this).find("[data-format]").data("format").toLowerCase().indexOf("yyyy-mm-dd") != -1;
            var pickTime = $(this).find("[data-format]").data("format").toLowerCase().indexOf("hh:mm:ss") != -1;
            $date.datetimepicker({
                pickDate : pickDate,
                pickTime : pickTime,
                maskInput: true,
                language:"zh-CN"
            }).on('changeDate', function(ev) {
                    if(pickTime == false) {
                        $(this).data("datetimepicker").hide();
                    }
                });
            $date.find(":input").click(function() {$date.find(".icon-calendar,.icon-time,.icon-date").click();});
            $date.attr("initialized", true);
        });
    },
    initCalendar : function() {

        var date = new Date();
        var d = date.getDate();
        var m = date.getMonth();
        var y = date.getFullYear();

        var calendar = $('#calendar').fullCalendar({
            header: {
                left: 'prev,next today',
                center: 'title',
                right: 'month,agendaWeek,agendaDay'
            },
            events: ctx + "/admin/personal/calendar/load",
            eventDrop: function(event, delta) {
                moveCalendar(event);
            },
            eventClick: function(event, delta) {
                viewCalendar(event);
            },
            loading: function(bool) {
                if (bool) $('#loading').show();
                else $('#loading').hide();
            },
            editable: true,
            selectable: true,
            selectHelper: true,
            select: function(start, end, allDay) {
                openNewCalendarForm(start, end);
                calendar.fullCalendar('unselect');
            }
        });

        $('span.fc-button-prev').before('<span class="fc-button fc-button-add fc-state-default fc-corner-left fc-corner-right">新增</span>');

        $(".fc-button-add").click(function() {
            openNewCalendarForm();
        });

        function openNewCalendarForm(start, end) {
            var url = ctx + "/admin/personal/calendar/new";
            if(start) {
                start = $.fullCalendar.formatDate(start, "yyyy-MM-dd HH:mm:ss");
                end = $.fullCalendar.formatDate(end, "yyyy-MM-dd HH:mm:ss");
                url = url + "?start=" + start + "&end=" + end;
            }
            $.app.modalDialog("新增提醒事项", url, {
                width:370,
                height:430,
                ok : function(modal) {

                    var form = modal.find("#editForm");
                    if(!form.validationEngine('validate')) {
                        return false;
                    }
                    var url = ctx + "/admin/personal/calendar/new";
                    $.post(url, form.serialize(), function() {
                        calendar.fullCalendar("refetchEvents");
                    });

                    return true;
                }
            });
        }

        function moveCalendar(event) {
            var url = ctx + "/admin/personal/calendar/move";
            var id = event.id;
            var start = $.fullCalendar.formatDate(event.start, "yyyy-MM-dd HH:mm:ss");
            var end = $.fullCalendar.formatDate(event.end, "yyyy-MM-dd HH:mm:ss");
            url = url + "?id=" + id;
            url = url + "&start=" + start + "&end=" + end;

            $.post(url, function() {
                calendar.fullCalendar("refetchEvents");
            });
        }

        function viewCalendar(event) {
            var url = ctx + "/admin/personal/calendar/view/" + event.id;
            $.app.modalDialog("查看提醒事项", url, {
                width:370,
                height:250,
                noTitle : false,
                okBtn : false,
                closeBtn : false
            });
        }
        $("body").on("click", ".btn-delete-calendar", function() {
            var $this = $(this);
            $.app.confirm({
                title : '确认删除提醒事项吗？',
                message : '确认删除提醒事项吗？',
                ok : function() {
                    var url = ctx + "/admin/personal/calendar/delete?id=" + $this.data("id");
                    $.post(url, function() {
                        calendar.fullCalendar("refetchEvents");
                        $.app.closeModalDialog();
                    });
                }
            });

        });
    }
    ,
    removeContextPath : function(url) {
        if(url.indexOf(ctx) == 0) {
            return url.substr(ctx.length);
        }
        return url;
    }
    ,
    /**
     * 异步化form表单或a标签
     * @param $form
     * @param containerId
     */
    asyncLoad : function($tag, containerId) {
        if($tag.is("form")) {
            $tag.submit(function() {
                if($tag.prop("method").toLowerCase() == 'post') {
                    $.post($tag.prop("action"), $tag.serialize(), function(data) {
                        $("#" + containerId).replaceWith(data);
                    });
                } else {
                    $.get($tag.prop("action"), $tag.serialize(), function(data) {
                        $("#" + containerId).replaceWith(data);
                    });
                }
                return false;
            });
        } else if($tag.is("a")) {
            $tag.click(function() {
                $.get($tag.prop("href"), function(data) {
                    $("#" + containerId).replaceWith(data);
                });
                return false;
            });
        } else {
            $.app.alert({message : "该标签不支持异步加载，支持的标签有form、a"});
        }

    },
    /**
     * 只读化表单
     * @param form
     */
    readonlyForm : function(form, removeButton) {
        var inputs = $(form).find(":input");
        inputs.not(":submit,:button").prop("readonly", true);
        if(removeButton) {
            inputs.remove(":button,:submit");
        }
    }
    ,

    /**
     * 将$("N").val() ----> [1,2,3]
     */
    joinVar : function(elem, separator) {
        if(!separator) {
            separator = ",";
        }
        var array = new Array();
        $(elem).each(function() {
            array.push($(this).val());
        });

        return array.join(separator);
    },

    /**
     * 异步加载table子内容(父子表格)
     * @param toggleEle
     * @param tableEle
     * @param asyncLoadURL
     */
    toggleLoadTable : function(tableEle, asyncLoadURL) {
        var openIcon = "icon-plus-sign";
        var closeIcon = "icon-minus-sign";
        $(tableEle).find("tr .toggle-child").click(function() {
            var $a = $(this);
            //只显示当前的 其余的都隐藏
            $a.closest("table")
                .find(".toggle-child." + closeIcon).not($a).removeClass(closeIcon).addClass(openIcon)
                .end().end()
                .find(".child-data").not($a.closest("tr").next("tr")).hide();

            //如果是ie7
            if($(this).closest("html").hasClass("ie7")) {
                var $aClone = $(this).clone(true);
                if($aClone.hasClass(closeIcon)) {
                    $aClone.addClass(openIcon).removeClass(closeIcon);
                } else {
                    $aClone.addClass(closeIcon).removeClass(openIcon);
                }
                $(this).after($aClone);
                $(this).remove();
                $a = $aClone;
            } else {
                $a.toggleClass(openIcon);
                $a.toggleClass(closeIcon);
            }

            var $currentTr = $a.closest("tr");
            var $dataTr = $currentTr.next("tr");
            if(!$dataTr.hasClass("child-data")) {
                $.app.waiting();
                $dataTr = $("<tr class='child-data' style='display: none;'></tr>");
                var $dataTd = $("<td colspan='" + $currentTr.find("td").size() + "'></td>");
                $dataTr.append($dataTd);
                $currentTr.after($dataTr);
                $dataTd.load(asyncLoadURL.replace("{parentId}", $a.data("id")),function() {
                    $.app.waitingOver();
                });
            }
            $dataTr.toggle();

            return false;
        });

    },

    initAutocomplete : function(config) {

        var defaultConfig = {
            minLength : 1,
            enterSearch : false,
            focus: function( event, ui ) {
                $(config.input).val( ui.item.label );
                return false;
            },
            renderItem : function( ul, item ) {
                return $( "<li>" )
                    .data( "ui-autocomplete-item", item )
                    .append( "<a>" + item.label + "</a>" )
                    .appendTo( ul );
            }
        };

        config = $.extend(true, defaultConfig, config);

        $(config.input)
            .on( "keydown", function( event ) {
                //回车查询
                if(config.enterSearch && event.keyCode === $.ui.keyCode.ENTER) {
                    config.select(event, {item:{value:$(this).val()}});
                }
            })
            .autocomplete({
                source : config.source,
                minLength : config.minLength,
                focus : config.focus,
                select : config.select
            }).data( "ui-autocomplete" )._renderItem = config.renderItem;
    }

};

$.layouts = {
    layout: null,
    /**初始化布局*/
    initLayout: function () {
        function resizePanel(panelName, panelElement, panelState, panelOptions, layoutName) {
            var tabul = $(".tabs-fix-top");
            if (panelName == 'north') {
                var top = 0;
                if($("html").hasClass("ie")) {
                    top = panelElement.height() - 33;

                } else {
                    top = panelElement.height() - 30;
                }
                if (panelState.isClosed) {
                    top = -58;
                }
                tabul.css("top", top);
            }

            if(panelName == "center") {
                tabul.find(".ul-wrapper").andSelf().width(panelState.layoutWidth);
                $.tabs.initTabScrollHideOrShowMoveBtn();
            }


        }

        this.layout = $('.index-panel').layout({
            west__size:  210
            ,   south__size: 30
            ,	west__spacing_closed:		20
            ,	west__togglerLength_closed:	100
            ,	west__togglerContent_closed:"菜<BR>单"
            ,	togglerTip_closed:	"打开"
            ,	togglerTip_open:	"关闭"
            ,	sliderTip:			"滑动打开"
            ,   resizerTip:         "调整大小"
            ,   onhide: resizePanel
            ,   onshow: resizePanel
            ,   onopen: resizePanel
            ,   onclose: resizePanel
            ,   onresize: resizePanel
            ,	center__maskContents:true // IMPORTANT - enable iframe masking
            ,   north : {
                togglerLength_open : 0
                ,  resizable : false
                ,  size: 95
            },
            south: {
                resizable:false
            }
        });
    }
}

$.menus = {
    /**初始化菜单*/
    initMenu: function () {
        var menus = $("#menu");
        menus.accordion({
            header:"h3",
            heightStyle:"content",
            icons : {
                header: "icon-caret-right",
                activeHeader: "icon-caret-down"
            },
            animate : {
                easing : "easeOutQuart"
            }
        });

        menus.find(".ui-accordion-header-icon").removeClass("ui-icon").addClass("menu-header-icon");

        var leafIconClass = "menu-icon icon-angle-right";
        var branchOpenIconClass = "menu-icon icon-double-angle-right";
        var branchCloseIconClass = "menu-icon icon-double-angle-down";
        menus.find("li").each(function () {
            var li = $(this);

            li.children("a").wrap("<div class='li-wrapper'></div>");
            var liWrapper = li.children(".li-wrapper");
            var liUL = li.find("ul");
            var hasChild = liUL.length;
            if (hasChild) {
                liUL.hide();
                liWrapper.prepend('<span class="' + branchOpenIconClass + '"></span>')
                    .click(function () {
                        if (liWrapper.children("span").hasClass(branchCloseIconClass)) {
                            liWrapper.children("span")
                                .removeClass(branchCloseIconClass)
                                .addClass(branchOpenIconClass)
                                .end()
                                .closest("li").children("ul").hide("blind");
                        } else {
                            liWrapper.children("span")
                                .removeClass(branchOpenIconClass)
                                .addClass(branchCloseIconClass)
                                .end()
                                .closest("li").children("ul").show("blind");
                        }
                    });
            } else {
                liWrapper.prepend('<span class="' + leafIconClass + '"></span>');
            }
        });

        menus.find("a").each(function () {
            var a = $(this);
            var title = a.text();
            var href = a.attr("href");
            a.attr("href", "#");
            if (href == "#" || href == '') {
                return;
            }

            var active = function(a, forceRefresh) {
                menus.find("a").closest("li > .li-wrapper").removeClass("active");
                a.closest("li > .li-wrapper").addClass("active");
                var oldPanelIndex = a.data("panelIndex");
                var activeMenuCallback = function(panelIndex) {
                    if(!a.data("panelIndex")) {
                        a.data("panelIndex", panelIndex);
                        a.attr("id", "menu-" + panelIndex);
                    }
                }
                $.tabs.activeTab(oldPanelIndex, title, href, forceRefresh, activeMenuCallback);

                return false;
            }

            a.closest("li")
                .click(function () {
                    active(a, false);
                    return false;
                }).dblclick(function() {
                    active(a, true);//双击强制刷新
                    return false;
                });
        });
    }
}

$.tabs = {
    tabs: null,
    maxTabIndex : 1,
    /*自己创建tab时起始索引*/
    customTabStartIndex : 9999999999,
    /**初始化tab */
    initTab: function () {
        function activeMenu(tabPanelId) {
            var currentMenu = $("#menu-" + tabPanelId.replace("tabs-", ""));
            $("#menu .li-wrapper.active").removeClass("active");

            if(currentMenu.length) {
                //把父菜单展示出来
                currentMenu.parents("ul").each(function(){
                    //不能使用“ul:hidden” 因为它是把只有隐藏的都查出来
                    // 比如<ul style="display:none"><li><ul><li><a class='a'></a></li></ul></li></ul>
                    //$(".a").parents("ul:hidden") 会查出两个 即hidden的元素对其子也是有效的
                    if($(this).css("display") == 'none') {
                        $(this).prev("a").click();
                    }
                });
                currentMenu.closest(".li-wrapper").addClass("active");
            }
        }

        var tabs = $(".tabs-bar").tabs({
            beforeActivate : function(event, ui) {
                setTimeout(function() {
                    var tabs = $.tabs.tabs;
                    tabs.find(".menu").hide();
                    tabs.find("#" + ui.newPanel.attr("aria-labelledby")).siblings(".menu").show();
                }, 0);
            },
            activate: function (event, ui) {
                setTimeout(function() {
                    var tabs = $.tabs.tabs;
                    var newPanelId = ui.newPanel.prop("id");
                    activeMenu(newPanelId);
                    $.app.activeIframe(newPanelId);
                }, 0);
            }
        });
        $.tabs.tabs = tabs;
        tabs.delegate("span.icon-remove", "click", function () {
            var panelId = $(this).closest("li").remove().attr("aria-controls");
            setTimeout(function() {
                $.tabs.removeTab(panelId);
            }, 0);
        });
        tabs.delegate("span.icon-refresh", "click", function () {
            var panelId = $(this).closest("li").attr("aria-controls");
            setTimeout(function() {
                $.tabs.activeTab(panelId, null, null, true);
            }, 0);
        });

        tabs.bind("keyup", function (event) {
            if (event.altKey && event.keyCode === $.ui.keyCode.BACKSPACE) {
                var panelId = tabs.find(".ui-tabs-active").remove().attr("aria-controls");
                setTimeout(function() {
                    $.tabs.removeTab(panelId);
                }, 0);
            }
        });

        $.tabs.initTabScroll();
        $.tabs.initTabContextMenu();
    },
    removeTab : function(panelId) {
        var tabs = $.tabs.tabs;
        var panel = $("#" + panelId);
        var iframe = $("#iframe-" + panelId);

        var currentMenu = $("#menu-" + panelId.replace("tabs-", ""));
        if(currentMenu.length) {
            currentMenu.attr("id", "");
            currentMenu.attr("panelIndex", "");
            $("#menu .li-wrapper.active").removeClass("active");
        }

        tabs.tabs("option", "active", tabs.find(".ui-tabs-panel").size());
        tabs.tabs("refresh");

        panel.remove();
        var iframeDom = iframe[0];
        iframeDom.src = "";
        iframeDom.contentWindow.document.write('');
        iframeDom.contentWindow.close();
        iframe.remove();
        var isIE = !-[1,];
        if (isIE) {
            CollectGarbage();
        }

    },
    /**
     * 创建新的tab
     * @param title
     * @param panelIndex
     */
    /**
     * 创建新的tab
     * @param title
     * @param panelIndex
     */
    createTab : function(title, panelIndex) {
        var tabs = $.tabs.tabs;

        if(tabs.find(".ui-tabs-panel").length > 20) {
            $.app.alert({
                message : "您打开的面板太多，为提高系统运行速度，请先关闭一些！"
            });
            return;
        }


        var newPanelIndex = panelIndex || $.tabs.maxTabIndex++ || 1;
        var newPanelId = "tabs-" + newPanelIndex;
        var tabTemplate = "<li><a href='#{href}'>{label}</a> <span class='menu icon-remove' role='presentation'title='关闭'></span><br/><span class='menu icon-refresh' role='presentation' title='刷新'></span></li>";
        var li = $(tabTemplate.replace(/\{href\}/g, newPanelId).replace(/\{label\}/g, title));

        tabs.find("ul.ui-tabs-nav").append(li);
        tabs.append('<div id="' + newPanelId + '"></div>');

        tabs.tabs("refresh");

        var newPanel = $("#" + newPanelId);
        newPanel.data("index", newPanelIndex);

        return newPanel;
    },
    /**
     * 激活指定索引处的tab 如果没有就创建一个
     * @param panelIdOrIndex
     * @param title
     * @param url
     * @param forceRefresh
     * @return {*}
     */
    activeTab: function (panelIdOrIndex, title, url, forceRefresh, callback) {
        var tabs = $.tabs.tabs;
        if(panelIdOrIndex && ("" + panelIdOrIndex).indexOf("tabs-") == 0) {
            currentTabPanel = $("#" + panelIdOrIndex);
        } else {
            var currentTabPanel = $("#tabs-" + panelIdOrIndex);
        }

        if (!currentTabPanel.length) {
            currentTabPanel = $.tabs.createTab(title, panelIdOrIndex);
        }

        if(callback) {
            callback(currentTabPanel.data("index"));
        }

        if(!url) {
            url = currentTabPanel.data("url");
        }

        setTimeout(function() {
            $.app.loadingToCenterIframe(currentTabPanel, url, null, forceRefresh);
            tabs.tabs("option", "active", tabs.find(".ui-tabs-panel").index(currentTabPanel));
        }, 0);
        return currentTabPanel.data("index");
    },

    initTabScrollHideOrShowMoveBtn : function(panelId) {
        var $ulWrapper = $(".tabs-bar .ul-wrapper");
        var $lastLI = $ulWrapper.find("ul li:last");
        var $firstLI = $ulWrapper.find("ul li:first");

        var ulWapperOffsetLeft = $ulWrapper.offset().left;
        var ulWrapperLeftPos = ulWapperOffsetLeft + $ulWrapper.width();

        var hideOrShowBtn = function() {
            var lastLIOffsetLeft = $lastLI.offset().left;
            var lastLILeftPos = lastLIOffsetLeft + $lastLI.width();
            var firstLIOffsetLeft = $firstLI.offset().left;

            var $leftBtn = $(".tabs-bar .icon-chevron-left");
            var $rightBtn = $(".tabs-bar .icon-chevron-right");

            if (ulWapperOffsetLeft == firstLIOffsetLeft) {
                $leftBtn.hide();
            } else {
                $leftBtn.show();
            }
            if (ulWrapperLeftPos >= lastLILeftPos) {
                $rightBtn.hide();
            } else {
                $rightBtn.show();
            }
        };

        if(panelId) {

            var $li = $(".tabs-bar").find("li[aria-labelledby='" + $("#" + panelId).attr("aria-labelledby") + "']");

            var liOffsetLeft = $li.offset().left;
            var liLeftPos = liOffsetLeft + $li.width();

            var isLast = $li.attr("aria-controls") == $lastLI.attr("aria-controls");

            //如果当前tab没有隐藏 则不scroll
            if((ulWapperOffsetLeft <= liOffsetLeft) && (liLeftPos <= ulWrapperLeftPos) && !isLast) {
                return;
            }

            var leftPos = 0;
            //right
            if(ulWrapperLeftPos < liLeftPos || isLast) {
                leftPos = $ulWrapper.scrollLeft() + (liLeftPos - ulWrapperLeftPos) + (isLast ? 10 :55);
            } else {
                //left
                leftPos = "-=" + (ulWapperOffsetLeft - liOffsetLeft + 55);
            }

            $ulWrapper.animate({scrollLeft: leftPos}, 600, function () {
                hideOrShowBtn();
            });
        } else {
            hideOrShowBtn();
        }


    },
    initTabScroll: function () {
        var move = function (step) {
            return function () {
                var $ulWrapper = $(".tabs-bar .ul-wrapper");
                var $lastLI = $ulWrapper.find("ul li:last");

                var leftPos = $ulWrapper.scrollLeft() + step;

                var ulWrapperLeftPos = $ulWrapper.offset().left + $ulWrapper.width();
                var lastLILeftPos = $lastLI.offset().left + $lastLI.width();
                var maxLeftPos = lastLILeftPos - ulWrapperLeftPos;

                //right move
                if (step > 0) {
                    if (maxLeftPos <= step + step / 2) {
                        leftPos = $ulWrapper.scrollLeft() + maxLeftPos;
                    }
                    if (maxLeftPos <= 0) {
                        return;
                    }
                }

                //left move
                if (step < 0) {
                    if (leftPos < -step) {
                        leftPos = 0;
                    }
                }

                if (leftPos < 0) {
                    leftPos = 0;
                }
                $ulWrapper.animate({scrollLeft: leftPos}, 600, function () {
                    $.tabs.initTabScrollHideOrShowMoveBtn();
                });
            };
        };

        $(".tabs-bar .icon-chevron-left").click(function () {
            setTimeout(function() {move(-200)()}, 0);
        });
        $(".tabs-bar .icon-chevron-right").click(function () {
            setTimeout(function() {move(200)()}, 0);
        });

    },
    /**
     * 初始化上下文菜单（右键菜单）
     */
    initTabContextMenu : function() {
        //初始化右键菜单
        var tabsMenu = $("#tabs-menu");
        //调用这个方法后将禁止系统的右键菜单
        $(document).bind('contextmenu', function (e) {
            var target = $(e.target);
            var clickTab = target.closest(".tabs-bar").length && target.is(".ui-tabs-anchor");

            if (clickTab && target.attr("href") == '#tabs-0') {
                return true;
            }
            if (clickTab) {
                showMenu(target.attr("id"), e.pageX - 5, e.pageY - 5);
                tabsMenu.mouseleave(function () {
                    hideMenu();
                });
                return false;
            }
            return true;
        });

        function hideMenu() {
            tabsMenu.hide();
            tabsMenu.data("tabId", "");
        }

        function showMenu(tabId, x, y) {
            tabsMenu.data("tabId", tabId);
            tabsMenu.css("left", x).css("top", y);
            tabsMenu.show();
        }

        function closeTab(tabId) {
            $("#" + tabId).parent().find(".icon-remove").click();
        }
        tabsMenu.find(".close-current").click(function (e) {
            var currentTabId = tabsMenu.data("tabId");
            closeTab(currentTabId);
            hideMenu();
        });

        tabsMenu.find(".close-others").click(function (e) {
            var currentTabId = tabsMenu.data("tabId");
            var tabs = $.tabs.tabs.find(".ul-wrapper > ul > li > a");
            tabs.each(function() {
                var tabId = this.id;
                if(tabId != currentTabId) {
                    closeTab(tabId);
                }
            });
            hideMenu();
        });
        tabsMenu.find(".close-all").click(function (e) {
            var currentTabId = tabsMenu.data("tabId");
            var tabs = $.tabs.tabs.find(".ul-wrapper > ul > li > a");
            tabs.each(function() {
                var tabId = this.id;
                closeTab(tabId);
            });
            hideMenu();
        });

        tabsMenu.find(".close-left-all").click(function (e) {
            var currentTabId = tabsMenu.data("tabId");
            var tabs = $.tabs.tabs.find(".ul-wrapper > ul > li > a");
            var currentTabIndex = tabs.index($("#" + currentTabId));
            tabs.each(function(index) {
                if(index < currentTabIndex) {
                    var tabId = this.id;
                    closeTab(tabId);
                }
            });
            hideMenu();
        });
        tabsMenu.find(".close-right-all").click(function (e) {
            var currentTabId = tabsMenu.data("tabId");
            var tabs = $.tabs.tabs.find(".ul-wrapper > ul > li > a");
            var currentTabIndex = tabs.index($("#" + currentTabId));
            tabs.each(function(index) {
                if(index > currentTabIndex) {
                    var tabId = this.id;
                    closeTab(tabId);
                }
            });
            hideMenu();
        });
    },

    /**
     * 获取下一个自定义panel的索引
     */
    nextCustomTabIndex : function() {
        var tabs = $.tabs.tabs;
        var maxIndex = $.tabs.customTabStartIndex;
        tabs.find(".ui-tabs-panel").each(function() {
            var index = parseInt($(this).attr("id").replace("tabs-"));
            if(maxIndex < index) {
                maxIndex = index;
            }
        });

        return maxIndex + 1;

    }
};

$.parentchild = {
    /**
     * 初始化父子操作中的子表单
     * options
     *     {
                form : 表单【默认$("childForm")】,
                tableId : "表格Id"【默认"childTable"】,
                excludeInputSelector : "[name='_show']"【排除的selector 默认无】,
                trId : "修改的哪行数据的tr id， 如果没有表示是新增的",
                validationEngine : null 验证引擎,
                modalSettings:{//模态窗口设置
                    width:800,
                    height:500,
                    buttons:{}
                },
                updateUrl : "${ctx}/showcase/parentchild/parent/child/{id}/update" 修改时url模板 {id} 表示修改时的id,
                deleteUrl : "${ctx}/showcase/parentchild/parent/child/{id}/delete  删除时url模板 {id} 表示删除时的id,
            }
     * @param options
     * @return {boolean}
     */
    initChildForm : function(options) {
        var defaults = {
            form : $("#childForm"),
            tableId : "childTable",
            excludeInputSelector : "",
            trId : "",
            validationEngine : null
        };

        if(!options) {
            options = {};
        }
        options = $.extend({}, defaults, options);

        //如果有trId则用trId中的数据更新当前表单
        if(options.trId) {
            var $tr = $("#" + options.trId);
            if($tr.length && $tr.find(":input").length) {
                //因为是按顺序保存的 所以按照顺序获取  第一个是checkbox 跳过
                var index = 1;
                $(":input", options.form).not(options.excludeInputSelector).each(function() {
                    var $input = $(this);
                    var $trInput = $tr.find(":input").eq(index++);
                    if(!$trInput.length) {
                        return;
                    }
                    var $trInputClone = $trInput.clone(true).show();
                    //saveModalFormToTable 为了防止重名问题，添加了tr id前缀，修改时去掉
                    $trInputClone.prop("name", $trInputClone.prop("name").replace(options.trId, ""));
                    $trInputClone.prop("id", $trInputClone.prop("id").replace(options.trId, ""));

                    //克隆后 select的选择丢失了 TODO 提交给jquery bug?
                    if($trInput.is("select")) {
                        $trInput.find("option").each(function(i) {
                            $trInputClone.find("option").eq(i).prop("selected", $(this).prop("selected"));
                        });
                    }
                    if($trInput.is(":radio,:checkbox")) {
                        $trInputClone.prop("checked", $trInput.prop("checked"));
                    }

                    $trInputClone.replaceAll($input);
                });
            }
        }

        //格式化子表单的 input label
        $(":input,label", options.form).each(function() {
            var prefix = "child_";
            if($(this).is(":input")) {
                var id = $(this).prop("id");
                if(id && id.indexOf(prefix) != 0) {
                    $(this).prop("id", prefix + id);
                }
            } else {
                var _for = $(this).prop("for");
                if(_for && _for.indexOf(prefix) != 0) {
                    $(this).prop("for", prefix + _for);
                }
            }
        });

        options.form.submit(function() {
            if(options.validationEngine && !options.validationEngine.validationEngine("validate")) {
                return false;
            }
            return $.parentchild.saveModalFormToTable(options);
        });
    }
    ,
    //保存打开的模态窗口到打开者的表格中
    /**
     * options
     *     {
                form : 表单【默认$("childForm")】,
                tableId : "表格Id"【默认"childTable"】,
                excludeInputSelector : "[name='_show']"【排除的selector 默认无】,
                updateCallback : 【修改时的回调  默认 updateChild】,
                deleteCallback : 【删除时的回调默认 deleteChild】,
                trId : "修改的哪行数据的tr id， 如果没有表示是新增的"
            }
     * @param options
     * @return {boolean}
     */
    saveModalFormToTable :function(options) {
        var $childTable =  $("#" + options.tableId);
        var $childTbody = $childTable.children("tbody");

        if(!options.trId || options.alwaysNew) {
            var counter = $childTbody.data("counter");
            if(!counter) {
                counter = 0;
            }
            options.trId = "new_" + counter++;
            $childTbody.data("counter", counter);
        }
        var $lastTr = $("#" + options.trId, $childTbody);

        var $tr = $("<tr></tr>");
        $tr.prop("id", options.trId);
        if(!$lastTr.length || options.alwaysNew) {
            $childTbody.append($tr);
        } else {
            $lastTr.replaceWith($tr);
        }

        var $td = $("<td></td>");

        //checkbox
        $tr.append($td.clone(true).addClass("check").append("<input type='checkbox'>"));

        var $inputs = $(":input", options.form).not(":button,:submit,:reset", options.form);
        if(options.excludeInputSelector) {
            $inputs = $inputs.not(options.excludeInputSelector);
        }
        $inputs = $inputs.filter(function() {
            return $inputs.filter("[name='" + $(this).prop("name") + "']").index($(this)) == 0;
        });
        $inputs.each(function() {
            var $input = $("[name='" + $(this).prop("name") + "']", options.form);

            var val = $input.val();
            //使用文本在父页显示，而不是值
            //如果是单选按钮/复选框 （在写的过程中，必须在输入框后跟着一个label）
            if($input.is(":radio,:checkbox")) {
                val = "";
                $input.filter(":checked").each(function() {
                    if(val != "") {
                        val = val + ",";
                    }
                    val = val + $("label[for='" + $(this).prop("id") + "']").text();
                });
            }
            //下拉列表
            if($input.is("select")) {
                val = "";
                $input.find("option:selected").each(function() {
                    if(val != "") {
                        val = val + ",";
                    }
                    val = val + $(this).text();
                });
            }

            //因为有多个孩子 防止重名造成数据丢失
            $input.each(function() {
                if($(this).is("[id]")) {
                    $(this).prop("id", options.trId + $(this).prop("id"));
                }
                $(this).prop("name", options.trId + $(this).prop("name"));
            });
            $tr.append($td.clone(true).append(val).append($input.hide()));

        });

        $.table.initCheckbox($childTable);

        $.app.cancelModelDialog();
        return false;
    }
    ,
    /**
     * 更新子
     * @param $a 当前按钮
     * @param updateUrl  更新地址
     */
    updateChild : function($tr, updateUrl, modalSettings) {
        if(updateUrl.indexOf("?") > 0) {
            updateUrl = updateUrl + "&";
        } else {
            updateUrl = updateUrl + "?";
        }
        updateUrl = updateUrl + "trId={trId}";

        //表示已经在数据库中了
        if($tr.is("[id^='old']")) {
            updateUrl = updateUrl.replace("{id}", $tr.prop("id").replace("old_", ""));
        } else {
            //表示刚刚新增的还没有保存到数据库
            updateUrl = updateUrl.replace("{id}", 0);
        }
        updateUrl = updateUrl.replace("{trId}", $tr.prop("id"));
        $.app.modalDialog("修改", updateUrl, modalSettings);
    }
    ,
    /**
     * 以当前行复制一份
     * @param $a 当前按钮
     * @param updateUrl  更新地址
     */
    copyChild : function($tr, updateUrl, modalSettings) {
        if(updateUrl.indexOf("?") > 0) {
            updateUrl = updateUrl + "&";
        } else {
            updateUrl = updateUrl + "?";
        }
        updateUrl = updateUrl + "trId={trId}";
        updateUrl = updateUrl + "&copy=true";

        //表示已经在数据库中了
        if($tr.is("[id^='old']")) {
            updateUrl = updateUrl.replace("{id}", $tr.prop("id").replace("old_", ""));
        } else {
            //表示刚刚新增的还没有保存到数据库
            updateUrl = updateUrl.replace("{id}", 0);
        }
        updateUrl = updateUrl.replace("{trId}", $tr.prop("id"));
        $.app.modalDialog("复制", updateUrl, modalSettings);
    }
    ,
    /**
     * 删除子
     * @param $a 当前按钮
     * @param deleteUrl 删除地址
     */
    deleteChild : function($a, deleteUrl) {
        $.app.confirm({
            message : "确认删除吗？",
            ok : function() {
                var $tr = $a.closest("tr");
                //如果数据库中存在
                if($tr.prop("id").indexOf("old_") == 0) {
                    deleteUrl = deleteUrl.replace("{id}", $tr.prop("id").replace("old_", ""));
                    $.post(deleteUrl, function() {
                        $tr.remove();
                    });
                } else {
                    $tr.remove();
                }

            }
        });
    }
    ,
    /**
     * 初始化父子表单中的父表单
     * {
     *     form: $form 父表单,
     *     tableId : tableId 子表格id,
     *     prefixParamName : "" 子表单 参数前缀,
     *     modalSettings:{} 打开的模态窗口设置
     *     createUrl : "${ctx}/showcase/parentchild/parent/child/create",
     *     updateUrl : "${ctx}/showcase/parentchild/parent/child/{id}/update" 修改时url模板 {id} 表示修改时的id,
     *     deleteUrl : "${ctx}/showcase/parentchild/parent/child/{id}/delete  删除时url模板 {id} 表示删除时的id,
     * }
     */
    initParentForm : function(options) {


        var $childTable = $("#" + options.tableId);
        $.table.initCheckbox($childTable);
        //绑定在切换页面时的事件 防止误前进/后退 造成数据丢失
        $(window).on('beforeunload',function(){
            if($childTable.find(":input").length) {
                return "确定离开当前编辑页面吗？";
            }
        });
        $(".btn-create-child").click(function() {
            $.app.modalDialog("新增", options.createUrl, options.modalSettings);
        });
        $(".btn-update-child").click(function() {
            var $trs = $childTable.find("tbody tr").has(".check :checkbox:checked:first");
            if(!$trs.length) {
                $.app.alert({message : "请先选择要修改的数据！"});
                return;
            }
            $.parentchild.updateChild($trs, options.updateUrl, options.modalSettings);
        });

        $(".btn-copy-child").click(function() {
            var $trs = $childTable.find("tbody tr").has(".check :checkbox:checked:first");
            if(!$trs.length) {
                $.app.alert({message : "请先选择要复制的数据！"});
                return;
            }
            $.parentchild.copyChild($trs, options.updateUrl, options.modalSettings);
        });


        $(".btn-delete-child").click(function() {
            var $trs = $childTable.find("tbody tr").has(".check :checkbox:checked");
            if(!$trs.length) {
                $.app.alert({message : "请先选择要删除的数据！"});
                return;
            }
            $.app.confirm({
                message: "确定删除选择的数据吗？",
                ok : function() {
                    var ids = new Array();
                    $trs.each(function() {
                        var id = $(this).prop("id");
                        if(id.indexOf("old_") == 0) {
                            id = id.replace("old_", "");
                            ids.push({name : "ids", value : id});
                        }
                    });

                    $.post(options.batchDeleteUrl, ids, function() {
                        $trs.remove();
                        $.table.changeBtnState($childTable);
                    });

                }
            });
        });

        options.form.submit(function() {
            //如果是提交 不需要执行beforeunload
            $(window).unbind("beforeunload");
            $childTable.find("tbody tr").each(function(index) {
                var tr = $(this);
                tr.find(".check > :checkbox").attr("checked", false);
                tr.find(":input").each(function() {
                    if($(this).prop("name").indexOf(options.prefixParamName) != 0) {
                        $(this).prop("name", options.prefixParamName + "[" + index + "]." + $(this).prop("name").replace(tr.prop("id"), ""));
                    }
                });
            });
        });
    }

}




$.table = {

    /**
     * 初始化表格：全选/反选 排序
     * @param table
     */
    initTable: function (table) {
        if(!table || !table.length || table.attr("initialized") == "true") {
            return;
        }

        table.attr("initialized", "true");

        $.table.initSort(table);
        $.table.initSearchForm(table);
        if(table.is(".move-table")) {
            $.movable.initMoveableTable(table);
        }

        //初始化table里的a标签
        $.table.initTableBtn(table);
        //初始化删除和修改按钮
        $.table.initDeleteSelected(table);
        $.table.initUpdateSelected(table);
        $.table.initCreate(table);

        //初始化checkbox
        $.table.initCheckbox(table);
        //初始化 按钮的状态
        $.table.changeBtnState(table);


    },
    initCheckbox: function(table) {
        var activeClass = "active";
        //初始化表格中checkbox 点击单元格选中
        table.find("td.check").each(function () {
            var checkbox = $(this).find(":checkbox,:radio");
            checkbox.off("click").on("click", function (event) {
                var checked = checkbox.is(":checked");
                if(!checked) {
                    checkbox.closest("tr").removeClass(activeClass);
                } else {
                    checkbox.closest("tr").addClass(activeClass);
                }
                $.table.changeBtnState(table);
                event.stopPropagation();
            });
            $(this).closest("tr").off("click").on("click", function (event) {
                var checked = checkbox.is(":checked");
                if(checked) {
                    checkbox.closest("tr").removeClass(activeClass);
                } else {
                    checkbox.closest("tr").addClass(activeClass);
                }
                checkbox.prop("checked", !checked);
                $.table.changeBtnState(table);
            });
        });
        //初始化全选反选
        table.find(".check-all").off("click").on("click", function () {
            var checkAll = $(this);
            if(checkAll.text() == '全选') {
                checkAll.text("取消");
                table.find("td.check :checkbox").prop("checked", true).closest("tr").addClass(activeClass);
            } else {
                checkAll.text("全选");
                table.find("td.check :checkbox").prop("checked", false).closest("tr").removeClass(activeClass);
            }
            $.table.changeBtnState(table);
        });
        table.find(".reverse-all").off("click").on("click", function () {
            table.find("td.check :checkbox").each(function () {
                var checkbox = $(this);
                var checked = checkbox.is(":checked");
                if(checked) {
                    checkbox.closest("tr").removeClass(activeClass);
                } else {
                    checkbox.closest("tr").addClass(activeClass);
                }
                checkbox.prop("checked", !checked);
                $.table.changeBtnState(table);
            });
        });
    },
    changeBtnState : function(table) {
        var hasChecked = table.find("td.check :checkbox:checked").length;
        var btns = table.closest(".panel").find(".tool .btn").not(".no-disabled");
        if(hasChecked) {
            btns.removeClass("disabled");
            btns.each(function() {
                var btn = $(this);
                var href = btn.data("btn-state-href");
                if(href) {
                    btn.attr("href", href);
                }
            });
        } else {
            btns.addClass("disabled");
            btns.each(function() {
                var btn = $(this);
                var href = btn.attr("href");
                if(href) {
                    btn.data("btn-state-href", href);
                    btn.removeAttr("href");
                }
            });
        }
    },
    /**
     * 初始化对应的查询表单
     * @param table
     */
    initSearchForm : function(table) {
        var id = $(table).attr("id");
        var searchForm = table.closest("[data-table='" + id + "']").find(".search-form");

        if(!searchForm.length) {
            return;
        }

        searchForm.find(".btn").addClass("no-disabled");

        searchForm.find(".btn-clear-search").click(function() {

            if (table.data("async") == true) {
                var resetBtn = searchForm.find("input[type='reset']");
                if(!resetBtn.length) {
                    searchForm.append("<input type='reset' style='display:none'>");
                    resetBtn = searchForm.find("input[type='reset']");
                }
                resetBtn.click();
            }
            turnSearch(table, searchForm, true);
        });

        var turnSearch = function(table, searchForm, isSearchAll) {
            var url = $.table.tableURL(table);
            url = $.table.removeSearchParam(url, searchForm);
            url = $.table.removePageParam(url, searchForm);
            if(!isSearchAll) {
                if(url.indexOf("?") == -1) {
                    url = url + "?";
                } else {
                    url = url + "&";
                }
                url = url + searchForm.serialize();
            }
            $.table.reloadTable(table, url, null);
        }

        searchForm.off("submit").on("submit", function() {
            turnSearch(table, searchForm, false);
            return false;
        });

        if(searchForm.is("[data-change-search=true]")) {
            searchForm.find(":input:not(:button,:submit,:reset)").off("change").on("change", function(e) {
                // avoid double search issue, when you click search button after change any input
                searchForm.off("submit").on("submit", function() {
                    return false;
                });
                turnSearch(table, searchForm, false);
            });
        }

        searchForm.find(".btn-search-all").off("click").on("click", function() {
            turnSearch(table, searchForm, true);
            return false;
        });


    },
    /**
     * 初始化sort
     * @param table
     */
    initSort: function (table) {
        if (!table.length) {
            return;
        }

        //初始化排序
        var prefix = $.table.getPrefix(table);

        var sortURL = $.table.tableURL(table);

        var sortBtnTemplate = '<div class="sort"><a class="{sort-icon}" href="#" title="排序"></a></div>';
        table.find("[sort]").each(function () {
            var th = $(this);
            var sortPropertyName = prefix + "sort." + th.attr("sort");
            var sortBtnStr = null;
            var matchResult = sortURL.match(new RegExp(sortPropertyName + "=(asc|desc)", "gi"));
            var order = null;
            if (matchResult) {
                order = RegExp.$1;
                if (order == 'asc') {
                    sortBtnStr = sortBtnTemplate.replace("{sort-icon}", "sort-hover icon-arrow-up");
                } else if (order == 'desc') {
                    sortBtnStr = sortBtnTemplate.replace("{sort-icon}", "sort-hover icon-arrow-down");
                }
            }
            if (sortBtnStr == null) {
                sortBtnStr = sortBtnTemplate.replace("{sort-icon}", "icon-arrow-down");
            }
            th.wrapInner("<div class='sort-title'></div>").append($(sortBtnStr));

            //当前排序
            th.prop("order", order);//设置当前的排序 方便可移动表格

            th.addClass("sort-th").click(function () {
                sortURL = $.table.tableURL(table);
                //清空上次排序
                sortURL = $.table.removeSortParam(sortURL);

                if (!order) { //asc
                    order = "asc";
                } else if (order == "asc") { //desc
                    order = "desc";
                } else if (order == "desc") { //none
                    order = "asc";
                }

                if (order) {
                    sortURL = sortURL + (sortURL.indexOf("?") == -1 ? "?" : "&");
                    sortURL = sortURL + sortPropertyName + "=" + order;
                }

                $.table.reloadTable(table, sortURL, null);
            });

        });
    },
    /**
     * 分页
     * @param pageSize
     * @param pn
     * @param child table的子
     */
    turnPage: function (pageSize, pn, child) {
        var table = $(child).closest(".table-pagination").prev("table");
        if(!table.length) {
            table = $(child).closest("table");
        }

        var pageURL = $.table.tableURL(table);

        //清空上次分页
        pageURL = $.table.removePageParam(pageURL);


        pageURL = pageURL + (pageURL.indexOf("?") == -1 ? "?" : "&");

        var prefix = $.table.getPrefix(table);
        pageURL = pageURL + prefix + "page.pn=" + pn;

        if (pageSize) {
            pageURL = pageURL + "&" + prefix + "page.size=" + pageSize;
        }

        $.table.reloadTable(table, pageURL, null);
    },
    /**
     * 执行跳转
     * @param table
     * @param url
     * @param backURL
     */
    reloadTable: function (table, url, backURL) {

        if(!url) {
            url = $.table.tableURL(table);
        }

        if (!backURL) {
            backURL = url;
        }
        //modalDialog时 把当前url保存下来方便翻页和排序
        table.closest(".ui-dialog").data("url", backURL);

        if (table.data("async") == true) {
            $.app.waiting();

            var tableId = table.attr("id");
            var containerId = table.data("async-container");
            var headers = {};

            if(!containerId) {//只替换表格时使用
                headers.table = true;
            } else {
                headers.container = true;
            }

            $.ajax({
                url: url,
                async:true,
                headers: headers
            }).done(function (data) {
                    if (containerId) {//装载到容器
                        $("#" + containerId).replaceWith(data);
                    } else {
                        var pagination = table.next(".table-pagination");
                        if(pagination.length) {
                            pagination.remove();
                        }
                        table.replaceWith(data);
                    }

                    table = $("#" + tableId);
                    table.data("url", backURL);
                    $.table.initTable(table);

                    var callback = table.data("async-callback");
                    if(callback && window[callback]) {
                        window[callback](table);
                    }

                    $.app.waitingOver();
                });
        } else {
            window.location.href = url;
        }
    }
    ,
    /**
     * 获取表格对于的url
     * @param table
     * @return {*}
     */
    tableURL : function(table) {
        var $dialog = table.closest(".ui-dialog");

        var url = table.data("url");
        if(!url && $dialog.length) {
            //modalDialog
            url = $dialog.data("url");
        }
        if (!url) {
            url = window.location.href;
        }
        //如果URL中包含锚点（#） 删除
        if(url.indexOf("#") > 0) {
            url = url.substring(0, url.indexOf("#"));
        }

        return url;
    },
    /**
     *
     * @param table
     */
    encodeTableURL : function(table) {
        return encodeURIComponent($.table.tableURL(table));
    }
    ,
    /**
     * 获取传递参数时的前缀
     * @param table
     */
    getPrefix : function(table) {
        var prefix = table.data("prefix");
        if (!prefix) {
            prefix = "";
        } else {
            prefix = prefix + "_";
        }
        return prefix;
    }
    ,
    removePageParam : function(pageURL) {
        pageURL = pageURL.replace(/\&\w*page.pn=\d+/gi, '');
        pageURL = pageURL.replace(/\?\w*page.pn=\d+\&/gi, '?');
        pageURL = pageURL.replace(/\?\w*page.pn=\d+/gi, '');
        pageURL = pageURL.replace(/\&\w*page.size=\d+/gi, '');
        pageURL = pageURL.replace(/\?\w*page.size=\d+\&/gi, '?');
        pageURL = pageURL.replace(/\?\w*page.size=\d+/gi, '');
        return pageURL;
    }
    ,
    removeSortParam : function(sortURL) {
        sortURL = sortURL.replace(/\&\w*sort.*=((asc)|(desc))/gi, '');
        sortURL = sortURL.replace(/\?\w*sort.*=((asc)|(desc))\&/gi, '?');
        sortURL = sortURL.replace(/\?\w*sort.*=((asc)|(desc))/gi, '');
        return sortURL;
    },
    removeSearchParam : function(url, form) {
        $.each(form.serializeArray(), function() {
            var name = this.name;
            url = url.replace(new RegExp(name + "=.*?\&","g"), '');
            url = url.replace(new RegExp("[\&\?]" + name + "=.*$","g"), '');
        });
        return url;
    }
    ,
    //格式化url前缀，默认清除url ? 后边的
    formatUrlPrefix : function(urlPrefix, $table) {

        if(!urlPrefix) {
            urlPrefix = $table.data("prefix-url");
        }

        if(!urlPrefix && $table && $table.length) {
            urlPrefix = decodeURIComponent($.table.tableURL($table));
        }

        if(!urlPrefix) {
            urlPrefix = currentURL;
        }

        if(urlPrefix.indexOf("?") >= 0) {
            return urlPrefix.substr(0, urlPrefix.indexOf("?"));
        }
        return urlPrefix;
    },

    initDeleteSelected : function($table, urlPrefix) {
        if(!$table || !$table.length) {
            return;
        }

        var $btn = $table.closest("[data-table='" + $table.attr("id") + "']").find(".btn-delete:not(.btn-custom)");
        urlPrefix = $.table.formatUrlPrefix(urlPrefix, $table);
        $btn.off("click").on("click", function() {
            var checkbox = $.table.getAllSelectedCheckbox($table);
            if(!checkbox.length)  return;

            $.app.confirm({
                message: "确定删除选择的数据吗？",
                ok : function() {
                    window.location.href =
                        urlPrefix + "/batch/delete?" + checkbox.serialize() + "&BackURL=" + $.table.encodeTableURL($table);
                }
            });
        });
    }
    ,
    initUpdateSelected : function($table, urlPrefix) {
        if(!$table || !$table.length) {
            return;
        }
        var $btn = $table.closest("[data-table='" + $table.attr("id") + "']").find(".btn-update:not(.btn-custom)");
        urlPrefix = $.table.formatUrlPrefix(urlPrefix, $table);
        $btn.off("click").on("click", function() {
            var checkbox = $.table.getFirstSelectedCheckbox($table);
            if(!checkbox.length)  return;
            var id = checkbox.val();
            window.location.href = urlPrefix + "/" + id + "/update?BackURL=" + $.table.encodeTableURL($table);
        });
    },
    initCreate : function($table, urlPrefix) {
        if(!$table || !$table.length) {
            return;
        }
        var $btn = $table.closest("[data-table='" + $table.attr("id") + "']").find(".btn-create");

        $btn.addClass("no-disabled");

        $btn.off("click").on("click", function() {
            var url =  $.table.formatUrlPrefix(urlPrefix, $table) + "/create";
            if($btn.attr("href")) {
                url = $btn.attr("href");
            }
            window.location.href = url + (url.indexOf("?") == -1 ? "?" : "&") + "BackURL=" + $.table.encodeTableURL($table);
            return false;
        });
    },
    initTableBtn : function($table, urlPrefix) {
        if(!$table || !$table.length) {
            return;
        }
        $table.closest("[data-table=" + $table.attr("id") + "]").find(".btn").not(".btn-custom,.btn-create,.btn-update,.btn-delete").each(function() {
            var $btn = $(this);
            var url = $btn.attr("href");
            if(!url || url.indexOf("#") == 0 || url.indexOf("javascript:") == 0) {//没有url就不处理了
                return;
            }
            $btn.off("click").on("click", function() {
                window.location.href = url + (url.indexOf("?") == -1 ? "?" : "&") + "BackURL=" + $.table.encodeTableURL($table);
                return false;
            });
        });

        urlPrefix = $.table.formatUrlPrefix(urlPrefix, $table);
        //支持双击编辑
        if($table.hasClass("table-dblclick-edit")) {
            $table.children("tbody").children("tr").off("dblclick").on("dblclick", function() {
                var id = $(this).find(":checkbox[name=ids]").val();
                window.location.href = urlPrefix + "/" + id + "/update?BackURL=" + $.table.encodeTableURL($table);
            });
        }

    },
    getFirstSelectedCheckbox :function($table) {
        var checkbox = $("#table :checkbox:checked:first");
        if(!checkbox.length) {

            //表示不选中 不可以用，此时没必要弹窗
            if($(this).hasClass(".no-disable") == false) {
                return checkbox;
            }

            $.app.alert({
                message : "请先选择要操作的数据！"
            });
        }
        return checkbox;
    },
    getAllSelectedCheckbox :function($table) {
        var checkbox = $table.find(":checkbox:checked");
        if(!checkbox.length) {

            //表示不选中 不可以用，此时没必要弹窗
            if($(this).hasClass(".no-disable") == false) {
                return checkbox;
            }

            $.app.alert({
                message : "请先选择要操作的数据！"
            });
        }
        return checkbox;
    }
}

$.movable = {
    /**
     * urlPrefix：指定移动URL的前缀，
     * 如/sample，生成的URL格式为/sample/{fromId}/{toId}/{direction:方向(up|down)}
     * @param table
     * @param urlPrefix
     */
    initMoveableTable : function(table) {
        if(!table.length) {
            return;
        }
        var urlPrefix = table.data("move-url-prefix");
        if(!urlPrefix) {
            $.app.alert({message : "请添加移动地址URL，如&lt;table move-url-prefix='/sample'&gt;<br/>自动生成：/sample/{fromId}/{toId}/{direction:方向(up|down)}"});
        }
        var fixHelper = function (e, tr) {
            var $originals = tr.children();
            var $helper = tr.clone();
            $helper.children().each(function (index) {
                // Set helper cell sizes to match the original sizes
                $(this).width($originals.eq(index).width())
            });
            return $helper;
        };

        //事表格可拖拽排序
        table.find("tbody")
            .sortable({
                helper: fixHelper,
                opacity: 0.5,
                cursor: "move",
                placeholder: "sortable-placeholder",
                update: function (even, ui) {
                    even.stopPropagation();
                    prepareMove(ui.item.find(".moveable").closest("td"));
                }
            });

        //弹出移动框
        table.find("a.pop-movable[rel=popover]")
            .mouseenter(function (e) {
                var a = $(this);
                a.popover("show");
                var idInput = a.closest("tr").find(".id");
                idInput.focus();
                a.next(".popover").find(".popover-up-btn,.popover-down-btn").click(function() {
                    var fromId = $(this).closest("tr").prop("id");
                    var toId = idInput.val();

                    if(!/\d+/.test(toId)) {
                        $.app.alert({message : "请输入数字!"});
                        return;
                    }

                    var fromTD = $(this).closest("td");

                    if($(this).hasClass("popover-up-btn")) {
                        move(fromTD, fromId, toId, "up");
                    } else {
                        move(fromTD, fromId, toId, "down");
                    }
                });
                a.parent().mouseleave(function() {
                    a.popover("hide");
                });
            });

        table.find(".up-btn,.down-btn").click(function() {
            var fromTR = $(this).closest("tr");
            if($(this).hasClass("up-btn")) {
                fromTR.prev("tr").before(fromTR);
            } else {
                fromTR.next("tr").after(fromTR);
            }
            prepareMove($(this).closest("td"));
        });

        /**
         *
         * @param fromTD
         */
        function prepareMove(fromTD) {
            var fromTR = fromTD.closest("tr");
            var fromId = fromTR.prop("id");
            var nextTR = fromTR.next("tr");
            if(nextTR.length) {
                move(fromTD, fromId, nextTR.prop("id"), "down");
            } else {
                var preTR = fromTR.prev("tr");
                move(fromTD, fromId, preTR.prop("id"), "up");
            }

        }
        function move(fromTD, fromId, toId, direction) {
            if(!(fromId && toId)) {
                return;
            }
            var order = $.movable.tdOrder(fromTD);
            if (!order) {
                $.app.alert({message: "请首先排序要移动的字段！"});
                return;
            }
            //如果升序排列 需要反转direction
            if(order == "desc") {
                if(direction == "up") {
                    direction = "down";
                } else {
                    direction = "up";
                }
            }
            $.app.waiting("正在移动");
            var url = urlPrefix + "/" + fromId + "/" + toId + "/" + direction;
            $.getJSON(url, function(data) {
                $.app.waitingOver();
                if(data.success) {
                    $.table.reloadTable(fromTD.closest("table"));
                } else {
                    $.app.alert({message : data.message});
                }

            });
        }
    }
    ,
    initMovableReweight : function($btn, url) {
        $btn.click(function () {
            $.app.confirm({
                message: "确定优化权重吗？<br/><strong>注意：</strong>优化权重执行效率比较低，请在本系统使用人员较少时执行（如下班时间）",
                ok: function () {
                    $.app.waiting("优化权重执行中。。");
                    $.getJSON(url, function(data) {
                        $.app.waitingOver();
                        if(!data.success) {
                            $.app.alert({message : data.message});
                        } else {
                            location.reload();
                        }
                    });
                }
            });
        });
    },

    tdOrder : function(td) {
        var tdIndex = td.closest("tr").children("td").index(td);
        return td.closest("table").find("thead > tr > th").eq(tdIndex).prop("order");
    }
};

$.btn = {
    initChangeStatus : function(urlPrefix, tableId, config) {
        $(config.btns.join(",")).each(function(i) {
            $(this).off("click").on("click", function() {
                var $table = $("#" + tableId);
                var checkbox = $.table.getAllSelectedCheckbox($table);
                if(checkbox.size() == 0) {
                    return;
                }
                var title = config.titles[i];
                var message = config.messages[i];
                var status = config.status[i];
                var url = urlPrefix + "/" + status + "?" + checkbox.serialize();
                $.app.confirm({
                    title : title,
                    message : message,
                    ok : function() {
                        window.location.href = url;
                    }
                });
            });
        });
    },
    /**
     * 初始化改变显示隐藏的btn
     */
    initChangeShowStatus : function(urlPrefix, tableId) {
        $.btn.initChangeStatus(urlPrefix, tableId, {
            btns : [".status-show", ".status-hide"],
            titles : ['显示数据', '隐藏数据'],
            messages : ['确认显示数据吗？', '确认隐藏数据吗？'],
            status : ['true', 'false']
        });
    }
};

$.array = {
    remove : function(array, data) {
        if(array.length == 0) {
            return;
        }
        for(var i = array.length - 1; i >= 0; i--) {
            if(array[i] == data) {
                array.splice(i, 1);
            }
        }
    },
    contains : function(array, data) {
        if(array.length == 0) {
            return false;
        }
        for(var i = array.length - 1; i >= 0; i--) {
            if(array[i] == data) {
                return true;
            }
        }
        return false;
    },
    indexOf : function(array, data) {
        if(array.length == 0) {
            return -1;
        }
        for(var i = array.length - 1; i >= 0; i--) {
            if(array[i] == data) {
                return i;
            }
        }
        return -1;
    },
    clear : function(array) {
        if(array.length == 0) {
            return;
        }
        array.splice(0, array.length);
    },
    trim : function(array) {
        for(var i = array.length - 1; i >= 0; i--) {
            if(array[i] == "" || array[i] == null) {
                array.splice(i, 1);
            }
        }
        return array;
    }

};

/*
 * Project: Twitter Bootstrap Hover Dropdown
 * Author: Cameron Spear
 * Contributors: Mattia Larentis
 *
 * Dependencies?: Twitter Bootstrap's Dropdown plugin
 *
 * A simple plugin to enable twitter bootstrap dropdowns to active on hover and provide a nice user experience.
 *
 * No license, do what you want. I'd love credit or a shoutout, though.
 *
 * http://cameronspear.com/blog/twitter-bootstrap-dropdown-on-hover-plugin/
 */
;(function($, window, undefined) {
    // outside the scope of the jQuery plugin to
    // keep track of all dropdowns
    var $allDropdowns = $();

    // if instantlyCloseOthers is true, then it will instantly
    // shut other nav items when a new one is hovered over
    $.fn.dropdownHover = function(options) {

        // the element we really care about
        // is the dropdown-toggle's parent
        $allDropdowns = $allDropdowns.add(this.parent());

        return this.each(function() {
            var $this = $(this).parent(),
                defaults = {
                    delay: 100,
                    instantlyCloseOthers: true
                },
                data = {
                    delay: $(this).data('delay'),
                    instantlyCloseOthers: $(this).data('close-others')
                },
                settings = $.extend(true, {}, defaults, options, data),
                timeout;

            $this.hover(function() {
                if(settings.instantlyCloseOthers === true)
                    $allDropdowns.removeClass('open');

                window.clearTimeout(timeout);
                $(this).addClass('open');
            }, function() {
                timeout = window.setTimeout(function() {
                    $this.removeClass('open');
                }, settings.delay);
            });
        });
    };

    // apply dropdownHover to all elements with the data-hover="dropdown" attribute
    $(document).ready(function() {
        $('[data-hover="dropdown"]').dropdownHover();
    });
})(jQuery, this);


$(function () {
    //global disable ajax cache
    $.ajaxSetup({ cache: true });

    $(".table").each(function() {
        $.table.initTable($(this));
    });
    $.app.initDatetimePicker();

    $.layout = top.$.layout;
//    $.app = top.$.app;
    $.tabs = top.$.tabs;
    $.menus = top.$.menus;

    $("[data-toggle='tooltip']").each(function() {

        $(this).tooltip({delay:300});
    });

//    if(!$("body").is(".index")) {
//        $("html").niceScroll({styler:"fb",cursorcolor:"#777", zindex:1});
//    }

    $(document).ajaxError(function(event, request, settings) {

        $.app.waitingOver();

        if(request.status == 0) {// 中断的不处理
            return;
        }

        top.$.app.alert({
            title : "网络故障/系统故障",
            //<refresh>中间的按钮在ajax方式中删除不显示
            message : request.responseText.replace(/(<refresh>.*<\/refresh>)/g, "")
        });
    });
});

