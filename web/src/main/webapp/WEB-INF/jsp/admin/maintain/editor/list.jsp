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

    <div class="row-fluid tool ui-toolbar">
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
<script type="text/javascript">
    $(function() {
        $(".btn-rename").click(function() {
            var checkbox = $.table.getFirstSelectedCheckbox($("#table"));
            if(!checkbox.length) {
                return;
            }

            var path = checkbox.val();
            var oldName = checkbox.siblings("[name='names']").val();

            $.app.confirm({
                title : "重命名",
                message: "请输入新的名字：<input type='text' id='newName' value='" + oldName + "'/>",
                ok : function() {
                    var newName = $("#newName").val();
                    location.href = ctx + '/admin/maintain/editor/rename?path=' + path + "&newName=" + newName;
                }
            });
        });

        $(".btn-delete").click(function() {
            var checkbox = $.table.getAllSelectedCheckbox($("#table"));
            if(!checkbox.length) {
                return;
            }

            $.app.confirm({
                title : "确认删除选中的文件",
                message: "<strong class='text-error'>注意：</strong><span class='text-error muted'>如果选择的是目录将会删除子目录！并且此操作不可恢复，执行请慎重！</span><br/><br/><span class='text-error'>请输入 <strong>ok</strong> 进行删除：</span><input type='text' id='confirmText' class='input-small'>",
                ok : function() {
                    var confirmText = $("#confirmText").val();
                    if(confirmText != 'ok') {
                        $.app.alert({message: "请输入<strong>ok</strong>确认执行操作！"});
                        return;
                    }
                    location.href = ctx + '/admin/maintain/editor/delete?' + checkbox.serialize();
                }
            });
        });

        $(".btn-create-directory").click(function() {
            $.app.confirm({
                title : "新建目录",
                message: "请输入目录名字：<input type='text' id='name' placeholder='支持如/a/b/c多级目录'/>",
                ok : function() {
                    var name = $("#name").val();
                    if($.trim(name)) {
                        $.app.alert({
                            message : "名称不能为空"
                        });
                    }
                    location.href = ctx + '/admin/maintain/editor/create/directory?parentPath=${current.path}' + "&name=" + name;
                }
            });
        });


        $(".btn-create-file").click(function() {
            $.app.confirm({
                title : "新建文件",
                message: "请输入文件名字：<input type='text' id='name' placeholder='支持如/a/b.txt多级目录'/>",
                ok : function() {
                    var name = $("#name").val();
                    if($.trim(name)) {
                        $.app.alert({
                            message : "名称不能为空"
                        });
                    }
                    location.href = ctx + '/admin/maintain/editor/create/file?parentPath=${current.path}' + "&name=" + name;
                }
            });
        });

        $(".btn-upload").click(function() {
            location.href = ctx + '/admin/maintain/editor/upload?parentPath=${current.path}';
        });

        $(".btn-compress").click(function() {
            var checkbox = $.table.getAllSelectedCheckbox($("#table"));
            if(!checkbox.length) {
                return;
            }
            $.app.confirm({
                title : "确认压缩并下载选中的文件",
                message: "<strong class='text-error'>注意：</strong><span class='text-error muted'>如果选择的文件比较大，速度可能比较慢！</span><br/><br/><span class='text-error'>请输入 <strong>ok</strong> 进行压缩并下载：</span><input type='text' id='confirmText' class='input-small'>",
                ok : function() {
                    var confirmText = $("#confirmText").val();
                    if(confirmText != 'ok') {
                        $.app.alert({message: "请输入<strong>ok</strong>确认执行操作！"});
                        return;
                    }
                    window.location.href = ctx + "/admin/maintain/editor/compress?parentPath=${current.path}&" + checkbox.serialize();
                }
            });

        });

        $(".btn-uncompress").click(function() {
            var checkbox = $.table.getAllSelectedCheckbox($("#table"));
            if(!checkbox.length) {
                return;
            }
            var canUncompress = true;
            $(checkbox).each(function() {
                if(decodeURIComponent($(this).val()).toLowerCase().indexOf(".zip") == -1) {
                    canUncompress = false;
                }
            });
            if(!canUncompress) {
                $.app.alert({
                    message : "目前只支持zip文件的解压缩，请只选择zip文件"
                });
                return;
            }

            $.app.confirm({
                title : "确认解压选中的文件",
                message: "<span class='text-error muted'>冲突时：<label class='radio inline'><input type='radio' name='conflict' value='override'>覆盖</label><label class='radio inline'><input type='radio' name='conflict' value='ignore' checked='checked'>跳过</label><br/><br/><span class='text-error'>请输入 <strong>ok</strong> 进行解压：</span><input type='text' id='confirmText' class='input-small'>",
                ok : function() {
                    var conflict = $("[name=conflict]:checked").val();
                    var confirmText = $("#confirmText").val();
                    if(confirmText != 'ok') {
                        $.app.alert({message: "请输入<strong>ok</strong>确认执行操作！"});
                        return;
                    }
                    window.location.href = ctx + "/admin/maintain/editor/uncompress?parentPath=${current.path}&conflict=" + conflict + "&" + checkbox.serialize();
                }
            });

        });
    });
</script>