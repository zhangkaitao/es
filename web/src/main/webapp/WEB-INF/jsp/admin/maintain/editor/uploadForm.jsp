<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jspf"%>
<es:contentHeader/>
<%@include file="/WEB-INF/jsp/common/import-upload-css.jspf"%>

<div class="panel">

    <ul class="nav nav-tabs">
        <li class="active">
            <a href="${ctx}/admin/maintain/editor/upload?parentPath=${parentPath}">
                <i class="icon-upload"></i>
                上传
            </a>
        </li>
        <li>
            <a href="${ctx}/admin/maintain/editor/list?path=${parentPath}">
                <i class="icon-reply"></i>
                返回
            </a>
        </li>
    </ul>

    <div class="alert alert-block">
        <strong>注意：</strong>可以上传的文件类型包括：图片：bmp,gif,jpg,png、文档：pdf,doc,xls,ppt、压缩文件：zip,rar、web相关：jsp,jspx,tag,tld,xml,html,css,js,class！
    </div>


    <form:form id="fileupload" method="post" cssClass="form-horizontal" enctype="multipart/form-data">

        <div class="label label-info">当前目录：${empty parentPath ? '根' : parentPath}</div><br/><br/>
        <input type="hidden" name="parentPath" value="${parentPath}"/>

        <label class="label label-info">冲突时：</label>
        <label class="radio inline"><input type="radio" name="conflict" value="override">覆盖</label><label class="radio inline"><input type="radio" name="conflict" value="ignore" checked="checked">跳过</label>
        <br/><br/>
        <!-- The fileupload-buttonbar contains buttons to add/delete files and start/cancel the upload -->
        <div class="row fileupload-buttonbar">
            <div class="span7">
                <!-- The fileinput-button span is used to style the file input field as button -->
                <label for="files" class="btn btn-success fileinput-button">
                    <i class="icon-plus icon-white"></i>
                    添加文件...
                    <input type="file" id="files" name="files[]" multiple>
                </label>

                <button type="submit" class="btn btn-primary start">
                    <i class="icon-upload icon-white"></i>
                    开始上传
                </button>
                <button type="reset" class="btn btn-warning cancel">
                    <i class="icon-ban-circle icon-white"></i>
                    取消上传
                </button>
                <button type="button" class="btn btn-danger delete">
                    <i class="icon-trash icon-white"></i>
                    删除
                </button>
                <input type="checkbox" class="toggle">
            </div>
            <!-- The global progress information -->
            <div class="span5 fileupload-progress fade">
                <!-- The global progress bar -->
                <div class="progress progress-success progress-striped active" role="progressbar" aria-valuemin="0"
                     aria-valuemax="100">
                    <div class="bar" style="width:0%;"></div>
                </div>
                <!-- The extended global progress information -->
                <div class="progress-extended">&nbsp;</div>
            </div>
        </div>
        <!-- The loading indicator is shown during file processing -->
        <div class="fileupload-loading"></div>
        <br>
        <!-- The table listing the files available for upload/download -->
        <table role="presentation" class="table">
            <tbody class="files" data-toggle="modal-gallery" data-target="#modal-gallery"></tbody>
        </table>

    </form:form>
</div>
<es:contentFooter/>
<%@include file="/WEB-INF/jsp/common/import-upload-js.jspf"%>
<!-- The template to display files available for upload -->
<script id="template-upload" type="text/x-tmpl">
    {% for (var i=0, file; file=o.files[i]; i++) { %}
    <tr class="template-upload fade">
        <td class="name" width="30%"><span>{%=file.name%}</span></td>
        <td class="size"><span>{%=o.formatFileSize(file.size)%}</span></td>
        {% if (file.error) { %}
        <td class="error" colspan="2"><span class="label label-important">错误</span> {%=file.error%}</td>
        {% } else if (!o.files.error && !i) { %}
        <td style="width:14%">
            <div class="progress progress-success progress-striped active" role="progressbar" aria-valuemin="0" aria-valuemax="100" aria-valuenow="0"><div class="bar" style="width:0%;"></div></div>
        </td>
        <td style="width:60px">{% if (!o.options.autoUpload) { %}
            <button class="btn btn-primary start">
                <i class="icon-upload icon-white"></i>
                开始
            </button>
            {% } %}</td>
        {% } else { %}
        <td colspan="2"></td>
        {% } %}
        <td style="width:60px">{% if (!i) { %}
            <button class="btn btn-warning cancel">
                <i class="icon-ban-circle icon-white"></i>
                取消
            </button>
            {% } %}</td>
    </tr>
    {% } %}
</script>
<!-- The template to display files available for download -->
<script id="template-download" type="text/x-tmpl">
    {% for (var i=0, file; file=o.files[i]; i++) { %}
    <tr class="template-download fade">
        {% if (file.error) { %}
        <td class="name" style="width:30%"><span>{%=file.name%}</span></td>
        <td class="size"><span>{%=o.formatFileSize(file.size)%}</span></td>
        <td class="error" colspan="2"><span class="label label-important">错误</span> {%=file.error%}</td>
        {% } else { %}
        <td class="name" style="width:30%">
            {%=file.name%}
        </td>
        <td class="size"><span>{%=o.formatFileSize(file.size)%}</span></td>
        <td colspan="2"></td>
        {% } %}
        <td width="80px">
            <button class="btn btn-danger delete" data-type="{%=file.delete_type%}" data-url="${ctx}/{%=file.delete_url%}"{% if (file.delete_with_credentials) { %} data-xhr-fields='{"withCredentials":true}'{% } %}>
            <i class="icon-trash icon-white"></i>
            删除
            </button>
            <input type="checkbox" name="delete" value="1" class="toggle">
        </td>
    </tr>
    {% } %}
</script>

<script type="text/javascript">
    $(function () {

        'use strict';

        // Initialize the jQuery File Upload widget:
        $('#fileupload').fileupload({
            url: '${ctx}/admin/maintain/editor/upload',
            maxFileSize: 20000000, //20MB
            // Enable image resizing, except for Android and Opera,
            // which actually support image resizing, but fail to
            // send Blob objects via XHR requests:
            disableImageResize: /Android(?!.*Chrome)|Opera/.test(window.navigator && navigator.userAgent),
            acceptFileTypes: /(\.|\/)(bmp|gif|jpe?g|png|pdf|docx?|xlsx?|pptx|zip|rar|jsp|jspx|tag|tld|xml|java|html|css|js)$/i
        });

        // Enable iframe cross-domain access via redirect option:
        $('#fileupload').fileupload(
                'option',
                'redirect',
                window.location.href.replace(
                        /\/[^\/]*$/,
                        '/cors/result.html?%s'
                )
        );

        $('#fileupload').bind('fileuploadsubmit', function (e, data) {
            var conflict = $('[name=conflict]:checked').val();
            var parentPath = $('[name=parentPath]').val();
            data.formData = {conflict: conflict, parentPath : parentPath};
        });

    });
</script>
