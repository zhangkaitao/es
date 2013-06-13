<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jspf"%>
<es:contentHeader/>
<div data-table="table" class="panel">


    <ul class="nav nav-tabs">
        <li ${empty param['search.show_eq'] ? 'class="active"' : ''}>
            <a href="${ctx}/showcase/product/category">
                <i class="icon-table"></i>
                所有类别列表
            </a>
        </li>
        <li ${param['search.show_eq'] eq 'true' ? 'class="active"' : ''}>
            <a href="${ctx}/showcase/product/category?search.show_eq=true">
                <i class="icon-table"></i>
                可显示的类别列表
            </a>
        </li>
        <li ${param['search.show_eq'] eq 'false' ? 'class="active"' : ''}>
            <a href="${ctx}/showcase/product/category?search.show_eq=false">
                <i class="icon-table"></i>
                隐藏的类别列表
            </a>
        </li>
    </ul>


    <es:showMessage/>

    <div class="row-fluid tool ui-toolbar">
        <div class="span6">
            <div class="btn-group">
                <shiro:hasPermission name="showcase:productCategory:create">
                <a class="btn btn-create">
                    <span class="icon-file-alt"></span>
                    新&nbsp;增
                </a>
                </shiro:hasPermission>

                <shiro:hasPermission name="showcase:productCategory:update">
                <a id="update" class="btn btn-update">
                    <span class="icon-edit"></span>
                    修改
                </a>
                </shiro:hasPermission>
                <shiro:hasPermission name="showcase:productCategory:delete">
                <a class="btn btn-delete">
                    <span class="icon-trash"></span>
                    删除
                </a>
                </shiro:hasPermission>
                <div class="btn-group last">
                    <a class="btn no-disabled dropdown-toggle" data-toggle="dropdown" href="#">
                        <i class="icon-wrench"></i>
                        更多操作
                        <span class="caret"></span>
                    </a>
                    <ul class="dropdown-menu">
                        <shiro:hasPermission name="showcase:productCategory:create or showcase:productCategory:update or showcase:productCategory:delete">
                        <li>
                            <a class="btn no-disabled btn-link reweight">
                                <i class="icon-cog"></i>
                                优化权重
                            </a>
                        </li>
                        </shiro:hasPermission>
                        <shiro:hasPermission name="showcase:product:create">
                        <li class="divider"></li>
                        <li>
                            <a class="btn no-disabled btn-link list-all-product">
                                <i class="icon-table"></i>
                                查看所有产品
                            </a>
                        </li>
                        <li>
                            <a class="btn btn-link create-product">
                                <i class="icon-file-alt"></i>
                                新增选中类别的产品
                            </a>
                        </li>
                        </shiro:hasPermission>
                        <li class="divider"></li>
                        <li>
                            <a class="btn btn-link list-product">
                                <i class="icon-table"></i>
                                查看选中类别的产品
                            </a>
                        </li>

                    </ul>
                </div>
            </div>
        </div>
        <div class="span6">
            <%@include file="searchForm.jsp"%>
        </div>
    </div>

    <%@include file="listTable.jsp"%>

</div>
<es:contentFooter/>

<script type="text/javascript">
    $(function() {

        $.movable.initMovableReweight($(".reweight"), "${ctx}/showcase/product/category/reweight");

        $(".list-all-product").click(function() {
            window.location.href=
                    "${ctx}/showcase/product/product?fromCategory=1&BackURL=" + $.table.tableURL($(".table"));
        });

        $(".create-product").click(function() {

            var checkbox = $.table.getFirstSelectedCheckbox($(".table"));
            if(checkbox.size() == 0) {
                return;
            }
            var id = checkbox.val();
            window.location.href=
                    "${ctx}/showcase/product/product/category-"+ id + "/create" +
                            "?BackURL=" + $.table.tableURL($(".table"));
        });

        $(".list-product").click(function() {

            var checkbox = $.table.getAllSelectedCheckbox($(".table"));
            if(checkbox.size() == 0) {
                return;
            }
            var ids = $.app.joinVar(checkbox);
            window.location.href=
                    "${ctx}/showcase/product/product?search.category.id_in=" + ids +
                            "&fromCategory=1&BackURL=" + $.table.tableURL($(".table"));
        });

    });
</script>
