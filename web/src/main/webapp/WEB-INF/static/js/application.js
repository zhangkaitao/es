//自己扩展的jquery函数
//压缩时请把编码改成ANSI
$.app = {
    tabs: null,
    layout: null,
    /**初始化主页 layout，菜单，tab*/
    initIndex: function () {
        this.initLayout();
        this.initMenu();
        this.initTab();
        this.initTabScroll();
    },
    /**初始化布局*/
    initLayout: function () {
        function resizePanel(panelName, panelElement, panelState, panelOptions, layoutName) {
            var tabul = $(".tabs-fix-top");
            if (panelName == 'west') {
                var left = panelElement.width() + 13;
                if (panelState.isClosed) {
                        left = 23;
                }
                tabul.css("left", left);
            }
            if (panelName == 'north') {
                var top = panelElement.height() + 13;
                if (panelState.isClosed) {
                    top = 9;
                }
                tabul.css("top", top);
            }
            tabul.find(".ul-wrapper").width(tabul.width());
            $.app.initTabScrollHideOrShowMoveBtn();
        }

        this.layout = $('body').layout({
                west__size:  250
            ,   north__size: 65
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
            ,	center__maskContents:		true // IMPORTANT - enable iframe masking
        });
    },
    /**初始化tab */
    initTab: function () {
        function activeMenu(tabPanelId) {
            var currentMenu = $("#menu-" + tabPanelId.replace("tabs-", ""));
            $(".menu .select").removeClass("select");
            if(currentMenu.size() > 0) {
                //把父菜单展示出来
                currentMenu.parents("ul").each(function(){
                    //不能使用“ul:hidden” 因为它是把只有隐藏的都查出来
                    // 比如<ul style="display:none"><li><ul><li><a class='a'></a></li></ul></li></ul>
                    //$(".a").parents("ul:hidden") 会查出两个 即hidden的元素对其子也是有效的
                    if($(this).css("display") == 'none') {
                        $(this).prev("a").click();
                    }
                });
                currentMenu.closest("li").addClass("select");
            }
        }
        var tabs = $("#tabs").tabs({
            beforeActivate: function (event, ui) {
                var tabs = $("#tabs");
                tabs.find(".ui-icon").hide();
                var newPanelId = ui.newPanel.prop("id");
                tabs.find("#" + ui.newPanel.attr("aria-labelledby")).siblings(".ui-icon").show();
                activeMenu(newPanelId);
                $.app.activeIframe(newPanelId);
            }
        });
        this.tabs = tabs;
        tabs.delegate("span.ui-icon-close", "click", function () {
            var panelId = $(this).closest("li").remove().attr("aria-controls");
            $("#" + panelId).remove();
            $("#iframe-" + panelId).remove();
            tabs.tabs("refresh");
        });
        tabs.delegate("span.ui-icon-refresh", "click", function () {
            var panelId = $(this).closest("li").attr("aria-controls");
            var panel = $("#" + panelId);
            $.app.loadingToCenterIframe(panel, panel.data("url"), null, true);
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
    initTabScrollHideOrShowMoveBtn : function(panelId) {
        var $ulWrapper = $("#tabs .ul-wrapper");
        var $lastLI = $ulWrapper.find("ul li:last");
        var $firstLI = $ulWrapper.find("ul li:first");

        var ulWapperOffsetLeft = $ulWrapper.offset().left;
        var ulWrapperLeftPos = ulWapperOffsetLeft + $ulWrapper.width();

        var hideOrShowBtn = function() {
            var lastLIOffsetLeft = $lastLI.offset().left;
            var lastLILeftPos = lastLIOffsetLeft + $lastLI.width();
            var firstLIOffsetLeft = $firstLI.offset().left;

            var $leftBtn = $("#tabs .icon-chevron-left");
            var $rightBtn = $("#tabs .icon-chevron-right");

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
            var $li = $("#tabs").find("li[aria-labelledby='" + $("#" + panelId).attr("aria-labelledby") + "']");
            var liOffsetLeft = $li.offset().left;
            var liLeftPos = liOffsetLeft + $li.width();

            //如果当前tab没有隐藏 则不scroll
            if((ulWapperOffsetLeft <= liOffsetLeft) && (liLeftPos <= ulWrapperLeftPos)) {
                return;
            }

            var leftPos = 0;
            //right
            if(ulWrapperLeftPos < liLeftPos) {
                var isLast = $li.attr("aria-labelledby") == $lastLI.attr("aria-labelledby");
                leftPos = $ulWrapper.scrollLeft() + (liLeftPos - ulWrapperLeftPos) + (isLast ? 10 : 50);
            } else {
                //left
                leftPos = "-=" + (ulWapperOffsetLeft - liOffsetLeft + 50);
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
                var $ulWrapper = $("#tabs .ul-wrapper");
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
                    $.app.initTabScrollHideOrShowMoveBtn();
                });
            };
        };

        $("#tabs .icon-chevron-left").click(function () {
            move(-200)();
        });
        $("#tabs .icon-chevron-right").click(function () {
            move(200)();
        });

    },
    /**初始化菜单*/
    initMenu: function () {
        var menus = $("#menu");

        var rootCloseIconClass = "ui-icon-circle-plus";
        var rootOpenIconClass = "ui-icon-circle-minus";
        menus.addClass("ui-accordion ui-widget ui-helper-reset");
        menus.find("h3")
            .addClass("ui-accordion-header ui-helper-reset ui-state-default ui-accordion-icons ui-corner-all")
            .append('<span class="ui-accordion-header-icon ui-icon ui-icon-menu '+ rootCloseIconClass + '"></span>')
            .next(".submenu").hide().end()
            .click(function () {
                var $menu = $(this);
                if ($menu.hasClass("ui-state-active")) {
                    $menu
                        .find("span").removeClass(rootOpenIconClass).addClass(rootCloseIconClass).end()
                        .next(".submenu").hide("blind", function() {
                            $menu.removeClass("ui-state-active").removeClass("ui-accordion-header-active")
                                 .removeClass("ui-corner-top").addClass("ui-corner-all");
                        });
                } else {
                    $menu
                        .addClass("ui-accordion-header-active").addClass("ui-state-active")
                        .addClass("ui-corner-top").removeClass("ui-corner-all")
                        .find("span").removeClass(rootCloseIconClass).addClass(rootOpenIconClass).end()
                        .next(".submenu").show("blind");
                }
            }).eq(0).click();

        var leafIconClass = "icon-angle-right";
        var branchOpenIconClass = "icon-double-angle-right";
        var branchCloseIconClass = "icon-double-angle-down";
        menus.find("div > ul")
            .addClass("ui-accordion-content ui-helper-reset ui-widget-content ui-widget-menu ui-corner-bottom ui-accordion-content-active")
            .children("li").each(function () {
                var submenu = $(this);
                var submenuUL = submenu.find("ul");
                if (submenuUL.size() > 0) {
                    submenuUL.hide();
                    submenu.find("li:not(:has(ul))").prepend('<span class="' + leafIconClass + '"></span>');
                    submenuUL.closest("li")
                        .prepend('<span class="' + branchOpenIconClass + '"></span>')
                        .find("a,span")
                        .click(function () {
                            if ($(this).parent().children("span").hasClass(branchCloseIconClass)) {
                                $(this).parent().children("span")
                                    .removeClass(branchCloseIconClass)
                                    .addClass(branchOpenIconClass)
                                    .end()
                                    .children("ul").hide("blind");
                            } else {
                                $(this).parent().children("span")
                                    .removeClass(branchOpenIconClass)
                                    .addClass(branchCloseIconClass)
                                    .end()
                                    .children("ul").show("blind");
                            }
                        });
                } else {
                    submenu.prepend('<span class="' + leafIconClass + '"></span>');
                }
            });


        var index = 2;
        var tabTemplate = "<li><a href='#{href}'>{label}</a> <span class='ui-icon ui-icon-close' role='presentation'title='关闭'>关闭</span><br/><span class='ui-icon ui-icon-refresh' role='presentation' title='刷新'>刷新</span></li>";
        $("#menu a").each(function () {
            var a = $(this);
            var href = a.attr("href");
            if (href == "#" || href == '') {
                return;
            }

            var active = function(a, forceRefresh) {
                $("#menu a").each(function () {
                    $(this).closest("li").removeClass("select");
                });
                a.closest("li").addClass("select");
                var panelIndex = a.data("index");
                var panelId = "tabs-" + panelIndex;
                var currentTabPanel = $("#" + panelId);
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
                $.app.loadingToCenterIframe(currentTabPanel, a.data("url"), null, forceRefresh);
                tabs.tabs("option", "active", currentTabPanel.data("index"));

                return false;
            }

            a.data("index", index++)
                .data("url", a.attr("href"))
                .attr("href", "#")
                .attr("id", "menu-" + a.data("index"))
                .click(function () {
                    active($(this), false);
                }).dblclick(function() {
                    active($(this), true);//双击强制刷新
                });
        });
    },
    /**
     * 异步加载url内容到tab
     */
    loadingToCenterIframe: function (panel, url, loadingMessage, forceRefresh) {
        panel.data("url", url);

        var panelId = panel.prop("id");
        var iframeId = "iframe-" + panelId;
        var iframe = $("#" + iframeId);
        if (iframe.size() == 0 || forceRefresh) {

            if(iframe.size() == 0) {
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
        if (this.layout.panes.center.prop("id") == iframe.prop("id")) {
            return;
        }
        this.layout.panes.center.hide();
        this.layout.panes.center = iframe;
        this.layout.panes.center.show();
        this.layout.resizeAll();
        $.app.initTabScrollHideOrShowMoveBtn(panelId);
    },

    waiting : function(message) {
        if(!message) {
            message = "装载中...";
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
                color: '#000'

            },
            message: '<h4><img src="' + ctx + '/static/images/loading.gif" /> ' + message + ' </h4>'
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
        var defaultSettings = {
            title : title,
            closeText : "关闭",
            closeOnEscape:false,
            height:500,
            width:800,
            modal:true,
            close: function() {
                $(this).closest(".ui-dialog").remove();
            },
            buttons:{
                '关闭': function () {
                    $(this).dialog("close");
                    if($.app._modalDialogQueue.length > 0) {
                        $.app._modalDialogQueue.pop();
                    }
                }
            }
        };
        if(!settings) {
            settings = {};
        }
        settings = $.extend({}, defaultSettings, settings);

        $.app.waiting();
        $.ajax({
            url: url,
            headers: { table:true }
        }).done(function (data) {
                $.app.waitingOver();
                var div = $("<div></div>").append(data);
                var dialog = div.dialog(settings)
                    .closest(".ui-dialog").attr("data-url", url).removeClass("ui-widget-content")
                    .find(".ui-dialog-content ").removeClass("ui-widget-content");
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
            message   : "非法的操作",
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
    isImage : function(filename) {
        return /gif|jpe?g|png|bmp$/i.test(filename);
    }
    ,
    initDatetimePicker : function() {
        //初始化 datetime picker
        $('.date').each(function() {
            var $date = $(this);

            if($date.attr("initialized") == "true") {
                return;
            }

            var pickDate = $(this).find("[data-format]").attr("data-format").toLowerCase().indexOf("yyyy-mm-dd") != -1;
            var pickTime = $(this).find("[data-format]").attr("data-format").toLowerCase().indexOf("hh:mm:ss") != -1;
            $date.datetimepicker({
                pickDate : pickDate,
                pickTime : pickTime
            });
            $date.find(":input").click(function() {$date.find(".icon-calendar,.icon-time,.icon-date").click();});
            $date.attr("initialized", true);
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
                updateUrl : "${ctx}/showcase/parentchild/parent/child/update/{id}" 修改时url模板 {id} 表示修改时的id,
                deleteUrl : "${ctx}/showcase/parentchild/parent/child/delete/{id}  删除时url模板 {id} 表示删除时的id,
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
            if($tr.size() > 0 && $tr.find(":input").size() > 0) {
                //因为是按顺序保存的 所以按照顺序获取  第一个是checkbox 跳过
                var index = 1;
                $(":input", options.form).not(options.excludeInputSelector).each(function() {
                    var $input = $(this);
                    var $trInput = $tr.find(":input").eq(index++);
                    if($trInput.size() == 0) {
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
        if($lastTr.size() == 0 || options.alwaysNew) {
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


        var $updateBtn = $("<a class='icon-edit' href='javascript:void(0);' title='修改'></a>");
        $updateBtn.click(function() {
            $.parentchild.updateChild($(this), options.updateUrl, options.modalSettings);
        });

        var $deleteBtn = $("<a class='icon-trash' href='javascript:void(0);' title='删除'></a>");
        $deleteBtn.click(function() {
            $.parentchild.deleteChild($(this), options.deleteUrl, options.modalSettings);
        })
        var $copyBtn = $("<a class='icon-copy' href='javascript:void(0);' title='以此为模板复制一份'></a>");
        $copyBtn.click(function() {
            $.parentchild.copyChild($(this), options.updateUrl, options.modalSettings);
        });

        $tr.append($td.clone(true).append($updateBtn).append(" ").append($deleteBtn).append(" ").append($copyBtn));

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
    updateChild : function($a, updateUrl, modalSettings) {
        var $tr = $a.closest("tr");
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
    copyChild : function($a, updateUrl, modalSettings) {
        var $tr = $a.closest("tr");
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
     *     updateUrl : "${ctx}/showcase/parentchild/parent/child/update/{id}" 修改时url模板 {id} 表示修改时的id,
     *     deleteUrl : "${ctx}/showcase/parentchild/parent/child/delete/{id}  删除时url模板 {id} 表示删除时的id,
     * }
     */
    initParentForm : function(options) {


        var $childTable = $("#" + options.tableId);
        $.table.initCheckbox($childTable);
        //绑定在切换页面时的事件 防止误前进/后退 造成数据丢失
        $(window).on('beforeunload',function(){
            if($childTable.find(":input").size() > 0) {
                return "确定离开当前编辑页面吗？";
            }
        });
        $(".btn-create-child").click(function() {
            $.app.modalDialog("新增", options.createUrl, options.modalSettings);
        });
        $(".btn-batch-delete-child").click(function() {
            var $trs = $childTable.find("tbody tr").has(".check :checkbox:checked");
            if($trs.size() == 0) {
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
                    });

                }
            });
        });
        $childTable.find("tbody tr").each(function() {
            var $tr = $(this);
            if($tr.prop("id").indexOf("old_") == 0) {
                $tr.find(".icon-edit").click(function() {
                    $.parentchild.updateChild($(this), options.updateUrl, options.modalSettings);
                });
                $tr.find(".icon-remove").click(function() {
                    $.parentchild.deleteChild($(this), options.deleteUrl, options.modalSettings);
                });
                $tr.find(".icon-copy").click(function() {
                    $.parentchild.copyChild($(this), options.updateUrl, options.modalSettings);
                });
            }
        });
        options.form.submit(function() {
            //如果是提交 不需要执行beforeunload
            $(window).unbind("beforeunload");
            $childTable.find("tbody tr").each(function(index) {
                var tr = $(this);
                tr.find(":input").each(function() {
                    if($(this).prop("name").indexOf(options.prefixParamName) != 0) {
                        $(this).prop("name", options.prefixParamName + "[" + index + "]." + $(this).prop("name").replace(tr.prop("id"), ""));
                    }
                });
            });
        });
    }
    ,
    /**
     * 初始化父子列表中子列表
     * @param $table 父表格
     * @param loadUrl 装载子表格的地址
     */
    initChildList : function($table, loadUrl) {
        var openIcon = "icon-plus-sign";
        var closeIcon = "icon-minus-sign";
        $table.find("tr ." + openIcon).click(function() {
            var $a = $(this);
            //只显示当前的 其余的都隐藏
            $a.closest("table")
                .find("." + closeIcon).not($a).removeClass(closeIcon).addClass(openIcon)
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
                $dataTd.load(loadUrl.replace("{parentId}", $a.attr("data-id")),function() {
                    $.app.waitingOver();
                });
            }
            $dataTr.toggle();

            return false;
        });

    }
}


$.table = {
    
    /**
     * 初始化表格：全选/反选 排序
     * @param table
     */
    initTable: function (table) {
        if(!table || table.size() == 0 || table.attr("initialized") == "true") {
            return;
        }

        table.attr("initialized", "true");
        $.table.initCheckbox(table);

        $.table.initSort(table);
        $.table.initSearchForm(table);
        if(table.is(".move-table")) {
            $.movable.initMoveableTable(table);
        }

        //初始化table里的a标签
        $.table.initTableBtn(table);
        //初始化批量删除和修改按钮
        $.table.initBatchDeleteSelected(table);
        $.table.initUpdateSelected(table);
        $.table.initCreate(table);


    },
    initCheckbox: function(table) {
        //初始化表格中checkbox 点击单元格选中
        table.find("td.check").each(function () {
            var checkbox = $(this).find(":checkbox,:radio");
            checkbox.off("click").on("click", function (event) {
                event.stopPropagation();
            });
            $(this).off("click").on("click", function (event) {
                checkbox.prop("checked", !checkbox.prop("checked"));
            });
        });
        //初始化全选反选
        table.find(".check-all").off("click").on("click", function () {
            var checkAll = $(this);
            if(checkAll.text() == '全选') {
                checkAll.text("取消");
                table.find("td.check :checkbox").prop("checked", true);
            } else {
                checkAll.text("全选");
                table.find("td.check :checkbox").prop("checked", false);
            }
        });
        table.find(".reverse-all").off("click").on("click", function () {
            table.find("td.check :checkbox").each(function () {
                $(this).prop("checked", !$(this).prop("checked"));
            });
        });
    },
    /**
     * 初始化对应的查询表单
     * @param table
     */
    initSearchForm : function(table) {
        var id = $(table).attr("id");
        var searchForm = table.closest("[data-table='" + id + "']").find(".search-form");
        if(searchForm.size() == 0) {
            return;
        }

        var turnSearch = function(table, searchForm, isSearchAll) {
            var url = $.table.tableURL(table);
            url = $.table.removeSearchParam(url, searchForm);
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
            searchForm.find(":input:not(:button,:submit)").off("change").on("change", function() {
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
        if (table.size() == 0) {
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
        var table = $(child).closest("table");

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
        table.closest(".ui-dialog").attr("data-url", backURL);

        if (table.attr("data-async") == "true") {
            $.app.waiting();

            var tableId = table.attr("id");
            var containerId = table.attr("data-async-container");
            var headers = {};

            if(!containerId) {//只有只替换表格时使用
                headers.table = true;
            }

            $.ajax({
                url: url,
                headers: headers
            }).done(function (data) {
                    $.app.waitingOver();
                    if (containerId) {//装载到容器
                        $("#" + containerId).replaceWith(data);
                    } else {
                        table.replaceWith(data);
                    }

                    table = $("#" + tableId);
                    table.attr("data-url", backURL);
                    $.table.initTable(table);


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

        var url = table.attr("data-url");
        if(!url && $dialog.size() > 0) {
            //modalDialog
            url = $dialog.attr("data-url");
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
        var prefix = table.attr("data-prefix");
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
            url = url.replace(new RegExp(name + "=.*\&","g"), '');
            url = url.replace(new RegExp("[\&\?]" + name + "=.*$","g"), '');
        });
        return url;
    }
    ,
    //格式化url前缀，默认清除url ? 后边的
    formatUrlPrefix : function(urlPrefix, $table) {

        if($table && $table.size() > 0) {
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

    initBatchDeleteSelected : function($table, urlPrefix) {
        if(!$table || $table.size() == 0) {
            return;
        }

        var $btn = $table.closest("[data-table='" + $table.attr("id") + "']").find(".btn-batch-delete");
        urlPrefix = $.table.formatUrlPrefix(urlPrefix, $table);
        $btn.off("click").on("click", function() {
            var $selectedIds = $table.find(".check :checkbox:checked");
            if($selectedIds.size() == 0) {
                $.app.alert({message : "请先选择要删除的数据！"});
                return;
            }

            $.app.confirm({
                message: "确定删除选择的数据吗？",
                ok : function() {
                    window.location.href =
                        urlPrefix + "/batch/delete?" + $selectedIds.serialize() + "&BackURL=" + $.table.encodeTableURL($table);
                }
            });
        });
    }
    ,
    initUpdateSelected : function($table, urlPrefix) {
        if(!$table || $table.size() == 0) {
            return;
        }
        var $btn = $table.closest("[data-table='" + $table.attr("id") + "']").find(".btn-update");

        urlPrefix = $.table.formatUrlPrefix(urlPrefix, $table);
        $btn.off("click").on("click", function() {
            var id = $table.find(".check :checkbox:checked").val();
            if(!id) {
                $.app.alert({message : "请先选择要修改的数据"});
                return;
            }

            window.location.href = urlPrefix + "/update/" + id + "?BackURL=" + $.table.encodeTableURL($table);
        });
    },
    initCreate : function($table, urlPrefix) {
        if(!$table || $table.size() == 0) {
            return;
        }
        var $btn = $table.closest("[data-table='" + $table.attr("id") + "']").find(".btn-create");
        var url =  $.table.formatUrlPrefix(urlPrefix, $table) + "/create";
        if($btn.attr("href")) {
            url = $btn.attr("href");
        }
        $btn.attr("href", "javascript:;");
        $btn.off("click").on("click", function() {
            window.location.href = url + (url.indexOf("?") == -1 ? "?" : "&") + "BackURL=" + $.table.encodeTableURL($table);
        });
    },
    initTableBtn : function($table) {
        if(!$table || $table.size() == 0) {
            return;
        }
        $table.closest("[data-table=" + $table.attr("id") + "]").find(".btn").not(".btn-custom,.btn-create,.btn-update,.btn-batch-delete").each(function() {
            var $btn = $(this);
            var url = $btn.attr("href");
            if(!url || url.indexOf("#") == 0 || url.indexOf("javascript:") == 0) {//没有url就不处理了
                return;
            }
            $btn.off("click").on("click", function() {
                window.location.href = url + (url.indexOf("?") == -1 ? "?" : "&") + "BackURL=" + $.table.encodeTableURL($table);
            }).attr("href", "javascript:;");

        });

    }
}

$.movable = {
    /**
     * urlPrefix：指定移动URL的前缀，
     * 如/sample，生成的URL格式为/sample/move/{direction:方向(up|down)}/{fromId}/{toId}
     * @param table
     * @param urlPrefix
     */
    initMoveableTable : function(table) {
        if(table.size() == 0) {
            return;
        }
        var urlPrefix = table.attr("data-move-url-prefix");
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
            var url = urlPrefix + "/" + direction + "/" + fromId + "/" + toId;
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


$(function () {
    //global disable ajax cache
    $.ajaxSetup({ cache: false });

    $(".table").each(function() {
        $.table.initTable($(this));
    });
    $.app.initDatetimePicker();


    $(document).ajaxError(function(event,request, settings){
        $.app.alert({
            title : "网络故障/系统故障(请截屏反馈给管理员)",
            message : "出错状态码:" + request.status + "[" + request.statusText + "]" + "<br/>出错页面????:" + settings.url
        });
        $.app.waitingOver();
    });
});

