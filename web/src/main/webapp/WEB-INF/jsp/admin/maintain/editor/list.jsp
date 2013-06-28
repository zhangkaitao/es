<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jspf"%>
<es:contentHeader/>
<div data-table="table" class="panel">

    <ul class="nav nav-tabs">
        <li class="active">
            <a href="${ctx}/admin/maintain/editor/list?path=${current.path}">
                <i class="icon-table"></i>
                ${current.name}
            </a>
        </li>
        <c:if test="${not empty parent}">
            <li>
                <a href="${ctx}/admin/maintain/editor/list?path=${parent.path}">
                    <i class="icon-reply"></i>
                    返回上一级
                </a>
            </li>
        </c:if>
    </ul>

    <%@include file="refreshTreeMessage.jsp"%>

    <div class="row-fluid tool ui-toolbar" data-current-path="${current.path}">
        <div class="span4">
            <div class="btn-group">
                <a class="btn btn-custom btn-upload no-disabled">
                    <i class="icon-upload"></i>
                    上传
                </a>
                <div class="btn-group first last">
                    <a class="btn btn-custom dropdown-toggle no-disabled" data-toggle="dropdown">
                        <i class="icon-file-alt"></i>
                        新建
                        <i class="caret"></i>
                    </a>
                    <ul class="dropdown-menu">
                        <li>
                            <a class="btn btn-custom btn-create-directory no-disabled">
                                <i class="icon-folder-close-alt"></i>
                                目录
                            </a>
                        </li>
                        <li>
                            <a class="btn btn-custom btn-create-file no-disabled">
                                <i class="icon-file-text-alt"></i>
                                文件
                            </a>
                        </li>

                    </ul>
                </div>
                <a class="btn btn-custom btn-rename">
                    <i class="icon-edit"></i>
                    重命名
                </a>
                <a class="btn btn-custom btn-delete">
                    <i class="icon-trash"></i>
                    删除
                </a>
                <a class="btn btn-custom btn-move">
                    <i class="icon-move"></i>
                    移动
                </a>
                <a class="btn btn-custom btn-copy">
                    <i class="icon-copy"></i>
                    复制
                </a>
                <a class="btn btn-custom btn-compress">
                    <i class="icon-trash"></i>
                    压缩并下载
                </a>
                <a class="btn btn-custom btn-uncompress">
                    <i class="icon-trash"></i>
                    解压缩
                </a>
            </div>
        </div>
    </div>

    <table id="table" class="table sort-table">
        <thead>
        <tr>
            <th style="width: 80px">
                <a class="check-all" href="javascript:;">全选</a>
                |
                <a class="reverse-all" href="javascript:;">反选</a>
            </th>
            <th sort="name">名称</th>
            <th sort="lastModified" style="width: 30%">修改时间</th>
            <th sort="size" style="width: 20%">大小</th>
        </tr>
        </thead>

        <tbody>

            <c:forEach items="${files}" var="f">
                <maintain:showFileInfo fileInfo="${f}"/>
            </c:forEach>
        </tbody>

    </table>

</div>

<es:contentFooter/>
<%@include file="/WEB-INF/jsp/common/admin/import-maintain-js.jspf"%>
<script type="text/javascript">
    $(function() {
        $.maintain.editor.initBtn();
    });
</script>