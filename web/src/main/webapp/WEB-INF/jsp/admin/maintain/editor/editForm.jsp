<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jspf"%>
<es:contentHeader/>
<ul class="nav nav-tabs">
    <li ${empty param['editorType'] ? 'class="active"' : ''}>
        <a href="${ctx}/admin/maintain/editor/edit?path=${path}">
            <i class="icon-table"></i>
            纯文本编辑器
        </a>
    </li>
    <li ${param['editorType'] eq 'html' ? 'class="active"' : ''}>
        <a href="${ctx}/admin/maintain/editor/edit?path=${path}&editorType=html">
            <i class="icon-table"></i>
            html编辑器
        </a>
    </li>
    <li>
        <a href="${ctx}/admin/maintain/editor/list?path=${parentPath}">
            <i class="icon-table"></i>
            返回目录
        </a>
    </li>
</ul>

<div data-table="table" class="panel">
    <c:if test="${param['editorType'] eq 'html'}">
        <div class="alert alert-info">
            <strong>注意：</strong>注意：当前编辑器是html编辑器，可能会添加额外的标签造成页面出错，如果是jsp等动态页面，请考虑使用纯文本编辑器。<br/>
        </div>

    </c:if>

    <form action="" method="post">

        <textarea name="content" style="width: 550px;height: 300px;">${content}</textarea>
        <br/>
        <input type="submit" class="btn btn-primary" value="保存">
        <a href="${ctx}/admin/maintain/editor/list?path=${parentPath}" class="btn">返回目录</a>
    </form>
</div>

<es:contentFooter/>
<c:if test="${param['editorType'] eq 'html'}">
    <%@include file="/WEB-INF/jsp/common/import-editor-js.jspf"%>
    <script type="text/javascript">

        $(function() {
            var isLoadingChange = false; //是否是装载时的change 第一次加载
            var editor = KindEditor.create('textarea[name=content]', {
                themeType: 'simple',
                uploadJson: '${ctx}/kindeditor/upload',
                fileManagerJson: '${ctx}/kindeditor/filemanager',
                allowFileManager: true,
                afterBlur: function(){this.sync();},
                afterChange : function() {if(!isLoadingChange) {isLoadingChange = true; return;} contentChange = true;}

            });
        });
    </script>
</c:if>
<script type="text/javascript">
    var contentChange = false;
    $("#content").keypress(function() {
        contentChange = true;
    });


    $(function() {
        $(window).on('beforeunload',function(){
            if(contentChange) {
                return "确认离开当前编辑页面吗？"
            }
       });
    });
</script>