<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jspf"%>
<es:contentHeader/>
<%@include file="/WEB-INF/jsp/common/import-zTree-css.jspf"%>

<ul class="nav nav-tabs">
    <li class="active">
        <a>
            <i class="icon-sitemap"></i>
            组织机构树
        </a>
    </li>
</ul>

<es:contentFooter/>
<%@include file="/WEB-INF/jsp/common/import-zTree-js.jspf"%>
<script type="text/javascript">
    var async = ${not empty param.async and param.async eq true};

    function treeNodeClick(node, id, pId) {
        parent.frames['maintainFrame'].location.href='${ctx}/admin/sys/organization/organization/maintain/' + id + "?async=" + async;
    }
    $(function() {
        var zNodes =[
            <c:forEach items="${trees}" var="m">
            { id:${m.id}, pId:${m.pId}, name:"${m.name}", icon:"${m.icon}", open: true,
                click : "${m.click}", root : ${m.root},isParent:${m.isParent}},
            </c:forEach>
        ];

        $.zTree.initMovableTree({
            zNodes : zNodes,
            urlPrefix : "${ctx}/admin/sys/organization/organization",
            async : async,
            autocomplete : {
                enable : true,
                async : async,
                callback : function(searchName) {
                    location.href = "${ctx}/showcase/tree/tree?async=" + async + "&searchName=" + searchName;
                }
            }
        });

    });
</script>