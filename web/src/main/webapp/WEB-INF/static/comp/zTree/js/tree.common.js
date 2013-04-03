$.zTree = {
    index : 1,

    /**
     * 初始化可移动树
     */
    initMovableTree : function(zNodes, renameUrl, removeUrl, addUrl, moveUrl, async, loadUrl) {
        loadUrl = loadUrl + (loadUrl.indexOf("?") == -1 ? "?" : "&") + "async=" + async;
        var setting = {
            async: {
                enable: async,
                url:loadUrl,
                autoParam:["id"],
                dataFilter: $.zTree.filter
            },
            view: {
                addHoverDom: addHoverDom,
                removeHoverDom: removeHoverDom,
                selectedMulti: false
            },
            edit: {
                enable: true,
                editNameSelectAll: true,
                showRemoveBtn : function(treeId, treeNode) {return !treeNode.root;},
                showRenameBtn: true,
                removeTitle: "移除",
                renameTitle: "重命名",
                drag : {
                    prev: drop,
                    inner: drop,
                    next: drop
                }
            },
            data: {
                simpleData: {
                    enable: true
                }
            },
            callback:{
                beforeRemove: function(treeId, treeNode) { return confirm("确认删除吗？")},
                beforeRename : beforeRename,
                onRemove: onRemove,
                onRename: onRename,
                onDrop : onDrop
            }
        };


        function drop(treeId, nodes, targetNode) {
            if(!targetNode || !targetNode.getParentNode()) {
                return false;
            }
            for (var i = 0, l = nodes.length; i < l; i++) {
                if (nodes[i].root === true) {
                    return false;
                }
            }
            return true;
        }


        function addHoverDom(treeId, treeNode) {
            var sObj = $("#" + treeNode.tId + "_span");
            if (treeNode.editNameFlag || $("#addBtn_" + treeNode.id).length > 0) return;
            var addStr = "<span class='button add' id='addBtn_" + treeNode.id
                + "' title='添加子节点' onfocus='this.blur();'></span>";
            sObj.after(addStr);
            var btn = $("#addBtn_" + treeNode.id);
            if (btn)
                btn.bind("click", function (e) {
                    onAdd(e, treeId, treeNode);
                    return false;
                });
        }
        function removeHoverDom(treeId, treeNode) {
            $("#addBtn_" + treeNode.id).unbind().remove();
        }

        function beforeRename(treeId, treeNode, newName) {
            var oldName = treeNode.name;
            if (newName.length == 0) {
                $.app.alert({
                    message : "节点名称不能为空。"
                });
                return false;
            }
            if(!confirm("确认重命名吗？")) {
                var zTree = $.fn.zTree.getZTreeObj(treeId);
                zTree.cancelEditName(treeNode.name);
                return false;
            }
            return true;
        }
        /**
         * 重命名结束
         * @param e
         * @param treeId
         * @param treeNode
         */
        function onRename(e, treeId, treeNode) {
            var url = renameUrl.replace("{id}", treeNode.id).replace("{newName}",treeNode.name);
            $.getJSON(url, function (data) {
                location.reload();
            });
        }
        /**
         * 重命名结束
         * @param e
         * @param treeId
         * @param treeNode
         */
        function onRemove(e, treeId, treeNode) {
            var url = removeUrl.replace("{id}", treeNode.id);
            $.getJSON(url, function (data) {
                location.reload();
            });
        }

        /**
         * 添加新节点
         * @param e
         * @param treeId
         * @param treeNode
         */
        function onAdd(e, treeId, treeNode) {
            var url = addUrl.replace("{id}", treeNode.id);
            $.getJSON(url, function(newNode) {
                location.reload();
            });
        }

        /**
         * 移动结束
         * @param event
         * @param treeId
         * @param treeNodes
         * @param targetNode
         * @param moveType
         * @param isCopy
         */
        function onDrop(event, treeId, treeNodes, targetNode, moveType, isCopy) {
            if(!targetNode || treeNodes.length == 0) {
                return;
            }
            var sourceId = treeNodes[0].id;
            var targetId = targetNode.id;
            var moveType = moveType;
            var url = moveUrl.replace("{sourceId}", sourceId).replace("{targetId}", targetId).replace("{moveType}", moveType);
            $.getJSON(url, function (newNode) {
                location.reload();
            });
        }

        var treeId = "tree" + this.index++;
        $("body").append("<ul id='" + treeId + "' class='ztree'></ul>");
        var zTree = $.fn.zTree.init($("#" + treeId), setting, zNodes);
        return zTree;

    },

    /**
     *
     * @param zNodes 所有节点
     * @param btn 触发选择树的按钮
     * @param idDomId 要保存的编号的dom id
     * @param nameDomId 要保存的名称的dom id
     */
    initSelectTree : function(zNodes, async, loadUrl, btn, idDomId, nameDomId, autocomplete, autocompleteUrl) {
        loadUrl = loadUrl + (loadUrl.indexOf("?") == -1 ? "?" : "&") + "async=" + async;
        var id = this.index++;
        var treeContentStr =
            '<div id="treeContent{id}" class="treeContent" style="display:none; position: absolute;">' +
                (autocomplete ?
                    '<div class="control-group tree-search" style="margin-top:5px;">' +
                        '<label for="searchName{id}">名称</label>' +
                        '<div class="controls">' +
                            '<input type="text" id="searchName{id}" class="input-medium" placeholder="模糊匹配 回车键查询"/>' +
                        '</div>' +
                    '</div>' : '') +
                '<ul id="treeSelect{id}" class="ztree"></ul>' +
                '</div>';

        $("body").append(treeContentStr.replace(/\{id\}/g, id));

        var $id = $("#" + idDomId);
        var $name = $("#" + nameDomId);
        var treeContent = "treeContent" + id;
        var $treeContent = $("#" + treeContent);
        var treeSelect = "treeSelect" + id;

        var setting = {
            async: {
                enable: async,
                url:loadUrl,
                autoParam:["id"],
                dataFilter: $.zTree.filter
            },
            view: {
                dblClickExpand: false
            },
            data: {
                simpleData: {
                    enable: true
                }
            },
            callback: {
                onClick: onClick
            }
        };

        function onClick(e, treeId, treeNode) {
            var zTree = $.fn.zTree.getZTreeObj(treeSelect);
            var nodes = zTree.getSelectedNodes();
            var lastNode = nodes[nodes.length - 1];
            $name.prop("value", lastNode.name);
            $id.prop("value", lastNode.id);
        }

        function showMenu() {
            var nameOffset = $name.offset();
            $treeContent.css({left: nameOffset.left + "px", top: nameOffset.top + $name.outerHeight() + "px"}).slideDown("fast");
            $("body").bind("mousedown", onBodyDown);
        }

        function hideMenu() {
            $treeContent.fadeOut("fast");
            $("body").unbind("mousedown", onBodyDown);
        }

        function onBodyDown(event) {
            if (!($(event.target).closest(".ui-autocomplete").length > 0  || event.target.id == treeContent || $(event.target).closest("#" + treeContent).length > 0)) {
                hideMenu();
            }
        }

        btn.click(function () {
            showMenu();
        });
        window.treeNodeClick = hideMenu;

        var zTree = $.fn.zTree.init($("#" + treeSelect), setting, zNodes);

        if(autocomplete) {
            $.zTree.initAutocomplete(
                $("#searchName" + id),
                async,
                autocompleteUrl,
                function(searchName) { //按照名字搜索
                    var url = loadUrl + "&searchName=" + searchName;
                    zTree.destroy();
                    $.getJSON(url, function(zNodes) {
                        if(zNodes.length > 0) { //如果没找到节点就不必展示
                            zTree = $.fn.zTree.init($("#" + treeSelect), setting, zNodes);
                        }
                    });
                }
            );
        }

    },
    initMaintainBtn : function(updateUrl, deleteUrl, appendChildUrl, moveTreeUrl) {

        $("#updateTree").click(function() {
            this.form.action = updateUrl;
        });
        $("#deleteTree").click(function () {
            var btn = this;
            $.app.confirm({
                message : "确认删除吗？",
                ok : function() {
                    btn.form.action = deleteUrl;
                    btn.form.submit();
                }
            });
            return false;
        });
        $("#appendChild").click(function () {
            window.location.href = appendChildUrl;
            return false;
        });
        $("#moveTree").click(function () {
            window.location.href = moveTreeUrl;
            return false;
        });

    },
    initMoveBtn : function() {
        $("#moveAsPrev").click(function() {
            $("#moveType").val("prev");
        });
        $("#moveAsNext").click(function() {
            $("#moveType").val("next");
        });
        $("#moveAsInner").click(function() {
            $("#moveType").val("inner");
        });

    }
    ,
    split : function( val ) {
    return val.split( /,\s*/ );
    },
    extractLast : function( term ) {
        return this.split( term ).pop();
    }
    ,
    initAutocomplete : function(input, async, autocompleteUrl, searchCallback) {
        $(input)
            .on( "keydown", function( event ) {
                //回车查询
                if(event.keyCode === $.ui.keyCode.ENTER) {
                    searchCallback(input.val());
                }
            })
            .autocomplete({
                source: autocompleteUrl,
                minLength:1,
                select: function() {searchCallback(input.val());}
            });
    },
    filter : function(treeId, parentNode, childNodes) {
        if (!childNodes) return null;
        for (var i=0, l=childNodes.length; i<l; i++) {
            childNodes[i].name = childNodes[i].name.replace(/\.n/g, '.');
        }
        return childNodes;
    }

}