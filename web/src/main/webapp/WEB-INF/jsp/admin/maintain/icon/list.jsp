<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jspf"%>
<es:contentHeader/>
<div data-table="table" class="panel">

    <ul class="nav nav-tabs">
        <li ${empty param['search.type_eq'] ? 'class="active"' : ''}>
            <a href="${ctx}/admin/maintain/icon">
                <i class="icon-table"></i>
                所有图标列表
            </a>
        </li>
        <c:forEach items="${types}" var="t">
            <li ${param['search.type_eq'] eq t ? 'class="active"' : ''}>
                <a href="${ctx}/admin/maintain/icon?search.type_eq=${t}">
                    <i class="icon-table"></i>
                    ${t.info}列表
                </a>
            </li>
        </c:forEach>
    </ul>

    <es:showMessage/>

    <div class="row-fluid tool ui-toolbar">
        <div class="span4">
            <div class="btn-group">
                <shiro:hasPermission name="maintain:icon:create">
                <div class="btn-group first">
                    <a class="btn btn-custom dropdown-toggle no-disabled" data-toggle="dropdown">
                        <i class="icon-file-alt"></i>
                        新&nbsp;增
                        <i class="caret"></i>
                    </a>
                    <ul class="dropdown-menu">
                        <c:forEach items="${types}" var="t">
                            <li>
                                <a class="btn no-disabled" href="${ctx}/admin/maintain/icon/${t}/create">
                                    <i class="icon-file-alt"></i>
                                    ${t.info}
                                </a>
                            </li>
                        </c:forEach>

                    </ul>
                </div>
                </shiro:hasPermission>
                <shiro:hasPermission name="maintain:icon:update">
                <a id="update" class="btn btn-update">
                    <i class="icon-edit"></i>
                    修改
                </a>
                </shiro:hasPermission>
                <shiro:hasPermission name="maintain:icon:delete">
                <a class="btn btn-delete">
                    <i class="icon-trash"></i>
                    删除
                </a>
                </shiro:hasPermission>
                <shiro:hasPermission name="maintain:icon:create or maintain:icon:update or maintain:icon:delete">
                <a class="btn no-disabled btn-gen-css-file"
                   data-toggle="tooltip" data-placement="bottom" data-html="true"
                   title="生成相应的样式表文件(icon.css)<br/>标识符作为css class使用">
                    <i class="icon-asterisk"></i>
                    生成样式表
                </a>
                </shiro:hasPermission>
            </div>
        </div>
        <div class="span8">
            <%@include file="searchForm.jsp"%>
        </div>
    </div>
    <%@include file="listTable.jsp"%>

</div>

<es:contentFooter/>
<script type="text/javascript">
    $(".btn-gen-css-file").click(function() {
        $.app.waiting("正在生成css图标文件");
        $.get("${ctx}/admin/maintain/icon/genCssFile", function(data) {
            $.app.waitingOver();
            $.app.alert({
                message : data
            });
        });
    });
</script>