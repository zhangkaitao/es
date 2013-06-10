<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jspf"%>
<es:contentHeader/>
<%@include file="/WEB-INF/jsp/common/import-zTree-css.jspf"%>


<ul class="nav nav-tabs">
    <li ${empty param['search.show_eq'] ? 'class="active"' : ''}>
        <a href="${ctx}/admin/maintain/editor/tree?async=${not empty param.async and param.async eq true}">
            <i class="icon-table"></i>
            目录列表
            <i class="icon-refresh" title="点击刷新"></i>
        </a>
    </li>

</ul>

<es:contentFooter/>
<%@include file="/WEB-INF/jsp/common/import-zTree-js.jspf"%>
<script type="text/javascript">
    var async = ${not empty param.async and param.async eq true};
    $(function() {
        var zNodes =[
            <c:forEach items="${trees}" var="m">
            { id:${m.id}, pId:${m.pId}, name:"${m.name}", path : "${m.path}", iconSkin:"${m.iconSkin}", open: ${m.open}, root : ${m.root},isParent:${m.isParent}},
            </c:forEach>
        ];

        $.zTree.initMovableTree({
            zNodes : zNodes,
            urlPrefix : "${ctx}/admin/maintain/editor",
            async : async,
            setting : {
                async : {
                    type: "get",
                    autoParam : ["id", "path"]
                },
                callback : {
                    onClick: function(event, treeId, treeNode, clickFlag) {
                        parent.frames['listFrame'].location.href='${ctx}/admin/maintain/editor/list?path=' + treeNode.path;
                    }
                }
            }
        });

    });
</script>