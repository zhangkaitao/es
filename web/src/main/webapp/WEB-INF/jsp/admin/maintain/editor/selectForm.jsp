<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jspf"%>
<%@include file="/WEB-INF/jsp/common/import-zTree-css.jspf"%>

<div id="selectTree"></div>

<%@include file="/WEB-INF/jsp/common/import-zTree-js.jspf"%>
<script type="text/javascript">
    $(function() {
        var zNodes =[
            <c:forEach items="${trees}" var="m">
            { id:${m.id}, pId:${m.pId}, name:"${m.name}", path : "${m.path}", iconSkin:"${m.iconSkin}", open: ${m.open}, root : ${m.root},isParent:${m.isParent}},
            </c:forEach>
        ];

        var excludePaths = [];

        <c:forEach items="${excludePaths}" var="path">
            excludePaths.push('${path}');
        </c:forEach>



        $.zTree.initMovableTree({
            containerId : "selectTree",
            zNodes : zNodes,
            urlPrefix : "${ctx}/admin/maintain/editor",
            async : true,
            setting : {
                async : {
                    type: "get",
                    autoParam : ["id", "path"],
                    otherParam : ["paths", excludePaths.join(",")]
                }
            }
        });

    });
</script>