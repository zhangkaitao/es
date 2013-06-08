<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jspf"%>
<es:contentHeader/>
<div data-table="table" class="panel">

    <br/>
    <div class="alert alert-info">
        <strong>注意：</strong>你应该把需要版本化的css/js放到webapp/WEB-INF/jsp/common下，且已jspf为后缀，系统自动扫描这些文件
    </div>

    <es:showMessage/>

    <div class="row-fluid tool ui-toolbar">
        <div class="span4">
            <div class="btn-group">
                <a class="btn btn-custom btn-batch-version">
                    <i class="icon-file"></i>
                    版本+1
                </a>
                <a class="btn btn-custom btn-clear-version">
                    <i class="icon-file"></i>
                    清空版本
                </a>
            </div>
        </div>
    </div>
    <table id="table" class="table table-bordered table-hover">
        <thead>
        <tr>
            <th style="width: 80px">
                <a class="check-all" href="javascript:;">全选</a>
                |
                <a class="reverse-all" href="javascript:;">反选</a>
            </th>
            <th>URL</th>
            <th style="width: 100px">当前版本</th>
            <th style="width: 100px">操作</th>
        </tr>
        </thead>
        <tbody>

        <c:forEach items="${resources}" var="r">
            <c:set var="fileName" value="${r.key}"/>
            <c:set var="resourceList" value="${r.value}"/>
            <tr class="info bold">
               <td colspan="4">文件：${fileName}</td>
            </tr>
            <c:forEach items="${resourceList}" var="s">
                <tr>
                    <td class="check">
                        <input type="checkbox" name="ids">
                        <input type="hidden" name="fileName" value="${fn:escapeXml(fileName)}">
                        <input type="hidden" name="content" value="${fn:escapeXml(s.content)}">
                    </td>
                    <td>${s.url}</td>
                    <td name="version">${s.version}</td>
                    <td><a class="btn btn-version">版本+1</a></td>
                </tr>
            </c:forEach>
        </c:forEach>
        </tbody>
    </table>
</div>

<es:contentFooter/>
<script type="text/javascript">
$(function() {
    $(".btn-version").click(function() {
        $.app.waiting("正在执行...");
        var tr = $(this).closest("tr");
        var fileName = tr.find("[name='fileName']").val();
        var content = tr.find("[name='content']").val();
        var version = tr.find("[name='version']").text();
        if(!version) {
            version = "1";
        } else {
            version = parseInt(version) + 1;
        }

        var url = "${ctx}/admin/maintain/staticResource/incVersion";
        $.post(
                url,
                {
                    fileName: fileName,
                    content : content,
                    newVersion : version
                }
                ,
                function(newContent) {
                    $.app.waitingOver();
                    tr.find("[name='version']").text(version);
                    tr.find("[name='content']").val(newContent);
                    $.app.alert({message : '操作成功'});
                }
        );
        return false;
    });

    $(".btn-batch-version").click(function() {
        var checkboxes = $.table.getAllSelectedCheckbox($("#table"));
        if(!checkboxes.length) {
            return;
        }
        $.app.confirm({
            message : "确认选中的js/css版本+1吗？",
            ok : function() {
                 var fileNames = [];
                var contents = [];
                var versions = [];
                for(var i = 0, l = checkboxes.length; i < l; i++) {
                    var tr = $(checkboxes[i]).closest("tr");

                    var fileName = tr.find("[name='fileName']").val();
                    var content = tr.find("[name='content']").val();
                    var version = tr.find("[name='version']").text();
                    if(!version) {
                        version = "1";
                    } else {
                        version = parseInt(version) + 1;
                    }
                    fileNames[fileNames.length] = fileName;
                    contents[contents.length] = content;
                    versions[versions.length] = version;
                }

                var url = "${ctx}/admin/maintain/staticResource/batchIncVersion";
                $.post(
                        url,
                        {
                            "fileNames[]" : fileNames,
                            "contents[]" : contents,
                            "newVersions[]" : versions
                        },
                        function(data) {
                            $.app.alert({
                                message : data,
                                ok : function() {
                                    window.location.reload();
                                }
                            });

                        }
                );
            }
        });
    });


    $(".btn-clear-version").click(function() {
        var checkboxes = $.table.getAllSelectedCheckbox($("#table"));
        if(!checkboxes.length) {
            return;
        }
        $.app.confirm({
            message : "确认清空选中的js/css版本吗？",
            ok : function() {
                var fileNames = [];
                var contents = [];
                for(var i = 0, l = checkboxes.length; i < l; i++) {
                    var tr = $(checkboxes[i]).closest("tr");
                    var fileName = tr.find("[name='fileName']").val();
                    var content = tr.find("[name='content']").val();

                    fileNames[fileNames.length] = fileName;
                    contents[contents.length] = content;
                }

                var url = "${ctx}/admin/maintain/staticResource/clearVersion";
                $.post(
                        url,
                        {
                            "fileNames[]" : fileNames,
                            "contents[]" : contents,
                        },
                        function(data) {
                            $.app.alert({
                                message : data,
                                ok : function() {
                                    window.location.reload();
                                }
                            });

                        }
                );
            }
        });
    });
});
</script>