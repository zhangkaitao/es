<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jspf"%>
<es:contentHeader/>
<%@include file="/WEB-INF/jsp/common/import-zTree-css.jspf"%>


<ul class="nav nav-tabs">
    <li <c:if test="${empty param['search.show_eq']}">class="active"</c:if>>
        <a href="${ctx}/showcase/tree/tree?async=${not empty param.async and param.async eq true}">
            <i class="icon-table"></i>
            所有
            <i class="icon-refresh" title="点击刷新"></i>
        </a>
    </li>
    <li <c:if test="${not empty param['search.show_eq']}">class="active"</c:if>>
        <a href="${ctx}/showcase/tree/tree?async=${not empty param.async and param.async eq true}&search.show_eq=true">
            <i class="icon-table"></i>
            显示的
        </a>
    </li>
</ul>

<div id="treeContainer"></div>

<es:contentFooter/>
<%@include file="/WEB-INF/jsp/common/import-zTree-js.jspf"%>
<script type="text/javascript">
    var async = ${not empty param.async and param.async eq true};
    $(function() {
        var zNodes =[
            <c:forEach items="${trees}" var="m">
            { id:${m.id}, pId:${m.pId}, name:"${m.name}", iconSkin:"${m.iconSkin}", open: true, root : ${m.root},isParent:${m.isParent}},
            </c:forEach>
        ];

        $.zTree.initMovableTree({
            zNodes : zNodes,
            urlPrefix : "${ctx}/showcase/tree",
            async : async,
            onlyShow:${param['search.show_eq'] eq true},
            autocomplete : {
                enable : true
            },
            setting : {
                callback : {
                    onClick: function(event, treeId, treeNode, clickFlag) {
                        parent.frames['listFrame'].location.href=
                                '${ctx}/showcase/tree/list/' + treeNode.id + "?async=" + async;
                    }
                }
            }
        });

    });
</script>