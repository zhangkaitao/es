<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jspf"%>
<es:contentHeader/>
<%@include file="/WEB-INF/jsp/common/import-upload-css.jspf"%>
<div>

    <ul class="nav nav-tabs">
        <li class="active">
            <a>
                <i class="icon-file-alt"></i>
                ${op}
            </a>
        </li>
        <li>
            <a href="<es:BackURL/>" class="btn btn-link">
                <i class="icon-reply"></i>
                返回
            </a>
        </li>
    </ul>

    <form:form id="editForm" method="post" commandName="upload" cssClass="form-horizontal" enctype="multipart/form-data">


            <es:showGlobalError commandName="upload"/>

            <form:hidden path="id"/>

            <div class="control-group">
                <form:label path="name" cssClass="control-label">名称</form:label>
                <div class="controls">
                    <form:input path="name" cssClass="validate[required,maxSize[10]]" placeholder="最多10个字符"/>
                </div>
            </div>

            <div class="control-group" style="margin-bottom: 0px;<c:if test="${empty upload.src}">display: none</c:if>">
                <label for="files" class="control-label"></label>
                <div class="controls">
                    <div class="ajax-upload-view"></div>
                    <form:hidden path="src"/>
                </div>
            </div>

            <div class="control-group">
                <label for="files" class="control-label">文件</label>
                <div class="controls">
                   <label for="files" class="btn btn-success fileinput-button">
                        <i class="icon-plus icon-white"></i>
                        <span>添加文件...</span>
                       <input type="file" id="files" name="files[]" data-url="${ctx}/ajaxUpload" multiple>
                   </label>

                </div>
            </div>

            <div class="control-group">
                <div class="controls">
                    <button type="submit" class="btn btn-primary">
                        <i class="icon-file-alt"></i>
                        ${op}
                    </button>
                    <a href="<es:BackURL/>" class="btn">
                        <i class="icon-reply"></i>
                        返回
                    </a>
                </div>
            </div>
    </form:form>
</div>
<es:contentFooter/>
<%@include file="/WEB-INF/jsp/common/import-upload-simple-js.jspf"%>

<script type="text/javascript">
    $(function () {
        $('.fileinput-button input[type="file"]').fileupload({
            dataType : "json"
        });
        $('.fileinput-button input[type="file"]').fileupload("option", {
            progressall: function (e, data) {
                var view = $(".ajax-upload-view");
                view.parent().parent().show();
                var progressBar = view.find(".progress");
                if(progressBar.size() == 0) {
                    var progressBarTemplate =
                            '<div class="progress progress-striped">' +
                                    '<div class="bar"></div>' +
                                    '</div>';
                    progressBar = view.append(progressBarTemplate);
                }
                var progress = parseInt(data.loaded / data.total * 100, 10);
                progressBar.find(".bar").css("width", progress + "%");
            },
            start : function(e) {
                $(".ajax-upload-view").html("");
                var submitBtn = $(this).closest("form").find(":submit");
                submitBtn.data("value", submitBtn.val()).val("上传文件中...").prop("disabled", true);
            },
            //上传完成
            done: function (e, data) {
                $.each(data.result.files, function (index, file) {
                    if (file.error) {
                        $(".ajax-upload-view").html("<div class='alert alert-error'>" + file.error + "</div>");
                    } else {
                        $("[name=src]").val(file.url);
                        var msg = "<div class='alert alert-success'><strong>上传成功！</strong><br/>{preview}</div>";
                        var preview = "";
                        var url = ctx + "/" + file.url;
                        var thumbnail_url = ctx + "/" + file.thumbnail_url;
                        if($.app.isImage(file.name)) {
                            preview = "<a href='{url}' target='_blank'><img src='{thumbnail_url}' title='{name}' height='120px'/></a>"
                        } else {
                            preview = "<a href='{url}' target='_blank'>{name}</a>"
                        }
                        preview = preview.replace("{url}", url).replace("{thumbnail_url}", thumbnail_url).replace("{name}", file.name);
                        msg = msg.replace("{preview}", preview);
                        $(".ajax-upload-view").html(msg);

                    }
                });
                var submitBtn = $(this).closest("form").find(":submit");
                submitBtn.val(submitBtn.data("value")).prop("disabled", false);
            }
        });
        var validationEngine = $("#editForm").validationEngine();
        <es:showFieldError commandName="upload"/>
    });
</script>