<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jspf"%>
<es:contentHeader/>
<%@include file="/WEB-INF/jsp/common/import-zTree-css.jspf"%>


<ul class="nav nav-tabs">
    <li ${empty param['search.show_eq'] ? 'class="active"' : ''}>
        <a href="${ctx}/admin/sys/resource/tree?async=${not empty param.async and param.async eq true}">
            <i class="icon-table"></i>
            所有
            <i class="icon-refresh" title="点击刷新"></i>
        </a>
    </li>
    <li ${not empty param['search.show_eq'] ? 'class="active"' : ''}>
        <a href="${ctx}/admin/sys/resource/tree?search.show_eq=true&async=${not empty param.async and param.async eq true}">
            <i class="icon-table"></i>
            显示的
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
            { id:${m.id}, pId:${m.pId}, name:"${m.name}", iconSkin:"${m.iconSkin}", open: true, root : ${m.root},isParent:${m.isParent}},
            </c:forEach>
        ];


        $.zTree.initMovableTree({
            zNodes : zNodes,
            urlPrefix : "${ctx}/admin/sys/resource",
            async : async,
            onlyDisplayShow:${param['search.show_eq'] eq true},
            permission: <es:treePermission resourceIdentity="sys:resource"/>,
            autocomplete : {
                enable : true
            },
            setting : {
                callback : {
                    onClick: function(event, treeId, treeNode, clickFlag) {
                        parent.frames['listFrame'].location.href='${ctx}/admin/sys/resource/' + treeNode.id + "/update?async=" + async ;
                    }
                }
            }
        });

    });
</script>