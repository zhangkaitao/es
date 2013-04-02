<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jspf"%>
<es:contentHeader/>
<%@include file="/WEB-INF/jsp/common/import-zTree-css.jspf"%>

<ul class="nav nav-tabs">
    <li class="active">
        <a>
            <i class="icon-sitemap"></i>
            工作职务树
        </a>
    </li>
</ul>


<div class="control-group tree-search">
    <esform:label path="searchName">名称</esform:label>
    <div class="controls">
        <esform:input path="searchName" cssClass="input-medium" placeholder="模糊匹配 回车键查询"/>
    </div>
</div>

<es:contentFooter/>
<%@include file="/WEB-INF/jsp/common/import-zTree-js.jspf"%>
<script type="text/javascript">
    var async = ${not empty param.async and param.async eq true};
    function treeNodeClick(node, id, pId) {
        parent.frames['maintainFrame'].location.href='${ctx}/admin/sys/organization/job/maintain/' + id + "?async=" + async;
    }
    $(function() {
        var zNodes =[
            <c:forEach items="${trees}" var="m">
            { id:${m.id}, pId:${m.pId}, name:"${m.name}", icon:"${m.icon}", open: true,
                click : "${m.click}", root : ${m.root},isParent:${m.isParent}},
            </c:forEach>
        ];

        var ztree = $.zTree.initMovableTree(
                zNodes,
                //重命名url模式
                "${ctx}/admin/sys/organization/job/ajax/rename/{id}?newName={newName}",
                //删除url模式
                "${ctx}/admin/sys/organization/job/ajax/delete/{id}",
                //新增url模式
                "${ctx}/admin/sys/organization/job/ajax/appendChild/{id}",
                //移动url模式
                "${ctx}/admin/sys/organization/job/ajax/move/{sourceId}/{targetId}/{moveType}",
                //异步模式
                async,
                //异步加载url
                "${ctx}/admin/sys/organization/job/ajax/asyncLoad"
        );

        $.zTree.initAutocomplete(
                $("[name='searchName']"),
                async,
                "${ctx}/admin/sys/organization/job/ajax/autocomplete",
                "${ctx}/admin/sys/organization/job/tree");
    });
</script>