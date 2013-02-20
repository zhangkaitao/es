<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jspf"%>
<es:contentHeader/>
<%@include file="/WEB-INF/jsp/common/import-zTree-css.jspf"%>
<ul id="tree" class="ztree"></ul>

<es:contentFooter/>
<%@include file="/WEB-INF/jsp/common/import-zTree-js.jspf"%>
<script type="text/javascript">
        <!--
        var setting = {
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

        var newCount = 1;
        function addHoverDom(treeId, treeNode) {
            var sObj = $("#" + treeNode.tId + "_span");
            if (treeNode.editNameFlag || $("#addBtn_" + treeNode.id).length > 0) return;
            var addStr = "<span class='button add' id='addBtn_" + treeNode.id
                    + "' title='添加子节点' onfocus='this.blur();'></span>";
            sObj.after(addStr);
            var btn = $("#addBtn_" + treeNode.id);
            if (btn) btn.bind("click", function (e) {
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
            var url = "${ctx}/showcase/tree/ajax/rename/" + treeNode.id + "?newName=" + treeNode.name;
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
            var url = "${ctx}/showcase/tree/ajax/delete/" + treeNode.id;
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
            var url = "${ctx}/showcase/tree/ajax/appendChild/" + treeNode.id;
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
            var sourcePath = treeNodes[0].id;
            var targetPath = targetNode.id;
            var moveType = moveType;
            var url = "${ctx}/showcase/tree/ajax/move/" + sourcePath + "/" + targetPath + "/" + moveType;
            $.getJSON(url, function (newNode) {
                location.reload();
            });
        }

        var zNodes =[
            <c:forEach items="${trees.content}" var="t">
                { id:'${t.path}', pId:'${t.parentPath}', name:"${t.name}", icon:"${ctx}/${t.icon}", open: true,
                  click : "parent.frames['maintainFrame'].location.href='${ctx}/showcase/tree/maintain/${t.id}'",
                  root : ${t.root}},
            </c:forEach>
        ];
 
        $(document).ready(function(){
            $.fn.zTree.init($("#tree"), setting, zNodes);
        });
        //-->
</script>