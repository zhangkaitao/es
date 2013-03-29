<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jspf"%>
<table id="table" class="sort-table table table-bordered table-hover"
       data-move-url-prefix="${ctx}/showcase/move">
    <thead>
    <tr>
        <th style="width: 300px;">名称</th>
        <th>权重</th>
        <th>是否显示</th>
        <th style="width: 80px">操作</th>
    </tr>
    <tbody>
    <c:forEach items="${content}" var="m">
        <tr data-tt-id="${m.treetableIds}" <c:if test="${m.root eq false}">data-tt-parent-id="${m.treetableParentIds}"</c:if>>
            <td>
                <c:choose>
                    <c:when test="${m.leaf eq true}">
                        <span class="file">
                            <img src="${ctx}/${m.leafDefaultIcon}"/>
                            <a href="javascript:;">${m.name}</a>
                        </span>

                    </c:when>
                    <c:otherwise>
                        <span class="folder">
                            <img src="${ctx}/${m.branchDefaultIcon}"/>
                            <a href="javascript:;">${m.name}</a>
                        </span>

                    </c:otherwise>
                </c:choose>

            </td>
            <td>${m.weight}</td>
            <td>${m.show == true ? '是':'否'}</td>
            <td>修改|删除</td>
        </tr>
    </c:forEach>
    </tbody>
</table>


<script type="text/javascript">
    $(function() {
        var treetable = $("#table");
        treetable.treetable({
            expandable: true,
            stringCollapse: "收缩",
            stringExpand: "展开"
        });
        treetable.treetable("expandAll");

        // Highlight selected row
        treetable.find("tbody tr").mousedown(function() {
            $("tr.selected").removeClass("selected");
            $(this).addClass("selected");
        });

        $.movable.initMoveableTable(treetable);

    });
</script>