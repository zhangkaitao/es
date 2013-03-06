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
                var left = panelElement.width() + 30;
                if (panelState.isClosed) {
                    left = 21;
                }
                tabul.css("left", left);
            }
            if (panelName == 'north') {
                var top = panelElement.height() + 30;
                if (panelState.isClosed) {
                    top = 9;
                }
                tabul.css("top", top);
            }
        }

        this.layout = $('body').layout({
                west__size:  230
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
        var tabs = $("#tabs").tabs({
            beforeActivate: function (event, ui) {
                $("#tabs").find(".ui-icon").hide();
                var newPanelId = ui.newPanel.prop("id");
                $("#tabs").find("#" + ui.newPanel.attr("aria-labelledby")).siblings(".ui-icon").show();
                $.app.activeIframe(newPanelId);
                activeMenu(newPanelId);
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

        function activeMenu(tabPanelId) {
            var currentMenu = $("#menu-" + tabPanelId.replace("tabs-", ""));
            $("#menu .select").removeClass("select");
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
        this.initTabScroll();
    },
        initTabScroll: function () {
            var scrollInterval;
            var speed = 20;

            var move = function(step) {
                return function() {
                    var $ulWrapper = $("#tabs .ul-wrapper");
                    var $lastLI = $ulWrapper.find("ul li:last");

                    var ulWrapperLeftPos = $ulWrapper.offset().left + $ulWrapper.width();
                    var lastLILeftPos = $lastLI.offset().left + $lastLI.width();
                    var leftPos = $ulWrapper.scrollLeft() + step;
                    var maxLeftPos = lastLILeftPos - ulWrapperLeftPos;
                    //right move
                    if(step > 0) {
                        if(lastLILeftPos < ulWrapperLeftPos + 200) {
                        }
                        if(step > 0  && maxLeftPos <= 0) {
                            return;
                        }
                    }

                    if(leftPos < 0) {
                        leftPos = 0;
                    }



                    $ulWrapper.animate({scrollLeft : leftPos}, 900);
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
            .addClass("ui-accordion-header ui-helper-reset ui-state-default  ui-accordion-icons ui-widget-menu")
            .append('<span class="ui-icon ui-icon-menu '+ rootCloseIconClass + '"></span>')
            .next("ul").hide()
            .end()
            .click(function () {
                if ($(this).hasClass("ui-state-active")) {
                    $(this)
                        .removeClass("ui-state-active")
                        .find("span")
                        .removeClass(rootOpenIconClass)
                        .addClass(rootCloseIconClass)
                        .end()
                        .next("ul").hide("blind");
                } else {
                    $(this)
                        .addClass("ui-state-active")
                        .find("span")
                        .removeClass(rootCloseIconClass)
                        .addClass(rootOpenIconClass)
                        .end()
                        .next("ul").show("blind");
                }
            }).eq(0).click();

        var leafIconClass = "icon-angle-right";
        var branchOpenIconClass = "icon-double-angle-right";
        var branchCloseIconClass = "icon-double-angle-down";
        menus.find("div > ul")
            .addClass(" ui-accordion-content ui-helper-reset ui-widget-content ui-widget-menu ui-corner-bottom ui-accordion-content-active")
            .children("li").each(function () {
                var submenu = $(this);
                if (submenu.find("ul").size() > 0) {
                    submenu.find("ul").hide();
                    submenu.find("li:not(:has(ul))").prepend('<span class="' + leafIconClass + '"></span>');
                    submenu.find("ul").closest("li")
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
            var currentTabPanel = null;
            a.data("index", index++)
                .data("url", a.attr("href"))
                .attr("href", "#")
                .attr("id", "menu-" + a.data("index"))
                .click(function () {
                    var a = $(this);
                    $("#menu a").each(function () {
                        $(this).closest("li").removeClass("select");
                    });
                    a.closest("li").addClass("select");
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
            var checkbox = $(this).find(":checkbox,:radio");
            checkbox.click(function (event) {
                event.stopPropagation();
            });
            $(this).click(function (event) {
                checkbox.prop("checked", !checkbox.prop("checked"));
            });
        });
        //初始化全选反选
        table.find(".check-all").click(function () {
            var checkAll = $(this);
            if(checkAll.text() == '全选') {
                checkAll.text("取消");
                table.find("td.check :checkbox").prop("checked", true);
            } else {
                checkAll.text("全选");
                table.find("td.check :checkbox").prop("checked", false);
            }
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
     * @param async 异步加载吗》
     * @param containerId 异步加载到的容器ID 请参考tags/page.tag
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

        var async = table.attr("sort-async");
        var containerId = table.attr("sort-container-id");
        if(async == 'true' && !containerId) {
            $.app.alert({message: "异步加载时，容器id(sort-container-id)不能为空"});
            return;
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
                $.app.turnSort(sortURL, sortPropertyName, "asc", async, containerId);
            });
            sortBtn.find(".down").click(function () {
                $.app.turnSort(sortURL, sortPropertyName, "desc", async, containerId);
            });
            sortBtn.find(".cancel").click(function () {
                $.app.turnSort(sortURL, null, null, async, containerId);
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

        $.app.waiting(loadingMessage);

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
        this.layout.resizeAll();

    },

    waiting : function(message) {
        if(!message) {
            message = "装载中...";
        }
        top.window.$.blockUI({
            fadeIn: 700,
            fadeOut: 700,
            showOverlay: false,
            css: {
                border: 'none',
                padding: '15px',
                backgroundColor: '#ccc',
                '-webkit-border-radius': '10px',
                '-moz-border-radius': '10px',
                opacity:.6,
                color: '#000'
            },
            message: '<h4><img src="' + ctx + '/static/images/loading.gif" /> ' + message + ' </h4>'
        });
    }
    ,
    waitingOver: function () {
        top.window.$.unblockUI();
    }
    ,
    /**
     * 模态窗口
     * @title 标题
     * @param url
     * @param css
     */
    modalDialog : function(title, url, css, currentDocument) {
        var defaultCss = {
            cursor:"default",
            "text-align": "",
            padding : "10px",
            width: "800px",
            top: "20%",
            left: "30%"
        };
        if(!css) {
            css = {};
        }
        css = $.extend({}, defaultCss, css);

        $.app.waiting();
        $.get(url, function(data) {
            $.app.waitingOver();
            //因为是在top中弹出窗口编辑，因此需要把document保存下来，方便接下来的操作
            if(!currentDocument) {
                currentDocument = document;
            }
            top.window.currentDocument = currentDocument;
            top.window.$.blockUI({
                theme:true,
                showOverlay : true,
                title : title + "<a class='btn btn-link icon-remove blockUI-close' href='javascript:$.app.waitingOver();' title='关闭'></a>",
                message : data,
                css : css,
                themedCSS: css
            });
        });
    }
    ,
    /**
     * 取消编辑
     */
    cancelModelDialog : function() {
        this.waitingOver();
        top.window.currentDocument = null;
    }
    ,
    /**
     * 分页
     * @param url
     * @param pagePrefix
     * @param pageSize
     * @param pn
     * @param async 异步模式
     * @param containerId异步加载到的容器ID
     */
    turnPage: function (url, pagePrefix, pageSize, pn, async, containerId) {
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

        if(async && !containerId) {
            $.app.alert({
                message : "异步模式下，容器id不能空"
            });
            return;
        }

        if(async) {
            $.get(pageURL, function(data) {
                $("#" + containerId).replaceWith(data);
            });
        } else {
            window.location.href = pageURL;
        }
    },
    /**
     * 执行排序
     * @param sortURL
     * @param sortPropertyName
     * @param order
     * @param async
     * @param containerId
     */
    turnSort: function (sortURL, sortPropertyName, order, async, containerId) {
        //如果URL中包含锚点（#） 删除
        if(sortURL.indexOf("#") > 0) {
            sortURL = sortURL.substring(0, sortURL.indexOf("#"));
        }
        //清空上次排序
        sortURL = sortURL.replace(/\&.*sort.*=((asc)|(desc))/gi, '');
        sortURL = sortURL.replace(/\?.*sort.*=((asc)|(desc))\&/gi, '?');
        sortURL = sortURL.replace(/\?.*sort.*=((asc)|(desc))/gi, '');
        if (!sortPropertyName) {
            if(async) {
                $.get(sortURL, function(data){
                    $("#" + containerId).replaceWith(data);
                });
            } else {
                window.location.href = sortURL;
            }
            return;
        }
        if (sortURL.indexOf("?") == -1) {
            sortURL = sortURL + "?";
        } else {
            sortURL = sortURL + "&";
        }
        sortURL = sortURL + sortPropertyName + "=" + order;

        if(async) {
            $.get(sortURL, function(data){
                $("#" + containerId).replaceWith(data);
            });
        } else {
            window.location.href = sortURL;
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
                '<p>{message}</p>' +
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
            var pickDate = $(this).find("[data-format]").attr("data-format").toLowerCase().indexOf("yyyy-mm-dd") != -1;
            var pickTime = $(this).find("[data-format]").attr("data-format").toLowerCase().indexOf("hh:mm:ss") != -1;
            $(this).datetimepicker({
                pickDate : pickDate,
                pickTime : pickTime
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
            //currentDocument 是执行$.app.modalEdit时保存的当前document 请参考application.modalDialog
            var $tr = $("#" + options.trId, currentDocument);
            if($tr.size() > 0 && $tr.find(":input").size() > 0) {
                //因为是按顺序保存的 所以按照顺序获取  第一个是checkbox 跳过
                var index = 1;
                options.form.find(":input").not(options.excludeInputSelector).each(function() {
                    var $input = $(this);
                    var $trInput = $tr.find(":input").eq(index++);
                    if($trInput.size() == 0) {
                        return;
                    }
                    var $trInputClone = $trInput.clone(true).show();
                    //saveModalFormToTable 为了防止重名问题，添加了tr id前缀，修改时去掉
                    $trInputClone.prop("name", $trInputClone.prop("name").replace(options.trId, ""));
                    $trInputClone.prop("id", $trInputClone.prop("id").replace(options.trId, ""));

                    //克隆后 select的选择丢失了 TODO 提交给jquery bug
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
        //currentDocument 是执行$.app.modalEdit时保存的当前document 请参考application.modalDialog
        var currentDocument = top.window.currentDocument;
        var $childTbody = $("#" + options.tableId + " tbody", currentDocument);

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

        var $inputs = options.form.find(":input").not(":button,:submit,:reset");
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
                        val = val + "，";
                    }
                    val = val + $("label[for='" + $(this).prop("id") + "']").text();
                });
            }
            //下拉列表
            if($input.is("select")) {
                val = "";
                $input.find("option:selected").each(function() {
                    if(val != "") {
                        val = val + "，";
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
            $.parentchild.updateChild($(this), options.updateUrl, currentDocument);
        });

        var $deleteBtn = $("<a class='icon-trash' href='javascript:void(0);' title='删除'></a>");
        $deleteBtn.click(function() {
            $.parentchild.deleteChild($(this), options.deleteUrl, currentDocument);
        })
        var $copyBtn = $("<a class='icon-copy' href='javascript:void(0);' title='以此为模板复制一份'></a>");
        $copyBtn.click(function() {
            $.parentchild.copyChild($(this), options.updateUrl, currentDocument);
        });

        $tr.append($td.clone(true).append($updateBtn).append(" ").append($deleteBtn).append(" ").append($copyBtn));

        $.app.cancelModelDialog();
        return false;
    }
    ,
    /**
     * 更新子
     * @param $a 当前按钮
     * @param updateUrl  更新地址
     * @param currentDocument 当前文档
     */
    updateChild : function($a, updateUrl, currentDocument) {
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
        $.app.modalDialog("修改", updateUrl, null, currentDocument);
    }
    ,
    /**
     * 以当前行复制一份
     * @param $a 当前按钮
     * @param updateUrl  更新地址
     * @param currentDocument 当前文档
     */
    copyChild : function($a, updateUrl, currentDocument) {
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
        $.app.modalDialog("复制", updateUrl, null, currentDocument);
    }
    ,
    /**
     * 删除子
     * @param $a 当前按钮
     * @param deleteUrl 删除地址
     * @param currentDocument 当前文档
     */
    deleteChild : function($a, deleteUrl, currentDocument) {
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
     *     createUrl : "${ctx}/showcase/parentchild/parent/child/create",
     *     updateUrl : "${ctx}/showcase/parentchild/parent/child/update/{id}" 修改时url模板 {id} 表示修改时的id,
     *     deleteUrl : "${ctx}/showcase/parentchild/parent/child/delete/{id}  删除时url模板 {id} 表示删除时的id,
     * }
     */
    initParentForm : function(options) {
        var $childTable = $("#" + options.tableId);

        //绑定在切换页面时的事件 防止误前进/后退 造成数据丢失
        $(window).on('beforeunload',function(){
            if($childTable.find(":input").size() > 0) {
                return "确定离开当前编辑页面吗？";
            }
        });
        $("#createChild").click(function() {
            $.app.modalDialog("新增", options.createUrl);
        });
        $("#removeSelectChild").click(function() {
            $.app.confirm({
                message: "确定删除选中的数据吗？",
                ok : function() {
                    var ids = new Array();
                    var $trs = $childTable.find("tbody tr").has(":checked");
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
                    $.parentchild.updateChild($(this), options.updateUrl, document);
                });
                $tr.find(".icon-remove").click(function() {
                    $.parentchild.deleteChild($(this), options.deleteUrl, document);
                });
                $tr.find(".icon-copy").click(function() {
                    $.parentchild.copyChild($(this), options.updateUrl, document);
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

$.edit = {
    //格式化url前缀，默认清除url ? 后边的
    formatUrlPrefix : function(urlPrefix) {
        if(!urlPrefix) {
            urlPrefix = currentURL;
        }
        if(urlPrefix.indexOf("?") >= 0) {
            return urlPrefix.substr(0, urlPrefix.indexOf("?"));
        }
        return urlPrefix;
    }
    ,
    initRemoveSelected : function($btn, $table, urlPrefix) {
        if(!$btn || !$table) {
            return;
        }
        urlPrefix = this.formatUrlPrefix(urlPrefix);
        $btn.click(function() {
            $.app.confirm({
                message: "确定删除选中的数据吗？",
                ok : function() {
                    window.location.href =
                        urlPrefix + "/batch/delete?" +
                            $table.find(".check :checkbox").serialize() +
                            "&BackURL=" + $.app.removeContextPath(currentURL);
                    //ajax删除
//                    $.ajax({
//                        type : "post",
//                        dataType: "json",
//                        url : "${ctx}/showcase/upload/batch/delete",
//                        data : $(".check :checkbox").serialize(),
//                        success : function (data) {
//                            if (!data.success) {
//                                $.app.alert("删除时遇到问题，请重试或联系管理员");
//                            } else {
//                                location.reload();
//                            }
//                        }
//                    });
                }
            });
        });
    }
    ,
    initUpdateSelected : function($btn, $table, urlPrefix) {

        if(!$btn || !$table) {
            return;
        }

        urlPrefix = this.formatUrlPrefix(urlPrefix);
        $btn.click(function() {
            var id = $table.find(".check :checkbox:checked").val();
            if(!id) {
                $.app.alert({message : "请先选中要修改的数据"});
                return;
            }
            window.location.href = urlPrefix + "/update/" + id + "?BackURL=" + $.app.removeContextPath(currentURL);;
        });
    }
};

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

    $.app.initTable($(".table"));
    $.movable.initMoveableTable($(".move-table"));
    $.app.initDatetimePicker();

    //初始化批量删除和修改按钮
    $.edit.initRemoveSelected($("#removeSelect"), $(".table"));
    $.edit.initUpdateSelected($("#update"), $(".table"));


    $(document).ajaxError(function(event,request, settings){
        $.app.alert({
            title : "网络故障/系统故障(请截屏反馈给管理员)",
            message : "出错状态码:" + request.status + "[" + request.statusText + "]" + "<br/>出错页面:" + settings.url
        });
        $.app.waitingOver();
    });
});

