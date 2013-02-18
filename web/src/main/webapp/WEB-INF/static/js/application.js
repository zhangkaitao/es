//自己扩展的jquery函数
$.app = {
    tabs: null,
    layout: null,
    /**初始化主页 layout，菜单，tab*/
    initIndex: function () {
        this.initLayout();
        this.initMenu();
        this.initTab();
    },
    /**初始化布局*/
    initLayout: function () {
        function resizePanel(panelName, panelElement, panelState, panelOptions, layoutName) {
            var tabul = $(".tabs-fix-top");
            if (panelName == 'west') {
                var left = panelElement.width() + 28;
                if (panelState.isClosed) {
                    left = 19;
                }
                tabul.css("left", left);
            }
            if (panelName == 'north') {
                var top = panelElement.height() + 28;
                if (panelState.isClosed) {
                    top = 7;
                }
                tabul.css("top", top);
            }
        }

        this.layout = $('body').layout({
            applyDefaultStyles: true,
            west__size: "20%",
            north__size: "11%",
            south__size: "8%",
            togglerTip_open: "关闭",
            togglerTip_closed: "打开",
            west__spacing_closed: 20,
            west__togglerContent_closed: "菜<BR>单",
            resizerTip: "调整大小",
            sliderTip: "滑动打开",
            onhide: resizePanel,
            onshow: resizePanel,
            onopen: resizePanel,
            onclose: resizePanel,
            onresize: resizePanel
        });
    },
    /**初始化tab */
    initTab: function () {
        var tabs = $("#tabs").tabs({
            beforeActivate: function (event, ui) {
                $.app.activeIframe(ui.newPanel.prop("id"));
            }
        });
        this.tabs = tabs;
        tabs.delegate("span.ui-icon-close", "click", function () {
            var panelId = $(this).closest("li").remove().attr("aria-controls");
            $("#" + panelId).remove();
            tabs.tabs("refresh");
        });
        tabs.delegate("span.ui-icon-refresh", "click", function () {
            var panelId = $(this).closest("li").attr("aria-controls");
            var panel = $("#" + panelId);
            $.app.loadingToCenterIframe(panel, panel.data("url"));
            tabs.tabs("option", "active", panel.data("index"));
            tabs.tabs("refresh");
        });

        tabs.bind("keyup", function (event) {
            if (event.altKey && event.keyCode === $.ui.keyCode.BACKSPACE) {
                var panelId = tabs.find(".ui-tabs-active").remove().attr("aria-controls");
                $("#" + panelId).remove();
                tabs.tabs("refresh");
            }
        });
    },
    /**初始化菜单*/
    initMenu: function () {
        var menus = $("#menu");
        menus.addClass("ui-accordion ui-widget ui-helper-reset");
        menus.find("h3")
            .addClass("ui-accordion-header ui-helper-reset ui-state-default  ui-accordion-icons ui-widget-menu")
            .append('<span class="ui-icon ui-icon-menu ui-icon-circle-plus"></span>')
            .next("ul").hide()
            .end()
            .click(function () {
                if ($(this).hasClass("ui-state-active")) {
                    $(this)
                        .removeClass("ui-state-active")
                        .find("span")
                        .removeClass("ui-icon-circle-minus")
                        .addClass("ui-icon-circle-plus")
                        .end()
                        .next("ul").hide();
                } else {
                    $(this)
                        .addClass("ui-state-active")
                        .find("span")
                        .removeClass("ui-icon-circle-plus")
                        .addClass("ui-icon-circle-minus")
                        .end()
                        .next("ul").show();
                }
            }).eq(0).click();

        menus.find("div > ul")
            .addClass(" ui-accordion-content ui-helper-reset ui-widget-content ui-widget-menu ui-corner-bottom ui-accordion-content-active")
            .children("li").each(function () {
                var submenu = $(this);
                if (submenu.children("ul").size() > 0) {
                    submenu.find("ul").hide();
                    submenu.find("ul > li").prepend('<span class="ui-icon ui-icon-document"></span>');
                    submenu
                        .prepend('<span class="ui-icon ui-icon-triangle-1-e"></span>')
                        .find("a,span")
                        .click(function () {
                            if ($(this).parent().find("span").hasClass("ui-icon-triangle-1-s")) {
                                $(this).parent().find("span")
                                    .removeClass("ui-icon-triangle-1-s")
                                    .addClass("ui-icon-triangle-1-e")
                                    .end()
                                    .find("ul").hide();
                            } else {
                                $(this).parent().find("span")
                                    .removeClass("ui-icon-triangle-1-e")
                                    .addClass("ui-icon-triangle-1-s")
                                    .end()
                                    .find("ul").show();
                            }
                        });
                } else {
                    submenu.prepend('<span class="ui-icon ui-icon-document"></span>');
                }
            });


        var index = 2;
        var tabTemplate = "<li><a href='#{href}'>{label}</a> <span class='ui-icon ui-icon-close' role='presentation'title='关闭'>关闭</span><span class='ui-icon ui-icon-refresh' role='presentation' title='刷新'>刷新</span></li>";
        $("#menu a").each(function () {
            var a = $(this);
            var href = a.attr("href");
            if (href == "#" || href == '') {
                return;
            }
            var currentTabPanel = null;
            a.data("index", index++)
                .data("url", a.attr("href"))
                .attr("href", "#")
                .click(function () {
                    var a = $(this);
                    $("#menu a").each(function () {
                        $(this).removeClass("select");
                    });
                    a.addClass("select");
                    var panelIndex = a.data("index");
                    var panelId = "tabs-" + panelIndex;
                    currentTabPanel = $("#" + panelId);
                    var tabs = $.app.tabs;
                    if (currentTabPanel.size() == 0) {
                        var title = a.text();
                        var li = $(tabTemplate.replace(/\{href\}/g, panelId).replace(/\{label\}/g, title));

                        tabs.find("ul.ui-tabs-nav").append(li);
                        tabs.append('<div id="' + panelId + '"></div>');
                        tabs.tabs("refresh");
                        currentTabPanel = $("#" + panelId);
                        currentTabPanel.data("index", tabs.find("ul li").index(li));

                    }
                    $.app.loadingToCenterIframe(currentTabPanel, a.data("url"));
                    tabs.tabs("option", "active", currentTabPanel.data("index"));

                    return false;
                });
        });
    },
    /**
     * 初始化表格：全选/反选 排序
     * @param table
     */
    initTable: function (table) {
        //初始化表格中checkbox 点击单元格选中
        table.find("td.check").each(function () {
            var checkbox = $(this).find(":checkbox");
            checkbox.click(function (event) {
                event.stopPropagation();
            });
            $(this).click(function (event) {
                checkbox.prop("checked", !checkbox.prop("checked"));
            });
        });
        //初始化全选反选
        table.find(".check-all").click(function () {
            table.find("td.check :checkbox").prop("checked", true);
        });
        table.find(".reverse-all").click(function () {
            table.find("td.check :checkbox").each(function () {
                $(this).prop("checked", !$(this).prop("checked"));
            });
        });
        this.initSort(table);
    },
    /**
     * 初始化sort
     * @param table
     */
    initSort: function (table) {
        //初始化排序
        var sortPrefix = table.attr("sort-prefix");
        if (!sortPrefix) {
            sortPrefix = "";
        } else {
            sortPrefix = sortPrefix + "_";
        }
        var sortURL = table.attr("sort-url");
        if (!sortURL) {
            sortURL = window.location.href;
        }
        var sortBtnTemplate =
            '<div class="dropdown sort">' +
                '  <a class="dropdown-toggle {sort-icon}" data-toggle="dropdown" href="#" title="排序"></a>' +
                '  <ul class="dropdown-menu" role="menu">' +
                '      <li><a class="up icon-arrow-up"> 升序</a></li>' +
                '       <li><a class="down icon-arrow-down"> 降序</a></li>' +
                '       <li><a class="cancel icon-remove"> 取消</a></li>' +
                '  </ul>' +
                '</div>';
        table.find("[sort]").each(function () {
            var sortComp = $(this);
            var sortPropertyName = sortPrefix + "sort." + sortComp.attr("sort");
            var sortBtnStr = null;
            var matchResult = sortURL.match(new RegExp(sortPropertyName + "=(asc|desc)", "gi"));
            if (matchResult) {
                var order = RegExp.$1;
                if (order == 'asc') {
                    sortBtnStr = sortBtnTemplate.replace("{sort-icon}", "sort-hover icon-arrow-up");
                    sortComp.prop("order", "asc");
                } else if (order == 'desc') {
                    sortBtnStr = sortBtnTemplate.replace("{sort-icon}", "sort-hover icon-arrow-down");
                    sortComp.prop("order", "desc");
                } else {
                    sortComp.removeProp("order");
                }
            }
            if (sortBtnStr == null) {
                sortBtnStr = sortBtnTemplate.replace("{sort-icon}", "icon-arrow-up");
            }
            var sortBtn = $(sortBtnStr);
            sortComp.wrapInner("<div class='sort-title'></div>").append(sortBtn);

            sortBtn.find(".up").click(function () {
                $.app.turnSort(sortURL, sortPropertyName, "asc");
            });
            sortBtn.find(".down").click(function () {
                $.app.turnSort(sortURL, sortPropertyName, "desc");
            });
            sortBtn.find(".cancel").click(function () {
                $.app.turnSort(sortURL);
            });
        });
    },

    tdOrder : function(td) {
        var tdIndex = td.closest("tr").children("td").index(td);
        return td.closest("table").find("thead tr th").eq(tdIndex).prop("order");
    }
    ,
    /**
     * 异步加载url内容到tab
     */
    loadingToCenterIframe: function (panel, url, loadingMessage) {
        panel.data("url", url);
        if (!loadingMessage) {
            loadingMessage = '装载中...';
        }
        $.blockUI({
            fadeIn: 700,
            fadeOut: 700,
            showOverlay: false,
            css: {
                border: 'none',
                padding: '15px',
                backgroundColor: '#ddd',
                '-webkit-border-radius': '10px',
                '-moz-border-radius': '10px',
                opacity: .6,
                color: '#000'
            },
            message: '<h4><img src="' + ctx + '/static/images/loading.gif" /> ' + loadingMessage + ' </h4>'
        });

        var panelId = panel.prop("id");
        var iframeId = "iframe-" + panelId;
        var iframe = $("#" + iframeId);
        if (iframe.size() == 0) {
            var iframe = $("iframe[tabs=true]:last").clone(true);
            iframe.prop("id", iframeId);
            $("iframe[tabs=true]:last").after(iframe);
        }
        iframe.prop("src", url).one("load", function () {
            $.app.activeIframe(panelId, iframe);
            $.app.waitingOver();
        });
    },
    activeIframe: function (panelId, iframe) {
        if (!iframe) {
            iframe = $("#iframe-" + panelId);
        }
        if (this.layout.panes.center.prop("id") == iframe.prop("id")) {
            return;
        }
        this.layout.panes.center.hide();
        this.layout.panes.center = iframe;
        this.layout.panes.center.show();
    },

    waiting : function(message) {
        $.blockUI({
            fadeIn: 700,
            fadeOut: 700,
            showOverlay: true,
            css: {
                border: 'none',
                padding: '15px',
                backgroundColor: '#ddd',
                '-webkit-border-radius': '10px',
                '-moz-border-radius': '10px',
                opacity: .6,
                color: '#000'
            },
            message: '<h4><img src="' + ctx + '/static/images/loading.gif" /> ' + message + ' </h4>'
        });
    }
    ,
    waitingOver: function () {
        $.unblockUI();
    },
    /**
     * 分页
     * @param url
     * @param pagePrefix
     * @param pageSize
     * @param pn
     */
    turnPage: function (url, pagePrefix, pageSize, pn) {
        var pageURL = url;
        if (pagePrefix != '') {
            pagePrefix = pagePrefix + "_";
        }
        if (pageURL.indexOf("?") != -1) {
            pageURL = pageURL + "&";
        } else {
            pageURL = pageURL + "?";
        }

        pageURL = pageURL + pagePrefix + "page.pn=" + pn;

        if (pageSize != '') {
            pageURL = pageURL + "&" + pagePrefix + "page.size=" + pageSize;
        }
        window.location.href = pageURL;
    },
    /**
     * 执行排序
     * @param sortURL
     * @param sortPropertyName
     * @param order
     */
    turnSort: function (sortURL, sortPropertyName, order) {
        //如果URL中包含锚点（#） 删除
        if(sortURL.indexOf("#") > 0) {
            sortURL = sortURL.substring(0, sortURL.indexOf("#"));
        }
        //清空上次排序
        sortURL = sortURL.replace(/\&.*sort.*=((asc)|(desc))/gi, '');
        sortURL = sortURL.replace(/\?.*sort.*=((asc)|(desc))\&/gi, '?');
        sortURL = sortURL.replace(/\?.*sort.*=((asc)|(desc))/gi, '');
        if (arguments.length == 1) {
            window.location.href = sortURL;
            return;
        }
        if (sortURL.indexOf("?") == -1) {
            sortURL = sortURL + "?";
        } else {
            sortURL = sortURL + "&";
        }
        sortURL = sortURL + sortPropertyName + "=" + order;
        window.location.href = sortURL;
    }
    ,

    alert : function(options) {
        var defaults = {
            title : "警告",
            message   : "非法的操作",
            okTitle : "关闭",
            ok : $.noop
        };
        if(options) {
            if(!options.title) options.title = defaults.title;
            if (!options.message)  options.message = defaults.message;
            if (!options.okTitle) options.okTitle = defaults.okTitle;
            if (!options.ok) options.ok = defaults.ok;
        } else {
            options = defaults;
        }
        options.alert = true;
        this.confirm(options);
    }
    ,
    /**
     * 格式
     * @param options
     */
    confirm : function(options) {
        if(window != top && top.$.app.confirm) {
            top.$.app.confirm(options);
            return;
        }
        var defaults = {
            title : "确认执行操作",
            message : "确认执行操作吗？",
            cancelTitle : '取消',
            okTitle : '确定',
            cancel : $.noop,
            ok : $.noop,
            alert : false
        };
        var template =
            '<div class="modal hide fade confirm">' +
              '<div class="modal-header">' +
                '<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>' +
                '<h3 class="title">{title}</h3>' +
              '</div>' +
              '<div class="modal-body">' +
                 '<p>{message}</p>' +
              '</div>' +
              '<div class="modal-footer">' +
                '<a href="#" class="btn btn-ok btn-danger" data-dismiss="modal">{okTitle}</a>' +
                '<a href="#" class="btn btn-cancel" data-dismiss="modal">{cancelTitle}</a>'+
              '</div>' +
            '</div>';
        if(options) {
            if(!options.title) options.title = defaults.title;
            if(!options.message)  options.message = defaults.message;
            if(!options.cancelTitle) options.cancelTitle = defaults.cancelTitle;
            if(!options.okTitle) options.okTitle = defaults.okTitle;
            if(!options.cancel) options.cancel = defaults.cancel;
            if(!options.ok) options.ok = defaults.ok;
            if(!options.alert) options.okCallback = defaults.alert;
        } else {
            options = defaults;
        }

        var modalDom =
            $(template
                .replace("{title}", options.title)
                .replace("{message}", options.message)
                .replace("{cancelTitle}", options.cancelTitle)
                .replace("{okTitle}", options.okTitle), top.window.document);


        if(options.alert) {
            modalDom.find(".modal-footer > .btn-cancel").remove();
        } else {
            modalDom.find(".modal-footer > .btn-cancel").click(options.cancel);
        }
        modalDom.find(".modal-footer > .btn-ok").click(options.ok);

        modalDom.modal();
    }
    ,
    /**
     * urlPrefix：指定移动URL的前缀，
      * 如/sample，生成的URL格式为/sample/move/{direction:方向(up|down)}/{fromId}/{toId}
     * @param table
     * @param urlPrefix
      */
    initTableMoveable : function(table) {
        if(table.size() == 0) {
            return;
        }
        var urlPrefix = table.attr("move-url-prefix");
        if(!urlPrefix) {
            $.app.alert({message : "请添加移动地址URL，如&lt;table move-url-prefix='/move'&gt;<br/>自动生成：/move/{direction:方向(up|down)}/{fromId}/{toId}"});
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
             if(nextTR.size() > 0) {
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
             var order = $.app.tdOrder(fromTD);
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
             var url = urlPrefix + "/" + direction + "/" + fromId + "/" + toId;
             $.getJSON(url, function(data) {
                 $.app.waitingOver();
                 if(data.success) {
                     location.reload();
                 } else {
                     $.app.alert({message : data.message});
                 }

             });
         }
    },
    isImage : function(filename) {
        return /gif|jpe?g|png|bmp$/i.test(filename);
    }
};


$(function () {
    $.app.initTable($(".table"));
    $.app.initTableMoveable($(".move-table"));
    //初始化 datetime picker
    $('.date').datetimepicker();
    //初始化 文件上传框
    $('input[type="file"].custom-file-input').customFileInput({
            button_position 	: 'right',
            feedback_text		: '还没有选择文件...',
            button_text			: '浏  览',
            button_change_text	: '更  改'
    });

    $(document).ajaxError(function(event,request, settings){
        $.app.alert({
            title : "网络故障/系统故障(请截屏反馈给管理员)",
            message : "出错状态码:" + request.status + "[" + request.statusText + "]" + "<br/>出错页面:" + settings.url
        });
    });
});

