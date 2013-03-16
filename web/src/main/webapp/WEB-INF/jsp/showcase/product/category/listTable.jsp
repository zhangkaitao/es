<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jspf"%>
<table id="table" class="sort-table move-table table table-bordered table-hover table-striped"
       data-move-url-prefix="${ctx}/showcase/product/category" data-async="true">
    <thead>
    <tr>
        <th style="width: 80px">
            <a class="check-all" href="javascript:;">全选</a>
            |
            <a class="reverse-all" href="javascript:;">反选</a>
        </th>
        <th sort="id">编号</th>
        <th sort="name">名称</th>
        <th sort="weight">权重</th>
        <th>是否显示</th>
        <th style="width: 50px">操作</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${page.content}" var="m" varStatus="status">
        <tr id="${m.id}">
            <td class="check"><input type="checkbox" name="ids" value="${m.id}"></td>
            <td>${m.id}</td>
            <td>${m.name}</td>
            <td>
                 ${m.weight}
                <es:movable page="${page}" first="${status.first}" last="${status.last}"/>
            </td>
            <td>${m.show}</td>
            <td>

                <a class="btn btn-link btn-edit" title="修改" href="${ctx}/showcase/product/category/update/${m.id}">
                    <span class=" icon-edit"></span>
                </a>
                <a class="btn btn-link btn-edit" title="删除" href="${ctx}/showcase/product/category/delete/${m.id}">
                    <span class=" icon-trash"></span>
                </a>
                <a class="btn btn-link btn-edit" title="查看相关产品" href="${ctx}/showcase/product/product/category-${m.id}">
                    <span class=" icon-list"></span>
                </a>
                <a class="btn btn-link btn-edit" title="添加相关产品" href="${ctx}/showcase/product/product/category-${m.id}/create">
                    <span class="icon-file"></span>
                </a>

            </td>
        </tr>
    </c:forEach>
    </tbody>
    <tfoot>
        <tr>
            <td colspan="1000">
                <es:page page="${page}" />
            </td>
        </tr>
    </tfoot>
</table>
