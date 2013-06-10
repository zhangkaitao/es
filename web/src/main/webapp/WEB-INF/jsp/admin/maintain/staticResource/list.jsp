<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jspf"%>
<es:contentHeader/>
<div data-table="table" class="panel">

    <div class="alert alert-info">
        <strong>注意：</strong>你应该把需要版本化的css/js放到webapp/WEB-INF/jsp/common下，且已jspf为后缀，系统自动扫描这些文件<br/>
        <strong>yui 压缩命令：</strong>java -jar D:\yuicompressor-2.4.2.jar  application.js   --charset utf-8  -o min.js
    </div>

    <es:showMessage/>

    <div class="row-fluid tool ui-toolbar">
        <div class="span4">
            <div class="btn-group">
                <a class="btn btn-custom btn-batch-version">
                    <i class="icon-plus"></i>
                    版本+1
                </a>
                <a class="btn btn-custom btn-clear-version">
                    <i class="icon-eraser"></i>
                    清空版本
                </a>
                <a class="btn btn-custom btn-batch-compress">
                    <i class="icon-magic"></i>
                    压缩js/css
                </a>
                <div class="btn-group last">
                    <a class="btn dropdown-toggle" data-toggle="dropdown" href="#">
                        <i class="icon-retweet"></i>
                        切换版本
                        <span class="caret"></span>
                    </a>
                    <ul class="dropdown-menu">
                        <li>
                            <a class="btn btn-custom btn-batch-switch min">
                                <i class="icon-retweet"></i>
                                切换到压缩版
                            </a>
                        </li>
                        <li>
                            <a class="btn btn-custom btn-batch-switch">
                                <i class="icon-retweet"></i>
                                切换到开发版
                            </a>
                        </li>
                    </ul>
                </div>

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
            <th style="width: 80px">当前版本</th>
            <th style="width: 200px">操作</th>
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
                    <td name="url">${s.url}</td>
                    <td name="version">${s.version}</td>
                    <td>
                        <a class="btn btn-version">版本+1</a>
                        <a class="btn btn-compress">压缩</a>
                        <div class="btn-group">
                            <a class="btn dropdown-toggle" data-toggle="dropdown" href="#">
                                切换版本
                                <span class="caret"></span>
                            </a>
                            <ul class="dropdown-menu pull-right">
                                <li>
                                    <a class="btn btn-custom btn-switch min">
                                        切换到压缩版
                                    </a>
                                </li>
                                <li>
                                    <a class="btn btn-custom btn-switch">
                                        切换到开发版
                                    </a>
                                </li>
                            </ul>
                        </div>
                    </td>

                </tr>
            </c:forEach>
        </c:forEach>
        </tbody>
    </table>
</div>

<es:contentFooter/>
<%@include file="/WEB-INF/jsp/common/admin/import-maintain-js.jspf"%>
<script type="text/javascript">
$(function() {
    $.maintain.staticResource.initBtn();
});
</script>